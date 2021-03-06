package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Explosion;
import aoop.asteroids.model.Spaceship;


public class Spectator extends Thread{
	
	int clientPort;
	InetAddress clientAddress;
	int serverPort;
	InetAddress serverAddress;
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	ObjectOutputStream objOut;
	ObjectInputStream objIn;
	ByteArrayOutputStream bytesOut;
	ByteArrayInputStream bytesIn;
	byte[] byteData;
	
	protected int round;
	
	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	ArrayList<Bullet> bullets  = new ArrayList<Bullet>();
	ArrayList<Spaceship> ships = new ArrayList<Spaceship>();
	ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	GameListener gameListener;
    
    protected boolean spectating;
	
	public Spectator(String serverAddress, int serverPort) {
		try {
			this.clientSocket = new DatagramSocket();
			this.clientPort = clientSocket.getLocalPort();
			this.clientAddress = clientSocket.getLocalAddress().getLocalHost();
			this.gameListener = new GameListener(clientAddress, clientPort, 0);
			this.serverAddress = InetAddress.getByName(serverAddress);
			this.serverPort = serverPort;
			this.spectating = true;
			
			// Send Ping to Server with this clients socket information
			bytesOut = new ByteArrayOutputStream();
			objOut = new ObjectOutputStream(bytesOut);
			objOut.writeObject(this.gameListener);
	        byteData = bytesOut.toByteArray();	
			packet = new DatagramPacket(byteData, byteData.length, this.serverAddress, this.serverPort);
		    clientSocket.send(packet);
	        objOut.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(spectating){
		try {
			
	        // Receive Data from Server
			byteData = new byte[1600];
			packet = new DatagramPacket(byteData, byteData.length);
			
			try{
				clientSocket.setSoTimeout(1000);
				clientSocket.receive(packet);
				byteData = packet.getData();
			}catch(SocketTimeoutException e){
				this.spectating = false;
			}
	       
	        bytesIn = new ByteArrayInputStream(byteData);
	        objIn = new ObjectInputStream(bytesIn);
	        int id = (int) objIn.readObject();

	        if (id == 0) {
	        	objIn.close();
	        }
	        else if (id == 1) {
		        try {
		        	this.round     = (int) objIn.readObject();
					this.ships     = (ArrayList<Spaceship>) objIn.readObject();
					this.asteroids = (ArrayList<Asteroid>) objIn.readObject();
					this.bullets   = (ArrayList<Bullet>) objIn.readObject();
					this.explosions = (ArrayList<Explosion>) objIn.readObject();
					objIn.close();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					clientSocket.close();
					this.spectating =false;
					objIn.close();
				}
	        }
    		
		}catch(Exception e){
			System.out.println(e + " on the Client");
			this.spectating = false;
	        clientSocket.close();
		}
	}
		// Remove gameListener from Server
		bytesOut = new ByteArrayOutputStream();
		try {
			objOut = new ObjectOutputStream(bytesOut);
			objOut.writeObject(this.gameListener);
	        byteData = bytesOut.toByteArray();	
			packet = new DatagramPacket(byteData, byteData.length, this.serverAddress, this.serverPort);
		    clientSocket.send(packet);
	        objOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isSpectating(){
		return this.spectating;
	}
	
	public void setSpectating(boolean bool){
		this.spectating = bool;
	}
	
	public ArrayList<Spaceship> getShips() {
		return this.ships;
	}
	
	public ArrayList<Asteroid> getAsteroids(){
		return this.asteroids;
	}
	
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	public int getRound() {
		return round;
	}

	public Collection<Explosion> getExplosions() {
		// TODO Auto-generated method stub
		return this.explosions;
	}
}