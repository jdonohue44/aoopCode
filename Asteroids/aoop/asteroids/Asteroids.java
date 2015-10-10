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
		frame.getAsteroidsPanel().startGame(); //gives AsteroidsPanel something to paint
		
		while(true){
			int gameId = menu.getGameId();
			
			if(gameId == 0){ // single player game
				frame.getAsteroidsPanel().startGame();
				Game game = frame.getAsteroidsPanel().getGame();
				
				Spectator spec = new Spectator(4055);
				Thread clientThread = new Thread(spec);
				clientThread.start();
				Server server = new Server(game, "localhost",4055);
//				System.out.println(game.getPlayer().getLocation().getX());
				Thread serverThread = new Thread(server);
				serverThread.start();
				
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
			else if (gameId == 3){ // spectate
				Spectator spectator = null;
				try {
					spectator = new Spectator("localhost",4720);
				} catch (Exception e1) {
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
		new Asteroids ();
//		Game game = new Game();
//		Thread t = new Thread(game);
//		t.start();
//		Spectator spec = new Spectator(4955);
//		Thread clientThread = new Thread(spec);
//		clientThread.start();
//		Server server = new Server(game, "localhost",4955);
//		Thread serverThread = new Thread(server);
//		serverThread.start();
	}
	
}
