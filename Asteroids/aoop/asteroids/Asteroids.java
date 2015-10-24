package aoop.asteroids;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import aoop.asteroids.gui.AsteroidsFrame;
import aoop.asteroids.gui.AsteroidsPanel;
import aoop.asteroids.gui.MenuPanel;
import aoop.asteroids.gui.NetworkInfoPanel;
import aoop.asteroids.gui.Player;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.JoinGame;
import aoop.asteroids.model.MultiplayerGame;
import aoop.asteroids.model.Participant;
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
		
		// DB


		
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
//				String nickname = menu.getNp().getNickname();
				String nickname = "Host";
				
				asteroidsPanel.startGame(new MultiplayerGame(nickname));
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
				NetworkInfoPanel nip = menu.getNip();
//				String nickname = menu.getNp().getNickname();
//				String host = nip.getHost();
//				int port = nip.getPort();
				
				String nickname = "Client";
				String host = "localhost";
				int port = 57653;
				
				
				Joiner joiner = new Joiner(host, port, new Spaceship());
				Thread clientThread = new Thread(joiner);
				clientThread.start();
				
				JoinGame game = new JoinGame(nickname, joiner);
				asteroidsPanel.startGame(game);
				Thread t = new Thread(game);
				t.start();

				
				Player player = new Player ();
				frame.addKeyListener(player);
				game.linkController (player);
				
				try {
					t.join();
					if(menu.getGameId() != -1){
						menu.setGameId(-1);
						frame.getCardLayout().show(frame.getCardPanel(), "connectionErrorPanel");
					}
					else{
						joiner.setSpectating(false);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}

			else if (gameId == 3){ // spectate
				AsteroidsPanel asteroidsPanel = frame.getAsteroidsPanel();
				NetworkInfoPanel nip = menu.getNip();
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
					if(menu.getGameId() != -1){
						menu.setGameId(-1);
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
	}
	
}
