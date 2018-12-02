package fenster;

import java.awt.Graphics;

import funktionen.Funktion;
import funktionen.keinParameter.Leer;

public class Anker {
	private static final int X = 50;
	private Funktion f;
	private String name;
	
	public Anker(String name) {
		this.name = name;
		this.f = new Leer();
	}
	
	public Anker(String name, Funktion f) {
		this.name = name;
		this.f = f;
	}
	
	public void zeichnen(Graphics g, int b, int h) {
		int y = h / 2;
		g.setFont(HauptFenster.SCHRIFT);
		Funktion.schreiben(this.name, X, y, g);
		this.f.zeichnenKlammern(g, X + (Funktion.länge(this.name) / 2) + (this.f.breiteKlammern(null) / 2), y, null);
	}
	
	public Funktion geklickt(int xKlick, int yKlick, int b, int h) {
		int y = h / 2;
		return this.f.geklickt(X + (Funktion.länge(this.name) / 2) + (this.f.breiteKlammern(null) / 2), y,
				xKlick, yKlick, null);
	}
	
	public void highlite(Funktion f, Graphics g, int b, int h) {
		int y = h / 2;
		this.f.highlite(f, g, X + (Funktion.länge(this.name) / 2) + (this.f.breiteKlammern(null) / 2), y,
				null);
	}
	
	public void ersetzen(Funktion markiert, Funktion funktion) {
		this.f = this.f.ersetzen(markiert, funktion);
	}
	
	public Funktion getF() {
		return this.f;
	}
	
	public void setF(Funktion f) {
		this.f = f;
	}
	
	public int breite() {
		return this.f.breiteKlammern(null) + Funktion.länge(this.name);
	}
	
	public int höhe() {
		return Math.max(this.f.höheKlammern(null), HauptFenster.SCHRIFT.getSize());
	}
	
	public Funktion parent(Funktion child) {
		return this.f.parent(child);
	}
}
