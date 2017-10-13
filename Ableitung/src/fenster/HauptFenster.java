package fenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import funktionen.Funktion;
import funktionen.keinParameter.Leer;

public class HauptFenster extends JFrame implements MouseListener {
	private static final String TITEL = "Rechner";
	private Schirm schirm;
	private JPanel knopfPanel;
	Color themeColor = Color.BLACK;
	public static Font SCHRIFT = new Font("Dialog.plain", Font.PLAIN, 15);
	
	private Anker anker;
	private Funktion markiert = null;
	private boolean bearbeiten;
	
	public HauptFenster() {
		super("Rechner");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 550, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		this.anker = new Anker("f(x) = ");
		this.markiert = this.anker.getF();
		this.bearbeiten = false;
		
		this.schirm = new Schirm();
		add(this.schirm, BorderLayout.CENTER);
		this.schirm.setBackground(Color.WHITE);
		this.schirm.addMouseListener(this);
		
		JPanel unteres;
		JPanel rechtes;
		JPanel linkes;
		JPanel oberes;
		
		unteres = new JPanel();
		unteres.setBackground(this.themeColor);
		unteres.setLayout(new BorderLayout());
		add(unteres, BorderLayout.SOUTH);
		
		this.knopfPanel = new JPanel();
		this.knopfPanel.setBackground(this.themeColor);
		this.knopfPanel.setLayout(new GridBagLayout());
		
		FunktionsKopfListener[] funktionsListener = FunktionsKopfListener.getListener(this);
		for (int i = 0; i < funktionsListener.length; i++) {
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.gridx = funktionsListener[i].getX();
			c.gridy = funktionsListener[i].getY();
			Button b = new Button(funktionsListener[i].getS(), this.themeColor);
			b.addActionListener(funktionsListener[i]);
			addKeyListener(funktionsListener[i]);
			this.knopfPanel.add(b, c);
		}
		
		ZahlenKnopfListener zahlenListnener = new ZahlenKnopfListener(this);
		addKeyListener(zahlenListnener);
		for (int i = 0; i < ZahlenKnopfListener.knopfTexte.length; i++) {
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.gridx = ZahlenKnopfListener.knopfXPos[i];
			c.gridy = ZahlenKnopfListener.knopfYPos[i];
			Button b = new Button(ZahlenKnopfListener.knopfTexte[i], this.themeColor);
			b.addActionListener(zahlenListnener);
			this.knopfPanel.add(b, c);
		}
		
		unteres.add(this.knopfPanel, BorderLayout.CENTER);
		
		rechtes = new JPanel();
		rechtes.setBackground(this.themeColor);
		rechtes.setLayout(new GridLayout(5, 1));
		unteres.add(rechtes, BorderLayout.EAST);
		
		linkes = new JPanel();
		linkes.setBackground(this.themeColor);
		linkes.setLayout(new GridLayout(5, 1));
		unteres.add(linkes, BorderLayout.WEST);
		
		Button ableiten = new Button("f´(x)", this.themeColor);
		ableiten.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ableiten();
			}
		});
		rechtes.add(ableiten);
		
		Button detailiertesableiten = new Button("f´(x) *", this.themeColor);
		detailiertesableiten.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				detainiertesAbleiten();
			}
		});
		rechtes.add(detailiertesableiten);
		
		Button vereinfachen = new Button("1 + 1 = 2", this.themeColor);
		vereinfachen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vereinfachen();
			}
		});
		rechtes.add(vereinfachen);
		
		Button löschen = new Button("C", this.themeColor);
		löschen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				löschen();
			}
		});
		rechtes.add(löschen);
		
		Button zeichnen = new Button("f(x) = ~*", this.themeColor);
		zeichnen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new GraphFenster(HauptFenster.this.anker.getF(), HauptFenster.this.themeColor);
			}
		});
		linkes.add(zeichnen);
		
		Button einsetzen = new Button("f(?)", this.themeColor);
		einsetzen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				einsetzen();
				
			}
		});
		linkes.add(einsetzen);
		
		setVisible(true);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Funktion f = this.anker.geklickt(x, y, this.schirm.getWidth(), this.schirm.getHeight());
		this.bearbeiten = false;
		this.markiert = f;
		repaint();
	}
	
	private void ableiten() {
		Funktion l = null;
		try {
			l = HauptFenster.this.anker.getF().vereinfachen().ableitung().vereinfachen();
			this.bearbeiten = false;
		} catch (Exception a) {
			// a.printStackTrace();
			BenachrichtigungsFenster.show("Error", "Beim ableiten ist ein Fehler aufgetreten!",
					Color.RED, true, HauptFenster.this);
			return;
		}
		
		new FunktionsVorschauFenster(new Anker("f´(x) = ", l), HauptFenster.this);
	}
	
	private void vereinfachen() {
		Funktion l = null;
		try {
			l = HauptFenster.this.anker.getF().vereinfachen();
			this.bearbeiten = false;
		} catch (Exception a) {
			BenachrichtigungsFenster.show("Error", "Beim vereinfachen ist ein Fehler aufgetreten!",
					Color.RED, true, HauptFenster.this);
			// a.printStackTrace();
			return;
		}
		
		new FunktionsVorschauFenster(new Anker("f(x) = ", l), HauptFenster.this);
	}
	
	private void löschen() {
		Funktion l = new Leer();
		HauptFenster.this.anker.setF(l);
		HauptFenster.this.markiert = l;
		this.bearbeiten = false;
		repaint();
	}
	
	private void detainiertesAbleiten() {
		Funktion l = null;
		try {
			l = HauptFenster.this.anker.getF().ableitung();
			this.bearbeiten = false;
		} catch (Exception a) {
			BenachrichtigungsFenster.show("Error", "Beim ableiten ist ein Fehler aufgetreten!",
					Color.RED, true, HauptFenster.this);
			return;
		}
		
		new FunktionsVorschauFenster(new Anker("f´(x) = ", l), HauptFenster.this);
	}
	
	private void einsetzen() {
		Funktion f = null;
		try {
			f = HauptFenster.this.anker.getF().kopieren().vereinfachen();
		} catch (Exception esd) {
			BenachrichtigungsFenster.show("Error", "Beim ensetzen ist ein Fehler aufgetreten!",
					Color.RED, true, HauptFenster.this);
			return;
		}
		String s = JOptionPane.showInputDialog(HauptFenster.this, "x =", "Einsetzen",
				JOptionPane.QUESTION_MESSAGE);
		if (s == null) {
			return;
		}
		int i = 0;
		try {
			i = Integer.parseInt(s);
			BenachrichtigungsFenster.show("Einsetzen", "f(" + i + ") = " + f.wert(i), Color.BLACK,
					false, HauptFenster.this);
		} catch (Exception esd) {
			BenachrichtigungsFenster.show("Error", "Beim ensetzen ist ein Fehler aufgetreten!",
					Color.RED, true, HauptFenster.this);
		}
	}
	
	private class Schirm extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			try {
				HauptFenster.this.anker.highlite(HauptFenster.this.markiert, g,
						HauptFenster.this.schirm.getWidth(), HauptFenster.this.schirm.getHeight());
			} catch (Exception ssdf) {}
			HauptFenster.this.anker.zeichnen(g, HauptFenster.this.schirm.getWidth(),
					HauptFenster.this.schirm.getHeight());
		}
	}
	
	public void setAnker(Anker a) {
		this.anker = a;
	}
	
	public Anker getAnker() {
		return this.anker;
	}
	
	public Funktion getMarkiert() {
		return this.markiert;
	}
	
	public void setMarkiert(Funktion markiert) {
		this.markiert = markiert;
	}
	
	public boolean isBearbeiten() {
		return this.bearbeiten;
	}
	
	public void setBearbeiten(boolean bearbeiten) {
		this.bearbeiten = bearbeiten;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
}
