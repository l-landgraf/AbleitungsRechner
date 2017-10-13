package funktionen.einParameter;

import funktionen.EinParameterFunktion;
import funktionen.Funktion;
import funktionen.keinParameter.Konstante;
import funktionen.mehrParameter.Multiplikation;

public class Cosinus extends EinParameterFunktion {
	
	public Cosinus(Funktion f) {
		super(f);
	}
	
	@Override
	public String zeichen() {
		return "cos ";
	}
	
	@Override
	public Funktion ableitung() {
		return new Multiplikation(this.a.ableitung(), new Konstante(-1),
				new Sinus(this.a.kopieren()));
	}
	
	@Override
	public Funktion vereinfachen() {
		Funktion av = this.a.vereinfachen();
		if (av instanceof Konstante) {
			return new Konstante(Math.cos(((Konstante) av).get()));
		}
		return new Cosinus(this.a.vereinfachen());
	}
	
	@Override
	protected Funktion neu(Funktion a) {
		return new Cosinus(a);
	}
	
	@Override
	public Funktion kopieren() {
		return new Cosinus(this.a.kopieren());
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Cosinus && ((Cosinus) f).a.gleich(this.a);
	}
	
	@Override
	public double wert(double x) {
		return Math.cos(this.a.wert(x));
	}
}