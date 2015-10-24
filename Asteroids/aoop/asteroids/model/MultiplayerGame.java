package aoop.asteroids.model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import aoop.asteroids.Joiner;
import aoop.asteroids.Spectator;
import aoop.asteroids.gui.Player;

public class MultiplayerGame extends Game implements Runnable {
		
	public MultiplayerGame(String nickname) {
		Game.rng = new Random ();
		this.initGameData ();
		this.ships = new ArrayList<Spaceship>();
		
		Spaceship s = new Spaceship(nickname);
		this.ship = s;
		this.ships.add(s);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	protected boolean gameOver ()
	{
		return this.getShips().size() == 0;
	}
	
	@Override
	protected synchronized void update ()
	{
		for (Asteroid a : this.asteroids) a.nextStep ();
		for (Bullet b : this.bullets) b.nextStep ();
		for (Spaceship s : this.ships) s.nextStep ();
		for (Explosion e : this.explosions) e.nextStep();

		for(Spaceship s : this.getShips()){
			if (s.isFiring ()){
				double direction = s.getDirection ();
				this.bullets.add (new Bullet(s.getLocation (), s.getVelocityX () + Math.sin (direction) * 15, s.getVelocityY () - Math.cos (direction) * 15));
				s.setFired ();
				}
			}

		this.checkCollisions ();
		this.removeDestroyedObjects ();

		if (this.cycleCounter == 0 && this.asteroids.size () < this.asteroidsLimit) this.addRandomAsteroid ();
		this.cycleCounter++;
		this.cycleCounter %= 200;

		this.setChanged ();
		this.notifyObservers ();
	}
	
	public void addSpaceship(Spaceship s){
		this.ships.add(s);
	}
	
	public void removeSpaceship(Spaceship s){
		this.ships.remove(s);
	}
}

