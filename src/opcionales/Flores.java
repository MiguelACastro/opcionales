package opcionales;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Flores extends JFrame {
	public Flores() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.add(paint(), BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
		
		this.setMinimumSize(getMinimumSize());
		this.setPreferredSize(getPreferredSize());
		revalidate();
		repaint();
	}
	
	private JPanel paint() {
		JPanel panelInterfaz = new JPanel(new GridBagLayout());
		GridBagConstraints reglas = new GridBagConstraints();
		panelInterfaz.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		panelInterfaz.setBackground(Color.BLACK);
		
		PaintPanel panelCanvas = new PaintPanel();
		panelCanvas.setPreferredSize(new Dimension(800, 600));
		panelCanvas.setMinimumSize(new Dimension(800, 600));
		reglas.gridx = 1;
		reglas.gridy = 1;
		reglas.gridwidth = 1;
		reglas.gridheight = 1;
		reglas.weightx = 1.0;
		reglas.weighty = 1.0;
		reglas.fill = GridBagConstraints.BOTH;
		reglas.insets = new Insets(0, 15, 0, 0);
		panelInterfaz.add(panelCanvas, reglas);
		reglas.insets = new Insets(0, 0, 0, 0);
		
		JPanel panelHerramientas = new JPanel();
		panelHerramientas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelHerramientas.setLayout(new BoxLayout(panelHerramientas, BoxLayout.Y_AXIS));
		reglas.gridx = 0;
		reglas.gridy = 1;
		reglas.gridwidth = 1;
		reglas.gridheight = 1;
		reglas.weightx = 0.2;
		reglas.fill = GridBagConstraints.VERTICAL;
		panelInterfaz.add(panelHerramientas, reglas);
		
		JPanel panelColores = new JPanel(new GridLayout(3, 3));
		panelColores.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelHerramientas.add(panelColores);
		
		Color[][] colores = {{Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY},
				{Color.BLUE, Color.RED, Color.GREEN},
				{Color.PINK, Color.MAGENTA, Color.ORANGE}};
		
		reglas.weightx = 1.0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {				
				JButton botonColor = new JButton();
				botonColor.setBackground(colores[i][j]);
				botonColor.setPreferredSize(new Dimension(50,50));
				botonColor.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						panelCanvas.setColor(botonColor.getBackground());
					}
				});
				reglas.gridy = i;
				reglas.gridx = j;
				reglas.gridwidth = 1;
				reglas.gridheight = 1;
				reglas.insets = new Insets(0, 0, 15, 15);
				reglas.fill = GridBagConstraints.NONE;
				reglas.anchor = GridBagConstraints.NORTHWEST;
				panelColores.add(botonColor);
			}
		}
		panelHerramientas.add(Box.createVerticalGlue());
		
		JPanel panelDibujar = new JPanel();
		panelDibujar.setLayout(new BoxLayout(panelDibujar, BoxLayout.Y_AXIS));
		panelDibujar.setPreferredSize(new Dimension(200, 0));
		panelHerramientas.add(panelDibujar);
		
		JPanel panelLabelCoordenadas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		panelLabelCoordenadas.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panelDibujar.add(panelLabelCoordenadas);
		JLabel labelX = new JLabel("X: ");
		labelX.setPreferredSize(new Dimension(75, 30));
		panelLabelCoordenadas.add(labelX);
		
		JLabel labelY = new JLabel("Y: ");
		labelY.setPreferredSize(new Dimension(75, 30));
		panelLabelCoordenadas.add(labelY);
		
		JPanel panelCoordenadas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		panelCoordenadas.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDibujar.add(panelCoordenadas);
		
		JTextField xField = new JTextField();
		xField.setPreferredSize(new Dimension(75, 30));
		panelCoordenadas.add(xField);
		
		JTextField yField = new JTextField();
		yField.setPreferredSize(new Dimension(75, 30));
		panelCoordenadas.add(yField);
	
		JButton botonDibujar = new JButton("Dibujar");
		botonDibujar.addActionListener(_ -> {
			String textoX = xField.getText();
			String textoY = yField.getText();
			
			if(textoX.matches("^[0-9]+$") && textoY.matches("^[0-9]+$")) {
				int x = Integer.valueOf(textoX);
				int y = Integer.valueOf(textoY);
				panelCanvas.dibujarEnCoordenada(x, y);
			} else {
				JOptionPane.showMessageDialog(this, "Solo numeros enteros",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		botonDibujar.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDibujar.add(botonDibujar);
		
		panelHerramientas.add(Box.createVerticalGlue());
		
		JPanel panelLimpiar = new JPanel();
		panelLimpiar.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelHerramientas.add(panelLimpiar);
		JButton botonLimpiar = new JButton("Limpiar");
		botonLimpiar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelCanvas.borrarTodo();
			}
		});
		panelLimpiar.add(botonLimpiar);
		
		return panelInterfaz;
	}
	
	class PaintPanel extends JPanel{
		
		private ArrayList<Flor> flores = new ArrayList<Flor>();
		private Color color;
		
		public PaintPanel() {
			color = Color.PINK;
			this.setOpaque(true);
			this.setBackground(new Color(225, 225, 255));
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int x = e.getX();
					int y = e.getY();
					flores.add(new Flor(x, y, color));
					revalidate();
					repaint();
				}
			});
		}

		public void dibujarEnCoordenada(int x, int y) {
			flores.add(new Flor(x, y, color));
			repaint();
			revalidate();
		}
		
		public void borrarTodo() {
			flores.clear();
			revalidate();
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			for(Flor flor: flores) {
				flor.Paint(g2d);
			}
		}

		public void setColor(Color color) {
			this.color = color;
		}
		

	}
	
	class Flor {
		private int x, y;
		private Color color;
		
		public Flor(int x, int y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
		
		public void Paint(Graphics2D g2d) {
			double radio = 40;
			double alturaPetalos = 50;
			double anchoPetalos = 2;
			double numPetalos = 2.5;
			
			Path2D rosa = new Path2D.Double();
		    for (double theta = 0; theta <= 2 * Math.PI; theta += 0.01) {
	            double r = rosaPolar(radio, alturaPetalos, numPetalos, anchoPetalos, theta);
	            Point2D punto = puntoPolar(r, theta);
	            
	            if (theta==0) {
	                rosa.moveTo(punto.getX(), punto.getY());
	            } else {
	                rosa.lineTo(punto.getX(), punto.getY());
	            }
	        }
		    
		    int newX = x-(rosa.getBounds().width/2);
		    int newY = y-(rosa.getBounds().height/2);
		    rosa.transform(AffineTransform.getTranslateInstance(newX, newY));
		    
		    Path2D tallo = new Path2D.Double();
		    tallo.moveTo(x+5, y+50);
		    tallo.curveTo(x-20, y+60, x-20, y+100, x, y+150);
		    
		    g2d.setStroke(new BasicStroke(10f));
		    g2d.setColor(Color.GREEN);
		    g2d.draw(tallo);
		    
		    g2d.setColor(color);
		    g2d.fill(rosa);
		    
		    g2d.setColor(Color.YELLOW);
		    g2d.fillOval(x-15, y-15, 60, 60);
		    
		}
		
		private double rosaPolar(double radio, double alturaPetalos, double numPetalos, double anchoPetalos, double theta) {
			return radio+alturaPetalos*Math.pow(Math.cos(numPetalos*theta), anchoPetalos);
		}
		
		private Point2D puntoPolar(double r, double theta) {
			int x = (int) Math.floor(r*Math.cos(theta));
			int y = (int) Math.floor(r*Math.sin(theta));
			return new Point(x+100, y+100);
		}
	}
}
