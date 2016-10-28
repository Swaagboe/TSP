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
	private int activeNeighbours;
	
	
	
	public TSP(String map, int numberOfNaurons, double learningRate, int activeNeighbours) throws Exception{
		this.learningRate = learningRate;
		this.activeNeighbours = activeNeighbours;
		this.cityList = HelpMethods.generateCities(map);
		this.minMaxLatLong = HelpMethods.findMinMaxLatLong(cityList);
		this.nauronList = HelpMethods.generateRandomNauronList(numberOfNaurons, minMaxLatLong);
		HelpMethods.showCurrentMap(nauronList, cityList);
		HelpMethods.generateNeigboursForNaurons(nauronList);
		HelpMethods.initializeDistanceToNeuronsList(cityList, nauronList);

		run();
	}
	
	public void run() throws Exception{
		int iterations = 0;

		while(iterations<30000){
			City city = HelpMethods.pickRandomCity(cityList);//velger random city
			Neuron nearestNeuron = HelpMethods.findNearestNeuron(city, false);//finner nearmest city
			HelpMethods.updateLatLongForNeuronAndNeighbours(cityList, nearestNeuron, city, learningRate, activeNeighbours);//flytter nauron nearmere by og oppdaterer alle cities med den nye distansen
			iterations++;	
		}
		HelpMethods.showCurrentMap(nauronList, cityList);
		ArrayList<Neuron> finalNeuronList = HelpMethods.findFinalNeurons(cityList, nauronList);
		HelpMethods.showCurrentMap(finalNeuronList, cityList);	
	}
	
	public static void main(String[] args) throws Exception {
		int numberOfNaurons = 600;
		double learningRate = 0.5;
		int activeNeighbours = 4;
		new TSP("Qatar.txt", numberOfNaurons, learningRate, activeNeighbours);
//		new TSP("Qatar.txt", numberOfNaurons, learningRate);
//		new TSP("Djibouti.txt", numberOfNaurons, learningRate, activeNeighbours);
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