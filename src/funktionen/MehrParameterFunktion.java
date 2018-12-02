package funktionen;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import fenster.HauptFenster;

public abstract class MehrParameterFunktion extends Funktion {
	protected List<Funktion> f;

	public MehrParameterFunktion(Funktion... f) {
		this.f = new ArrayList<>();
		for (int i = 0; i < f.length; i++) {
			this.f.add(f[i]);
		}
	}

	public MehrParameterFunktion(List<Funktion> f) {
		this.f = new ArrayList<>();
		this.f.addAll(f);
	}

	public List<Funktion> getF() {
		return this.f;
	}

	public void setF(List<Funktion> f) {
		this.f = f;
	}

	@Override
	public String toText() {
		String s = "";
		for (int i = 0; i < this.f.size(); i++) {
			if (i == 0) {
				s += "(" + this.f.get(i).toText() + ")";
			} else {
				s += zeichen() + "(" + this.f.get(i).toText() + ")";
			}
		}
		return s;
	}

	@Override
	public void zeichnen(Graphics g, int x, int y, Funktion parent) {
		debuggen(g, x, y, parent);
		int xpos = x - halbeBreite();
		for (int i = 0; i < this.f.size(); i++) {
			this.f.get(i).zeichnenKlammern(g, xpos + this.f.get(i).halbeBreiteKlammern(this), y,
					this);
			xpos += this.f.get(i).breiteKlammern(this);
			if (i != (this.f.size() - 1)) {
				schreiben(zeichen(), xpos + (länge(zeichen()) / 2), y + yZeichenVerschiebung(), g);
			}
			xpos += länge(zeichen());
		}
	}

	protected int yZeichenVerschiebung() {
		return 0;
	}

	@Override
	public int breite() {
		int s = 0;
		for (Funktion d : this.f) {
			s += d.breiteKlammern(this);
		}
		return s + (this.f.size() - 1) * länge(zeichen());
	}

	@Override
	public int höhe() {
		int max = HauptFenster.SCHRIFT.getSize();
		for (Funktion d : this.f) {
			if (d.höheKlammern(this) > max) {
				max = d.höheKlammern(this);
			}
		}
		return max;
	}

	@Override
	public Funktion ersetzen(Funktion ersetzen, Funktion durch) {

		if (ersetzen == this) {
			return durch;
		} else {
			List<Funktion> ersetzt = new ArrayList<>();
			for (Funktion e : this.f) {
				ersetzt.add(e.ersetzen(ersetzen, durch));
			}
			return neu(ersetzt);
		}
	}

	public abstract Funktion neu(List<Funktion> ersetzt);

	@Override
	public Funktion geklickt(int x, int y, int xKlick, int yKlick, Funktion parent) {
		int xpos = x - halbeBreite();
		for (int i = 0; i < this.f.size(); i++) {
			Funktion s = this.f.get(i).geklickt(xpos + this.f.get(i).halbeBreite(), y, xKlick,
					yKlick, this);
			xpos += this.f.get(i).breiteKlammern(this) + länge(zeichen());
			if (s != null) {
				return s;
			}
		}

		if (kollision(x, y, xKlick, yKlick, this)) {
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

		int xpos = x - halbeBreite();
		for (int i = 0; i < this.f.size(); i++) {
			this.f.get(i).highlite(f, g, xpos + this.f.get(i).halbeBreiteKlammern(this), y, this);
			xpos += this.f.get(i).breiteKlammern(this) + länge(zeichen());
		}
	}

	@Override
	public Funktion parent(Funktion child) {
		for (Funktion f : this.f) {
			if (f == child) {
				return this;
			}
		}

		for (Funktion f : this.f) {
			Funktion c = f.parent(child);
			if (c != null) {
				return c;
			}
		}
		return null;
	}

	public Funktion entfernen(Funktion markiert) {
		List<Funktion> f = new ArrayList<>();
		for (Funktion g : this.f) {
			f.add(g.kopieren());
		}
		f.remove(markiert);
		return neu(f);
	}

	public void hinzufügen(Funktion f) {
		this.f.add(f);
	}

	@Override
	public boolean brauchtKlammern(Funktion parent) {
		return true;
	}
}
