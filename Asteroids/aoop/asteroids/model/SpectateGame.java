package aoop.asteroids.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import aoop.asteroids.Spectator;

public class SpectateGame extends Game implements Runnable {

	Spectator spectator;
	
	public SpectateGame() {
			try {
				this.spectator = new Spectator(4355);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
