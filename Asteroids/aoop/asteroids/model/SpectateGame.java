package aoop.asteroids.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import aoop.asteroids.Spectator;

public class SpectateGame extends Game implements Runnable {

	Spectator spectator;
	ArrayList<Spaceship> ships = new ArrayList<Spaceship>();
	
	public SpectateGame(Spectator spectator) {
		this.spectator = spectator;
	}

	@Override
	protected synchronized void update ()
	{
		this.ships = spectator.getShips();
		if(this.ships.size()>0) this.ship = this.ships.get(0);
		this.asteroids = spectator.getAsteroids();
		this.bullets = spectator.getBullets();
		
		for (Asteroid a : this.asteroids) a.nextStep ();
		for (Bullet b : this.bullets) b.nextStep ();
		for (Spaceship s : this.ships) s.nextStep();
		
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

