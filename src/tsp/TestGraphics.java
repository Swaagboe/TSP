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
	
	
	public TestGraphics(ArrayList<City> cities, ArrayList<Neuron> neurons){
		int[] a = {};
		int[] b = {};
		double[] minMax = HelpMethods.findMinMaxLatLong(cities);
		this.polygon = new Polygon(a,b,0);
		this.cities = cities;
		this.neurons = neurons;
		this.maxLong = (int) Math.round(minMax[3])+20;
		this.setPreferredSize(new Dimension((int)Math.round(minMax[1])+20, (int)Math.round(minMax[3])+20));
		addNeuronsToRing(neurons);
	}

	public void addLine(int x1, int x2, int x3, int x4) {
	    addLine(x1, x2, x3, x4, Color.black);
	}

	public void addLine(int x1, int x2, int x3, int x4, Color color) {
	    lines.add(new Line(x1,x2,x3,x4, color));
	    repaint();
	}

	public void clearLines() {
	    lines.clear();
	    repaint();
	}
	
	public void setPolygon(Polygon polygon){
		this.polygon = polygon;
	}
	
	public void addNeuronsToRing(ArrayList<Neuron> neurons) {
		for(Neuron n : neurons){
			this.polygon.addPoint((int)Math.round(n.getLatitude()), maxLong - (int)Math.round(n.getLongitude()));		
		}
		
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(Color.BLACK);
	    g.drawPolygon(polygon);
	    g.setColor(Color.GREEN);
	    for(Neuron n : neurons){
	    	g.drawOval((int) Math.round(n.getLatitude()), maxLong - (int)Math.round(n.getLongitude()), 2, 2);
	    }
	    g.setColor(Color.RED);
	    for(City c : cities){
	    	g.drawOval((int) Math.round(c.getLatitude()), maxLong - (int)Math.round(c.getLongitude()), 4, 4);
	    }
	    	
	}
	
	
	
	public static void main(String[] args) {
	    JFrame testFrame = new JFrame();
	    testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    ArrayList<City> cities = new ArrayList<City>();
	    cities.add(new City(1,1,10));
	    cities.add(new City(1,1,100));
	    cities.add(new City(1,100,100));
	    cities.add(new City(1,100,10));
	    ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	    neurons.add(new Neuron(1, 20, 20));
	    neurons.add(new Neuron(1, 20, 50));
	    neurons.add(new Neuron(1, 50, 50));
	    neurons.add(new Neuron(1, 50, 20));
	    final TestGraphics comp = new TestGraphics(cities, neurons);
	    
	    testFrame.getContentPane().add(comp, BorderLayout.CENTER);

	    testFrame.pack();
	    testFrame.setVisible(true);
	}
		
		
	}
