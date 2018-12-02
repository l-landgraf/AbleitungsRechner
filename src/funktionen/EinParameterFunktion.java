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
	public void zeichnen(Graphics g, int x, int y, Funktion parent) {
		debuggen(g, x, y, parent);
		schreiben(zeichen(), x - halbeBreite() + (l�nge(zeichen()) / 2), yPosA(y), g);
		this.a.zeichnenKlammern(g, xPosA(x, parent), yPosA(y), this);
	}

	@Override
	public int breite() {
		return this.a.breiteKlammern(this) + l�nge(zeichen());
	}

	@Override
	public int h�he() {
		return Math.max(HauptFenster.SCHRIFT.getSize(), this.a.h�heKlammern(this));
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
	public Funktion geklickt(int x, int y, int xKlick, int yKlick, Funktion parent) {
		Funktion o = this.a.geklickt(xPosA(x, parent), yPosA(y), xKlick, yKlick, this);
		if (o != null) {
			return o;
		} else if (kollision(x, y, xKlick, yKlick, parent)) {
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

		this.a.highlite(f, g, xPosA(x, parent), yPosA(y), parent);
	}

	protected int xPosA(int x, Funktion parent) {
		return x + halbeBreite() - this.a.halbeBreiteKlammern(parent);
	}

	protected int yPosA(int y) {
		return y;
	}

	@Override
	public boolean brauchtKlammern(Funktion parent) {
		return true;
	}
}
