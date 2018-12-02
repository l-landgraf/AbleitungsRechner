package funktionen;

import java.awt.Graphics;

import fenster.HauptFenster;

public abstract class KeinParameterFunktion extends Funktion {
	
	public KeinParameterFunktion() {}
	
	public abstract Funktion neu();
	
	@Override
	public int breite() {
		return länge(zeichen());
	}
	
	@Override
	public String toText() {
		return zeichen();
	}
	
	@Override
	public int höhe() {
		return HauptFenster.SCHRIFT.getSize();
	}
	
	@Override
	public void zeichnen(Graphics g, int x, int y, Funktion parent) {
		debuggen(g, x, y, parent);
		schreiben(zeichen(), x, y, g);
	}
	
	@Override
	public Funktion ersetzen(Funktion ersetzen, Funktion durch) {
		if (ersetzen == this) {
			return durch;
		} else {
			return neu();
		}
	}
	
	@Override
	public Funktion geklickt(int x, int y, int xKlick, int yKlick, Funktion parent) {
		if (kollision(x, y, xKlick, yKlick, parent)) {
			return this;
		} else {
			return null;
		}
	}
	
	@Override
	public void highlite(Funktion f, Graphics g, int x, int y, Funktion parent) {
		if (f == this) {
			umrandungZeichnen(g, x, y, parent);
			return;
		}
	}
	
	@Override
	public Funktion parent(Funktion child) {
		return null;
	}
	
	@Override
	public boolean brauchtKlammern(Funktion parent) {
		return false;
	}
}
