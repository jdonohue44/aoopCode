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
	HighScoresPanel hsp;
	
	public MenuPanel(JPanel cardPanel, CardLayout cardLayout){
		this.setLayout(null);
		setBackground(Color.black);
		
		JLabel title = new JLabel("Asteriods", SwingConstants.CENTER);
		JButton singleplayerButton = new JButton("Singleplayer");
		JButton hostButton = new JButton("Host a Multiplayer Game");
		JButton joinButton = new JButton("Join a Multiplayer Game");
		JButton spectateButton = new JButton("Spectate a Game");
		JButton highScoresButton = new JButton("High Scores");
		
		title.setForeground(new Color(0,200,200));
		
		singleplayerButton.setBackground(Color.white);
		hostButton.setBackground(Color.white);
		joinButton.setBackground(Color.white);
		spectateButton.setBackground(Color.white);
		highScoresButton.setBackground(Color.red);
		
		title.setBounds(100, 10, 600, 150);
		singleplayerButton.setBounds(20,350,350,75);
		hostButton.setBounds(20,450,350,75);
		joinButton.setBounds(420,350,350,75);
		spectateButton.setBounds(420,450,350,75);
		highScoresButton.setBounds(200,200,400,100);
		
		title.setFont(new Font("Impact", Font.BOLD, 130));
		singleplayerButton.setFont(new Font("Impact", Font.PLAIN, 30));
		hostButton.setFont(new Font("Impact", Font.PLAIN, 30));
		joinButton.setFont(new Font("Impact", Font.PLAIN, 30));
		spectateButton.setFont(new Font("Impact", Font.PLAIN, 30));
		highScoresButton.setFont(new Font("Impact", Font.PLAIN, 40));
		
		singleplayerButton.setFocusable(false);
		hostButton.setFocusable(false);
		joinButton.setFocusable(false);
		spectateButton.setFocusable(false);
		highScoresButton.setFocusable(false);
		
		this.add(title);
		this.add(singleplayerButton);
		this.add(hostButton);
		this.add(joinButton);
		this.add(spectateButton);
		this.add(highScoresButton);
		
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
//		            MenuPanel.this.setNip(new NetworkInfoPanel());
		         	MenuPanel.this.setGameId(3);
		         	cardLayout.show(cardPanel,"asteroidsPanel");
            	} catch (UnsupportedOperationException u) {
            		MenuPanel.this.setGameId(-1);
            	}
           
            }
        }); 
        highScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try {
		         	cardLayout.show(cardPanel,"highScoresPanel");
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
	
	public void setHSP(HighScoresPanel hsp){
		this.hsp = hsp;
	}

	public NicknamePanel getNp() {
		return np;
	}

	public void setNp(NicknamePanel np) {
		this.np = np;
	}
}
