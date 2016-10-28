package tsp;

import java.util.ArrayList;

public class Nauron {
	
	private final int id;
	private double latitude;
	private double longitude;
	private ArrayList<Nauron> neighbours;
	
	public Nauron(int id, double latitude, double longitude){
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void addNeighbour(Nauron n){
		neighbours.add(n);
	}
	
	//removes Nauron with id from neighbourlist
	public void removeNeighbour(int id){
		for (Nauron n : neighbours) {
			if (n.getId() == id){
				neighbours.remove(n);
			}
		}
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
