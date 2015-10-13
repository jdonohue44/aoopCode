package aoop.asteroids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import aoop.asteroids.model.Asteroid;


public class Spectator extends Thread {

	int clientPort;
	int serverPort;
	static int counter = 1;
	int id = 1;	
	InetAddress serverAddress;
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	DataOutputStream dataOut;
	ByteArrayOutputStream bytesOut;
	ByteArrayInputStream bytesIn;
	DataInputStream dataIn;
	byte[] byteData;
	
	ArrayList<Asteroid> asteroids; 
	Point location;
	Asteroid a;
	int numberOfAsteroids =0;
	int numberOfBullets =0;
	int packetNumber;
	double[] shipPositions = new double[2];
	double[] asteroidPositions = new double[2];
	double[] bulletPositions    = new double[2];
	double[]    asteroidRadii = new double[2];
	double shipDirection = 0;
    boolean shipAccelerating = false;
    int score = 0;
    int indexCounter = 0;
    int radiiCounter = 0;
	
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
			byteData = new byte[256];
			packet = new DatagramPacket(byteData, byteData.length);
			clientSocket.receive(packet);
	        byteData = packet.getData();
	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
			DataInputStream dataIn = new DataInputStream(bytesIn);

	        numberOfAsteroids = dataIn.readInt();
	        numberOfBullets = dataIn.readInt();
	        
	        asteroidPositions   = new double[numberOfAsteroids*2]; // X,Y
	        bulletPositions     = new double[numberOfBullets*2]; // X,y
 	        asteroidRadii       = new double[numberOfAsteroids];

 	        // Read asteroid data
    		for(int i = 0; i < numberOfAsteroids * 2; i++){
    			asteroidPositions[i] = dataIn.readDouble();
    		}
    		for(int i = 0; i < numberOfAsteroids; i++){
    			asteroidRadii[i] = dataIn.readDouble();
    		}
    		
    		// Read bullet data
    		for(int i = 0; i < numberOfBullets * 2; i++){
    			bulletPositions[i] = dataIn.readDouble();
    		}
    		
    		// Read ship data
    		shipPositions[0] = dataIn.readDouble(); // Ship X position
    		shipPositions[1] = dataIn.readDouble(); // Ship Y position
    		shipDirection = dataIn.readDouble();
    		shipAccelerating = dataIn.readBoolean();
    		score = dataIn.readInt();
    		
	        dataIn.close();
	        
		}catch(IOException e){
			System.out.println(e.getMessage());
	        clientSocket.close();
		}
	}
}
	
	public double[] getShipPositions() {
		return shipPositions;
	}

	public void setShipPositions(double[] shipPositions) {
		this.shipPositions = shipPositions;
	}

	public double getShipDirection() {
		return shipDirection;
	}

	public void setShipDirection(double shipDirection) {
		this.shipDirection = shipDirection;
	}

	public boolean isShipAccelerating() {
		return shipAccelerating;
	}

	public void setShipAccelerating(boolean shipAccelerating) {
		this.shipAccelerating = shipAccelerating;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}