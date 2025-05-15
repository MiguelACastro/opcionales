package opcionales;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MickeyMouse extends JFrame{
	
	public MickeyMouse() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setMinimumSize(new Dimension(600, 600));
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.add(new PaintPanel(), BorderLayout.CENTER);
		repaint();
	}
	
	class PaintPanel extends JPanel {
		
		public PaintPanel() {
			this.setBackground(Color.WHITE);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); 
			
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform actual = g2d.getTransform();
			Stroke grosorActual = g2d.getStroke();
			
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(3));
			g2d.rotate(Math.toRadians(45), 210, 370);
			g2d.drawOval(210, 370, 50, 60);
			
			g2d.setTransform(actual);
			g2d.rotate(Math.toRadians(-45), 330, 410);
			g2d.drawOval(330, 410, 50, 60);
			
			//Cabeza
			g2d.setTransform(actual);
			g2d.setStroke(grosorActual);
			g2d.fillOval(140, 130, 300, 300);
			
			//Orejas
			g2d.fillOval(100, 80, 130, 130);
			g2d.fillOval(350, 80, 130, 130);
			
			//Cara
			g2d.setColor(new Color(250, 205, 176));
			g2d.fillOval(180, 170, 130, 250);
			g2d.fillOval(270, 170, 130, 250);
			
			g2d.rotate(Math.toRadians(35), 190, 300);
			g2d.fillOval(190, 300, 150, 80);
		
			g2d.setTransform(actual);
			g2d.rotate(-Math.toRadians(35), 260, 389);
			g2d.fillOval(260, 389, 150, 80);
		
			g2d.setTransform(actual);
			g2d.fillOval(265, 410, 35, 21);
			
			//Ojos y boca
			g2d.setColor(Color.BLACK);
			g2d.fillOval(230, 300, 30, 40);
			g2d.fillOval(330, 300, 30, 40);
			g2d.fillOval(275, 340, 40, 30);
		}
	}
}
