package aoop.asteroids.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import aoop.asteroids.model.Game;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	
	public MenuPanel(Game game, JPanel cardPanel, CardLayout cardLayout){
		setBackground(new Color(131, 154, 215));
		
		JButton singleplayerButton = new JButton("Singleplayer");
		JButton hostButton = new JButton("Host a Multiplayer Game");
		JButton joinButton = new JButton("Join a Multiplayer Game");
		JButton spectateButton = new JButton("Spectate a Multiplayer Game");
		
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
            	try
        		{
        			Thread.sleep(50);
        		}
        		catch (InterruptedException exc)
        		{
        			System.err.println ("Could not sleep before initialing a new game.");
        			exc.printStackTrace ();
        		}
	    		game.initGameData ();
	    		cardLayout.show(cardPanel,"asteroidsPanel");
            }
        }); 
        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               NicknamePanel nicknamePanel = new NicknamePanel();          
            }
        }); 
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {               
               try{
            	   NetworkInfoPanel networkInfoPanel = new NetworkInfoPanel();
                   NicknamePanel nicknamePanel = new NicknamePanel();
               }
               catch(UnsupportedOperationException exc){
            	   System.out.println(exc.getStackTrace());
               }
            }
        }); 
        spectateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try{
	               NetworkInfoPanel networkInfoPanel = new NetworkInfoPanel();  
	               NicknamePanel nicknamePanel = new NicknamePanel();
            	}
            	catch(UnsupportedOperationException exc){
            		System.out.println(exc.getStackTrace());
            	}
            }
        }); 
	}
}
