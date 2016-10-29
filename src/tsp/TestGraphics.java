package tsp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Polygon;

public class TestGraphics extends JComponent{
	private final LinkedList<Line> lines = new LinkedList<Line>();
	private Polygon polygon;
	private ArrayList<City> cities;
	private ArrayList<Neuron> neurons;
	private int maxLong =0;
	private int minLat;
	private int minLong;
	private int avgLong;
	private int avgLat;
	private ArrayList<ArrayList<Integer>> neuronFixList;
	private ArrayList<ArrayList<Integer>> cityFixList;
	
	
	public TestGraphics(ArrayList<City> cities, ArrayList<Neuron> neurons){
		int[] a = {};
		int[] b = {};
		double[] minMax = HelpMethods.findMinMaxLatLong(cities);
		this.polygon = new Polygon(a,b,0);
		this.cities = cities;
		this.neurons = neurons;
		this.minLat = HelpMethods.toInt(minMax[0]);
		this.minLong = HelpMethods.toInt(minMax[2]);
		this.avgLat = HelpMethods.toInt(0.5*(minMax[0]+minMax[1]));
		this.avgLat = HelpMethods.toInt(0.5*(minMax[2]+minMax[3]));
		this.maxLong = (int) Math.round(minMax[3])- HelpMethods.toInt(minMax[2]);
		this.setPreferredSize(new Dimension(HelpMethods.toInt(minMax[1]) - HelpMethods.toInt(minMax[0]), HelpMethods.toInt(minMax[3]) - HelpMethods.toInt(minMax[2])));                
		addNeuronsToRing(neurons);
		neuronFixList= new ArrayList<ArrayList<Integer>>();
		cityFixList= new ArrayList<ArrayList<Integer>>();
		for(Neuron n : neurons) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(HelpMethods.toInt(n.getLatitude()));
			t.add(HelpMethods.toInt(n.getLongitude()));
			
			neuronFixList.add(t);
		}
		for(City c : cities) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(HelpMethods.toInt(c.getLatitude()));
			t.add(HelpMethods.toInt(c.getLongitude()));
			
			cityFixList.add(t);
		}
	}
	
	public void addNeuronsToRing(ArrayList<Neuron> neurons) {
		for(Neuron n : neurons){
			this.polygon.addPoint((int)Math.round(n.getLatitude()) - minLat, ((int)Math.round(n.getLongitude())- minLong));		
		}
		
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D gr = (Graphics2D) g;
	    gr.scale(0.1,0.1);

	    gr.setColor(Color.CYAN);
	    gr.drawPolygon(polygon);
	    g.setColor(Color.BLACK);
	    for(int i = 0 ; i < neuronFixList.size() ; i++){
	    	int y = neuronFixList.get(i).get(0);
	    	int x = neuronFixList.get(i).get(1);
	    	gr.drawOval(y - minLat,  (x- minLong), 3, 3);
	    }
	    gr.setColor(Color.RED);
	    for(int t = 0 ; t < cityFixList.size() ; t++){
	    	int a = cityFixList.get(t).get(0);
	    	int b = cityFixList.get(t).get(1);

	    	gr.drawOval(a - minLat,  b- minLong, 6, 6);
	    }


	    
	    	
	}
	
	
	
	public static void main(String[] args) {
//	    JFrame testFrame = new JFrame();
//	    testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//	    ArrayList<City> cities = new ArrayList<City>();
//	    cities.add(new City(1,21,21));
//	    cities.add(new City(1,21,121));
//	    cities.add(new City(1,121,121));
//	    cities.add(new City(1,121,21));
//	    ArrayList<Neuron> neurons = new ArrayList<Neuron>();
//	    neurons.add(new Neuron(1, 41, 41));
//	    neurons.add(new Neuron(1, 41, 71));
//	    neurons.add(new Neuron(1, 71, 71));
//	    neurons.add(new Neuron(1, 71, 41));
//	    
//	    HelpMethods.showCurrentMap(neurons, cities);
//	    final TestGraphics comp = new TestGraphics(cities, neurons);
//	    
//	    testFrame.getContentPane().add(comp, BorderLayout.CENTER);
//
//	    testFrame.pack();
//	    testFrame.setVisible(true);
	}
		
		
	}