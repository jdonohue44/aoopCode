package aoop.asteroids.model;

import java.util.ArrayList;
import java.util.Collection;

import aoop.asteroids.Spectator;

public class SpectateGame extends Game implements Runnable {

	Spectator spectator;
	Spaceship ship;
	ArrayList<Asteroid> asteroids;
	ArrayList<Bullet> bullets;
	private boolean aborted;
	
	public SpectateGame(Spectator spectator) {
		this.ship = new Spaceship();
		this.asteroids = new ArrayList<Asteroid>();
		this.bullets = new ArrayList<Bullet>();
		this.spectator = spectator;
	}

	protected void update ()
	{
		for (Asteroid a : this.asteroids) a.nextStep ();
		for (Bullet b : this.bullets) b.nextStep ();
		this.ship.nextStep (spectator.getShipPositions()[0], spectator.getShipPositions()[1], spectator.getShipDirection(), spectator.isShipAccelerating(), spectator.getScore());
		this.setChanged ();
		this.notifyObservers ();
	}
	

	/** Sets all game data to hold the values of a new game. */
	public void initGameData ()
	{
		super.initGameData();
	}


	public Spaceship getPlayer ()
	{
		return this.ship.clone ();
	}

	public Collection <Asteroid> getAsteroids ()
	{
		Collection <Asteroid> c = new ArrayList <> ();
		for (Asteroid a : this.asteroids) c.add (a.clone ());
		return c;
	}

	public Collection <Bullet> getBullets ()
	{
		Collection <Bullet> c = new ArrayList <> ();
		for (Bullet b : this.bullets) c.add (b.clone ());
		return c;
	}
	
	private boolean gameOver ()
	{
		return this.ship.isDestroyed ();
	}
	
	public void run ()
	{ // Update -> sleep -> update -> sleep -> etc...
		long executionTime, sleepTime;
		while (!this.aborted)
		{
			if (!this.gameOver ())
			{
				executionTime = System.currentTimeMillis ();
				this.update ();
				executionTime -= System.currentTimeMillis ();
				sleepTime = 40 - executionTime;
			}
			else sleepTime = 100;

			try
			{
				Thread.sleep (sleepTime);
			}
			catch (InterruptedException e)
			{
				System.err.println ("Could not perfrom action: Thread.sleep(...)");
				System.err.println ("The thread that needed to sleep is the game thread, responsible for the game loop (update -> wait -> update -> etc).");
				e.printStackTrace ();
			}
		}
	}
}

