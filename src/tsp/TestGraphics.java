package tsp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestGraphics extends JComponent{
	private final LinkedList<Line> lines = new LinkedList<Line>();

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

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    for (Line line : lines) {
	        g.setColor(line.color);
	        g.drawLine(line.x1, line.y1, line.x2, line.y2);
	    }
	}
	
	public static void main(String[] args) {
	    JFrame testFrame = new JFrame();
	    testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    final TestGraphics comp = new TestGraphics();
	    comp.setPreferredSize(new Dimension(320, 200));
	    testFrame.getContentPane().add(comp, BorderLayout.CENTER);
	    JPanel buttonsPanel = new JPanel();
	    testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	    
	    testFrame.pack();
	    testFrame.setVisible(true);
	}
		
		
	}
