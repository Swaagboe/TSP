package tsp;

import java.util.ArrayList;
import java.util.LinkedList;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

public class TSP {
	
	//[lat][long]
	private final ArrayList<City> cityList;
	private final ArrayList<Neuron> nauronList;
	private double[] minMaxLatLong;
	private double learningRate;
	
	
	
	public TSP(String map, int numberOfNaurons, double learningRate) throws Exception{
		this.learningRate = learningRate;
		this.cityList = HelpMethods.generateCities(map);
		this.minMaxLatLong = HelpMethods.findMinMaxLatLong(cityList);
		this.nauronList = HelpMethods.generateRandomNauronList(numberOfNaurons, minMaxLatLong);
		HelpMethods.generateNeigboursForNaurons(nauronList);
		HelpMethods.initializeDistanceToNeuronsList(cityList, nauronList);
		run();
	}
	
	public void run() throws Exception{
		int iterations = 0;
		while(iterations<10000){
			City city = HelpMethods.pickRandomCity(cityList);
			Neuron nearestNeuron = HelpMethods.findNearestNeuron(city, false);
			double latDifference = city.getLatitude()-nearestNeuron.getLatitude();
			double longDifference = city.getLongitude()-nearestNeuron.getLongitude();
			HelpMethods.updateLatLongForNeuron(nearestNeuron, latDifference, longDifference, learningRate);
			double newDistance = HelpMethods.calcuLateDistanceBetweenCityAndNauron(city, nearestNeuron);
			city.updateDistanceToNeuronList(nearestNeuron, newDistance);
			
			int neighbourRadius = 4;
			ArrayList<Neuron> neighbours = nearestNeuron.getNeighbours();
			double rate = learningRate;
			for (int i = 0; i < neighbourRadius; i++) {
				Neuron neigbourNeuron = neighbours.get(i);
				latDifference = city.getLatitude()-neigbourNeuron.getLatitude();
				longDifference = city.getLongitude()-neigbourNeuron.getLongitude();
				HelpMethods.updateLatLongForNeuron(nearestNeuron, latDifference, longDifference, rate/2);
				rate = rate/2;
				newDistance = HelpMethods.calcuLateDistanceBetweenCityAndNauron(city, neigbourNeuron);
				city.updateDistanceToNeuronList(neigbourNeuron, newDistance);
			}
			iterations++;		
		}
	}
	
	public static void main(String[] args) throws Exception {
		int numberOfNaurons = 100;
		double learningRate = 0.2;
		new TSP("Westers_Sahara.txt", numberOfNaurons, learningRate);
//		new TSP("Qatar.txt", numberOfNaurons, learningRate);
//		new TSP("Uruguay.txt", numberOfNaurons, learningRate);
//		new TSP("Djibouti.txt", numberOfNaurons, learningRate);
		
	}
	
	public void printCityList(ArrayList<City> cityList){
		for (City cit : cityList) {
			System.out.println("Id: " + cit.getId()+" Lat: "+cit.getLatitude() + " Long: "+cit.getLongitude());
		}
	}
	public void printNauronList(LinkedList<Neuron> nauronList){
		for (Neuron nauron : nauronList) {
			System.out.println("Id: " + nauron.getId()+" Lat: "+nauron.getLatitude() + " Long: "+nauron.getLongitude());
		}
	}

}