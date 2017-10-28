package funktionen;

import java.awt.Graphics;

import fenster.HauptFenster;

public abstract class EinParameterFunktion extends Funktion {
	protected Funktion a;
	
	public Funktion getA() {
		return this.a;
	}
	
	public void setA(Funktion a) {
		this.a = a;
	}
	
	public EinParameterFunktion(Funktion x) {
		this.a = x;
	}
	
	protected abstract Funktion neu(Funktion a);
	
	@Override
	public String toText() {
		return zeichen() + "(" + this.a.toText() + ")";
	}
	
	@Override
	public void zeichen(Graphics g, int x, int y) {
		debuggen(g, x, y);
		schreiben(zeichen(), x - halbeBreite() + (länge(zeichen()) / 2), yPosA(y), g);
		this.a.zeichen(g, xPosA(x), yPosA(y));
	}
	
	@Override
	public int breite() {
		return this.a.breite() + länge(zeichen());
	}
	
	@Override
	public int höhe() {
		return Math.max(HauptFenster.SCHRIFT.getSize(), this.a.höhe());
	}
	
	@Override
	public Funktion ersetzen(Funktion ersetzen, Funktion durch) {
		if (ersetzen == this) {
			return durch;
		} else {
			return neu(this.a.ersetzen(ersetzen, durch));
		}
	}
	
	@Override
	public Funktion parent(Funktion child) {
		if (this.a == child) {
			return this;
		}
		return this.a.parent(child);
	}
	
	@Override
	public Funktion geklickt(int x, int y, int xKlick, int yKlick) {
		Funktion o = this.a.geklickt(xPosA(x), yPosA(y), xKlick, yKlick);
		if (o != null) {
			return o;
		} else if (kollision(x, y, xKlick, yKlick)) {
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
		
		this.a.highlite(f, g, xPosA(x), yPosA(y));
	}
	
	protected int xPosA(int x) {
		return x + halbeBreite() - this.a.halbeBreite();
	}
	
	protected int yPosA(int y) {
		return y;
	}
	
	@Override
	public boolean brauchtKlammern(Funktion parent) {
		return true;
	}
}
