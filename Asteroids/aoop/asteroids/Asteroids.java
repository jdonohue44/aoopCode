package aoop.asteroids;

import aoop.asteroids.gui.AsteroidsFrame;
import aoop.asteroids.gui.AsteroidsPanel;
import aoop.asteroids.gui.MenuPanel;
import aoop.asteroids.gui.NetworkInfoPanel;
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
		frame.getAsteroidsPanel().startGame(new Game()); //gives AsteroidsPanel something to paint
		
		while(true){
			int gameId = menu.getGameId();
			
			if(gameId == 0){ // single player game
				AsteroidsPanel singlePlayerPanel = frame.getAsteroidsPanel();
				singlePlayerPanel.startGame(new Game());
				Game game = singlePlayerPanel.getGame();
				
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
				AsteroidsPanel singlePlayerPanel = frame.getAsteroidsPanel();
				singlePlayerPanel.startGame(new Game());
				Game game = singlePlayerPanel.getGame();
				
				Server server = new Server(game, "localhost",4055);
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
			else if(gameId == 2){ // join
				frame.getAsteroidsPanel().startGame(new Game());
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
				AsteroidsPanel asteroidsPanel = frame.getAsteroidsPanel();
				NetworkInfoPanel nip = frame.getMenuPanel().getNip();
				String host = nip.getHost();
				int port = nip.getPort();

				Spectator spectator = new Spectator(host, port);
				Thread clientThread = new Thread(spectator);
				clientThread.start();
				
				Game game = new SpectateGame(spectator);
				asteroidsPanel.startGame(game);
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
		
//		Game g = new Game();
//		Thread gt = new Thread(g);
//		gt.start();
//		
//		Server s = new Server(g, "localhost", 4452);
//		Thread st = new Thread(s);
//		st.start();
//		
//		Spectator client = new Spectator("localhost", 4452);
//		Thread ct = new Thread(client);
//		ct.start();
	}
	
}
