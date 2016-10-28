package tsp;

import java.util.ArrayList;
import java.util.LinkedList;

public class TSP {
	
	//[lat][long]
	private ArrayList<City> cityList;
	private LinkedList<Nauron> nauronList;
	private double[] minMaxLatLong;
	
	
	
	public TSP(String map, int numberOfNaurons){
		this.cityList = HelpMethods.generateCities(map);
		this.minMaxLatLong = HelpMethods.findMinMaxLatLong(cityList);
		this.nauronList = HelpMethods.generateRandomNauronList(numberOfNaurons, minMaxLatLong);
	}
	
	public static void main(String[] args) {
		new TSP("Qatar.txt", 100);
		
	}
	
	public void printCityList(ArrayList<City> cityList){
		for (City cit : cityList) {
			System.out.println("Id: " + cit.getId()+" Lat: "+cit.getLatitude() + " Long: "+cit.getLongitude());
		}
	}
	public void printNauronList(LinkedList<Nauron> nauronList){
		for (Nauron nauron : nauronList) {
			System.out.println("Id: " + nauron.getId()+" Lat: "+nauron.getLatitude() + " Long: "+nauron.getLongitude());
		}
	}

}
