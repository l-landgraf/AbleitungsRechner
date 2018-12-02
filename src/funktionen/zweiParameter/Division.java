package funktionen.zweiParameter;

import java.awt.Graphics;
import java.util.LinkedList;

import funktionen.Funktion;
import funktionen.ZweiParameterFunktion;
import funktionen.keinParameter.Konstante;
import funktionen.mehrParameter.Addition;
import funktionen.mehrParameter.Multiplikation;

public class Division extends ZweiParameterFunktion {

	private static final int LINIEN_HÖHE = 4;
	private static final int ÜBERSTAND = 3;

	public Division(Funktion a, Funktion b) {
		super(a, b);
	}

	@Override
	public Funktion neu(Funktion a, Funktion b) {
		return new Division(a, b);
	}

	@Override
	public Funktion ableitung() {
		return new Division(new Addition(new Multiplikation(this.a.ableitung(), this.b.kopieren()),
				new Multiplikation(new Konstante(-1.0d), this.a.kopieren(), this.b.ableitung())),
				new Exponent(this.b.kopieren(), new Konstante(2.0d)));
	}

	@Override
	public void zeichnen(Graphics g, int x, int y, Funktion parent) {
		super.zeichnen(g, x, y, parent);
		g.drawLine(x - (halbeBreite()), y - (halbeHöhe()) + this.a.höheKlammern(this) + (LINIEN_HÖHE
				/ 2), x + (halbeBreite()), y - (halbeHöhe()) + this.a.höheKlammern(this)
						+ (LINIEN_HÖHE / 2));
	}

	@Override
	public Funktion vereinfachen() {
		Funktion av = this.a.vereinfachen();
		Funktion bv = this.b.vereinfachen();

		// al, bl liste der faktoren unter/über dem bruchstrich
		LinkedList<Funktion> al = new LinkedList<>();
		LinkedList<Funktion> bl = new LinkedList<>();
		if (av instanceof Multiplikation) {
			for (Funktion f : ((Multiplikation) av).getF()) {
				al.add(f.kopieren());
			}
		} else {
			al.add(av.kopieren());
		}

		if (bv instanceof Multiplikation) {
			for (Funktion f : ((Multiplikation) bv).getF()) {
				bl.add(f.kopieren());
			}
		} else {
			bl.add(bv.kopieren());
		}

		LinkedList<Funktion> doppeltA = new LinkedList<>();
		LinkedList<Funktion> doppeltB = new LinkedList<>();

		// Brüche
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i) instanceof Division) {
				al.add(((Division) al.get(i)).getA());
				bl.add(((Division) al.get(i)).getB());
				doppeltA.add(al.get(i));
			}
		}

		for (int i = 0; i < bl.size(); i++) {
			if (bl.get(i) instanceof Division) {
				al.add(((Division) bl.get(i)).getB());
				bl.add(((Division) bl.get(i)).getA());
				doppeltB.add(bl.get(i));
			}
		}

		al.removeAll(doppeltA);
		bl.removeAll(doppeltB);

		// Kürzen
		doppeltA.clear();
		doppeltB.clear();

		for (Funktion fa : al) {
			for (Funktion fb : bl) {
				if (fa.gleich(fb) && !doppeltA.contains(fa) && !doppeltB.contains(fb)) {
					doppeltA.add(fa);
					doppeltB.add(fb);
				}
			}
		}

		al.removeAll(doppeltA);
		bl.removeAll(doppeltB);

		// Exponenten
		doppeltA.clear();
		doppeltB.clear();

		for (int ia = 0; ia < al.size(); ia++) {
			for (int ib = 0; ib < bl.size(); ib++) {
				Funktion atest = al.get(ia);
				double aExp = 1;
				if (al.get(ia) instanceof Exponent && ((Exponent) al.get(ia))
						.getB() instanceof Konstante) {
					atest = ((Exponent) al.get(ia)).getA();
					aExp = ((Konstante) ((Exponent) al.get(ia)).getB()).get();
				}

				Funktion btest = bl.get(ib);
				double bExp = 1;
				if (bl.get(ib) instanceof Exponent && ((Exponent) bl.get(ib))
						.getB() instanceof Konstante) {
					btest = ((Exponent) bl.get(ib)).getA();
					bExp = ((Konstante) ((Exponent) bl.get(ib)).getB()).get();
				}

				if (btest.gleich(atest) && !doppeltA.contains(atest) && !doppeltB.contains(btest)) {
					doppeltB.add(btest);
					doppeltA.add(atest);

					atest = new Exponent(atest, new Konstante(aExp - Math.min(aExp, bExp)));
					btest = new Exponent(btest, new Konstante(bExp - Math.min(aExp, bExp)));

					al.set(ia, atest.vereinfachen());
					bl.set(ib, btest.vereinfachen());
				}
			}
		}

		if (al.size() == 0) {
			av = new Konstante(1);
		} else if (al.size() == 1) {
			av = al.get(0);
		} else {
			av = new Multiplikation(al);
		}

		if (bl.size() == 0) {
			bv = new Konstante(1);
		} else if (bl.size() == 1) {
			bv = bl.get(0);
		} else {
			bv = new Multiplikation(bl);
		}

		if (bv instanceof Konstante && ((Konstante) bv).get() == 0) {
			int i = 1 / 0;
			return null;
		} else if (av instanceof Konstante && ((Konstante) av).get() == 0) {
			return new Konstante(0);
		} else if (bv instanceof Konstante && ((Konstante) bv).get() == 1) {
			// Division
			// durch
			// 1
			return av.kopieren();
		} else if (av instanceof Konstante && bv instanceof Konstante) {// Zwei
																		// Variablen
			return new Konstante(((Konstante) av).get() / ((Konstante) bv).get());
		} else {
			return new Division(av, bv);
		}
	}

	@Override
	public int breite() {
		return (Math.max(this.a.breiteKlammern(this), this.b.breiteKlammern(this)) + (2
				* ÜBERSTAND));
	}

	@Override
	public int höhe() {
		return this.a.höheKlammern(this) + this.b.höheKlammern(this) + LINIEN_HÖHE;
	}

	@Override
	public String zeichen() {
		return "^";
	}

	@Override
	protected int xPosA(int x, Funktion parent) {
		return x;
	}

	@Override
	protected int yPosA(int y, Funktion parent) {
		return y - halbeHöhe() + this.a.halbeHöheKlammern(this);
	}

	@Override
	protected int xPosB(int x, Funktion parent) {
		return x;
	}

	@Override
	protected int yPosB(int y, Funktion parent) {
		return y + halbeHöhe() - this.b.halbeHöheKlammern(this);
	}

	@Override
	public Funktion kopieren() {
		return new Division(this.a.kopieren(), this.b.kopieren());
	}

	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Division && ((Division) f).a.gleich(this.a) && ((Division) f).b.gleich(
				this.b);
	}

	@Override
	public double wert(double x) {
		return this.a.wert(x) / this.b.wert(x);
	}
}
