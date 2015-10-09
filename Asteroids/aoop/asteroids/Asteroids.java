package aoop.asteroids;

import java.net.InetAddress;
import java.net.UnknownHostException;

import aoop.asteroids.gui.AsteroidsFrame;
import aoop.asteroids.gui.MenuPanel;
import aoop.asteroids.gui.Player;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.SpectateGame;

/**
 *	Main class of the Asteroids program.
 *	<p>
 *	Asteroids is simple game, in which the player is represented by a small 
 *	spaceship. The goal is to destroy as many asteroids as possible and thus 
 *	survive for as long as possible.
 *
 *	@author Yannick Stoffers
 */
public class Asteroids 
{

	/** Constructs a new instance of the program. */
	public Asteroids ()
	{
		AsteroidsFrame frame = new AsteroidsFrame();
		MenuPanel menu = frame.getMenuPanel();
		
		while(true){
			int gameId = menu.getGameId();
			
			if(gameId == 0){ // single player game
				frame.getAsteroidsPanel().startGame();
				Game game = frame.getAsteroidsPanel().getGame();
				
				Server server = new Server(game, 4720);
				Thread gameServer = new Thread(server);
				gameServer.start();
				
				Player player = new Player ();
				frame.addKeyListener(player);
				game.linkController (player);
				
				Thread t = new Thread(game);
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if(gameId == 1){ // host multiplayer
				frame.getAsteroidsPanel().startGame();
				Game game = frame.getAsteroidsPanel().getGame();
				Player player = new Player ();
				frame.addKeyListener(player);
				game.linkController (player);
				Thread t = new Thread(game);
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if(gameId == 2){ // join
				frame.getAsteroidsPanel().startGame();
				Game game = frame.getAsteroidsPanel().getGame();
				Player player = new Player ();
				frame.addKeyListener(player);
				game.linkController (player);
				Thread t = new Thread(game);
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else { // spectate
				Spectator spectator = null;
				try {
					spectator = new Spectator(InetAddress.getByName("localhost"),4876);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				
				Thread client = new Thread(spectator);
				client.start();
				
				// paint the graphics for a singleplayer game
				frame.getAsteroidsPanel().startGame();
				Game game = new SpectateGame();
				Thread t = new Thread(game);
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** 
	 *	Main function.
	 *
	 *	@param args input arguments.
	 */
	public static void main (String [] args)
	{
//		new Asteroids ();
	}
	
}
