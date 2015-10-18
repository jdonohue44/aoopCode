package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.JoinGame;
import aoop.asteroids.model.Spaceship;

public class Joiner extends Spectator implements Runnable {
	
	Spaceship ship;
	ArrayList<Bullet> bullets;

	public Joiner(String serverAddress, int serverPort) {
		super(serverAddress, serverPort);
		this.gameListener.setId(1);
	}
	
	@Override
	public void run(){
		while(spectating){
		try {
			// Send Ping to Server with this clients socket information
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			ObjectOutputStream dataOut = new ObjectOutputStream(bytesOut);
			dataOut.writeObject(this.gameListener);
			dataOut.writeObject(this.ship);
			dataOut.writeObject(this.bullets);
	        byteData = bytesOut.toByteArray();	
			packet = new DatagramPacket(byteData, byteData.length, this.serverAddress, this.serverPort);
		    clientSocket.send(packet);
	        dataOut.close();
	        
	        
	        // Recieve Data from Server
			byteData = new byte[1600];
			packet = new DatagramPacket(byteData, byteData.length);
			
			try{
				clientSocket.setSoTimeout(1000);
				clientSocket.receive(packet);
				byteData = packet.getData();
			}catch(SocketTimeoutException e){
				this.spectating = false;
				System.out.println(e + ", so I ended the client");
			}
	       
	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
	        ObjectInputStream objIn = new ObjectInputStream(bytesIn);

	        try {
				this.ships     = (ArrayList<Spaceship>) objIn.readObject();
				this.asteroids = (ArrayList<Asteroid>) objIn.readObject();
				this.bullets   = (ArrayList<Bullet>) objIn.readObject();
				objIn.close();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				clientSocket.close();
				this.spectating =false;
				objIn.close();
			}
		}catch(IOException e){
			System.out.println(e + " on the Client");
			this.spectating = false;
	        clientSocket.close();
		}
	}
}

	public Spaceship getShip() {
		return ship;
	}

	public void setShip(Spaceship ship) {
		this.ship = ship;
	}
	
}