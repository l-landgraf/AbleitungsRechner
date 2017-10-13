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
	public void zeichen(Graphics g, int x, int y) {
		debuggen(g, x, y);
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
	public Funktion geklickt(int x, int y, int xKlick, int yKlick) {
		if (kollision(x, y, xKlick, yKlick)) {
			return this;
		} else {
			return null;
		}
	}
	
	@Override
	public void highlite(Funktion f, Graphics g, int x, int y) {
		if (f == this) {
			umrandungZeichnen(g, x, y);
			return;
		}
	}
}
