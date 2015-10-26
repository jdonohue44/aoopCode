package aoop.asteroids.gui;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ConnectionErrorPanel extends JPanel {
	
	public ConnectionErrorPanel(){
		this.setLayout(null);
		setBackground(Color.black);
		
		JLabel text1 = new JLabel("Connection Error", SwingConstants.CENTER);
		JLabel text2 = new JLabel("Please go back to the main menu", SwingConstants.CENTER);
		
		text1.setForeground(Color.white);
		text2.setForeground(Color.white);
		text1.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
		text2.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
		text1.setBounds(0,0,800,300);
		text2.setBounds(0,300,800,50);
		this.add(text1);
		this.add(text2);
	}
}
		
