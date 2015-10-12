package aoop.asteroids.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import aoop.asteroids.Spectator;

public class SpectateGame extends Game implements Runnable {

	Spectator spectator;
	ArrayList<Asteroid> asteroids;
	ArrayList<Bullet> bullets;
	Spaceship ship;

	private static Random rng;

	private int cycleCounter;

	private int asteroidsLimit;

	private boolean aborted;
	
	public SpectateGame() {
		try {
			this.spectator = new Spectator(4355);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ship = new Spaceship ();
}
	
	public Spectator getSpectator() {
		return spectator;
	}

	public void setSpectator(Spectator spectator) {
		this.spectator = spectator;
	}

	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}

	public void setAsteroids(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public Spaceship getShip() {
		return ship;
	}

	public void setShip(Spaceship ship) {
		this.ship = ship;
	}

	public static Random getRng() {
		return rng;
	}

	public static void setRng(Random rng) {
		SpectateGame.rng = rng;
	}

	public int getCycleCounter() {
		return cycleCounter;
	}

	public void setCycleCounter(int cycleCounter) {
		this.cycleCounter = cycleCounter;
	}

	public int getAsteroidsLimit() {
		return asteroidsLimit;
	}

	public void setAsteroidsLimit(int asteroidsLimit) {
		this.asteroidsLimit = asteroidsLimit;
	}

	public boolean isAborted() {
		return aborted;
	}

	public void setAborted(boolean aborted) {
		this.aborted = aborted;
	}
	
	public Spaceship getPlayer ()
	{
		return this.ship.clone ();
	}
	
	protected void update()
	{
		super.update();
	}
}
