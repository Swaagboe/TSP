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
	private double discountRate;
	
	private final int STATIC_LEARNING_RATE = 0;
	private final int LINEAR_LEARNING_RATE = 1;
	private final int EXPONENTIAL_LEARNING_RATE = 2;
	private final int STATIC_NEIGBHOURHOOD_RADIUS = 0;
	private final int LINEAR_NEIGBHOURHOOD_RADIUS = 1;
	private final int EXPONENTIAL_NEIGBHOURHOOD_RADIUS = 2;
	
	private int decayLearningRate;
	private int decayNeighRadius;
	
	
	public TSP(String map, int numberOfNaurons, double learningRate, 
			int activeNeighbours, double discountRate, int decayFunctionLearningRate, int decayFunctionNeighbourhoodRadius) throws Exception{
		this.learningRate = learningRate;
		this.activeNeighbours = activeNeighbours;
		this.decayLearningRate = decayFunctionLearningRate;
		this.decayNeighRadius = decayFunctionNeighbourhoodRadius;
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
			if (decayFunctionLearningRate == LINEAR_LEARNING_RATE || decayFunctionNeighbourhoodRadius == LINEAR_NEIGBHOURHOOD_RADIUS 
					&& iterations%300 == 0 && activeNeighbours > 4){
				
				activeNeighbours-=4;
				HelpMethods.showCurrentMap(nauronList, cityList);
				if (learningRate > 0.1 && iterations%2000 == 0){
					learningRate-=0.05;
				}
			}
			City city = HelpMethods.pickRandomCity(cityList);//velger random city
			Neuron nearestNeuron = HelpMethods.findNearestNeuron(city, false);//finner nearmest city
			HelpMethods.updateLatLongForNeuronAndNeighbours(cityList, nearestNeuron, city, learningRate, activeNeighbours, discountRate);//flytter nauron nearmere by og oppdaterer alle cities med den nye distansen
			iterations++;	
		}
		HelpMethods.showCurrentMap(nauronList, cityList);
		ArrayList<Neuron> finalNeuronList = HelpMethods.findFinalNeurons(cityList, nauronList);
		HelpMethods.showCurrentMap(finalNeuronList, cityList);
		System.out.println(HelpMethods.totalDist(nauronList, cityList));
		System.out.println("Final: "+finalNeuronList.size());
		System.out.println("City: "+cityList.size());
	}
	
	public static void main(String[] args) throws Exception {
		final int STATIC_LEARNING_RATE = 0;
		final int LINEAR_LEARNING_RATE = 1;
		final int EXPONENTIAL_LEARNING_RATE = 2;
		final int STATIC_NEIGBHOURHOOD_RADIUS = 0;
		final int LINEAR_NEIGBHOURHOOD_RADIUS = 1;
		final int EXPONENTIAL_NEIGBHOURHOOD_RADIUS = 2; 
		
		int numberOfNaurons = 200;
		double learningRate = 0.9;
		int activeNeighbours = 100;
		double discountRate = 0.9;
		
		//optimal Qatar = 9352
		new TSP("Westers_Sahara.txt", numberOfNaurons, learningRate,
				activeNeighbours, discountRate, STATIC_LEARNING_RATE, STATIC_NEIGBHOURHOOD_RADIUS);
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