package tsp;

import java.util.ArrayList;

public class Neuron {
	
	private final int id;
	private double latitude;
	private double longitude;
	private ArrayList<Neuron> neighbours;
	public boolean isClosest;
	
	public Neuron(int id, double latitude, double longitude){
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	//removes Nauron with id from neighbourlist
	public void removeNeighbour(int id){
		for (Neuron n : neighbours) {
			if (n.getId() == id){
				neighbours.remove(n);
			}
		}
	}

	public ArrayList<Neuron> getNeighbours() {
		return neighbours;
	}
	
	public void setNeighbours(ArrayList<Neuron> neighbours) {
		this.neighbours = neighbours;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}
	
	

}
