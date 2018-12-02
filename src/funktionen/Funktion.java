package funktionen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import fenster.HauptFenster;

public abstract class Funktion {
	private static final int KLAMMERN_BREITE = 6;
	private static final int KLAMMERN_HÖHE = 6;
	public static boolean debuggen = false;

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

	public abstract Funktion geklickt(int x, int y, int xKlick, int yKlick, Funktion parent);

	public abstract Funktion ersetzen(Funktion ersetzen, Funktion durch);

	public abstract void highlite(Funktion f, Graphics g, int x, int y, Funktion parent);

	public abstract Funktion kopieren();

	public abstract boolean gleich(Funktion f);

	public abstract double wert(double x);

	public abstract String zeichen();

	protected abstract void zeichnen(Graphics g, int x, int y, Funktion parent);

	protected abstract int breite();

	protected abstract int höhe();

	public void zeichnenKlammern(Graphics g, int x, int y, Funktion parent) {
		if (brauchtKlammern(parent)) {
			zeichnen(g, x, y, parent);
			klammernZeichnen(g, x, y, parent);
		} else {
			zeichnen(g, x, y, parent);
		}
	}

	public void debuggen(Graphics g, int x, int y, Funktion parent) {
		if (debuggen) {
			g.setColor(Color.BLUE);
			g.drawRect(x - halbeBreite(), y - halbeHöhe(), breite(), höhe());
			g.setColor(Color.BLACK);
		}
	}

	public void umrandungZeichnen(Graphics g, int x, int y, Funktion parent) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x - (halbeBreiteKlammern(parent)), y - (halbeHöheKlammern(parent)),
				breiteKlammern(parent), höheKlammern(parent));
		g.setColor(Color.BLACK);
	}

	private void klammernZeichnen(Graphics g, int x, int y, Funktion parent) {
		g.drawRect(x - halbeBreiteKlammern(parent), y - halbeHöheKlammern(parent), breiteKlammern(
				parent), höheKlammern(parent));
	}

	public boolean kollision(int x, int y, int xKlick, int yKlick, Funktion parent) {
		return (new Rectangle(x - (halbeBreiteKlammern(parent)), y - (halbeHöheKlammern(parent)),
				breiteKlammern(parent), höheKlammern(parent))).contains(xKlick, yKlick);
	}

	public int breiteKlammern(Funktion parent) {
		if (brauchtKlammern(parent)) {
			return breite() + KLAMMERN_BREITE + KLAMMERN_BREITE;
		} else {
			return breite();
		}
	}

	public int höheKlammern(Funktion parent) {
		if (brauchtKlammern(parent)) {
			return höhe() + KLAMMERN_HÖHE + KLAMMERN_HÖHE;
		} else {
			return höhe();
		}
	}

	public int halbeBreiteKlammern(Funktion parent) {
		int b = breiteKlammern(parent);
		if (b % 2 == 1) {
			return (b / 2) + 1;
		}
		return b / 2;
	}

	public int halbeHöheKlammern(Funktion parent) {
		int h = höheKlammern(parent);
		if (h % 2 == 1) {
			return (h / 2) + 1;
		}
		return h / 2;
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
}
