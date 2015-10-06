package aoop.asteroids.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aoop.asteroids.model.Game;
import aoop.asteroids.model.SpectateGame;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	
	int gameId;
	
	public MenuPanel(JPanel cardPanel, CardLayout cardLayout){
		setBackground(new Color(131, 154, 215));
		
		JButton singleplayerButton = new JButton("Singleplayer");
		JButton hostButton = new JButton("Host a Multiplayer Game");
		JButton joinButton = new JButton("Join a Multiplayer Game");
		JButton spectateButton = new JButton("Spectate a Game");
		
		singleplayerButton.setFocusable(false);
		hostButton.setFocusable(false);
		joinButton.setFocusable(false);
		spectateButton.setFocusable(false);
		
		this.add(singleplayerButton);
		this.add(hostButton);
		this.add(joinButton);
		this.add(spectateButton);
		
        singleplayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	MenuPanel.this.gameId = 0;
	    		cardLayout.show(cardPanel,"asteroidsPanel");
            }
        }); 
        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try{
            		MenuPanel.this.gameId = 1;
            		new NicknamePanel();  
            	}
            	catch(UnsupportedOperationException exc){
             	   JOptionPane.showMessageDialog(MenuPanel.this, exc.toString(),"Error", JOptionPane.ERROR_MESSAGE);
            	}
            }
        }); 
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {               
               try{
            	   MenuPanel.this.gameId = 2;
            	   new NetworkInfoPanel();
                   new NicknamePanel();
               }
               catch(UnsupportedOperationException exc){
            	   JOptionPane.showMessageDialog(MenuPanel.this, exc.toString(),"Error", JOptionPane.ERROR_MESSAGE);
               }
            }
        }); 
        spectateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try{
            	   MenuPanel.this.gameId = 3;
	               new NetworkInfoPanel();  
	               new NicknamePanel();
            	}
            	catch(UnsupportedOperationException exc){
             	   JOptionPane.showMessageDialog(MenuPanel.this, exc.toString(),"Error", JOptionPane.ERROR_MESSAGE);
            	}
            }
        }); 
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
}
