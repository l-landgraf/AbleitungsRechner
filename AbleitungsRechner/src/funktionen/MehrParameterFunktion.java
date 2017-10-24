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
	public void zeichen(Graphics g, int x, int y) {
		debuggen(g, x, y);
		int xpos = x - halbeBreite();
		for (int i = 0; i < this.f.size(); i++) {
			this.f.get(i).zeichen(g, xpos + this.f.get(i).halbeBreite(), y);
			xpos += this.f.get(i).breite();
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
			s += d.breite();
		}
		return s + (this.f.size() - 1) * länge(zeichen());
	}

	@Override
	public int höhe() {
		int max = HauptFenster.SCHRIFT.getSize();
		for (Funktion d : this.f) {
			if (d.höhe() > max) {
				max = d.höhe();
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
	public Funktion geklickt(int x, int y, int xKlick, int yKlick) {
		int xpos = x - halbeBreite();
		for (int i = 0; i < this.f.size(); i++) {
			Funktion s = this.f.get(i).geklickt(xpos + this.f.get(i).halbeBreite(), y, xKlick,
					yKlick);
			xpos += this.f.get(i).breite() + länge(zeichen());
			if (s != null) {
				return s;
			}
		}

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

		int xpos = x - halbeBreite();
		for (int i = 0; i < this.f.size(); i++) {
			this.f.get(i).highlite(f, g, xpos + this.f.get(i).halbeBreite(), y);
			xpos += this.f.get(i).breite() + länge(zeichen());
		}
	}

	public List<Funktion> getF() {
		return this.f;
	}

	public void setF(List<Funktion> f) {
		this.f = f;
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

	@Override
	public void zusammenfügen() {
		List<Funktion> neuF = new ArrayList<>();
		for (int i = 0; i < this.f.size(); i++) {
			Funktion funkt = this.f.get(i);
			funkt.zusammenfügen();
			if (funkt instanceof MehrParameterFunktion) {
				neuF.addAll(((MehrParameterFunktion) funkt).f);
			} else {
				neuF.add(funkt);
			}
		}
		this.f = neuF;
	}

	public Funktion remove(Funktion markiert) {
		List<Funktion> f = new ArrayList<>();
		for (Funktion g : this.f) {
			f.add(g.kopieren());
		}
		f.remove(markiert);
		return neu(f);
	}
}
