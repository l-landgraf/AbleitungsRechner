package fenster;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import funktionen.Funktion;
import funktionen.keinParameter.Konstante;

public class ZahlenKnopfListener implements ActionListener, KeyListener {
	HauptFenster h;
	
	public static final String[] knopfTexte = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"." };
	public static final int[] knopfInts = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	
	public static final char[] keyChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.',
			',' };
	public static final int[] keyInts = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10 };
	
	public static final String[] zeichen = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"." };
	
	public static final int knopfXPos[] = { 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1 };
	
	public static final int knopfYPos[] = { 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6 };
	
	public ZahlenKnopfListener(HauptFenster h) {
		this.h = h;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < this.keyChars.length; i++) {
			if (e.getKeyChar() == this.keyChars[i]) {
				gedruckt(this.zeichen[this.keyInts[i]]);
				return;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < this.knopfTexte.length; i++) {
			if (((Button) e.getSource()).getText().equals(this.knopfTexte[i])) {
				gedruckt(this.zeichen[this.knopfInts[i]]);
				return;
			}
		}
	}
	
	private void gedruckt(String s) {
		String temp = "";
		if (this.h.getMarkiert() instanceof Konstante && this.h.isBearbeiten()) {
			temp = ((Konstante) this.h.getMarkiert()).getS();
			if (!(((Konstante) this.h.getMarkiert()).hinterPunkt() && s.equals("."))) {
				temp += s;
			}
			
		} else {
			temp = s;
			this.h.setBearbeiten(true);
		}
		Funktion l = new Konstante(temp);
		this.h.getAnker().ersetzen(this.h.getMarkiert(), l);
		this.h.setMarkiert(l);
		this.h.repaint();
		
	}
}
