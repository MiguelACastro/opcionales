package opcionales;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalTime;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import opcionales.FigurasRadiales.PaintPanel;

public class Reloj extends JFrame{

	public Reloj() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setMinimumSize(new Dimension(600, 600));
		this.setResizable(false);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.add(new PaintPanel(), BorderLayout.CENTER);
		Timer timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		timer.start();
	}
	
	class PaintPanel extends JPanel {
		
		public PaintPanel() {
			this.setBackground(Color.WHITE);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			
			int centroX = this.getWidth()/2;
			int centroY = this.getHeight()/2;
			
			g2d.setColor(Color.CYAN);
			g2d.fillOval(40, 35, 500, 500);
			
			g2d.setColor(Color.WHITE);
			g2d.fillOval(65, 60, 450, 450);
			
			g2d.setColor(Color.BLACK);
			
			Rectangle2D marcaGrande = new Rectangle2D.Double(285, 65, 10, 30);
			Rectangle2D marcaPequena = new Rectangle2D.Double(288, 65, 4, 15);
			
			for(int i = 0; i < 60; i++) {
				double angulo = 360/60*i;
				AffineTransform rotar = new AffineTransform();
				rotar.rotate(Math.toRadians(angulo), centroX, centroY);
				
				Path2D marca = null;
				if(angulo%30==0) {					
					marca = (Path2D) rotar.createTransformedShape(marcaGrande);
				} else {
					marca = (Path2D) rotar.createTransformedShape(marcaPequena);
				}
				
				g2d.fill(marca);
			}
			
			g2d.setFont(getFont().deriveFont(32f));
			g2d.drawString("12", 270, 130);
			g2d.drawString("1", 370, 150);
			g2d.drawString("2", 420, 200);
			g2d.drawString("3", 440, 290);
			g2d.drawString("4", 420, 370);
			g2d.drawString("5", 370, 430);
			g2d.drawString("6",	285, 460);
			g2d.drawString("7",	200, 430);
			g2d.drawString("8", 150, 370);
			g2d.drawString("9", 120, 295);
			g2d.drawString("10", 140, 210);
			g2d.drawString("11", 190, 150);
			
			Rectangle2D segundero = new Rectangle2D.Double(288, 75, 4, 205);
			Rectangle2D minutero = new Rectangle2D.Double(285, 95, 10, 195);
			Rectangle2D horario = new Rectangle2D.Double(282, 125, 16, 165);
			
			LocalTime tiempo = LocalTime.now();
			int segundos = tiempo.getSecond();
			int minutos = tiempo.getMinute();
			int horas = tiempo.getHour();
			
			double secondAngle = Math.toRadians((segundos * 6));
			double minuteAngle = Math.toRadians((minutos * 6));
			double hourAngle   = Math.toRadians((horas * 30 + minutos * 0.5));
			
			AffineTransform rotacionSegundero = AffineTransform.getRotateInstance(secondAngle, centroX, centroY);
			AffineTransform rotacionMinutero = AffineTransform.getRotateInstance(minuteAngle, centroX, centroY);
			AffineTransform rotacionHorario = AffineTransform.getRotateInstance(hourAngle, centroX, centroY);
			
			Path2D segRotado = (Path2D) rotacionSegundero.createTransformedShape(segundero);
			Path2D minRotado = (Path2D) rotacionMinutero.createTransformedShape(minutero);
			Path2D horRotado = (Path2D) rotacionHorario.createTransformedShape(horario);
			
			g2d.setColor(Color.GRAY);
			g2d.fill(horRotado);
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(minRotado);
			g2d.setColor(Color.RED);
			g2d.fill(segRotado);
		}	
	}	
}
