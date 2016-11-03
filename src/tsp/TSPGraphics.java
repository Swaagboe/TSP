package tsp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Polygon;

public class TSPGraphics extends JComponent{
	private final LinkedList<Line> lines = new LinkedList<Line>();
	private Polygon polygon;
	private ArrayList<City> cities;
	private ArrayList<Neuron> neurons;
	private int maxLong =0;
	private int minLat;
	private int minLong;
	private int avgLong;
	private int avgLat;
	private int maxLat;
	private ArrayList<ArrayList<Integer>> neuronFixList;
	private ArrayList<ArrayList<Integer>> cityFixList;
	private double[] scaleForPrint;
	private int centerLat;
	private int centerLong;
	
	
	public TSPGraphics(ArrayList<City> cities, ArrayList<Neuron> neurons, double[] scaleForPrint, boolean isLastPrint){
		int[] a = {};
		int[] b = {};
		double[] minMax = HelpMethods.findMinMaxLatLong(cities);
		this.polygon = new Polygon(a,b,0);
		this.cities = cities;
		this.neurons = neurons;
		this.scaleForPrint = scaleForPrint;
		this.minLat = HelpMethods.toInt(minMax[0]);
		this.minLong = HelpMethods.toInt(minMax[2]);
		this.maxLat = HelpMethods.toInt(minMax[1]);
		int maxLong1 = HelpMethods.toInt(minMax[3]);
		this.avgLat = HelpMethods.toInt(0.5*(minMax[0]+minMax[1]));
		this.avgLat = HelpMethods.toInt(0.5*(minMax[2]+minMax[3]));
		this.maxLong = (int) Math.round(minMax[3])- HelpMethods.toInt(minMax[2]);
		this.centerLong = (maxLong1-minLong)/2;
		this.centerLat = (maxLat - minLat)/2;
		this.setPreferredSize(new Dimension(900,1000));   
		if(isLastPrint){
			addNeuronsToFinalRing(neurons);
		}else{
			addNeuronsToRing(neurons);
		}
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
			this.polygon.addPoint((int)Math.round(n.getLatitude()) - minLat+(int)scaleForPrint[4], ((int)Math.round(n.getLongitude())- minLong)-(int)scaleForPrint[4]);		
		}
		
	}
	
	public void addNeuronsToFinalRing(ArrayList<Neuron> neurons) {
		for(Neuron n : neurons){
			this.polygon.addPoint((int)Math.round(n.isClosest.getLatitude()) - minLat+(int)scaleForPrint[4], ((int)Math.round(n.isClosest.getLongitude())- minLong)-(int)scaleForPrint[4]);		
		}
		
	}
	
	private static Shape mirrorAlongX(double x, Shape shape){
	    AffineTransform at = new AffineTransform();
	    at.translate(x, 0);
	    at.scale(-1, 1);
	    at.translate(-x, 0);
	    return at.createTransformedShape(shape);
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D gr = (Graphics2D) g;
	    gr.scale(scaleForPrint[0], scaleForPrint[1]);

	    gr.setColor(Color.BLACK);
	    gr.rotate(Math.toRadians(90), centerLat, centerLong);
	    gr.draw(mirrorAlongX(polygon.getBounds2D().getCenterX(), polygon));
	    //gr.drawPolygon(polygon);
	    gr.setColor(Color.BLUE);
	    for(int i = 0 ; i < neuronFixList.size() ; i++){
	    	int y = neuronFixList.get(i).get(0);
	    	int x = neuronFixList.get(i).get(1);
	    	gr.fillOval(2*centerLat-(y - minLat)+(int)scaleForPrint[4],  (x- minLong)-(int)scaleForPrint[4], (int) scaleForPrint[2], (int)scaleForPrint[2]);
	    }
	    gr.setColor(Color.RED);
	    for(int t = 0 ; t < cityFixList.size() ; t++){
	    	int a = cityFixList.get(t).get(0);
	    	int b = cityFixList.get(t).get(1);

	    	gr.fillOval(2*centerLat-(a - minLat)+(int)scaleForPrint[4],  (b- minLong)-(int)scaleForPrint[4], (int) scaleForPrint[3], (int) scaleForPrint[3]);
	    }


	    
	    	
	}
	
	
	
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