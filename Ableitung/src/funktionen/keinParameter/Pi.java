package funktionen.keinParameter;

import funktionen.Funktion;
import funktionen.KeinParameterFunktion;

public class Pi extends KeinParameterFunktion {
	public Pi() {
		super();
	}
	
	@Override
	public Funktion neu() {
		return new Pi();
	}
	
	@Override
	public String zeichen() {
		return "\u03c0";
	}
	
	@Override
	public Funktion ableitung() {
		return new Konstante(0.0);
	}
	
	@Override
	public Funktion vereinfachen() {
		return new Pi();
	}
	
	@Override
	public Funktion kopieren() {
		return new Pi();
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Pi;
	}
	
	@Override
	public double wert(double x) {
		return Math.PI;
	}
}
