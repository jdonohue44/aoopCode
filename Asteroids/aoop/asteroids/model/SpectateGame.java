package aoop.asteroids.model;

import java.awt.Color;

import aoop.asteroids.Spectator;

public class SpectateGame extends Game implements Runnable {

	Spectator spectator;
	
	public SpectateGame(Spectator spectator) {
		this.spectator = spectator;
	}

	@Override
	protected synchronized void update ()
	{
		this.round = spectator.getRound();
		this.ships = spectator.getShips();
		this.asteroids = spectator.getAsteroids();
		this.bullets = spectator.getBullets();
		this.explosions = spectator.getExplosions();
		
		for (Asteroid a : this.asteroids) a.nextStep ();
		for (Bullet b : this.bullets) b.nextStep ();
		for (Spaceship s : this.ships) s.nextStep();
		for (Explosion e : this.explosions) e.nextStep();
		
		this.setChanged ();
		this.notifyObservers ();
	}
	
	@Override
	public void run ()
	{ // Update -> sleep -> update -> sleep -> etc...
		long executionTime, sleepTime;
		while (!this.aborted && this.spectator.isSpectating())
		{
			if (!this.gameOver())
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

