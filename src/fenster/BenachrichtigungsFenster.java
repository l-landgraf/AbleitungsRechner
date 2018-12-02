package fenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BenachrichtigungsFenster extends JFrame implements ActionListener {
	
	public static void show(String titel, String nachricht, Color color, boolean autoDispose,
			HauptFenster haupt) {
		new BenachrichtigungsFenster(titel, nachricht, color, autoDispose, haupt);
	}
	
	private String nachricht;
	private Schirm schirm;
	private Color color;
	private boolean autoDispose;
	
	private BenachrichtigungsFenster(String titel, String nachricht, Color color,
			boolean autoDispose, HauptFenster haupt) {
		super(titel);
		this.nachricht = nachricht;
		this.schirm = new Schirm();
		this.color = color;
		this.autoDispose = autoDispose;
		setBounds(0, 0, 300, 150);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(haupt);
		setLayout(new BorderLayout());
		add(this.schirm, BorderLayout.CENTER);
		
		JPanel unteres = new JPanel();
		add(unteres, BorderLayout.SOUTH);
		unteres.setLayout(new BorderLayout());
		
		JPanel box = new JPanel();
		unteres.add(box, BorderLayout.SOUTH);
		
		Button b = new Button("  ok  ", haupt.themeColor);
		b.addActionListener(this);
		box.add(b, BoxLayout.X_AXIS);
		
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (autoDispose) {
					dispose();
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			
			}
		});
			
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					dispose();
				}
			}
		});
		setVisible(true);
	}
	
	private class Schirm extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(BenachrichtigungsFenster.this.color);
			g.fillRect(10, 10, 30, 30);
			g.setColor(Color.BLACK);
			g.setFont(new Font(g.getFont().getName(), Font.BOLD, g.getFont().getSize()));
			g.drawString(BenachrichtigungsFenster.this.nachricht, 50, 30);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
