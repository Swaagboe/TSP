package tsp;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

import com.sun.media.jai.opimage.NeuQuantOpImage;

public class HelpMethods {

	//generates 'numberOfNeurons' random neurons
	public static ArrayList<Neuron> generateRandomNauronList(int numberOfNaurons, double[] minMaxLatLong){
		Random r = new Random();
		double randomLatitude;
		double randomLongitude;
		double latCenter = (minMaxLatLong[0]+minMaxLatLong[1])/2;
		double longCenter = (minMaxLatLong[2]+minMaxLatLong[3])/2;
		double minLat = latCenter;
		double minLong = longCenter;
		int id = 1;
		ArrayList<Neuron> nauronList = new ArrayList<Neuron>();
		for (int i = 0; i < numberOfNaurons; i++) {
//			randomLatitude = minMaxLatLong[0] + (minMaxLatLong[1] - minMaxLatLong[0]) * r.nextDouble();
//			randomLongitude = minMaxLatLong[2] + (minMaxLatLong[3] - minMaxLatLong[2]) * r.nextDouble();
			randomLatitude = minLat+2300;
			randomLongitude = minLong+300;
//			Neuron n = new Neuron(id, randomLatitude, randomLongitude);
			Neuron n = new Neuron(id, latCenter, longCenter);
			nauronList.add(n);
			id++;
		}	
		return nauronList;
	}

	public static void initializeDistanceToNeuronsList(ArrayList<City> cityList, ArrayList<Neuron> neuronList){
		for (City city : cityList) {
			for (Neuron neuron : neuronList) {
				city.updateDistanceToNeuronList(neuron);
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
			for (int j = 0; j < nauronList.size(); j++) {
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
	
	public static double calcuLateDistanceBetweenNeurons(Neuron city, Neuron neuron){
		double latDifference = Math.abs(city.isClosest.getLatitude()-neuron.isClosest.getLatitude());
		double longDifference = Math.abs(city.isClosest.getLongitude()-neuron.isClosest.getLongitude());
		return Math.sqrt(Math.pow(latDifference, 2)+Math.pow(longDifference, 2));
	}


	public static void updateLatLongForNeuronAndNeighbours(ArrayList<City> cityList, 
			Neuron neuron, City city, double learningRate, int activeNeighbours, double discountRate){
		
		neuron.updateLatLong(city, learningRate, cityList);
		ArrayList<Neuron> neighbours = neuron.getNeighbours();
		int x = 0;
		for (int i = 0; i < activeNeighbours; i++) {
			neighbours.get(i).updateLatLong(city, learningRate*discountRate, cityList);
			if (x%2 == 1){
				learningRate = learningRate*discountRate;
			}
			x++;

		}
		
	}
		
	
	public static City pickRandomCity(ArrayList<City> cityList){	
		int randomCityID = 1 + (int)(Math.random() * cityList.size());
		return cityList.get(randomCityID-1);
	}
	
	
	//finds the neurons closest to the city
	public static Neuron findNearestNeuron(City city, boolean findFinal){
		HashMap<Neuron, Double> distanceToNeuron = city.getDistanceToNauronsList();
		Iterator it = distanceToNeuron.entrySet().iterator();
		double minDistance = Double.MAX_VALUE;
		Neuron bestNeuron = null;
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Neuron n = (Neuron) pair.getKey();
	        if(!findFinal){
		        if ((Double)pair.getValue() < minDistance){
		        	minDistance = (Double)pair.getValue();
		        	bestNeuron = (Neuron) pair.getKey();
		        }
	        }
	        else{
		        if ((Double)pair.getValue() < minDistance && n.isClosest == null){
		        	minDistance = (Double)pair.getValue();
		        	bestNeuron = (Neuron) pair.getKey();
	        }
	        }
	        //it.remove(); // avoids a ConcurrentModificationException  
	        
	    }
	    if(findFinal){
	    	bestNeuron.isClosest = city;
//	    	bestNeuron.setLatitude(city.getLatitude());
//	    	bestNeuron.setLatitude(city.getLongitude());
	    }
	    return bestNeuron;
	}
	
	public static void setAllIsClosestNeuronsToNullForAllNeurons(ArrayList<Neuron> neuronList){
		for (Neuron neuron : neuronList) {
			neuron.isClosest = null;
		}
	}
	
	public static void updateIsClosest(ArrayList<City> cities){
		for(City c : cities){
			findNearestNeuron(c,true);
		}
	}
	
	public static ArrayList<Neuron> findFinalNeurons(ArrayList<City> cities, ArrayList<Neuron> neurons) {
		updateIsClosest(cities);
		ArrayList<Neuron> finalRoute = new ArrayList<Neuron>();
		for(Neuron n : neurons){
			if(n.isClosest != null){
				finalRoute.add(n);
			}
		}
	        
		return finalRoute;
		
	}


	public static double totalDist(ArrayList<Neuron> neurons, ArrayList<City> cities){
		HelpMethods.updateIsClosest(cities);
		ArrayList<Neuron> finalRoute = new ArrayList<Neuron>();
		for(Neuron n : neurons){
			if(n.isClosest != null){
				finalRoute.add(n);
			}
		}
		for(Neuron n : neurons){
			if(n.isClosest != null){
				finalRoute.add(n);
				break;
			}
		}
		
		double totalDist = 0;
		for(int i = 0; i < finalRoute.size()-1 ; i++){
			totalDist+= HelpMethods.calcuLateDistanceBetweenNeurons(finalRoute.get(i), finalRoute.get(i+1));
		}
		return totalDist;

	}
	
	public static void showCurrentMap(ArrayList<Neuron> neurons, ArrayList<City> cities, double[] scalesForPrint, boolean isLastPrint){
		JFrame testFrame = new JFrame();
	    testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		TSPGraphics graphic = new TSPGraphics(cities, neurons, scalesForPrint, isLastPrint);
		double[] minMaxLatLong = findMinMaxLatLong(cities);
	    testFrame.getContentPane().add(graphic, BorderLayout.CENTER);

	    testFrame.pack();
	    testFrame.setVisible(true);
	}
	
	public static int toInt(double d){
		return (int)Math.round(d);
	}





}
