package aoop.asteroids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import aoop.asteroids.model.Game;
import aoop.asteroids.model.SpectateGame;

public class Spectator extends Thread {

	int port;
	String address;
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	byte[] sendBuf = new byte[256];
	Game game;
	
	public Spectator(String address, int port) {
			this.address = address;
			this.port = port;	
	}
	
	public void run(){
		try {
			clientSocket = new DatagramSocket();
			byte[] buf = new byte[256];
			InetAddress address = InetAddress.getByName("localhost");
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, this.port);
			clientSocket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
