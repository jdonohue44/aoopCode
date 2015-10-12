package aoop.asteroids.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import aoop.asteroids.Spectator;

public class SpectateGame extends Game implements Runnable {

	Spectator spectator;
	ArrayList<Asteroid> asteroids;
	ArrayList<Bullet> bullets;
	Spaceship ship;
	
	public SpectateGame() {
			try {
				this.spectator = new Spectator(4355);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.ship = new Spaceship ();
	}
	
	public Spaceship getPlayer ()
	{
		return this.ship.clone ();
	}
	
	private void update ()
	{
		for (Asteroid a : this.asteroids) a.nextStep ();
		for (Bullet b : this.bullets) b.nextStep ();
		this.ship.nextStep ();

		if (this.ship.isFiring ())
		{
			double direction = this.ship.getDirection ();
			this.bullets.add (new Bullet(this.ship.getLocation (), this.ship.getVelocityX () + Math.sin (direction) * 15, this.ship.getVelocityY () - Math.cos (direction) * 15));
			this.ship.setFired ();
		}

//		this.checkCollisions ();
//		this.removeDestroyedObjects ();
//
//		if (this.cycleCounter == 0 && this.asteroids.size () < this.asteroidsLimit) this.addRandomAsteroid ();
//		this.cycleCounter++;
//		this.cycleCounter %= 200;

		this.setChanged ();
		this.notifyObservers ();
	}
}
