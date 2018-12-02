package funktionen.zweiParameter;

import fenster.HauptFenster;
import funktionen.Funktion;
import funktionen.ZweiParameterFunktion;
import funktionen.einParameter.Logarithmus;
import funktionen.keinParameter.E;
import funktionen.keinParameter.Konstante;
import funktionen.mehrParameter.Addition;
import funktionen.mehrParameter.Multiplikation;

public class Exponent extends ZweiParameterFunktion {

	public Exponent(Funktion a, Funktion b) {
		super(a, b);
	}

	@Override
	public Funktion neu(Funktion a, Funktion b) {
		return new Exponent(a, b);
	}

	@Override
	public Funktion ableitung() {
		return new Multiplikation(new Exponent(this.a.kopieren(), this.b.kopieren()), new Addition(
				new Multiplikation(this.b.ableitung(), new Division(new Logarithmus(this.a
						.kopieren()), new Logarithmus(new E()))), new Multiplikation(this.b
								.kopieren(), new Division(new Konstante(1d), this.a.kopieren()),
								this.a.ableitung())));
	}

	@Override
	public Funktion vereinfachen() {
		Funktion av = this.a.vereinfachen();
		Funktion bv = this.b.vereinfachen();
		if (av instanceof Konstante && bv instanceof Konstante) {
			return new Konstante(Math.pow(((Konstante) av).get(), ((Konstante) bv).get()));
		}

		if (bv instanceof Konstante) {
			double d = ((Konstante) bv).get();
			if (d == 0) {
				return new Konstante(1);
			} else if (d == 1) {
				return av;
			}
		}

		if (av instanceof Exponent) {
			return new Exponent(((Exponent) av).getA(), new Multiplikation(((Exponent) av).getB(),
					this.b.kopieren())).vereinfachen();
		}

		return new Exponent(av, bv);
	}

	@Override
	public int breite() {
		return this.a.breiteKlammern(this) + this.b.breiteKlammern(this);
	}

	@Override
	public int höhe() {
		return this.a.höheKlammern(this) + this.b.höheKlammern(this) - (HauptFenster.SCHRIFT
				.getSize() / 2);
	}

	@Override
	public String zeichen() {
		return "^";
	}

	@Override
	protected int xPosA(int x, Funktion parent) {
		return x - halbeBreite() + this.a.halbeBreiteKlammern(this);
	}

	@Override
	protected int yPosA(int y, Funktion parent) {
		return y + halbeHöhe() - this.a.halbeHöheKlammern(this);
	}

	@Override
	protected int xPosB(int x, Funktion parent) {
		return x + halbeBreite() - this.b.halbeBreiteKlammern(this);
	}

	@Override
	protected int yPosB(int y, Funktion parent) {
		return y - halbeHöhe() + this.b.halbeHöheKlammern(this);
	}

	@Override
	public Funktion kopieren() {
		return new Exponent(this.a.kopieren(), this.b.kopieren());
	}

	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Exponent && ((Exponent) f).a.gleich(this.a) && ((Exponent) f).b.gleich(
				this.b);
	}

	@Override
	public double wert(double x) {
		return Math.pow(this.a.wert(x), this.b.wert(x));
	}
}
