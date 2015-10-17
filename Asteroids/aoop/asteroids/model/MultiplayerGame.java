package aoop.asteroids.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import aoop.asteroids.Joiner;
import aoop.asteroids.Spectator;
import aoop.asteroids.gui.Player;

public class MultiplayerGame extends Game implements Runnable {

	private Collection <Spaceship> ships;

	public MultiplayerGame() {
		Game.rng = new Random ();
		this.initGameData ();
		
		this.ships = new ArrayList<Spaceship>();
		this.ship = new Spaceship ();
		this.ships.add(this.ship);
		this.ships.add(new Spaceship());
	}

	public Collection <Spaceship> getShips ()
	{
		Collection <Spaceship> c = new ArrayList <> ();
		for (Spaceship s : this.ships) c.add (s.clone ());
		return c;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void addSpaceship(Spaceship s){
		this.ships.add(s);
	}
	
	public void removeSpaceship(Spaceship s){
		this.ships.remove(s);
	}
}

