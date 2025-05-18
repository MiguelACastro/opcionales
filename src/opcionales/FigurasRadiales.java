package opcionales;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FigurasRadiales extends JFrame{
	
	public FigurasRadiales() {
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
			double radio = 50;
			double alturaPetalos = 40;
			double anchoPetalos = 10;
			int numPetalos = 10;
			
			Path2D figuraRadial = new Path2D.Double();
		    for (double theta = 0; theta <= 2 * Math.PI; theta += 0.01) {
	            double r = rosaPolar(radio, alturaPetalos, numPetalos, anchoPetalos, theta);
	            Point2D punto = puntoPolar(r, theta);
	            
	            if (theta==0) {
	                figuraRadial.moveTo(punto.getX(), punto.getY());
	            } else {
	                figuraRadial.lineTo(punto.getX(), punto.getY());
	            }
	        }
		    
		    int numFiguras = 30;  
		    Random rng = new Random(System.currentTimeMillis());
		    for(int i = 0; i < numFiguras; i++) {
		    	int tx = rng.nextInt(this.getWidth());
		    	int ty = rng.nextInt(this.getHeight());
		    	AffineTransform trasladar = new AffineTransform();
		    	trasladar.translate(tx, ty);
		    	
		    	Path2D figuraTrasladada = (Path2D) trasladar.createTransformedShape(figuraRadial); 
		    	
		    	Color colorRelleno = new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
		    	g2d.setColor(colorRelleno);
		    	g2d.fill(figuraTrasladada);
		    	
		    	g2d.setStroke(new BasicStroke(3));
		    	Color colorBorde = new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
		    	g2d.setColor(colorBorde);
		    	g2d.draw(figuraTrasladada);
		    }
		}
		
		
//		anchoPetalos debe ser par
//		Si numPetalos es par entonces la rosa tendra el doble de petalos
//		https://www.desmos.com/calculator/rfilx6ul77
		private double rosaPolar(double radio, double alturaPetalos, int numPetalos, double anchoPetalos, double theta) {
			return radio+alturaPetalos*Math.pow(Math.cos(numPetalos*theta), anchoPetalos);
			
		}
		
		private Point2D puntoPolar(double r, double theta) {
			int x = (int) Math.floor(r*Math.cos(theta));
			int y = (int) Math.floor(r*Math.sin(theta));
			return new Point(x+100, y+100);
		}
	}
}
