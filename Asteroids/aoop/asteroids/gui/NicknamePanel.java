package aoop.asteroids.gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class NicknamePanel extends JPanel {

	
	public NicknamePanel(){
		String nickname = JOptionPane.showInputDialog(this,"Nickname:");
	}
}
