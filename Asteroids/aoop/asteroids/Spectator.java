package aoop.asteroids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.awt.Point;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Game;


public class Spectator extends Thread {

	int clientPort;
	int serverPort;
	static int counter = 1;
	int id = 1;
	InetAddress address;
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	ArrayList<Asteroid> asteroids; 
	Point location;
	Asteroid a;
	int numberOfAsteroids;
	int packetNumber;
	boolean addedAsteroid;
    int indexCounter = 0;
    int radiiCounter = 0;
	
	DataOutputStream dataOut;
	ByteArrayOutputStream bytesOut;
	ByteArrayInputStream bytesIn;
	DataInputStream dataIn;
	
	byte[] byteData;
	Game game;
	
	public Spectator(int serverPort) {
		try {
			clientSocket = new DatagramSocket();
			this.address = InetAddress.getByName("localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.id = counter;
		counter++;
		this.clientPort = clientSocket.getLocalPort();
		this.serverPort = serverPort;
	}
	
	public Spectator(String host, int serverPort) {
		try {
			byteData = new byte[256];
			clientSocket = new DatagramSocket();
			this.address = InetAddress.getByName(host);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.clientPort = clientSocket.getLocalPort();
		this.serverPort = serverPort;
	}
	
	public void run(){
		while(true){
		try {
			// Send Ping to Server
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(bytesOut);
			dataOut.writeInt(this.id);
			byteData = new byte[8];
	        byteData = bytesOut.toByteArray();	
			packet = new DatagramPacket(byteData, byteData.length, address, serverPort);
		    clientSocket.send(packet);
	        dataOut.close();

			
	        
	        // Recieve Data from Server
			byteData = new byte[256];
			packet = new DatagramPacket(byteData, byteData.length);
			clientSocket.receive(packet);
	        byteData = packet.getData();
	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
			DataInputStream dataIn = new DataInputStream(bytesIn);
	        


//	        double[] asteroidVelocities = new double[20];
//	        double[] bulletPositions = new double[20];
//	        double[] bulletVelocities = new double[20];
//	        int numberOfAsteroids = 0;
//	        int numberOfBullets = 0;
//	        int score = 0;
//	        int packetNumber = -1;
//	        boolean EOF = false; 

	        packetNumber = dataIn.readInt();
	        int newNumberOfAsteroids = dataIn.readInt();
	        
	        if(newNumberOfAsteroids > numberOfAsteroids){
	        	addedAsteroid = true;
	        	numberOfAsteroids = newNumberOfAsteroids;
	        }
	        else{
	        	addedAsteroid = false;
	        }
	        
	        double[] shipPositions  = new double[2];
	        double shipDirection    = 0;
	        double[] asteroidPositions   = new double[numberOfAsteroids*2];
 	        double[]    asteroidRadii    = new double[numberOfAsteroids];
 	        int score = 0;

	        
    		for(int i = 0; i < numberOfAsteroids * 2; i++){
    			asteroidPositions[i] = dataIn.readDouble();
    		}
    		
    		for(int i = 0; i < numberOfAsteroids; i++){
    			asteroidRadii[i] = dataIn.readDouble();
    		}
    		
    		shipPositions[0] = dataIn.readDouble();
    		shipPositions[1] = dataIn.readDouble();
    		shipDirection = dataIn.readDouble();
    		score = dataIn.readInt();
    		
    		

	        dataIn.close();
    

	        
		}catch(IOException e){
			System.out.println(e.getMessage());
	        clientSocket.close();

		}
		}
	}
	
	void addAsteroid(){
//		location = new Point ((int)asteroidPositions[indexCounter], (int)asteroidPositions[indexCounter + 1]);
//		asteroids.add(new Asteroid(location, asteroidVelocities[(indexCounter)], asteroidVelocities[indexCounter + 1], (int) asteroidRadii[radiiCounter]));
//		indexCounter +=2;
//		radiiCounter +=1;
	}
	
	void updateLocations(){
		
	}
}