package aoop.asteroids.model;

import java.util.ArrayList;

import aoop.asteroids.Joiner;
import aoop.asteroids.gui.Player;

public class JoinGame extends MultiplayerGame implements Runnable {

	Joiner joiner;
	
	public JoinGame(String nickname, Joiner joiner){
		super(nickname);
		this.joiner = joiner;
		this.ship = joiner.getShip();
		this.ships.add(this.ship);
	}
	
	@Override
	protected synchronized void update ()
	{
		ArrayList<Spaceship> incomingShips = new ArrayList<Spaceship>();
		for(Spaceship s : joiner.getShips()){
			if(s.getId() != this.ship.getId()) incomingShips.add(s);
		}
		this.ships = incomingShips;
		this.ships.add(this.ship);
		this.bullets = joiner.getBullets();
		this.asteroids = joiner.getAsteroids();

		for(Spaceship s : this.ships) s.nextStep();
		for(Asteroid a : this.asteroids) a.nextStep();
		for(Bullet b : this.bullets) b.nextStep();

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



}
