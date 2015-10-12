package aoop.asteroids.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NetworkInfoPanel extends JPanel {
	
	String hostname;
	int portname;

	public NetworkInfoPanel() throws UnsupportedOperationException{
		this.setLayout(new GridLayout(2,1));
		
		JTextField host = new JTextField(7);
		JTextField port = new JTextField(7);
		this.add(new JLabel("Host:"));
		this.add(host);
	
		this.add(new JLabel("\nPort:"));
		this.add(port);
		
		int result = JOptionPane.showConfirmDialog(null, this, "Please enter the network information.", JOptionPane.OK_CANCEL_OPTION);

		if((result == JOptionPane.OK_OPTION) && (!host.getText().isEmpty()) && (!port.getText().isEmpty())){
			this.hostname = host.getText();
			this.portname = Integer.parseInt(port.getText());
		}
		else if (result == JOptionPane.CANCEL_OPTION) {
			throw new UnsupportedOperationException();
		}
		else{
			JOptionPane.showMessageDialog(this, "You did not enter a value for the host name or port number.", "Error", JOptionPane.ERROR_MESSAGE);
			throw new UnsupportedOperationException();
		}
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPortname() {
		return portname;
	}

	public void setPortname(int portname) {
		this.portname = portname;
	}

}
