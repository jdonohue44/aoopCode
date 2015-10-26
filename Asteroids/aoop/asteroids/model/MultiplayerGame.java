package aoop.asteroids.model;

import java.awt.Color;
import java.awt.Point;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import aoop.asteroids.Joiner;
import aoop.asteroids.Spectator;
import aoop.asteroids.gui.Player;

public class MultiplayerGame extends Game implements Runnable {
	
	private InetAddress host;
	private int port;
	private final Object lock = new Object();
	protected boolean running;
	ArrayList<Spaceship> allShips = new ArrayList<Spaceship>();
	EntityManagerFactory emf;
	EntityManager em;
		
	public MultiplayerGame(String nickname, EntityManagerFactory emf, EntityManager em) {
		this.emf = emf;
		this.em = em;
		this.round = 0;
		Game.rng = new Random ();
		this.ship = new Spaceship(nickname);
		this.ships = new ArrayList<Spaceship>();
		this.initGameData ();
	}
	
	public MultiplayerGame(String nickname) {
		this.round = 0;
		Game.rng = new Random ();
		this.ship = new Spaceship(nickname);
		this.ships = new ArrayList<Spaceship>();
		this.initGameData ();
	}
	
	public void initGameData ()
	{
		this.round++;
		this.aborted = false;
		this.cycleCounter = 0;
		this.asteroidsLimit = 7;
		this.bullets = new ArrayList <> ();
		this.asteroids = new ArrayList <> ();
		this.explosions = new ArrayList<> ();
		this.ship.reinit ();
		if (!this.ships.contains(this.ship)) this.ships.add(this.ship);
		this.running = false;
	}
	
	public InetAddress getHost() {
		return host;
	}

	public void setHost(InetAddress host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Object getLock() {
		return lock;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	protected boolean gameOver ()
	{
		return this.getShips().size() == 0;
	}
	
	@Override
	protected synchronized void update ()
	{
		if (running) {
			for (Asteroid a : this.asteroids) a.nextStep ();
			for (Bullet b : this.bullets) b.nextStep ();
			for (Spaceship s : this.ships) s.nextStep ();
			for (Explosion e : this.explosions) e.nextStep();
	
			synchronized (lock){
			for(Spaceship s : this.getShips()){
				if (s.isFiring ()){
					double direction = s.getDirection ();
					this.bullets.add (new Bullet(s.getLocation (), s.getVelocityX () + Math.sin (direction) * 15, s.getVelocityY () - Math.cos (direction) * 15, s.getId()));
					s.setFired ();
					}
				}
			}
	
			if (this.cycleCounter == 0 && this.asteroids.size () < this.asteroidsLimit) this.addRandomAsteroid ();
			this.cycleCounter++;
			this.cycleCounter %= 200;
			
			this.checkCollisions ();
			this.removeDestroyedObjects ();
			
			if (this.getShips().size() <= 1){
					for (Spaceship s: this.getShips()) synchronized (lock) {
						s.increaseScore();
					}
					this.initGameData();
			}
			
		}

		this.setChanged ();
		this.notifyObservers ();
	}
	
	public void saveScores(){
		em.getTransaction().begin();
		for(Spaceship s : this.getShips()){
			em.persist(new Participant(100,"Man"));
	        em.getTransaction().commit();
		}
		em.close();
	}
	
	@Override
	protected void checkCollisions ()
	{ // Destroy all objects that collide.
		for (Bullet b : this.bullets)
		{ // For all bullets.
			for (Asteroid a : this.asteroids)
			{ // Check all bullet/asteroid combinations.
				if (a.collides (b))
				{ // Collision -> destroy both objects.
					for (Spaceship s: this.ships) {
						if (s.getId() == b.getId()) synchronized (lock) {
							s.increaseScore();
						}
					}
					b.destroy ();
					a.destroy ();
				}
			}

			for(Spaceship s : this.ships){
				if (b.collides(s))
				{// Collision with player -> destroy both objects.
					b.destroy();
					s.destroy();
					Point p = new Point ((int)s.locationX, (int)s.locationY);
					this.explosions.add(new Explosion(p,0,0,100,3));
				}
			}
		}

		
		for (Asteroid a : this.asteroids)
		{ // For all asteroids, no cross check with bullets required.
			for(Spaceship s : this.ships){
				if (a.collides(s))
				{// Collision with player -> destroy both objects.
					a.destroy();
					s.destroy();
					Point p = new Point ((int)s.locationX, (int)s.locationY);
					this.explosions.add(new Explosion(p,0,0,100,3));
				}
			}
		}
		
		if (this.asteroids.size() == 0) {
			for (Spaceship s: this.ships) synchronized (lock) {
				s.increaseScore();
			}
			this.initGameData();
		}
	}
	
	public void addSpaceship(Spaceship s){
		this.ships.add(s);
	}
	
	public void removeSpaceship(Spaceship s){
		this.ships.remove(s);
	}
	
	public void run ()
	{ // Update -> sleep -> update -> sleep -> etc...
		long executionTime, sleepTime;
		this.update();
		while (!this.aborted)
		{	
			if (this.getShips().size() > 1) this.setRunning(true);
			
			/*if (this.getShips().size() <= 1) {
				sleepTime = 100;
				this.setRunning(false);
			}
			else*/ if (!this.gameOver () && running)
			{
				//this.setRunning(true);
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

