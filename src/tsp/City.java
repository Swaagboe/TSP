package tsp;

import java.util.HashMap;

public class City {
	
	private final int id;
	private final double latitude;
	private final double longitude;
	private HashMap<Integer, Double> distanceToNauronsList;
	
	
	public City(int id, double latidude, double longitude){
		this.id = id;
		this.latitude = latidude;
		this.longitude = longitude;
	}
	

	public HashMap<Integer, Double> getDistanceToNauronsList() {
		return distanceToNauronsList;
	}


	public void setDistanceToNauronsList(HashMap<Integer, Double> distanceToNauronsList) {
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
