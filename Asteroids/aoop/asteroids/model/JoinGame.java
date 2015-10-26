package aoop.asteroids.model;

import java.util.ArrayList;

import aoop.asteroids.Joiner;
import aoop.asteroids.gui.Player;

public class JoinGame extends Game implements Runnable {

	Joiner joiner;
	
	public JoinGame(Joiner joiner){
		this.joiner = joiner;
		this.ship = joiner.getShip();
		this.ships.add(this.ship);
	}
	
	@Override
	protected synchronized void update ()
	{
		this.round = joiner.getRound();
		this.bullets = joiner.getBullets();
		this.asteroids = joiner.getAsteroids();
		this.explosions = joiner.getExplosions();
		
		// update ship positions
		ArrayList<Spaceship> incomingShips = new ArrayList<Spaceship>();
		boolean stillAlive = false;
		for(Spaceship s : joiner.getShips()){
			if(s.getId() == this.ship.getId()) {
				this.ship.setScore(s.getScore());
				stillAlive = true;
			}
			else incomingShips.add(s);
		}
		this.ships = incomingShips;
		
		if((!stillAlive) && (this.ship.stepsTilCollide() == 0)){
			this.ship.destroy();
		}
		else this.ships.add(this.ship);
		
		for(Spaceship s : this.ships) s.nextStep();
		for(Bullet b : this.bullets) b.nextStep();
		for(Asteroid a : this.asteroids) a.nextStep();
		for(Explosion e : this.explosions) e.nextStep();
		
		this.setChanged();
		this.notifyObservers();
		}
	
	@Override
	public void run ()
	{ // Update -> sleep -> update -> sleep -> etc...
		long executionTime, sleepTime;
		while (!this.aborted && this.joiner.isSpectating())
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
	
	public Spaceship getShip() {
		return this.ship;
	}



}
