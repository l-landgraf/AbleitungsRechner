package funktionen.keinParameter;

import funktionen.Funktion;
import funktionen.KeinParameterFunktion;

public class E extends KeinParameterFunktion {
	public E() {
		super();
	}
	
	@Override
	public Funktion neu() {
		return new E();
	}
	
	@Override
	public String zeichen() {
		return "e";
	}
	
	@Override
	public Funktion ableitung() {
		return new Konstante(0.0);
	}
	
	@Override
	public Funktion vereinfachen() {
		return new E();
	}
	
	@Override
	public Funktion kopieren() {
		return new E();
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof E;
	}
	
	@Override
	public double wert(double x) {
		return Math.E;
	}
}
