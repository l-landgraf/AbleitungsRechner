package funktionen.einParameter;

import funktionen.EinParameterFunktion;
import funktionen.Funktion;
import funktionen.keinParameter.Konstante;
import funktionen.mehrParameter.Multiplikation;

public class Sinus extends EinParameterFunktion {
	public Sinus(Funktion a) {
		super(a);
	}
	
	@Override
	public String zeichen() {
		return "sin ";
	}
	
	@Override
	public Funktion ableitung() {
		return new Multiplikation(this.a.ableitung(), new Cosinus(this.a.kopieren()));
	}
	
	@Override
	public Funktion vereinfachen() {
		Funktion av = this.a.vereinfachen();
		if (av instanceof Konstante) {
			return new Konstante(Math.log(((Konstante) av).get()));
		}
		return new Sinus(this.a.vereinfachen());
	}
	
	@Override
	protected Funktion neu(Funktion a) {
		return new Sinus(a);
	}
	
	@Override
	public Funktion kopieren() {
		return new Sinus(this.a.kopieren());
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Sinus && ((Sinus) f).a.gleich(this.a);
	}
	
	@Override
	public double wert(double x) {
		return Math.sin(this.a.wert(x));
	}
}