package aoop.asteroids.model;

import java.awt.Point;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import aoop.asteroids.gui.Player;

/**
 *	The game class is the backbone of all simulations of the asteroid game. It 
 *	contains all game object and keeps track of some other required variables 
 *	in order to specify game rules.
 *	<p>
 *	The game rules are as follows:
 *	<ul>
 *		<li> All game objects are updated according to their own rules every 
 *			game tick. </li>
 *		<li> Every 200th game tick a new asteroid is spawn. An asteroid cannot 
 *			spawn within a 50 pixel radius of the player. </li>
 *		<li> There is a maximum amount of asteroids that are allowed to be 
 *			active simultaneously. Asteroids that spawn from destroying a 
 *			larger asteroid do count towards this maximum, but are allowed to 
 *			spawn if maximum is exceeded. </li>
 *		<li> Destroying an asteroid spawns two smaller asteroids. I.e. large 
 *			asteroids spawn two medium asteroids and medium asteroids spawn two 
 *			small asteroids upon destruction. </li>
 *		<li> The player dies upon colliding with either a buller or an 
 *			asteroid. </li>
 *		<li> Destroying every 5th asteroid increases the asteroid limit by 1, 
 *			increasing the difficulty. </li>
 *	</ul>
 *	<p>
 *	This class implements Runnable, so all simulations will be run in its own 
 *	thread. This class extends Observable in order to notify the view element 
 *	of the program, without keeping a reference to those objects.
 *
 *	@author Yannick Stoffers
 */
public class Game extends Observable implements Runnable
{

	/** The spaceship of the player. */
	protected Spaceship ship;
	
	public Collection <Spaceship> ships;

	/** List of bullets. */
	public Collection <Bullet> bullets;

	/** List of asteroids. */
	public Collection <Asteroid> asteroids;
	
	public Collection <Explosion> explosions;

	/** Random number generator. */
	protected static Random rng;

	/** Game tick counter for spawning random asteroids. */
	protected int cycleCounter;

	/** Asteroid limit. */
	protected int asteroidsLimit;
	
	protected int round;
	

	/** 
	 *	Indicates whether the a new game is about to be started. 
	 *
	 *	@see #run()
	 */
	protected boolean aborted;

	/** Initializes a new game from scratch. */
	public Game ()
	{
		this.round = 0;
		Game.rng = new Random ();
		this.ship = new Spaceship ();
		this.ships = new ArrayList<Spaceship>();
		this.initGameData ();
	}

	/** Sets all game data to hold the values of a new game. */
	public void initGameData ()
	{
		if(this.getRound()==2){
			for(Spaceship s : this.getShips()){
				s.destroy();
			}
			EntityManagerFactory emf  = Persistence.createEntityManagerFactory("$objectdb/db/participantsTest.odb");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			for(Spaceship s : this.getShips()){
				em.persist(new Participant(s.getScore(),s.getNickname()));
		        em.getTransaction().commit();
			}
	        em.close();
	        emf.close();
		}
		else{
			this.round++;
			this.aborted = false;
			this.cycleCounter = 0;
			this.asteroidsLimit = 7;
			this.bullets = new ArrayList <> ();
			this.asteroids = new ArrayList <> ();
			this.ships = new ArrayList<> ();
			this.explosions = new ArrayList<> ();
			this.ship.reinit ();
			this.ships.add(this.ship);
		}
	}
	/** 
	 *	Links the given controller to the spaceship. 
	 *
	 *	@param p the controller that is supposed to control the spaceship.
	 */
	public void linkController (Player p)
	{
		p.addShip (this.ship);
	}

	/** 
	 *	Returns a clone of the spaceship, preserving encapsulation. 
	 *
	 *	@return a clone the spaceship.
	 */
	public Spaceship getPlayer ()
	{
		return this.ship.clone ();
	}
	
	public int getRound() {
		return round;
	}

	/** 
	 *	Returns a clone of the asteroid set, preserving encapsulation.
	 *
	 *	@return a clone of the asteroid set.
	 */
	public Collection <Asteroid> getAsteroids ()
	{
		Collection <Asteroid> c = new ArrayList <> ();
		for (Asteroid a : this.asteroids) c.add (a.clone ());
		return c;
		//return this.asteroids;
	}

	/** 
	 *	Returns a clone of the bullet set, preserving encapsulation.
	 *
	 *	@return a clone of the bullet set.
	 */
	public synchronized Collection <Bullet> getBullets ()
	{
		Collection <Bullet> c = new ArrayList <> ();
		for (Bullet b : this.bullets) c.add (b.clone ()); //rarely throws ConcurrentModificationException
		return c;
		//return this.bullets;
	}


	public Collection <Spaceship> getShips ()
	{
//		Collection <Spaceship> c = new ArrayList <> ();
//		for (Spaceship s : this.ships) c.add (s.clone ());
//		return c;
		return this.ships;
	}
	
	public Collection <Explosion> getExplosions(){
		return this.explosions;
	}
	
	public void setShips(ArrayList <Spaceship> ships){
		this.ships = ships;
	}
	
	public void addShip(Spaceship s){
		this.ships.add(s);
	}

	/**
	 *	Method invoked at every game tick. It updates all game objects first. 
	 *	Then it adds a bullet if the player is firing. Afterwards it checks all 
	 *	objects for collisions and removes the destroyed objects. Finally the 
	 *	game tick counter is updated and a new asteroid is spawn upon every 
	 *	200th game tick.
	 */
	protected void update ()
	{
		for (Asteroid a : this.asteroids) a.nextStep ();
		for (Bullet b : this.bullets) b.nextStep ();
		for (Spaceship s : this.ships) s.nextStep ();
		for (Explosion e : this.explosions) e.nextStep();

		if (this.ship.isFiring ())
		{
			double direction = this.ship.getDirection ();
			this.bullets.add (new Bullet(this.ship.getLocation (), this.ship.getVelocityX () + Math.sin (direction) * 15, this.ship.getVelocityY () - Math.cos (direction) * 15, this.ship.getId()));
			this.ship.setFired ();
		}

		this.checkCollisions ();
		this.removeDestroyedObjects ();

		if (this.cycleCounter == 0 && this.asteroids.size () < this.asteroidsLimit) this.addRandomAsteroid ();
		this.cycleCounter++;
		this.cycleCounter %= 200;
		
		if (this.asteroids.size() == 0) {
			this.increaseScore();
			this.initGameData();
		}

		this.setChanged ();
		this.notifyObservers ();
	}

	/** 
	 *	Adds a randomly sized asteroid at least 50 pixels removed from the 
	 *	player.
	 */
	protected void addRandomAsteroid ()
	{
		int prob = Game.rng.nextInt (3000);
		Point loc, shipLoc = this.ship.getLocation ();
		int x, y;
		do
		{
			loc = new Point (Game.rng.nextInt (800), Game.rng.nextInt (800));
			x = loc.x - shipLoc.x;
			y = loc.y - shipLoc.y;
		}
		while (Math.sqrt (x * x + y * y) < 50);

		if (prob < 1000)		this.asteroids.add (new LargeAsteroid  (loc, Game.rng.nextDouble () * 6 - 3, Game.rng.nextDouble () * 6 - 3));
		else if (prob < 2000)	this.asteroids.add (new MediumAsteroid (loc, Game.rng.nextDouble () * 6 - 3, Game.rng.nextDouble () * 6 - 3));
		else					this.asteroids.add (new SmallAsteroid  (loc, Game.rng.nextDouble () * 6 - 3, Game.rng.nextDouble () * 6 - 3));
	}

	/** 
	 *	Checks all objects for collisions and marks them as destroyed upon col-
	 *	lision. All objects can collide with objects of a different type, but 
	 *	not with objects of the same type. I.e. bullets cannot collide with 
	 *	bullets etc.
	 */
	protected void checkCollisions ()
	{ // Destroy all objects that collide.
		for (Bullet b : this.bullets)
		{ // For all bullets.
			for (Asteroid a : this.asteroids)
			{ // Check all bullet/asteroid combinations.
				if (a.collides (b))
				{ // Collision -> destroy both objects.
					this.increaseScore();
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
					this.explosions.add(new Explosion(p,0,0,100,10));
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
					this.explosions.add(new Explosion(p,0,0,100,10));
				}
			}
		}
	}

	/**
	 * 	Increases the score of the player by one and updates asteroid limit 
	 *	when required.
	 */
	protected void increaseScore ()
	{
		this.ship.increaseScore ();
		if (this.ship.getScore () % 5 == 0) this.asteroidsLimit++;
	}

	/**
	 *	Removes all destroyed objects. Destroyed asteroids increase the score 
	 *	and spawn two smaller asteroids if it wasn't a small asteroid. New 
	 *	asteroids are faster than their predecessor and travel in opposite 
	 *	direction.
	 */
	protected void removeDestroyedObjects ()
	{
		
		Collection <Spaceship> newShips = new ArrayList<> ();
		for (Spaceship s : this.ships) if(!s.isDestroyed()) newShips.add(s);
		this.ships = newShips;
		
		Collection <Asteroid> newAsts = new ArrayList <> ();
		for (Asteroid a : this.asteroids)
		{
			if (a.isDestroyed ())
			{
				Collection <Asteroid> successors = a.getSuccessors ();
				newAsts.addAll (successors);
			}
			else newAsts.add (a);
		}
		this.asteroids = newAsts;

		Collection <Bullet> newBuls = new ArrayList <> ();
		for (Bullet b : this.bullets) if (!b.isDestroyed ()) newBuls.add (b);
		this.bullets = newBuls;
		
		Collection <Explosion> newExpls = new ArrayList <> ();
		for (Explosion e : this.explosions) {
			if (!e.isDestroyed ()) newExpls.add (e);
		}
		this.explosions = newExpls;
	}

	/**
	 *	Returns whether the game is over. The game is over when the spaceship 
	 *	is destroyed.
	 *
	 *	@return true if game is over, false otherwise.
	 */ 
	protected boolean gameOver ()
	{
		return this.ship.isDestroyed ();
	}

	/** 
	 *	Aborts the game. 
	 *
	 *	@see #run()
	 */
	public void abort ()
	{
		this.aborted = true;
	}

	/**
	 *	This method allows this object to run in its own thread, making sure 
	 *	that the same thread will not perform non essential computations for 
	 *	the game. The thread will not stop running until the program is quit. 
	 *	If the game is aborted or the player died, it will wait 100 
	 *	milliseconds before reevaluating and continuing the simulation. 
	 *	<p>
	 *	While the game is not aborted and the player is still alive, it will 
	 *	measure the time it takes the program to perform a game tick and wait 
	 *	40 minus execution time milliseconds to do it all over again. This 
	 *	allows the game to update every 40th millisecond, thus keeping a steady 
	 *	25 frames per second. 
	 *	<p>
	 *	Decrease waiting time to increase fps. Note 
	 *	however, that all game mechanics will be faster as well. I.e. asteroids 
	 *	will travel faster, bullets will travel faster and the spaceship may 
	 *	not be as easy to control.
	 */
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
			else{
				sleepTime = 100;
				this.abort();
			}

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
