package funktionen.mehrParameter;

import java.util.ArrayList;
import java.util.List;

import funktionen.Funktion;
import funktionen.MehrParameterFunktion;
import funktionen.keinParameter.Konstante;

public class Addition extends MehrParameterFunktion {
	private ArrayList<Vorzeichen> vorzeichen;
	
	public Addition(Funktion... f) {
		super(f);
		this.vorzeichen = new ArrayList<>();
		for (int i = 0; i < f.length; i++) {
			this.vorzeichen.add(Vorzeichen.PLUS);
		}
	}
	
	public Addition(List<Funktion> f) {
		super(f);
		this.vorzeichen = new ArrayList<>();
		for (int i = 0; i < f.size(); i++) {
			this.vorzeichen.add(Vorzeichen.PLUS);
		}
	}
	
	public Addition(List<Funktion> f, ArrayList<Vorzeichen> vorzeichen) {
		super(f);
		this.vorzeichen = vorzeichen;
	}
	
	@Override
	public Funktion neu(List<Funktion> ersetzt) {
		return new Addition(ersetzt);
	}
	
	@Override
	public String zeichen() {
		return " + ";
	}
	
	private ArrayList<Funktion> vorzeichenauflösen() {
		ArrayList<Funktion> list = new ArrayList<>();
		for (int i = 0; i < this.f.size(); i++) {
			if (this.vorzeichen.get(i) == Vorzeichen.PLUS) {
				list.add(this.f.get(i));
			} else {
				list.add(new Multiplikation(new Konstante(-1), this.f.get(i)));
			}
		}
		
		return list;
	}
	
	@Override
	public Funktion ableitung() {
		List<Funktion> a = new ArrayList<>();
		for (Funktion f : vorzeichenauflösen()) {
			a.add(f.ableitung());
		}
		return new Addition(a);
	}
	
	public Funktion zusammenFuegen() {
		Addition add = new Addition();
		for (Funktion funkt : this.f) {
			if (funkt instanceof Addition) {
				add.f.addAll(((Addition) funkt).f);
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
		
		List<Funktion> ohneAddi = new ArrayList<>();
		for (Funktion funkt : vereinfacht) {
			if (funkt instanceof Addition) {
				ohneAddi.addAll(((Addition) funkt).f);
			} else {
				ohneAddi.add(funkt);
			}
		}
		
		List<Konstante> konstanten = new ArrayList<>();
		List<Funktion> ohneKonstanten = new ArrayList<>();
		
		for (Funktion funkt : ohneAddi) {
			if (funkt instanceof Konstante) {
				konstanten.add((Konstante) funkt);
			} else {
				ohneKonstanten.add(funkt);
			}
		}
		
		double d = 0;
		for (Konstante h : konstanten) {
			d += h.get();
		}
		
		if (ohneKonstanten.isEmpty() || d != 0) {
			ohneKonstanten.add(0, new Konstante(d));
		}
		
		if (ohneKonstanten.size() == 1) {
			return ohneKonstanten.get(0);
		}
		
		return new Addition(ohneKonstanten);
	}
	
	@Override
	public Funktion kopieren() {
		List<Funktion> u = new ArrayList<>();
		for (Funktion t : this.f) {
			u.add(t.kopieren());
		}
		return new Addition(u);
	}
	
	@Override
	public boolean gleich(Funktion f) {
		if (!(f instanceof Addition)) {
			return false;
		}
		
		Addition add = ((Addition) f);
		if (add.f.size() != this.f.size()) {
			return false;
		}
		
		List<Funktion> liste = new ArrayList<>();
		for (Funktion funk : add.f) {
			liste.add(funk.kopieren());
		}
		
		label: for (Funktion funk : this.f) {
			for (int i = 0; i < add.f.size(); i++) {
				if (add.f.get(i).gleich(funk)) {
					liste.remove(add.f.get(i));
					continue label;
				}
			}
			return false;
		}
		
		return true;
	}
	
	@Override
	public double wert(double x) {
		double i = 0;
		for (Funktion f : this.f) {
			i += f.wert(x);
		}
		return i;
	}
}
