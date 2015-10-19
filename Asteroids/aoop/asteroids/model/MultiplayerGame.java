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
		this.nickname = nickname;
		Game.rng = new Random ();
		this.initGameData ();
		this.ships = new ArrayList<Spaceship>();
		int colors[] = new int[3];
		int start = (int)Math.floor(Math.random()*3);
		colors[start] = (int)Math.floor(Math.random()*256);
		colors[(start+(int)Math.floor(Math.random()*2)+1)%3] = 255;
		Spaceship s = new Spaceship(new Color(colors[0],colors[1],colors[2]));
		this.ship = s;
		this.ships.add(s);
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

