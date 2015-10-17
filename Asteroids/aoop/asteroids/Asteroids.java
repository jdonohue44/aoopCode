package aoop.asteroids;

import aoop.asteroids.gui.AsteroidsFrame;
import aoop.asteroids.gui.AsteroidsPanel;
import aoop.asteroids.gui.MenuPanel;
import aoop.asteroids.gui.NetworkInfoPanel;
import aoop.asteroids.gui.Player;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiplayerGame;
import aoop.asteroids.model.Spaceship;
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
				AsteroidsPanel asteroidsPanel = frame.getAsteroidsPanel();
				asteroidsPanel.startGame(new Game());
				Game game = asteroidsPanel.getGame();
				
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
				AsteroidsPanel asteroidsPanel = frame.getAsteroidsPanel();
				asteroidsPanel.startGame(new MultiplayerGame());
				MultiplayerGame game = (MultiplayerGame) asteroidsPanel.getGame();
				asteroidsPanel.setGame(game);
				
				Server server = new Server(game);
				Thread serverThread = new Thread(server);
				serverThread.start(); 
				
				Player player = new Player ();
				frame.addKeyListener(player);
				game.linkController (player);
				
				Thread t = new Thread(game);
				t.start();
				try {
					t.join();
					server.getServerSocket().close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if(gameId == 2){ // join
				AsteroidsPanel asteroidsPanel = frame.getAsteroidsPanel();
				
//				NetworkInfoPanel nip = frame.getMenuPanel().getNip();
//				String host = nip.getHost();
//				int port = nip.getPort();
				
				Joiner joiner = new Joiner("localhost", 58762);
				Thread clientThread = new Thread(joiner);
				clientThread.start();
				
				MultiplayerGame game = (MultiplayerGame) asteroidsPanel.getGame();
				Spaceship s = new Spaceship();
				game.addSpaceship(s);
				
				Player player = new Player ();
				player.addShip(s);
				frame.addKeyListener(player);
				game.linkController (player);

				try {
					clientThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if (gameId == 3){ // spectate
				AsteroidsPanel asteroidsPanel = frame.getAsteroidsPanel();
//				NetworkInfoPanel nip = frame.getMenuPanel().getNip();
//				String host = nip.getHost();
//				int port = nip.getPort();

				Spectator spectator = new Spectator("localhost", 58762);
				Thread clientThread = new Thread(spectator);
				clientThread.start();
				
				Game game = new SpectateGame(spectator);
				asteroidsPanel.startGame(game);
				Thread t = new Thread(game);
				t.start();
				
				try {
					t.join();
					if(frame.getMenuPanel().getGameId() != -1){
						frame.getMenuPanel().setGameId(-1);
						frame.getCardLayout().show(frame.getCardPanel(), "connectionErrorPanel");
					}
					else{
						spectator.setSpectating(false);
					}
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
//		Server s = new Server(g, "localhost", 4453);
//		Thread st = new Thread(s);
//		st.start();
//		
//		Spectator client = new Spectator("localhost", 4453);
//		Thread ct = new Thread(client);
//		ct.start();
	}
	
}
