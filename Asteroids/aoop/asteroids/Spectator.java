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

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Spaceship;


public class Spectator extends Thread {

	static int counter = 1;
	int id = 1;	
	
	int clientPort;
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
	
	int numberOfAsteroids =0;
	int numberOfBullets =0;
	Spaceship ship = new Spaceship();
	int packetNumber;
    int score = 0;
    
    boolean isSpectating;
	
	public Spectator(String host, int serverPort) {
		try {
			this.clientSocket = new DatagramSocket();
			this.clientPort = clientSocket.getLocalPort();
			this.serverAddress = InetAddress.getByName(host);
			this.isSpectating = true;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		this.serverPort = serverPort;
	}
	
	public void run(){
		while(true && isSpectating){
		try {
			// Send Ping to Server with this clients Identifier
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(bytesOut);
			dataOut.writeInt(this.id);
			byteData = new byte[8];
	        byteData = bytesOut.toByteArray();	
			packet = new DatagramPacket(byteData, byteData.length, serverAddress, serverPort);
		    clientSocket.send(packet);
	        dataOut.close();

	        // Recieve Data from Server
			byteData = new byte[1056];
			packet = new DatagramPacket(byteData, byteData.length);
			
			try{
			clientSocket.setSoTimeout(2000);
			clientSocket.receive(packet);
	        byteData = packet.getData();
			}catch(SocketTimeoutException e){
				this.isSpectating = false;
				System.out.println("ended client");
			}
	       
	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
	        ObjectInputStream objIn = new ObjectInputStream(bytesIn);

	        try {
	        	this.numberOfAsteroids = objIn.readInt();
	        	this.numberOfBullets   = objIn.readInt();
				this.ship = (Spaceship) objIn.readObject();
				this.asteroids = (ArrayList<Asteroid>) objIn.readObject();
				this.bullets   = (ArrayList<Bullet>) objIn.readObject();
				objIn.close();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				clientSocket.close();
				this.isSpectating =false;
				objIn.close();
			}
    		

		}catch(IOException e){
			System.out.println(e + " on the Client");
			this.isSpectating = false;
	        clientSocket.close();
		}
	}
}

	public boolean isSpectating(){
		return this.isSpectating;
	}
	public Spaceship getShip(){
		return this.ship;
	}
	
	public ArrayList<Asteroid> getAsteroids(){
		return this.asteroids;
	}
	
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}
	
	
	
}