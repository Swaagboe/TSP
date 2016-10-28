package tsp;

import java.util.ArrayList;
import java.util.LinkedList;

public class TSP {
	
	//[lat][long]
	private final ArrayList<City> cityList;
	private final ArrayList<Neuron> nauronList;
	private double[] minMaxLatLong;
	
	
	
	public TSP(String map, int numberOfNaurons){
		this.cityList = HelpMethods.generateCities(map);
		this.minMaxLatLong = HelpMethods.findMinMaxLatLong(cityList);
		this.nauronList = HelpMethods.generateRandomNauronList(numberOfNaurons, minMaxLatLong);
		HelpMethods.generateNeigboursForNaurons(nauronList);
		System.out.println(nauronList.get(0).getNeighbours());
	}
	
	public static void main(String[] args) {
		int numberOfNaurons = 100;
		new TSP("Qatar.txt", numberOfNaurons);
		
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