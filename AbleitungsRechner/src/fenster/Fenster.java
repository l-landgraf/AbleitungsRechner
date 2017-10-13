package fenster;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public abstract class Fenster extends JFrame {
	public Color themeColor = Color.BLACK;
	JPanel border = new JPanel();
	JPanel panel = new JPanel();
	
	public Fenster(String tile) {
		setUndecorated(true);
		setLayout(new BorderLayout());
		this.border = new JPanel();
		this.border.setLayout(new BorderLayout());
		this.border.setBorder(new LineBorder(new Color(24, 24, 24)));
		super.add(this.border, BorderLayout.CENTER);
		this.panel = new JPanel();
		this.border.add(this.panel, BorderLayout.CENTER);
		this.panel.setBackground(Color.BLACK);
		
		JPanel bar = new JPanel();
		bar.setBackground(this.themeColor);
		this.border.add(bar, BorderLayout.NORTH);
		bar.setLayout(new BorderLayout());
		
		Button close = new Button();
		close.setHoverBackgroundColor(new Color(232, 17, 35));
		close.setPressedBackgroundColor(new Color(139, 10, 20));
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		Button minimize = new Button();
		
		JPanel buttons = new JPanel();
		bar.add(buttons, BorderLayout.EAST);
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		
		buttons.add(minimize);
		buttons.add(close);
		
		JTextPane title = new JTextPane();
		title.setEditable(false);
		title.setFocusable(false);
		title.setForeground(Color.WHITE);
		title.setBackground(this.themeColor);
		title.setText("Rechner");
		bar.add(title, BorderLayout.WEST);
	}
	
	private class Button extends JButton {
		
		private Color hoverBackgroundColor;
		private Color pressedBackgroundColor;
		
		public Button() {
			super();
			super.setContentAreaFilled(false);
			setBackground(Fenster.this.themeColor);
			setBorder(new EmptyBorder(50, 50, 50, 50));
			setForeground(Color.WHITE);
			setFocusable(false);
			setPressedBackgroundColor(new Color(50, 50, 50));
			setHoverBackgroundColor(new Color(25, 25, 25));
			setPreferredSize(new Dimension(45, 29));
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			if (getModel().isPressed()) {
				g.setColor(this.pressedBackgroundColor);
			} else if (getModel().isRollover()) {
				g.setColor(this.hoverBackgroundColor);
			} else {
				g.setColor(getBackground());
			}
			g.fillRect(0, 0, 45, 29);
		}
		
		@Override
		public void setContentAreaFilled(boolean b) {}
		
		public Color getHoverBackgroundColor() {
			return this.hoverBackgroundColor;
		}
		
		public void setHoverBackgroundColor(Color hoverBackgroundColor) {
			this.hoverBackgroundColor = hoverBackgroundColor;
		}
		
		public Color getPressedBackgroundColor() {
			return this.pressedBackgroundColor;
		}
		
		public void setPressedBackgroundColor(Color pressedBackgroundColor) {
			this.pressedBackgroundColor = pressedBackgroundColor;
		}
		
	}
}
