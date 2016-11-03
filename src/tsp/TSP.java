package tsp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

public class TSP {
	
	//[lat][long]
	private final ArrayList<City> cityList;
	private final ArrayList<Neuron> nauronList;
	private double[] minMaxLatLong;
	private double learningRate;
	private int activeNeighbours;
	private double discountRate;
	private double initialLearningRate;
	private double initialActiveNeighbours;
	private double lambdaLR;
	private double lambdaAN;
	private double learningLinearDecrease;
	private int activeNLinDecrease;
	private int nrOfIterations;
	private int stepsForPrint;
	
	private final int LINEAR = 1;
	private final int EXPONENTIAL = 2;
	
	private int decayAlternative;
	
	private double[] scalesForPrint;
	
	
	public TSP(String map, int decayAlternative, int stepsForPrint) throws Exception{
		this.decayAlternative = decayAlternative;
		this.stepsForPrint = stepsForPrint;
		int numberOfNaurons = 0;
		if(map.equals("Westers_Sahara.txt")){
			scalesForPrint = new double[]{0.1,0.1,20,40};
			numberOfNaurons = 150;
			this.learningRate = 0.5;
			this.activeNeighbours = 48;
			this.discountRate = Math.exp((2/activeNeighbours)*Math.log(0.01));
			this.initialLearningRate = learningRate;
			this.initialActiveNeighbours = activeNeighbours;
			this.lambdaLR = Math.log(0.1/initialLearningRate)/-1000;
			this.lambdaAN = Math.log(4/initialActiveNeighbours)/-1000;
			this.learningLinearDecrease = 0.00001;
			this.activeNLinDecrease = 4;
			this.nrOfIterations = 2000;
		}
		if(map.equals("Djibouti.txt")){
			scalesForPrint = new double[]{0.4,0.4,6,12};
			numberOfNaurons = 150;
			this.learningRate = 0.2;
			this.activeNeighbours = 48;
			this.discountRate = Math.exp((2/activeNeighbours)*Math.log(0.01));
			this.initialLearningRate = learningRate;
			this.initialActiveNeighbours = activeNeighbours;
			this.lambdaLR = Math.log(0.1/initialLearningRate)/-1000;
			this.lambdaAN = Math.log(4/initialActiveNeighbours)/-1000;
			this.learningLinearDecrease = 0.00001;
			this.activeNLinDecrease = 4;
			this.nrOfIterations = 2000;

		}
		if(map.equals("Qatar.txt")){
			scalesForPrint = new double[]{0.45,0.45,4,8};
			numberOfNaurons = 776;
			this.learningRate = 0.5;
			this.activeNeighbours = 70;
			this.discountRate = Math.exp((2/activeNeighbours)*Math.log(0.01));
			this.initialLearningRate = learningRate;
			this.initialActiveNeighbours = activeNeighbours;
			this.lambdaLR = Math.log(0.1/initialLearningRate)/-15000;
			this.lambdaAN = Math.log(4/initialActiveNeighbours)/-15000;
			this.learningLinearDecrease = (learningRate-0.1)/15000;
			this.activeNLinDecrease = 2;
			this.nrOfIterations = 30000;

		}
		if(map.equals("Uruguay.txt")){
			scalesForPrint = new double[]{0.20,0.12,9,18};
			numberOfNaurons = 4000;
			this.learningRate = 0.5;
			this.activeNeighbours = 48;
			this.discountRate = Math.exp((2/activeNeighbours)*Math.log(0.01));
			this.initialLearningRate = learningRate;
			this.initialActiveNeighbours = activeNeighbours;
			this.lambdaLR = Math.log(0.1/initialLearningRate)/-15000;
			this.lambdaAN = Math.log(4/initialActiveNeighbours)/-15000;
			this.learningLinearDecrease = 0.007;
			this.activeNLinDecrease = 4;
			this.nrOfIterations = 30000;
		}
		
		this.cityList = HelpMethods.generateCities(map);
		this.minMaxLatLong = HelpMethods.findMinMaxLatLong(cityList);
		this.nauronList = HelpMethods.generateRandomNauronList(numberOfNaurons, minMaxLatLong);
//		HelpMethods.showCurrentMap(nauronList, cityList, scalesForPrint);
		HelpMethods.generateNeigboursForNaurons(nauronList);
		HelpMethods.initializeDistanceToNeuronsList(cityList, nauronList);

		run();
	}
	
	public void run() throws Exception{
		int iterations = 0;
		int step = 0;
		while(iterations<nrOfIterations){
			if (decayAlternative == LINEAR){
				if (learningRate > 0.1 && iterations%150 == 0){
					learningRate-=learningLinearDecrease;
				}
				if (iterations%300 == 0 && activeNeighbours > 2){
					activeNeighbours-=activeNLinDecrease;	
				}
			}
			else if(decayAlternative == EXPONENTIAL){
				if (learningRate > 0.1){
					learningRate = initialLearningRate*Math.exp(-lambdaLR*iterations);
				}
				if (activeNeighbours > 2){
					activeNeighbours = (int) (initialActiveNeighbours*Math.exp(-lambdaAN*iterations));
				}
			}
			if (iterations%(nrOfIterations/stepsForPrint)==0){
				ArrayList<Neuron> finalNeuronList = HelpMethods.findFinalNeurons(cityList, nauronList);
				HelpMethods.showCurrentMap(finalNeuronList, cityList, scalesForPrint, true);
				System.out.println("After "+step+"/"+stepsForPrint+" of the total run, the distance is: " +HelpMethods.totalDist(nauronList, cityList));
				HelpMethods.setAllIsClosestNeuronsToNullForAllNeurons(nauronList);
				step++;
				
			}
			if (iterations%500==0){
				System.out.println("Nr of iterations: "+iterations);
			}
			City city = HelpMethods.pickRandomCity(cityList);//velger random city
			Neuron nearestNeuron = HelpMethods.findNearestNeuron(city, false);//finner nearmest city
			HelpMethods.updateLatLongForNeuronAndNeighbours(cityList, nearestNeuron, city, learningRate, activeNeighbours, discountRate);//flytter nauron nearmere by og oppdaterer alle cities med den nye distansen
			iterations++;	
		}
		ArrayList<Neuron> finalNeuronList = HelpMethods.findFinalNeurons(cityList, nauronList);
		HelpMethods.showCurrentMap(finalNeuronList, cityList, scalesForPrint, true);
		System.out.println("Total distance: "+HelpMethods.totalDist(nauronList, cityList));
		System.out.println("Final: "+finalNeuronList.size());
	}
	
	public static void main(String[] args) throws Exception {
		int STATIC = 0;
		int LINEAR = 1;
		int EXPONENTIAL = 2;
		Scanner sc = new Scanner(System.in);
		System.out.println("Which map you want to run on? (WS, D, Q or U): ");
		String map = sc.next();
		System.out.println("Do you want EXP, LIN or STATIC decay function? Write E, L or S: ");
		String decay = sc.next();
		System.out.println("How often do you want to see the current shortest distance?: ");
		String step = sc.next();
		int stepsForPrint = Integer.parseInt(step);
		if (map.equals("WS")){
			if (decay.equals("E")){
				new TSP("Westers_Sahara.txt", EXPONENTIAL, stepsForPrint);							
			}
			else if (decay.equals("L")){
				new TSP("Westers_Sahara.txt", LINEAR, stepsForPrint);											
			}
			else {
				new TSP("Westers_Sahara.txt", STATIC, stepsForPrint);											
			}
		}
		if (map.equals("D")){
			if (decay.equals("E")){
				new TSP("Djibouti.txt", EXPONENTIAL, stepsForPrint);							
			}
			else if (decay.equals("L")){
				new TSP("Djibouti.txt", LINEAR, stepsForPrint);											
			}
			else {
				new TSP("Djibouti.txt", STATIC, stepsForPrint);											
			}
		}
		if (map.equals("Q")){
			if (decay.equals("E")){
				new TSP("Qatar.txt", EXPONENTIAL, stepsForPrint);							
			}
			else if (decay.equals("L")){
				new TSP("Qatar.txt", LINEAR, stepsForPrint);											
			}
			else {
				new TSP("Qatar.txt", STATIC, stepsForPrint);											
			}
		}
		if (map.equals("U")){
			if (decay.equals("E")){
				new TSP("Uruguay.txt", EXPONENTIAL, stepsForPrint);							
			}
			else if (decay.equals("L")){
				new TSP("Uruguay.txt", LINEAR, stepsForPrint);											
			}
			else {
				new TSP("Uruguay.txt", STATIC, stepsForPrint);											
			}
		}
		
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