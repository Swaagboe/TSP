package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import com.sun.media.jai.opimage.NeuQuantOpImage;

public class HelpMethods {
	
	//generates 'numberOfNeurons' random neurons
	public static ArrayList<Neuron> generateRandomNauronList(int numberOfNaurons, double[] minMaxLatLong){
		Random r = new Random();
		double randomLatitude;
		double randomLongitude;
		int id = 1;
		ArrayList<Neuron> nauronList = new ArrayList<Neuron>();
		for (int i = 0; i < numberOfNaurons; i++) {
			randomLatitude = minMaxLatLong[0] + (minMaxLatLong[1] - minMaxLatLong[0]) * r.nextDouble();
			randomLongitude = minMaxLatLong[2] + (minMaxLatLong[3] - minMaxLatLong[2]) * r.nextDouble();
			Neuron n = new Neuron(id, randomLatitude, randomLongitude);
			nauronList.add(n);
			id++;
		}	
		return nauronList;
	}
	
	public static void initializeDistanceToNeuronsList(ArrayList<City> cityList, ArrayList<Neuron> neuronList){
		for (City city : cityList) {
			for (Neuron neuron : neuronList) {
				double distance = calcuLateDistanceBetweenCityAndNauron(city, neuron);				
			}
		}
	}
	
	//generates the neighbours for the different Naurons
	public static void generateNeigboursForNaurons(ArrayList<Neuron> nauronList){	
		for (int i = 0; i < nauronList.size(); i++) {
			ArrayList<Neuron> neighbours = new ArrayList<Neuron>();
			Neuron n = nauronList.get(i);
			//50 known neighbours available for each Nauron
			int forward = i+1;
			int backward = i-1;
			for (int j = 0; j < 50; j++) {
				if (j%2 == 0){
					if (forward < nauronList.size()){
						neighbours.add(nauronList.get(forward));
					}
					else{
						neighbours.add(nauronList.get(forward-nauronList.size()));
					}	
					forward++;
				}
				else{
					if (backward >= 0){
						neighbours.add(nauronList.get(backward));
					}
					else{
						neighbours.add(nauronList.get(nauronList.size()+backward));
					}
					backward--;				
				}
			}
			n.setNeighbours(neighbours);
			/*System.out.println("NEURON : "+n.getId());
			System.out.println("Neighbours: ");
			for (int j = 0; j < 50; j++) {
				System.out.print(neighbours.get(j).getId()+", ");
			}
			System.out.println();*/
		}
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
	public static double calcuLateDistanceBetweenCityAndNauron(City city, Neuron neuron){
		double latDifference = Math.abs(city.getLatitude()-neuron.getLatitude());
		double longDifference = Math.abs(city.getLongitude()-neuron.getLongitude());
		return Math.sqrt(Math.pow(latDifference, 2)+Math.pow(longDifference, 2));
	}
	
	//updates the distance HashMap of distances to naurons for all the cities
	public static void updateDistanceToNauronsForCities(ArrayList<City> cityList, ArrayList<Neuron> nauronList){
		
	}
	
	public static void drawCities(){
		
	}
	
	public static void drawNaurons(){
		
	}
	
	public static void totalDist(){
		
	}
	
	

}
