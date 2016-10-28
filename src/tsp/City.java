package tsp;

import java.util.HashMap;

public class City {
	
	private final int id;
	private final double latitude;
	private final double longitude;
	private HashMap<Neuron, Double> distanceToNauronsList;
	
	
	public City(int id, double latidude, double longitude){
		this.id = id;
		this.latitude = latidude;
		this.longitude = longitude;
		distanceToNauronsList = new HashMap<Neuron, Double>();
	}
	
	public void updateDistanceToNeuronList(Neuron neuron){
		double distance = HelpMethods.calcuLateDistanceBetweenCityAndNauron(this, neuron);
		distanceToNauronsList.put(neuron, distance);
	}
	

	public HashMap<Neuron, Double> getDistanceToNauronsList() {
		return distanceToNauronsList;
	}


	public void setDistanceToNauronsList(HashMap<Neuron, Double> distanceToNauronsList) {
		this.distanceToNauronsList = distanceToNauronsList;
	}


	public int getId() {
		return id;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
