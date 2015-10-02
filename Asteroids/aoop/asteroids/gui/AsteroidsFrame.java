package aoop.asteroids.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import aoop.asteroids.model.Game;

/**
 *	AsteroidsFrame is a class that extends JFrame and thus provides a game 
 *	window for the Asteroids game.
 *
 *	@author Yannick Stoffers
 */
public class AsteroidsFrame extends JFrame
{

	/** serialVersionUID */
	public static final long serialVersionUID = 1L;

	/** Quit action. */
	private AbstractAction quitAction;

	/** New game action. */
	private AbstractAction newGameAction;
	
	/** New main menu action. */
	private AbstractAction mainMenuAction;

	/** The game model. */
	 Game game;

	/** The panel in which the game is painted. */
	 AsteroidsPanel asteroidsPanel;
	 MenuPanel menuPanel;
	 NetworkInfoPanel networkInfoPanel;
	 NicknamePanel nicknamePanel;
	 
	 
	 JPanel cardPanel;
	 CardLayout cardLayout = new CardLayout();
	/** 
	 *	Constructs a new Frame, requires a game model.
	 *
	 *	@param game game model.
	 *	@param controller key listener that catches the users actions.
	 */

	public AsteroidsFrame (Game game, Player controller)
	{
		this.game = game;
		this.initActions ();
		this.setTitle ("Asteroids");
		this.setSize (800, 800);
		this.addKeyListener (controller);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		// Menu Bar setup
		JMenuBar mb = new JMenuBar ();
		JMenu m = new JMenu ("Game");
		mb.add(m);
		mb.setBackground(Color.gray);
		m.setBackground(Color.gray);
		mb.setBorderPainted(true);
		m.add (this.quitAction);
		m.add (this.newGameAction);
		m.add (this.mainMenuAction);
		this.setJMenuBar (mb);
		
		// CardLayout setup for multiple JPanels
		cardPanel = new JPanel(cardLayout);
		asteroidsPanel = new AsteroidsPanel(this.game);
		menuPanel = new MenuPanel(game,cardPanel,cardLayout);
		
		cardPanel.add(asteroidsPanel, "asteroidsPanel");
		cardPanel.add(menuPanel,"menuPanel");
		
		cardLayout.show(cardPanel,"menuPanel");
		
		this.add(cardPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public AsteroidsPanel getAp() {
		return asteroidsPanel;
	}

	public void setAp(AsteroidsPanel ap) {
		this.asteroidsPanel = ap;
	}

	public MenuPanel getMp() {
		return menuPanel;
	}

	public void setMp(MenuPanel mp) {
		this.menuPanel = mp;
	}

	/** Quits the old game and starts a new one. */
	private void newGame ()
	{
		this.game.abort ();
		try
		{
			Thread.sleep(50);
		}
		catch (InterruptedException e)
		{
			System.err.println ("Could not sleep before initialing a new game.");
			e.printStackTrace ();
		}
		this.game.initGameData ();
	}

	/** Initializes the quit- and new game action. */
	private void initActions() 
	{
		// Quits the application
		this.quitAction = new AbstractAction ("Quit") 
		{
			public static final long serialVersionUID = 2L;

			@Override
			public void actionPerformed (ActionEvent arg0) 
			{
				System.exit(0);
			}
		};
		
		// Creates a new model
		this.newGameAction = new AbstractAction ("New Game") 
		{
			public static final long serialVersionUID = 3L;

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				cardLayout.show(cardPanel,"asteroidsPanel");
				AsteroidsFrame.this.newGame ();
			}
		};
		
		// Creates a new model
		this.mainMenuAction = new AbstractAction ("Main Menu") 
		{
			public static final long serialVersionUID = 4L;

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				AsteroidsFrame.this.game.abort();
				cardLayout.show(cardPanel,"menuPanel");
			}
		};

	}
}

