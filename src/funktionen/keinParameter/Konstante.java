package funktionen.keinParameter;

import funktionen.Funktion;
import funktionen.KeinParameterFunktion;

public class Konstante extends KeinParameterFunktion {
	private String s;
	
	public Konstante(double d) {
		set(d);
	}
	
	public Konstante(String s) {
		this.s = s;
	}
	
	@Override
	public Funktion ersetzen(Funktion ersetzen, Funktion durch) {
		return super.ersetzen(ersetzen, durch);
	}
	
	@Override
	public Funktion neu() {
		return new Konstante(this.s);
	}
	
	@Override
	public String zeichen() {
		return this.s;
	}
	
	@Override
	public Funktion ableitung() {
		return new Konstante(0);
	}
	
	@Override
	public Funktion vereinfachen() {
		return new Konstante(Double.parseDouble(this.s));
	}
	
	public double get() {
		return Double.parseDouble(this.s);
	}
	
	public void set(double d) {
		if (((int) d) == d) {
			this.s = Integer.toString((int) d);
		} else {
			this.s = Double.toString(d);
		}
	}
	
	public String getS() {
		return this.s;
	}
	
	public void setS(String s) {
		this.s = s;
	}
	
	public boolean hinterPunkt() {
		return this.s.contains(".");
	}
	
	public boolean negatiev() {
		return this.s.contains("-");
	}
	
	@Override
	public Funktion kopieren() {
		return new Konstante(this.s.toString());
	}
	
	@Override
	public boolean gleich(Funktion f) {
		return f instanceof Konstante && ((Konstante) f).get() == get();
	}
	
	@Override
	public double wert(double x) {
		return get();
	}
}
