package funktionen.mehrParameter;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import funktionen.Funktion;
import funktionen.MehrParameterFunktion;
import funktionen.keinParameter.Konstante;

public class Addition extends MehrParameterFunktion {
	private List<Vorzeichen> vorzeichen;

	public Addition(Funktion... f) {
		super(f);
		this.vorzeichen = new ArrayList<>();
		for (int i = 0; i < f.length; i++) {
			this.vorzeichen.add(Vorzeichen.PLUS);
		}
	}

	public Addition(List<Funktion> f) {
		super(f);
		this.vorzeichen = new ArrayList<>();
		for (int i = 0; i < f.size(); i++) {
			this.vorzeichen.add(Vorzeichen.PLUS);
		}
	}

	public Addition(List<Funktion> f, List<Vorzeichen> vorzeichen) {
		super(f);
		this.vorzeichen = vorzeichen;
	}

	public Addition(Funktion a, Funktion b, Vorzeichen vor) {
		this.vorzeichen = new ArrayList<>();
		this.f = new ArrayList<>();
		this.f.add(a);
		this.f.add(b);
		this.vorzeichen.add(Vorzeichen.PLUS);
		this.vorzeichen.add(vor);
	}

	@Override
	public Funktion neu(List<Funktion> ersetzt) {
		return new Addition(ersetzt);
	}

	@Override
	public void zeichen(Graphics g, int x, int y) {
		debuggen(g, x, y);
		int xpos = x - halbeBreite();
		for (int i = 0; i < this.f.size(); i++) {
			String zeichen = this.vorzeichen.get(i).zeichen;
			if (!(i == 0 && zeichen == Vorzeichen.PLUS.zeichen)) {
				schreiben(zeichen, xpos + (länge(zeichen) / 2), y + yZeichenVerschiebung(), g);
				xpos += länge(zeichen);
			}
			this.f.get(i).zeichen(g, xpos + this.f.get(i).halbeBreite(), y);
			xpos += this.f.get(i).breite();
		}
	}

	@Override
	public int breite() {
		int s = 0;
		for (Funktion d : this.f) {
			s += d.breite();
		}
		for (int i = 0; i < this.vorzeichen.size(); i++) {
			if (!(i == 0 && this.vorzeichen.get(0) == Vorzeichen.PLUS)) {
				s += länge(this.vorzeichen.get(i).zeichen);
			}
		}

		return s;
	}

	@Override
	public Funktion geklickt(int x, int y, int xKlick, int yKlick) {
		int xpos = x - halbeBreite();
		for (int i = 0; i < this.f.size(); i++) {
			if (!(i == 0 && this.vorzeichen.get(0) == Vorzeichen.PLUS)) {
				xpos += länge(this.vorzeichen.get(i).zeichen);
			}
			Funktion s = this.f.get(i).geklickt(xpos + this.f.get(i).halbeBreite(), y, xKlick,
					yKlick);
			xpos += this.f.get(i).breite();
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
			if (!(i == 0 && this.vorzeichen.get(0) == Vorzeichen.PLUS)) {
				xpos += länge(this.vorzeichen.get(i).zeichen);
			}
			this.f.get(i).highlite(f, g, xpos + this.f.get(i).halbeBreite(), y);
			xpos += this.f.get(i).breite();
		}
	}

	@Override
	public Funktion ersetzen(Funktion ersetzen, Funktion durch) {
		if (ersetzen == this) {
			return durch;
		} else {
			List<Funktion> ersetzt = new ArrayList<>();
			List<Vorzeichen> vor = new ArrayList<>();
			for (int i = 0; i < this.f.size(); i++) {
				ersetzt.add(this.f.get(i).ersetzen(ersetzen, durch));
				vor.add(this.vorzeichen.get(i));
			}
			return new Addition(ersetzt, vor);
		}
	}

	private ArrayList<Funktion> vorzeichenauflösen() {
		ArrayList<Funktion> list = new ArrayList<>();
		for (int i = 0; i < this.f.size(); i++) {
			if (this.vorzeichen.get(i) == Vorzeichen.PLUS) {
				list.add(this.f.get(i));
			} else {
				list.add(new Multiplikation(new Konstante(-1), this.f.get(i)));
			}
		}

		return list;
	}

	@Override
	public Funktion ableitung() {
		List<Funktion> aufgelöst = vorzeichenauflösen();
		List<Funktion> a = new ArrayList<>();
		for (Funktion f : aufgelöst) {
			a.add(f.ableitung());
		}
		return new Addition(a);
	}

	@Override
	public Funktion vereinfachen() {
		List<Funktion> aufgelöst = vorzeichenauflösen();

		List<Funktion> vereinfacht = new ArrayList<>();
		for (Funktion funkt : aufgelöst) {
			vereinfacht.add(funkt.vereinfachen());
		}

		List<Funktion> ohneAddi = new ArrayList<>();
		for (Funktion funkt : vereinfacht) {
			if (funkt instanceof Addition) {
				ohneAddi.addAll(((Addition) funkt).f);
			} else {
				ohneAddi.add(funkt);
			}
		}

		List<Konstante> konstanten = new ArrayList<>();
		List<Funktion> ohneKonstanten = new ArrayList<>();

		for (Funktion funkt : ohneAddi) {
			if (funkt instanceof Konstante) {
				konstanten.add((Konstante) funkt);
			} else {
				ohneKonstanten.add(funkt);
			}
		}

		double d = 0;
		for (Konstante h : konstanten) {
			d += h.get();
		}

		if (ohneKonstanten.isEmpty() || d != 0) {
			ohneKonstanten.add(0, new Konstante(d));
		}

		if (ohneKonstanten.size() == 1) {
			return ohneKonstanten.get(0);
		}

		return new Addition(ohneKonstanten);
	}

	@Override
	public Funktion kopieren() {
		List<Funktion> u = new ArrayList<>();
		for (Funktion t : this.f) {
			u.add(t.kopieren());
		}
		List<Vorzeichen> h = new ArrayList<>();
		for (Vorzeichen v : this.vorzeichen) {
			h.add(v);
		}
		return new Addition(u, h);
	}

	@Override
	public boolean gleich(Funktion f) {
		if (!(f instanceof Addition)) {
			return false;
		}

		Addition add = ((Addition) f);
		if (add.f.size() != this.f.size()) {
			return false;
		}

		List<Funktion> liste = new ArrayList<>();
		for (Funktion funk : add.f) {
			liste.add(funk.kopieren());
		}

		label: for (int i = 0; i < this.f.size(); i++) {
			Funktion funk = this.f.get(i);
			Vorzeichen vor = this.vorzeichen.get(i);
			for (int e = 0; e < add.f.size(); e++) {
				if (add.f.get(e).gleich(funk) && add.vorzeichen.get(e) == vor) {
					liste.remove(add.f.get(e));
					continue label;
				}
			}
			return false;
		}

		return true;
	}

	@Override
	public double wert(double x) {
		double summ = 0;
		for (int i = 0; i < this.f.size(); i++) {
			if (this.vorzeichen.get(i) == Vorzeichen.PLUS) {
				summ += this.f.get(i).wert(x);
			} else {
				summ -= this.f.get(i).wert(x);
			}

		}
		return summ;
	}

	@Override
	public String zeichen() {
		return " + ";
	}

	@Override
	public void zusammenfügen() {
		List<Funktion> neuF = new ArrayList<>();
		List<Vorzeichen> neuV = new ArrayList<>();
		for (int i = 0; i < this.f.size(); i++) {
			Funktion funkt = this.f.get(i);
			funkt.zusammenfügen();
			if (funkt instanceof Addition) {
				neuF.addAll(((Addition) funkt).f);
				neuV.addAll(((Addition) funkt).vorzeichen);
			} else {
				neuF.add(funkt);
				neuV.add(this.vorzeichen.get(i));
			}
		}
		this.vorzeichen = neuV;
		this.f = neuF;
	}

	@Override
	public Funktion remove(Funktion markiert) {
		List<Funktion> f = new ArrayList<>();
		for (Funktion g : this.f) {
			f.add(g.kopieren());
		}

		List<Vorzeichen> v = new ArrayList<>();
		v.addAll(this.vorzeichen);
		int i = this.f.indexOf(markiert);
		f.remove(i);
		v.remove(i);
		return new Addition(f, v);
	}
}
