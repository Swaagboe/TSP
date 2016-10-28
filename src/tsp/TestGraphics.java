package tsp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	
	
	public TestGraphics(ArrayList<City> cities, ArrayList<Neuron> neurons){
		int[] a = {};
		int[] b = {};
		double[] minMax = HelpMethods.findMinMaxLatLong(cities);
		this.polygon = new Polygon(a,b,0);
		this.cities = cities;
		this.neurons = neurons;
		this.minLat = HelpMethods.toInt(minMax[0]);
		this.minLong = HelpMethods.toInt(minMax[2]);
		this.maxLong = (int) Math.round(minMax[3])- HelpMethods.toInt(minMax[2]);
		this.setPreferredSize(new Dimension(HelpMethods.toInt(minMax[1]) - HelpMethods.toInt(minMax[0]), HelpMethods.toInt(minMax[3]) - HelpMethods.toInt(minMax[2])));                
		addNeuronsToRing(neurons);
	}
	
	public void addNeuronsToRing(ArrayList<Neuron> neurons) {
		for(Neuron n : neurons){
			this.polygon.addPoint((int)Math.round(n.getLatitude()) - minLat, maxLong - ((int)Math.round(n.getLongitude())- minLong));		
		}
		
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(Color.CYAN);
	    g.drawPolygon(polygon);
	    g.setColor(Color.BLACK);
	    for(Neuron n : neurons){
	    	g.drawOval((int) Math.round(n.getLatitude()) - minLat, maxLong - ((int)Math.round(n.getLongitude()) - minLong), 3, 3);
	    }
	    g.setColor(Color.RED);
	    for(City c : cities){
	    	g.drawOval((int) Math.round(c.getLatitude()) - minLat, maxLong - ((int)Math.round(c.getLongitude())- minLong), 6, 6);
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
