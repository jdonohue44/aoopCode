package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Explosion;
import aoop.asteroids.model.Spaceship;

public class Joiner extends Spectator implements Runnable {
	
	Spaceship ship;
	
	public Joiner(String serverAddress, int serverPort, Spaceship ship) {
		super(serverAddress, serverPort);
		this.gameListener.setId(1);
		this.ship = ship;
	}
	
	@Override
	public void run(){
		while(spectating){
		try {
    		// Send Ping to Server with this clients socket information
    		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
    		ObjectOutputStream objOut = new ObjectOutputStream(bytesOut);
    		objOut.writeObject(this.gameListener);
    	    objOut.writeObject(this.getShip());
            byteData = bytesOut.toByteArray();	
    		packet = new DatagramPacket(byteData, byteData.length, this.serverAddress, this.serverPort);
    	    clientSocket.send(packet);
			objOut.close();
			
	        // Recieve Data from Server
			byteData = new byte[2400];
			packet = new DatagramPacket(byteData, byteData.length);
			
			try{
				clientSocket.setSoTimeout(1000);
				clientSocket.receive(packet);
				byteData = packet.getData();
			} catch(SocketTimeoutException e){
				this.spectating = false;
				System.out.println(e + ", so I ended the client");
			}

	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
	        ObjectInputStream objIn = new ObjectInputStream(bytesIn);

	        int id = (int) objIn.readObject();
		
	        if (id == 0) {
	        	objIn.close();
	        	this.ship.reinit();
	        }
	        else if (id == 1) {
		        try {
					synchronized(this){
						this.round     = (int) objIn.readObject();
						this.ships     = (ArrayList<Spaceship>) objIn.readObject();
						this.asteroids = (ArrayList<Asteroid>) objIn.readObject();
						this.bullets   =  (ArrayList<Bullet>) objIn.readObject();
						this.explosions = (ArrayList<Explosion>) objIn.readObject();
					}
					objIn.close();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					clientSocket.close();
					this.spectating = false;
					objIn.close();
				}
	        }
		} catch(Exception e){
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

	public Collection<Explosion> getExplosions() {
		return this.explosions;
	}
}