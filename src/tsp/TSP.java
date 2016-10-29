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
		HelpMethods.generateNeigboursForNaurons(nauronList);
		HelpMethods.initializeDistanceToNeuronsList(cityList, nauronList);
		run();
	}
	
	public void run() throws Exception{
		int iterations = 0;
		for (Neuron n : nauronList) {
			System.out.println("Id: " + n.getId() + " Lat: "+n.getLatitude() + " Long: "+n.getLongitude());
		}
		System.out.println(nauronList);
		HelpMethods.showCurrentMap(nauronList, cityList);
		while(iterations<30000){
			if (activeNeighbours > 4 && iterations%300 == 0){
				activeNeighbours -= 4;
				HelpMethods.showCurrentMap(nauronList, cityList);
			}
			City city = HelpMethods.pickRandomCity(cityList);//velger random city
			Neuron nearestNeuron = HelpMethods.findNearestNeuron(city, false);//finner nearmest city
			HelpMethods.updateLatLongForNeuronAndNeighbours(cityList, nearestNeuron, city, learningRate, activeNeighbours);//flytter nauron nearmere by og oppdaterer alle cities med den nye distansen
			iterations++;	
//			if(iterations%5000 == 0){
//				HelpMethods.showCurrentMap(nauronList, cityList);				
//			}
		}
		HelpMethods.showCurrentMap(nauronList, cityList);
		ArrayList<Neuron> finalNeuronList = HelpMethods.findFinalNeurons(cityList, nauronList);
		HelpMethods.showCurrentMap(finalNeuronList, cityList);		
	}
	
	public static void main(String[] args) throws Exception {
		int numberOfNaurons = 800;
		double learningRate = 0.8;
		int activeNeighbours = 100;
		new TSP("Qatar.txt", numberOfNaurons, learningRate, activeNeighbours);
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