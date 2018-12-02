package fenster;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import funktionen.Funktion;
import funktionen.MehrParameterFunktion;
import funktionen.einParameter.Cosinus;
import funktionen.einParameter.Logarithmus;
import funktionen.einParameter.Sinus;
import funktionen.einParameter.Tangens;
import funktionen.keinParameter.E;
import funktionen.keinParameter.Konstante;
import funktionen.keinParameter.Leer;
import funktionen.keinParameter.Pi;
import funktionen.keinParameter.X;
import funktionen.mehrParameter.Addition;
import funktionen.mehrParameter.Multiplikation;
import funktionen.mehrParameter.Vorzeichen;
import funktionen.zweiParameter.Division;
import funktionen.zweiParameter.Exponent;

public abstract class FunktionsKopfListener implements ActionListener, KeyListener {
	public static FunktionsKopfListener[] getListener(HauptFenster fenster) {
		return new FunktionsKopfListener[] {
				
				new FunktionsKopfListener("+", 0, 0, fenster, '+') {
					@Override
					public void funktionMarkiert() {
						Leer l = new Leer();
						if (this.h.getMarkiert() instanceof Addition) {
							Addition add = (Addition) this.h.getMarkiert().kopieren();
							add.hinzufügen(l, Vorzeichen.PLUS);
							this.h.getAnker().ersetzen(this.h.getMarkiert(), add);
						} else {
							this.h.getAnker().ersetzen(this.h.getMarkiert(),
									new Addition(this.h.getMarkiert().kopieren(), l));
						}
						
						fenster.setMarkiert(l);
					}
					
					@Override
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(),
								new Addition(l, new Leer()));
						
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("-", 1, 0, fenster, '-') {
					@Override
					public void funktionMarkiert() {
						Leer l = new Leer();
						if (this.h.getMarkiert() instanceof Addition) {
							Addition add = (Addition) this.h.getMarkiert().kopieren();
							add.hinzufügen(l, Vorzeichen.MINUS);
							this.h.getAnker().ersetzen(this.h.getMarkiert(), add);
						} else {
							this.h.getAnker().ersetzen(this.h.getMarkiert(),
									new Addition(this.h.getMarkiert().kopieren(), l));
						}
						
						fenster.setMarkiert(l);
					}
					
					@Override
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(),
								new Addition(l, new Leer(), Vorzeichen.MINUS));
						
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("*", 2, 0, fenster, '*') {
					@Override
					public void funktionMarkiert() {
						Leer l = new Leer();
						if (this.h.getMarkiert() instanceof Multiplikation) {
							Multiplikation mul = (Multiplikation) this.h.getMarkiert().kopieren();
							mul.hinzufügen(l);
							this.h.getAnker().ersetzen(this.h.getMarkiert(), mul);
						} else {
							this.h.getAnker().ersetzen(this.h.getMarkiert(),
									new Addition(this.h.getMarkiert().kopieren(), l));
						}
						
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(),
								new Multiplikation(l, new Leer()));
						
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("a/b", 3, 0, fenster, '/') {
					@Override
					public void funktionMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(),
								new Division(this.h.getMarkiert().kopieren(), l));
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(),
								new Division(l, this.h.getMarkiert().kopieren()));
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("sin", 0, 1, fenster) {
					@Override
					public void funktionMarkiert() {
						Funktion l = new Sinus(this.h.getMarkiert().kopieren());
						this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(), new Sinus(l));
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("cos", 1, 1, fenster) {
					@Override
					public void funktionMarkiert() {
						Funktion l = new Cosinus(this.h.getMarkiert().kopieren());
						this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(), new Cosinus(l));
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("tan", 2, 1, fenster) {
					@Override
					public void funktionMarkiert() {
						Funktion l = new Tangens(this.h.getMarkiert().kopieren());
						this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(), new Tangens(l));
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("a^b", 3, 1, fenster, 130) {
					@Override
					public void funktionMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(),
								new Exponent(this.h.getMarkiert().kopieren(), l));
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(),
								new Exponent(l, this.h.getMarkiert().kopieren()));
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("asin", 0, 2, fenster) {
					@Override
					public void funktionMarkiert() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					
					public void leerMarkiert() {
						// TODO Auto-generated method stub
						
					}
				},
				
				new FunktionsKopfListener("acos", 1, 2, fenster) {
					@Override
					public void funktionMarkiert() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					
					public void leerMarkiert() {
						// TODO Auto-generated method stub
						
					}
				},
				
				new FunktionsKopfListener("atan", 2, 2, fenster) {
					@Override
					public void funktionMarkiert() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					
					public void leerMarkiert() {
						// TODO Auto-generated method stub
						
					}
				}, new FunktionsKopfListener("log", 3, 2, fenster) {
					@Override
					public void funktionMarkiert() {
						Funktion l = new Logarithmus(this.h.getMarkiert().kopieren());
						this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(), new Logarithmus(l));
						fenster.setMarkiert(l);
					}
				},
				
				new FunktionsKopfListener("e", 3, 3, fenster) {
					@Override
					public void funktionMarkiert() {
						Funktion l = new E();
						this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						funktionMarkiert();
					}
				},
				
				new FunktionsKopfListener("x", 3, 4, fenster) {
					@Override
					public void funktionMarkiert() {
						Funktion l = new X();
						this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
						fenster.setMarkiert(l);
					}
					
					@Override
					
					public void leerMarkiert() {
						funktionMarkiert();
					}
				},
				
				new FunktionsKopfListener("\u03c0", 3, 5, fenster) {
					@Override
					public void funktionMarkiert() {
						Funktion l = new Pi();
						this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
						fenster.setMarkiert(l);
						;
					}
					
					@Override
					
					public void leerMarkiert() {
						funktionMarkiert();
					}
				},
				
				new FunktionsKopfListener("entf", 2, 6, fenster, 127) {
					@Override
					public void funktionMarkiert() {
						Leer l = new Leer();
						this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
						fenster.setMarkiert(l);
						;
					}
					
					@Override
					
					public void leerMarkiert() {
						Funktion p = this.h.getAnker().parent(this.h.getMarkiert());
						if (p instanceof MehrParameterFunktion) {
							MehrParameterFunktion m = (MehrParameterFunktion) p;
							Funktion r = m.entfernen(this.h.getMarkiert());
							this.h.getAnker().ersetzen(m, r);
							this.h.setMarkiert(r);
							
						}
					}
				}, new FunktionsKopfListener(" - ", 3, 6, fenster) {
					@Override
					public void funktionMarkiert() {
						if (this.h.getMarkiert() instanceof Konstante) {
							Konstante k = (Konstante) this.h.getMarkiert();
							if (k.negatiev()) {
								k.setS(k.getS().replaceAll("-", ""));
							} else {
								k.setS("-" + k.getS());
							}
							this.h.getAnker().ersetzen(this.h.getMarkiert(), k);
							this.h.setMarkiert(k);
						} else {
							List<Vorzeichen> vor = new ArrayList<>();
							vor.add(Vorzeichen.MINUS);
							List<Funktion> funkt = new ArrayList<>();
							funkt.add(this.h.getMarkiert().kopieren());
							Addition add = new Addition(funkt, vor);
							this.h.getAnker().ersetzen(this.h.getMarkiert(), add);
							this.h.setMarkiert(add);
						}
					}
					
					@Override
					
					public void leerMarkiert() {
						Konstante k = new Konstante("-");
						this.h.getAnker().ersetzen(this.h.getMarkiert(), k);
						this.h.setBearbeiten(true);
						this.h.setMarkiert(k);
					}
				} };
	}
	
	protected String s;
	protected int x;
	protected int y;
	protected HauptFenster h;
	protected char[] hotKeys;
	protected int[] hotKeysIds;
	boolean hasChars = false;
	
	public FunktionsKopfListener(String s, int x, int y, HauptFenster h) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.s = s;
		this.hotKeys = new char[0];
		this.hotKeysIds = new int[0];
	}
	
	public FunktionsKopfListener(String s, int x, int y, HauptFenster h, char... hotKeys) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.s = s;
		this.hotKeys = hotKeys;
		this.hotKeysIds = new int[0];
		this.hasChars = true;
	}
	
	public FunktionsKopfListener(String s, int x, int y, HauptFenster h, int... hotKeysIds) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.s = s;
		this.hotKeysIds = hotKeysIds;
		this.hotKeys = new char[0];
	}
	
	protected void ausfueren() {
		if (this.h.getMarkiert() == null) {
			return;
		} else if (this.h.getMarkiert() instanceof Leer) {
			leerMarkiert();
		} else {
			funktionMarkiert();
		}
		this.h.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ausfueren();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (this.hasChars) {
			for (char c : this.hotKeys) {
				if (c == e.getKeyChar()) {
					ausfueren();
					return;
				}
			}
		} else {
			for (int i : this.hotKeysIds) {
				if (i == e.getKeyCode()) {
					ausfueren();
					return;
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	public abstract void funktionMarkiert();
	
	public abstract void leerMarkiert();
	
	public String getS() {
		return this.s;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
