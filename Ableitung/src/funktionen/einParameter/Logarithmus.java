package funktionen.einParameter;

import funktionen.EinParameterFunktion;
import funktionen.Funktion;
import funktionen.keinParameter.Konstante;
import funktionen.mehrParameter.Multiplikation;

public class Logarithmus extends EinParameterFunktion {
	public Logarithmus(Funktion a) {
		super(a);
	}
	
	@Override
	public String zeichen() {
		return "log ";
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
		return new Logarithmus(this.a.vereinfachen());
	}
	
	@Override
	protected Funktion neu(Funktion a) {
		return new Logarithmus(a);
	}
	
	@Override
	public Funktion kopieren() {
		return new Logarithmus(this.a.kopieren());
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Logarithmus && ((Logarithmus) f).a.gleich(this.a);
	}
	
	@Override
	public double wert(double x) {
		return Math.log(this.a.wert(x));
	}
}