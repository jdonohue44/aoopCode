package aoop.asteroids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	
	List<Asteroid> asteroids = new ArrayList<Asteroid>();
	List<Bullet> bullets  = new ArrayList<Bullet>();
	List asteroidsList = Collections.synchronizedList(asteroids);
	List bulletsList  = Collections.synchronizedList(bullets);
	
	int numberOfAsteroids =0;
	int numberOfBullets =0;
	Spaceship ship = new Spaceship();
	int packetNumber;
    int score = 0;
	
	public Spectator(String host, int serverPort) {
		try {
			this.clientSocket = new DatagramSocket();
			this.clientPort = clientSocket.getLocalPort();
			this.serverAddress = InetAddress.getByName(host);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		this.serverPort = serverPort;
	}
	
	public void run(){
		while(true){
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
			clientSocket.receive(packet);
	        byteData = packet.getData();
	        
	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
	        ObjectInputStream objIn = new ObjectInputStream(bytesIn);

	        try {
	        	this.numberOfAsteroids = objIn.readInt();
	        	this.numberOfBullets   = objIn.readInt();
				this.ship = (Spaceship) objIn.readObject();
				for(int i=0; i < this.numberOfAsteroids; i++){
					this.asteroids.add((Asteroid) objIn.readObject());
				}
				for(int i=0; i < this.numberOfBullets; i++){
					this.bullets.add((Bullet) objIn.readObject());
				}
				objIn.close();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				clientSocket.close();
				objIn.close();
			}
    		

		}catch(IOException e){
			System.out.println(e + " on the Client");
			System.exit(1);
	        clientSocket.close();
		}
	}
}

	public Spaceship getShip(){
		return this.ship;
	}
	
	public List<Asteroid> getAsteroids(){
		return this.asteroidsList;
	}
	
	public List<Bullet> getBullets(){
		return this.bulletsList;
	}
	
	
	
}