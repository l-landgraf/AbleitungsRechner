package funktionen.keinParameter;

import funktionen.Funktion;
import funktionen.KeinParameterFunktion;

public class X extends KeinParameterFunktion {
	public X() {
		super();
	}
	
	@Override
	public Funktion neu() {
		return new X();
	}
	
	@Override
	public String zeichen() {
		return "x";
	}
	
	@Override
	public Funktion ableitung() {
		return new Konstante(1);
	}
	
	@Override
	public Funktion vereinfachen() {
		return new X();
	}
	
	@Override
	public Funktion kopieren() {
		return new X();
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof X;
	}
	
	@Override
	public double wert(double x) {
		return x;
	}
}
