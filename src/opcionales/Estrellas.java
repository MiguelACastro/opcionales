package opcionales;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Estrellas extends JFrame{
	
	public Estrellas() {
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

			int[] xPoints = {300,340,410, 350,370,300,230,250,190,260};
			int[] yPoints = {200,260,260,310,380,340,380,310,260,260};
			Polygon e = new Polygon(xPoints, yPoints, 10);
			Path2D estrella = new Path2D.Double(e); 

			AffineTransform encoger = AffineTransform.getScaleInstance(0.5, 0.5);
			estrella = (Path2D) encoger.createTransformedShape(estrella);

			double panelCentroX = this.getWidth()/2;
			double panelCentroY = this.getHeight()/2;
			double origenX = -estrella.getBounds().getX();
			double origenY = -estrella.getBounds().getY();
			double centroX = panelCentroX-estrella.getBounds().getX()/2;
			double centroY = panelCentroY-estrella.getBounds().getY()/2;

			AffineTransform moverAlCentro = new AffineTransform();
			moverAlCentro.translate(origenX, origenY);
			moverAlCentro.translate(centroX, centroY);
			moverAlCentro.translate(0, -200);

			estrella = (Path2D) moverAlCentro.createTransformedShape(estrella);

			int numEstrellas = 40;
			int anguloEstrella = 360/numEstrellas;
			Random rng = new Random(System.currentTimeMillis());

			for(int i = 0; i < numEstrellas; i++) {
				Color colorAleatorio = new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
				g2d.setColor(colorAleatorio);

				double angulo = Math.toRadians(anguloEstrella*i);
				AffineTransform rotar = AffineTransform.getRotateInstance(angulo, panelCentroX, panelCentroY);
				Path2D estrellaRotada = (Path2D) rotar.createTransformedShape(estrella);

				g2d.fill(estrellaRotada);
			}			
		}
	}
}
