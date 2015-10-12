package aoop.asteroids.gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NicknamePanel extends JPanel {

	String nickname;
	public NicknamePanel(){
		String input = JOptionPane.showInputDialog(this,"Nickname:");
		if (input == null) {
			throw new UnsupportedOperationException();
		}
		else if(!input.isEmpty()){
			this.nickname = input;
		}
		else{
			JOptionPane.showMessageDialog(this, "You did not enter a nickname", "Error", JOptionPane.ERROR_MESSAGE);
			throw new UnsupportedOperationException();
		}
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
