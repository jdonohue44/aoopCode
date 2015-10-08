package aoop.asteroids.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import aoop.asteroids.Spectator;

public class SpectateGame extends Game implements Runnable {

	Spectator spectator;
	
	public SpectateGame() {
			try {
				this.spectator = new Spectator(InetAddress.getByName("localhost"), 4445);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	}
}
