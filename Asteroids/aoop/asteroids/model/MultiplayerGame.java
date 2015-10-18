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
	
	public MultiplayerGame() {
		Game.rng = new Random ();
		this.initGameData ();
		this.ships = new ArrayList<Spaceship>();
		Spaceship s = new Spaceship();
		this.ship = s;
		this.ships.add(s);
		this.ships.add(new Spaceship(Color.RED));
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

