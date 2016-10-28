package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class HelpMethods {
	
	//generates 'numberOfNaurons' random naurons
	public static LinkedList<Nauron> generateRandomNauronList(int numberOfNaurons, double[] minMaxLatLong){
		Random r = new Random();
		double randomLatitude;
		double randomLongitude;
		int id = 1;
		LinkedList<Nauron> nauronList = new LinkedList<Nauron>();
		for (int i = 0; i < numberOfNaurons; i++) {
			randomLatitude = minMaxLatLong[0] + (minMaxLatLong[1] - minMaxLatLong[0]) * r.nextDouble();
			randomLongitude = minMaxLatLong[2] + (minMaxLatLong[3] - minMaxLatLong[2]) * r.nextDouble();
			Nauron n = new Nauron(id, randomLatitude, randomLongitude);
			nauronList.add(n);
			id++;
		}
		return nauronList;
	}
	
	//generates the neighbours for the different Naurons
	public static void generateNeigboursForNaurons(ArrayList<Nauron> nauronList){
		
	}
	
	//generates cityList from txt-file
	public static ArrayList<City> generateCities(String file){
		ArrayList<City> cityList;
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));	
			StringBuilder sb = new StringBuilder();
			String line = "";
			String[] list = {};
			cityList = new ArrayList<City>();
			while (line != null) {
				line = br.readLine();
				if (line == null){
					break;
				}
				list = line.split(" ");
				cityList.add(new City(Integer.parseInt(list[0]), 
						Double.parseDouble(list[1]), Double.parseDouble(list[2])));
			}
			return cityList;
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}
	
	//returns list like this [minLat, maxLat, minLong, maxLong]
	public static double[] findMinMaxLatLong(ArrayList<City> cityList){
		double[] minMaxList = new double[4];
		double INF = Double.MAX_VALUE;
		minMaxList[0] = INF;
		minMaxList[1] = -INF;
		minMaxList[2] = INF;
		minMaxList[3] = -INF;	
		for (City city : cityList) {
			if (city.getLatitude() < minMaxList[0]){
				minMaxList[0] = city.getLatitude();
			}
			if (city.getLatitude() > minMaxList[1]){
				minMaxList[1] = city.getLatitude();
			}
			if (city.getLongitude() < minMaxList[2]){
				minMaxList[2] = city.getLongitude();
			}
			if (city.getLongitude() > minMaxList[3]){
				minMaxList[3] = city.getLongitude();
			}
		}
		return minMaxList;
	}
	
	//returns distance between city and nauron
	public static double calcuLateDistanceBetweenCityAndNauron(City city, Nauron nauron){
		return 0;
	}
	
	//updates the distance HashMap of distances to naurons for all the cities
	public static void updateDistanceToNauronsForCities(ArrayList<City> cityList, ArrayList<Nauron> nauronList){
		
	}
	
	public static void drawCities(){
		
	}
	
	public static void drawNaurons(){
		
	}
	
	public static void totalDist(){
		
	}
	
	

}
