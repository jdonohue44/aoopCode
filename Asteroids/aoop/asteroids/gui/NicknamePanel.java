package aoop.asteroids.gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class NicknamePanel extends JPanel {

	String nickname;
	public NicknamePanel(){
		String input = JOptionPane.showInputDialog(this,"Nickname:");
		if(!input.isEmpty()){
			this.nickname = input;
		}
		else{
			throw new UnsupportedOperationException("You did not enter a nickname.");
		}
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
