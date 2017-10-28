package funktionen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import fenster.HauptFenster;

public abstract class Funktion {
	private static boolean debuggen = true;
	
	public static int länge(String s) {
		Graphics g = (new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)).getGraphics();
		
		return g.getFontMetrics(HauptFenster.SCHRIFT).stringWidth(s);
	}
	
	public static void schreiben(String s, int x, int y, Graphics g) {
		g.drawString(s, x - (länge(s) / 2), y + (g.getFont().getSize() / 2));
	}
	
	public abstract boolean brauchtKlammern(Funktion parent);
	
	public abstract Funktion parent(Funktion child);
	
	public abstract Funktion ableitung();
	
	public abstract String toText();
	
	public abstract Funktion vereinfachen();
	
	public abstract void zeichen(Graphics g, int x, int y);
	
	public abstract Funktion geklickt(int x, int y, int xKlick, int yKlick);
	
	public abstract Funktion ersetzen(Funktion ersetzen, Funktion durch);
	
	public abstract void highlite(Funktion f, Graphics g, int x, int y);
	
	public abstract int breite();
	
	public abstract int höhe();
	
	public abstract Funktion kopieren();
	
	public abstract boolean gleich(Funktion f);
	
	public abstract double wert(double x);
	
	public abstract String zeichen();
	
	public void debuggen(Graphics g, int x, int y) {
		if (debuggen) {
			g.drawRect(x - (halbeBreite()), y - (halbeHöhe()), breite(), höhe());
		}
	}
	
	public void umrandungZeichnen(Graphics g, int x, int y) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x - (halbeBreite()), y - (halbeHöhe()), breite(), höhe());
		g.setColor(Color.BLACK);
	}
	
	public int halbeBreite() {
		int b = breite();
		if (b % 2 == 1) {
			return (b / 2) + 1;
		}
		return b / 2;
	}
	
	public int halbeHöhe() {
		int h = höhe();
		if (h % 2 == 1) {
			return (h / 2) + 1;
		}
		return h / 2;
	}
	
	public boolean kollision(int x, int y, int xKlick, int yKlick) {
		return (new Rectangle(x - (halbeBreite()), y - (halbeHöhe()), breite(), höhe()))
				.contains(xKlick, yKlick);
	}
}
