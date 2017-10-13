package funktionen.mehrParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fenster.HauptFenster;
import funktionen.Funktion;
import funktionen.MehrParameterFunktion;
import funktionen.keinParameter.Konstante;
import funktionen.zweiParameter.Division;
import funktionen.zweiParameter.Exponent;

public class Multiplikation extends MehrParameterFunktion {
	public Multiplikation(Funktion... f) {
		super(f);
	}
	
	public Multiplikation(List<Funktion> f) {
		super(f);
	}
	
	@Override
	public String zeichen() {
		return " * ";
	}
	
	@Override
	protected int yZeichenVerschiebung() {
		return (HauptFenster.SCHRIFT.getSize() / 5);
	}
	
	@Override
	public Funktion neu(List<Funktion> ersetzt) {
		return new Multiplikation(ersetzt);
	}
	
	@Override
	public Funktion ableitung() {
		List<Funktion> summanten = new ArrayList<>();
		for (int i = 0; i < this.f.size(); i++) {
			List<Funktion> faktoren = new ArrayList<>();
			for (int e = 0; e < this.f.size(); e++) {
				if (e == i) {
					faktoren.add(this.f.get(e).ableitung());
				} else {
					faktoren.add(this.f.get(e).kopieren());
				}
			}
			summanten.add(new Multiplikation(faktoren));
		}
		return new Addition(summanten);
	}
	
	public Funktion zusammenFuegen() {
		Multiplikation add = new Multiplikation();
		for (Funktion funkt : this.f) {
			if (funkt instanceof Multiplikation) {
				add.f.addAll(((Multiplikation) funkt).f);
			} else {
				add.f.add(funkt);
			}
		}
		return add;
	}
	
	@Override
	public Funktion vereinfachen() {
		List<Funktion> vereinfacht = new ArrayList<>();
		for (Funktion funkt : this.f) {
			vereinfacht.add(funkt.vereinfachen());
		}
		
		// Multiplikationen zusammen fassen
		List<Funktion> ohneMulti = new ArrayList<>();
		for (Funktion funkt : this.f) {
			if (funkt instanceof Multiplikation) {
				ohneMulti.addAll(((Multiplikation) funkt).f);
			} else {
				ohneMulti.add(funkt);
			}
		}
		
		// Divisionen Entfernen
		List<Funktion> oben = new ArrayList<>();
		List<Funktion> unten = new ArrayList<>();
		boolean mitDivision = false;
		for (Funktion funkt : ohneMulti) {
			if (funkt instanceof Division) {
				oben.add(((Division) funkt).getA());
				unten.add(((Division) funkt).getB());
				mitDivision = true;
			} else {
				oben.add(funkt);
			}
		}
		
		if (mitDivision) {
			return new Division(new Multiplikation(oben), new Multiplikation(unten)).vereinfachen();
		}
		
		// Exponenten Zusammenfassen
		HashMap<Funktion, Funktion> exponenten = new HashMap<>();
		for (Funktion funkt : ohneMulti) {
			if (funkt instanceof Exponent) {
				boolean gefunden = false;
				for (Funktion e : exponenten.keySet()) {
					if (e.gleich(((Exponent) funkt).getA())) {
						exponenten.put(e, new Addition(exponenten.get(e),
								(((Exponent) funkt).getB()).kopieren()));
						gefunden = true;
						break;
					}
				}
				
				if (!gefunden) {
					exponenten.put(((Exponent) funkt).getA(),
							(((Exponent) funkt).getB()).kopieren());
				}
			} else {
				boolean gefunden = false;
				for (Funktion e : exponenten.keySet()) {
					if (e.gleich(funkt)) {
						exponenten.put(e, new Addition(exponenten.get(e), new Konstante(1)));
						gefunden = true;
						break;
					}
				}
				
				if (!gefunden) {
					exponenten.put(funkt, new Konstante(1));
				}
			}
		}
		
		List<Funktion> ohneExpo = new ArrayList<>();
		
		for (Funktion funkt : exponenten.keySet()) {
			ohneExpo.add(new Exponent(funkt, exponenten.get(funkt)).vereinfachen());
		}
		
		// Konstanten zusammen fassen
		List<Konstante> konstanten = new ArrayList<>();
		List<Funktion> ohneKonstanten = new ArrayList<>();
		
		for (Funktion funkt : ohneExpo) {
			if (funkt instanceof Konstante) {
				konstanten.add((Konstante) funkt);
			} else {
				ohneKonstanten.add(funkt);
			}
		}
		
		double d = 1;
		for (Konstante h : konstanten) {
			d *= h.get();
		}
		
		if (d == -1 && ohneKonstanten.size() == 1) {
			return new Addition(ohneKonstanten.get(0));
		}
		
		if (d == 0) {
			return new Konstante(0);
		}
		
		if (d != 1) {
			ohneKonstanten.add(0, new Konstante(d));
		}
		
		if (ohneKonstanten.size() == 1) {
			return ohneKonstanten.get(0);
		}
		
		return new Multiplikation(ohneKonstanten);
	}
	
	@Override
	public Funktion kopieren() {
		List<Funktion> u = new ArrayList<>();
		for (Funktion t : this.f) {
			u.add(t.kopieren());
		}
		return new Multiplikation(u);
	}
	
	@Override
	public boolean gleich(Funktion f) {
		if (!(f instanceof Multiplikation)) {
			return false;
		}
		
		Multiplikation mul = ((Multiplikation) f);
		if (mul.f.size() != this.f.size()) {
			return false;
		}
		
		List<Funktion> liste = new ArrayList<>();
		for (Funktion funk : mul.f) {
			liste.add(funk.kopieren());
		}
		
		label: for (Funktion funk : this.f) {
			for (int i = 0; i < mul.f.size(); i++) {
				if (mul.f.get(i).gleich(funk)) {
					liste.remove(mul.f.get(i));
					continue label;
				}
			}
			return false;
		}
		
		return true;
	}
	
	@Override
	public double wert(double x) {
		double i = 1;
		for (Funktion f : this.f) {
			i *= f.wert(x);
		}
		return i;
	}
}
