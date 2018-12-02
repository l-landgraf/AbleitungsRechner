package funktionen.einParameter;

import funktionen.EinParameterFunktion;
import funktionen.Funktion;
import funktionen.keinParameter.Konstante;
import funktionen.mehrParameter.Multiplikation;
import funktionen.zweiParameter.Division;
import funktionen.zweiParameter.Exponent;

public class Tangens extends EinParameterFunktion {
	public Tangens(Funktion a) {
		super(a);
	}
	
	@Override
	public String zeichen() {
		return "tan ";
	}
	
	@Override
	public Funktion ableitung() {
		return new Multiplikation(this.a.ableitung(), new Division(new Konstante(1),
				new Exponent(new Cosinus(this.a.kopieren()), new Konstante(2.0d))));
	}
	
	@Override
	public Funktion vereinfachen() {
		Funktion av = this.a.vereinfachen();
		if (av instanceof Konstante) {
			return new Konstante(Math.tan(((Konstante) av).get()));
		}
		return new Tangens(this.a.vereinfachen());
	}
	
	@Override
	protected Funktion neu(Funktion a) {
		return new Tangens(a);
	}
	
	@Override
	public Funktion kopieren() {
		return new Tangens(this.a.kopieren());
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Tangens && ((Tangens) f).a.gleich(this.a);
	}
	
	@Override
	public double wert(double x) {
		return Math.tan(this.a.wert(x));
	}
}
