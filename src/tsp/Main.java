package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Main {

	
	public Main(String city){
		double[][] cityCoordinates;
		if(city.equals("WS")){
			cityCoordinates = readFile("Westers_Sahara.txt");
		}
		else if (city.equals("D")){
			cityCoordinates = readFile("Djibouti.txt");			
		}
		else if (city.equals("Q")){
			cityCoordinates = readFile("Qatar.txt");			
		}
		else {
			cityCoordinates = readFile("Uruguay.txt");			
		}
		new TSP(cityCoordinates);
		
	}
	
	public double[][] readFile(String file){
		int numberOfData = 0;
		double[][] dataMatrix;
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));	
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			String[] list = line.split(" ");
			numberOfData = Integer.parseInt(list[0]);
			dataMatrix = new double[numberOfData][2];
			int c = 0;
			while (line != null) {
				line = br.readLine();
				if (line == null){
					break;
				}
				list = line.split(" ");
//				System.out.println(Arrays.toString(list));
				for (int i = 0; i < 2; i++) {
					dataMatrix[c][i] = Double.parseDouble(list[i+1]);
				}
				c++;
			}
			for (int i = 0; i < numberOfData; i++) {
				for (int j = 0; j < 2; j++) {
					System.out.print(Arrays.toString(dataMatrix[i]));
				}
				System.out.println();
			}
			return dataMatrix;
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}
	
	public static void main(String[] args) {
		new Main("WS");
	}

}


