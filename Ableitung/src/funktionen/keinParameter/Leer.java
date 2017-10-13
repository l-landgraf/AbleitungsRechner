package funktionen.keinParameter;

import funktionen.Funktion;
import funktionen.KeinParameterFunktion;

public class Leer extends KeinParameterFunktion {
	public Leer() {
		super();
	}
	
	@Override
	public Funktion neu() {
		return new Leer();
	}
	
	@Override
	public String zeichen() {
		return "?";
	}
	
	@Override
	public Funktion ableitung() {
		@SuppressWarnings("unused")
		int i = 1 / 0;
		return null;
	}
	
	@Override
	public Funktion vereinfachen() {
		@SuppressWarnings("unused")
		int i = 1 / 0;
		return null;
	}
	
	@Override
	public Funktion kopieren() {
		return new Leer();
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Leer;
	}
	
	@Override
	public double wert(double x) {
		@SuppressWarnings("unused")
		int i = 1 / 0;
		return 0;
	}
}
