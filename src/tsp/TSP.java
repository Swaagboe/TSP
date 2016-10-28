package tsp;

public class TSP {
	
	//[lat][long]
	private double[][] cityCoordinates;
	private double maxLat;
	private double minLat;
	private double maxLong;
	private double minLong;
	private final double INF = Double.MAX_VALUE;
	
	
	
	public TSP(double[][] cityCoordinates){
		this.cityCoordinates = cityCoordinates;
		findMaxLatLong();
	}
	
	public void findMaxLatLong(){
		double maxLat = -INF;
		double maxLong = -INF;
		double minLat = INF;
		double minLong = INF;		
		for (int i = 0; i < cityCoordinates.length; i++) {
			if (cityCoordinates[i][0] > maxLat){
				maxLat = cityCoordinates[i][0];
			}
			if (cityCoordinates[i][1] > maxLong){
				maxLong = cityCoordinates[i][1]; 
			}
			if (cityCoordinates[i][0] < minLat){
				minLat = cityCoordinates[i][0];
			}
			if (cityCoordinates[i][1] < minLong){
				minLong = cityCoordinates[i][1];
			}
			
		}
		this.maxLat = maxLat;
		this.maxLong = maxLong;
		this.minLat = minLat;
		this.minLong = minLong;
		
	}

}
