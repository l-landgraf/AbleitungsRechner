package fenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import funktionen.Funktion;

public class GraphFenster extends JFrame {
	public static final int ANZEIGE_BREITE = 20;
	public static final int ANZEIGE_HÖHE = 20;
	private static final String TITEL = "Graph-Vorschau";
	GraphSchirm graph;
	FunktionsSchirm anzeige;
	
	public static int ZEICHEN_HÖHE = 200;
	public static int ZEICHEN_BREITE = 300;
	
	Anker anker;
	private Color themecolor;
	
	public GraphFenster(Funktion f, Color themecolor) {
		super(TITEL);
		this.anker = new Anker("f(x) = ", f.kopieren());
		this.graph = new GraphSchirm();
		this.anzeige = new FunktionsSchirm();
		this.themecolor = themecolor;
		
		setLayout(new BorderLayout());
		add(this.anzeige, BorderLayout.NORTH);
		add(this.graph, BorderLayout.CENTER);
		setBounds(0, 0, this.anzeige.getWidth() + ZEICHEN_BREITE,
				this.anzeige.getHeight() + ZEICHEN_HÖHE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private class GraphSchirm extends JPanel {
		private JTextField oben;
		private JTextField unten;
		private JTextField rechts;
		private JTextField links;
		
		public GraphSchirm() {
			this.oben = new JTextField();
			this.unten = new JTextField();
			this.rechts = new JTextField();
			this.links = new JTextField();
			
			this.oben.
					
					setLayout(new BorderLayout());
			add(this.oben, BorderLayout.NORTH);
			add(this.unten, BorderLayout.SOUTH);
			add(this.rechts, BorderLayout.EAST);
			add(this.links, BorderLayout.WEST);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			try {
				
			} catch (Exception ssdf) {}
			super.paintComponent(g);
		}
		
		private class TextFeld extends JTextField {
			
			public TextFeld(int zahl) {
				setText(Integer.toString(zahl));
			}
			
			public int getZahl() {
				return Integer.parseInt(getText());
			}
		}
	}
	
	private class FunktionsSchirm extends JPanel {
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(GraphFenster.this.anker.getF().breite() + ANZEIGE_BREITE,
					GraphFenster.this.anker.getF().höhe() + ANZEIGE_HÖHE);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(GraphFenster.this.themecolor);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.WHITE);
			GraphFenster.this.anker.zeichnen(g, GraphFenster.this.anzeige.getWidth(),
					GraphFenster.this.anzeige.getHeight());
		}
	}
}
