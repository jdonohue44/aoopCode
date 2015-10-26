package aoop.asteroids.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.shape.Circle;

import javax.swing.JPanel;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Explosion;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.JoinGame;
import aoop.asteroids.model.MultiplayerGame;
import aoop.asteroids.model.Spaceship;
import aoop.asteroids.model.SpectateGame;

/**
 *	AsteroidsPanel extends JPanel and thus provides the actual graphical 
 *	representation of the game model.
 *
 *	@author Yannick Stoffers
 */
public class AsteroidsPanel extends JPanel
{

	/** serialVersionUID */
	public static final long serialVersionUID = 4L;

	/** Game model. */
	private Game game;

	/** 
	 *	Constructs a new game panel, based on the given model.
	 *
	 *	@param game game model.
	 */
	
	public AsteroidsPanel(){
		this.game = new Game();
		this.game.addObserver (new Observer ()
		{
			@Override
			public void update (Observable o, Object arg)
			{
				AsteroidsPanel.this.repaint ();
			}
		});
	}
	
	public void startGame(Game game){
		this.game = game;
		this.game.addObserver (new Observer ()
		{
			@Override
			public void update (Observable o, Object arg)
			{
				AsteroidsPanel.this.repaint ();
			}
		});
	}
	
	public Game getGame(){
		return this.game;
	}
	
	public void setGame(Game g){
		this.game = g;
	}
	/**
	 *	Method for refreshing the GUI.
	 *
	 *	@param g graphics instance to use.
	 */
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent (g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.setBackground (Color.black);

		this.paintSpaceship (g2);
		this.paintAsteroids (g2);
		this.paintBullets (g2);
		this.paintExplosions (g2);

		
		int locationY = 20;
		for(Spaceship s : this.game.getShips()){
			g2.setColor (s.getColor());
			g2.drawString (s.getNickname()+": "+String.valueOf (s.getScore ()), 10, locationY);
			locationY += 20;
		}
		
		g2.setColor (Color.white);
		String s;
		FontMetrics fontMetrics = g2.getFontMetrics();
		
		if(this.game instanceof MultiplayerGame){
			if (((MultiplayerGame)this.game).getPort() != 0) {
				s = "Address: " + ((MultiplayerGame)this.game).getHost().getHostAddress();
				g2.drawString(s, 775 - fontMetrics.stringWidth(s), 20);
				s = "Port: " + ((MultiplayerGame)this.game).getPort();
				g2.drawString(s, 775 - fontMetrics.stringWidth(s), 40);
			}
			
			if (!((MultiplayerGame)this.game).isRunning()) {
				g2.setFont(new Font("Impact", Font.PLAIN, 30));
				fontMetrics = g2.getFontMetrics();
				s = "Waiting for other players to join...";
				g2.drawString (s, 400 - fontMetrics.stringWidth(s)/2, 100);
			}
		}
		
			if(this.game.getRound()!=0) {
				g2.setFont(new Font("Impact", Font.PLAIN, 30));
				fontMetrics = g2.getFontMetrics();
				s = "Round "+this.game.getRound();
				g2.drawString (s, 400 - fontMetrics.stringWidth(s)/2, 40);
			}
	}

	/**
	 *	Draws all bullets in the GUI as a yellow circle.
	 *
	 *	@param g graphics instance to use.
	 */
	private void paintBullets (Graphics2D g)
	{
		g.setColor(Color.yellow);

		for (Bullet b : this.game.getBullets ())
		    g.drawOval (b.getLocation ().x - 2, b.getLocation ().y - 2, 5, 5);
	}

	/**
	 *	Draws all asteroids in the GUI as a filled gray circle.
	 *
	 *	@param g graphics instance to use.
	 */
	private void paintAsteroids (Graphics2D g)
	{
		g.setColor (Color.GRAY);

		for (Asteroid a : this.game.getAsteroids ())
		{
			Ellipse2D.Double e = new Ellipse2D.Double ();
			e.setFrame (a.getLocation ().x - a.getRadius (), a.getLocation ().y - a.getRadius (), 2 * a.getRadius (), 2 * a.getRadius ());
			g.fill (e);
		}
	}
	
	private void paintExplosions (Graphics2D g){
		for (Explosion e : this.game.getExplosions()){	
			Ellipse2D.Double circle = new Ellipse2D.Double((int)(e.getLocation ().x-30), (int) e.getLocationY()-30, e.getRadius(), e.getRadius());
			g.setColor (Color.ORANGE);
			g.fill (circle);
			g.draw (circle);
			
			Ellipse2D.Double circle2 = new Ellipse2D.Double((int)(e.getLocation ().x-15), (int) e.getLocationY()-15, e.getRadius()/1.5, e.getRadius()/1.5);
			g.setColor (Color.RED);
			g.fill (circle2);
			g.draw (circle2);
		}
	}

	/**
	 *	Draws the player in the GUI as a see-through white triangle. If the 
	 *	player is accelerating a yellow triangle is drawn as a simple represen-
	 *	tation of flames from the exhaust.
	 *
	 *	@param g graphics instance to use.
	 */
	private void paintSpaceship (Graphics2D g)
	{
			for(Spaceship s : this.game.getShips()){
				// Draw body of the spaceship
				Polygon p = new Polygon ();
				p.addPoint ((int)(s.getLocation ().x + Math.sin (s.getDirection ()				  ) * 20), (int)(s.getLocation ().y - Math.cos (s.getDirection ()				 ) * 20));
				p.addPoint ((int)(s.getLocation ().x + Math.sin (s.getDirection () + 0.8 * Math.PI) * 20), (int)(s.getLocation ().y - Math.cos (s.getDirection () + 0.8 * Math.PI) * 20));
				p.addPoint ((int)(s.getLocation ().x + Math.sin (s.getDirection () + 1.2 * Math.PI) * 20), (int)(s.getLocation ().y - Math.cos (s.getDirection () + 1.2 * Math.PI) * 20));
	
				g.setColor (Color.BLACK);
				g.fill (p);
				g.setColor (s.getColor());
				g.draw (p);
	
				// Spaceship accelerating -> continue, otherwise abort.
				if (!s.isAccelerating ()) continue;
	
				// Draw flame at the exhaust
				p = new Polygon ();
				p.addPoint ((int)(s.getLocation ().x - Math.sin (s.getDirection ()				  ) * 25), (int)(s.getLocation ().y + Math.cos (s.getDirection ()				 ) * 25));
				p.addPoint ((int)(s.getLocation ().x + Math.sin (s.getDirection () + 0.9 * Math.PI) * 15), (int)(s.getLocation ().y - Math.cos (s.getDirection () + 0.9 * Math.PI) * 15));
				p.addPoint ((int)(s.getLocation ().x + Math.sin (s.getDirection () + 1.1 * Math.PI) * 15), (int)(s.getLocation ().y - Math.cos (s.getDirection () + 1.1 * Math.PI) * 15));
				g.setColor(Color.yellow);
				g.fill(p);
			}	
		}
	}
