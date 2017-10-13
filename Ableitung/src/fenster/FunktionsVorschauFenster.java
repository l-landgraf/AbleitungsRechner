package fenster;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FunktionsVorschauFenster extends JFrame implements ActionListener {
	private static final String TITEL = "Funktionsvorschau";
	private Anker a;
	private Schirm schirm;
	private HauptFenster haupt;
	
	public FunktionsVorschauFenster(Anker a, HauptFenster haupt) {
		super(TITEL);
		this.a = a;
		this.schirm = new Schirm();
		this.haupt = haupt;
		setBounds(0, 0, 300, 200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		add(this.schirm, BorderLayout.CENTER);
		
		JPanel unteres = new JPanel();
		add(unteres, BorderLayout.SOUTH);
		unteres.setLayout(new BorderLayout());
		
		Button b = new Button("übernehmen", haupt.themeColor);
		b.addActionListener(this);
		unteres.add(b, BorderLayout.EAST);
		setVisible(true);
	}
	
	private class Schirm extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			try {
				FunktionsVorschauFenster.this.a.zeichnen(g,
						FunktionsVorschauFenster.this.schirm.getWidth(),
						FunktionsVorschauFenster.this.schirm.getHeight());
			} catch (Exception s) {
				
			}
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.haupt.getAnker().setF(this.a.getF());
		this.haupt.repaint();
	}
}
