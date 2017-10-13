package fenster;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class Button extends JButton {
	
	private Color hoverBackgroundColor;
	private Color pressedBackgroundColor;
	
	public Button(String text, Color color) {
		super(text);
		super.setContentAreaFilled(false);
		setBackground(color);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setForeground(Color.WHITE);
		setFocusable(false);
		setPressedBackgroundColor(new Color(50, 50, 50));
		setHoverBackgroundColor(new Color(25, 25, 25));
	}
	
	public Button(Color color) {
		super();
		super.setContentAreaFilled(false);
		setBackground(color);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setForeground(Color.WHITE);
		setFocusable(false);
		setPressedBackgroundColor(new Color(50, 50, 50));
		setHoverBackgroundColor(new Color(25, 25, 25));
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
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
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