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
import aoop.asteroids.model.SpectateGame;

//import aoop.asteroids.model.Game;

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
	
	/** New main menu action. */
	private AbstractAction mainMenuAction;

	/** The game model. */

	/** The panel in which the game is painted. */
	 AsteroidsPanel asteroidsPanel;
	 MenuPanel menuPanel;
	 NetworkInfoPanel networkInfoPanel;
	 NicknamePanel nicknamePanel;
	 ConnectionErrorPanel connectionErrorPanel;
	 HighScoresPanel highScoresPanel;
	 
	 Game game;
	 
	 JPanel cardPanel;
	 CardLayout cardLayout = new CardLayout();
	/** 
	 *	Constructs a new Frame, requires a game model.
	 *
	 *	@param game game model.
	 *	@param controller key listener that catches the users actions.
	 */

	public AsteroidsFrame ()
	{
		//this.game = null;
		this.initActions ();
		this.setTitle ("Asteroids");
		this.setSize (800, 800);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		// Menu Bar setup
		JMenuBar mb = new JMenuBar ();
		JMenu m = new JMenu ("Game");
		mb.add(m);
		mb.setBackground(Color.gray);
		m.setBackground(Color.gray);
		mb.setBorderPainted(true);
		m.add (this.quitAction);
		m.add (this.mainMenuAction);
		this.setJMenuBar (mb);
		
		// CardLayout setup for multiple JPanels
		cardPanel = new JPanel(cardLayout);
		asteroidsPanel = new AsteroidsPanel();
		menuPanel = new MenuPanel(cardPanel,cardLayout);
		connectionErrorPanel = new ConnectionErrorPanel();
		highScoresPanel = new HighScoresPanel();
		cardPanel.add(asteroidsPanel, "asteroidsPanel");
		cardPanel.add(menuPanel,"menuPanel");
		cardPanel.add(connectionErrorPanel,"connectionErrorPanel");
		cardPanel.add(highScoresPanel, "highScoresPanel");
		cardLayout.show(cardPanel,"menuPanel");
		this.add(cardPanel);
		
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public Game getGame(){
		return this.game;
	}
	
	public CardLayout getCardLayout(){
		return this.cardLayout;
	}
	
	public JPanel getCardPanel(){
		return this.cardPanel;
	}
	
	public void setGame(Game g){
		this.game=g;
	}

	public AsteroidsPanel getAsteroidsPanel() {
		return asteroidsPanel;
	}

	public void setAsteroidsPanel(AsteroidsPanel ap) {
		this.asteroidsPanel = ap;
	}

	public MenuPanel getMenuPanel() {
		return this.menuPanel;
	}

	public void setMenuPanel(MenuPanel menuPanel) {
		this.menuPanel = menuPanel;
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
		this.mainMenuAction = new AbstractAction ("Main Menu") 
		{
			public static final long serialVersionUID = 4L;

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				AsteroidsFrame.this.getAsteroidsPanel().getGame().abort();
				menuPanel.setGameId(-1);
				cardLayout.show(cardPanel,"menuPanel");
			}
		};

	}
}

