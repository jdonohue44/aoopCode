package aoop.asteroids.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	
	int gameId = -1;
	NetworkInfoPanel nip;
	NicknamePanel np;
	
	public MenuPanel(JPanel cardPanel, CardLayout cardLayout){
		this.setLayout(null);
		setBackground(Color.black);
		
		JLabel title = new JLabel("Asteriods", SwingConstants.CENTER);
		JButton singleplayerButton = new JButton("Singleplayer");
		JButton hostButton = new JButton("Host a Multiplayer Game");
		JButton joinButton = new JButton("Join a Multiplayer Game");
		JButton spectateButton = new JButton("Spectate a Game");
		
		title.setForeground(new Color(0,200,200));
		
		singleplayerButton.setBackground(Color.white);
		hostButton.setBackground(Color.white);
		joinButton.setBackground(Color.white);
		spectateButton.setBackground(Color.white);
		
		title.setBounds(100, 65, 600, 150);
		singleplayerButton.setBounds(200,250,400,75);
		hostButton.setBounds(200,350,400,75);
		joinButton.setBounds(200,450,400,75);
		spectateButton.setBounds(200,550,400,75);
		
		title.setFont(new Font("Impact", Font.BOLD, 130));
		singleplayerButton.setFont(new Font("Impact", Font.PLAIN, 40));
		hostButton.setFont(new Font("Impact", Font.PLAIN, 30));
		joinButton.setFont(new Font("Impact", Font.PLAIN, 30));
		spectateButton.setFont(new Font("Impact", Font.PLAIN, 40));
		
		singleplayerButton.setFocusable(false);
		hostButton.setFocusable(false);
		joinButton.setFocusable(false);
		spectateButton.setFocusable(false);
		
		this.add(title);
		this.add(singleplayerButton);
		this.add(hostButton);
		this.add(joinButton);
		this.add(spectateButton);
		
        singleplayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	MenuPanel.this.setGameId(0);
	    		cardLayout.show(cardPanel,"asteroidsPanel");
            }
        }); 
        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try {
//            		MenuPanel.this.setNp(new NicknamePanel());
                	MenuPanel.this.setGameId(1);
                	cardLayout.show(cardPanel,"asteroidsPanel");
            	} catch (UnsupportedOperationException u) {
            		MenuPanel.this.setGameId(-1);
	            }
            }
        }); 
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try {
//            		MenuPanel.this.setNip(new NetworkInfoPanel());
//	            	MenuPanel.this.setNp(new NicknamePanel());
	            	MenuPanel.this.setGameId(2);
	            	cardLayout.show(cardPanel,"asteroidsPanel");
            	} catch (UnsupportedOperationException u) {
            		MenuPanel.this.setGameId(-1);
	            }

            }
        }); 
        spectateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try {
		            MenuPanel.this.setNip(new NetworkInfoPanel());
		         	MenuPanel.this.setGameId(3);
		         	cardLayout.show(cardPanel,"asteroidsPanel");
            	} catch (UnsupportedOperationException u) {
            		MenuPanel.this.setGameId(-1);
            	}
           
            }
        }); 
	}
	
	public synchronized int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public NetworkInfoPanel getNip() {
		return nip;
	}

	public void setNip(NetworkInfoPanel nip) {
		this.nip = nip;
	}

	public NicknamePanel getNp() {
		return np;
	}

	public void setNp(NicknamePanel np) {
		this.np = np;
	}
}
