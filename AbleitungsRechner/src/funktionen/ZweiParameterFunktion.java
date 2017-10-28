package funktionen;

import java.awt.Graphics;

public abstract class ZweiParameterFunktion extends Funktion {
	protected Funktion a;
	protected Funktion b;
	
	public Funktion getA() {
		return this.a;
	}
	
	public void setA(Funktion a) {
		this.a = a;
	}
	
	public Funktion getB() {
		return this.b;
	}
	
	public void setB(Funktion b) {
		this.b = b;
	}
	
	public ZweiParameterFunktion(Funktion a, Funktion b) {
		this.a = a;
		this.b = b;
	}
	
	public abstract Funktion neu(Funktion a, Funktion b);
	
	@Override
	public abstract String zeichen();
	
	@Override
	public String toText() {
		return "(" + this.a.toText() + ")" + zeichen() + "(" + this.b.toText() + ")";
	}
	
	@Override
	public void zeichen(Graphics g, int x, int y) {
		debuggen(g, x, y);
		this.a.zeichen(g, xPosA(x), yPosA(y));
		this.b.zeichen(g, xPosB(x), yPosB(y));
	}
	
	protected abstract int xPosA(int x);
	
	protected abstract int yPosA(int y);
	
	protected abstract int xPosB(int x);
	
	protected abstract int yPosB(int y);
	
	@Override
	public Funktion geklickt(int x, int y, int xKlick, int yKlick) {
		Funktion o = this.a.geklickt(xPosA(x), yPosA(y), xKlick, yKlick);
		Funktion s = this.b.geklickt(xPosB(x), yPosB(y), xKlick, yKlick);
		if (o != null) {
			return o;
		} else if (s != null) {
			return s;
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
		this.b.highlite(f, g, xPosB(x), yPosB(y));
	}
	
	@Override
	public Funktion ersetzen(Funktion ersetzen, Funktion durch) {
		if (ersetzen == this) {
			return durch;
		} else {
			return neu(this.a.ersetzen(ersetzen, durch), this.b.ersetzen(ersetzen, durch));
		}
	}
	
	@Override
	public Funktion parent(Funktion child) {
		if (this.a == child || this.b == child) {
			return this;
		}
		Funktion fa = this.a.parent(child);
		if (fa != null) {
			return fa;
		}
		return this.b.parent(child);
	}
	
	@Override
	public boolean brauchtKlammern(Funktion parent) {
		return true;
	}
}
