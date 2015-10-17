package aoop.asteroids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Spaceship;


public class Spectator extends Thread{
	
	int clientPort;
	String clientAddress;
	int serverPort;
	InetAddress serverAddress;
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	DataOutputStream dataOut;
	ByteArrayOutputStream bytesOut;
	ByteArrayInputStream bytesIn;
	byte[] byteData;
	
	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	ArrayList<Bullet> bullets  = new ArrayList<Bullet>();
	ArrayList<Spaceship> ships = new ArrayList<Spaceship>();
	GameListener gameListener;
	
	int numberOfAsteroids =0;
	int numberOfBullets =0;
	Spaceship ship = new Spaceship();
	int packetNumber;
    int score = 0;
    
    boolean spectating;
	
	public Spectator(String serverAddress, int serverPort) {
		try {
			this.clientSocket = new DatagramSocket();
			this.clientPort = clientSocket.getLocalPort();
			this.clientAddress = this.clientSocket.getLocalAddress().getHostName();
			System.out.println(this.clientAddress);
			this.gameListener = new GameListener(clientAddress, clientPort);
			this.serverAddress = InetAddress.getByName(serverAddress);
			this.serverPort = serverPort;
			this.spectating = true;
	        
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(spectating){
		try {
			// Send Ping to Server with this clients socket information
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			ObjectOutputStream dataOut = new ObjectOutputStream(bytesOut);
			dataOut.writeObject(this.gameListener);
	        byteData = bytesOut.toByteArray();	
			packet = new DatagramPacket(byteData, byteData.length, this.serverAddress, this.serverPort);
		    clientSocket.send(packet);
	        dataOut.close();
	        
	        // Recieve Data from Server
			byteData = new byte[800];
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

	public boolean isSpectating(){
		return this.spectating;
	}
	
	public void setSpectating(boolean bool){
		this.spectating = bool;
	}
	
	public ArrayList<Spaceship> getShips() {
		return this.ships;
	}
	
	public Spaceship getShip() {
		return this.ship;
	}
	
	public ArrayList<Asteroid> getAsteroids(){
		return this.asteroids;
	}
	
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}
	
}