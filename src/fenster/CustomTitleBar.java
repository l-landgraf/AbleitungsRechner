package fenster;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class CustomTitleBar {
	public static void setCustomTitleBar(JFrame frame) {
		frame.setUndecorated(true);
		
		JPanel bar = new JPanel();
		frame.add(bar);
		bar.setLayout(new BorderLayout());
		
		JTextPane title = new JTextPane();
		title.setText("TST");
		bar.add(title, BorderLayout.WEST);
	}
	
}
