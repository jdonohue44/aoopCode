package aoop.asteroids;

import java.net.SocketException;

import aoop.asteroids.gui.AsteroidsFrame;
import aoop.asteroids.gui.MenuPanel;
import aoop.asteroids.gui.Player;
import aoop.asteroids.model.Game;

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
			else if(gameId == 1){ // host
				Server server;
				server = new Server();
				Thread serve = new Thread(server);
				serve.start();
				
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
				Spectator spectator = new Spectator("localhost", 4876);
				Thread client = new Thread(spectator);
				client.start();
				
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
		}
	}

	/** 
	 *	Main function.
	 *
	 *	@param args input arguments.
	 */
	public static void main (String [] args)
	{
		new Asteroids ();
	}
	
}
