package aoop.asteroids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;

import aoop.asteroids.model.Game;


public class Spectator extends Thread {

	int clientPort;
	int serverPort;
	static int counter = 1;
	int id = 1;
	InetAddress address;
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	ArrayList<Double> list; 
	
	DataOutputStream dataOut;
	ByteArrayOutputStream bytesOut;
	ByteArrayInputStream bytesIn;
	DataInputStream dataIn;
	
	byte[] byteData;
	Game game;
	
	public Spectator(int serverPort) {
		try {
			list = new ArrayList<Double>();
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
			list = new ArrayList<Double>();
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
//	        double[] shipPositions = new double[2];
//	        double[] shipVelocities = new double[2];
//	        int numberOfAsteroids = 0;
//	        int numberOfBullets = 0;
//	        int score = 0;
//	        int packetNumber = -1;
//	        boolean EOF = false; 



	        int packetNumber = dataIn.readInt();
	        int numberOfAsteroids = dataIn.readInt();
	        
	        double[] asteroidPositions   = new double[numberOfAsteroids*2];
	        double[] asteroidVelocities  = new double[numberOfAsteroids*2];
 	        double[]    asteroidRadii    = new double[numberOfAsteroids];

	        
    		for(int i = 0; i < numberOfAsteroids * 2; i++){
    			asteroidPositions[i] = dataIn.readDouble();
    		}
    		
    		for(int i = 0; i < numberOfAsteroids * 2; i++){
    			asteroidVelocities[i] = dataIn.readDouble();
    		}
    		
    		for(int i = 0; i < numberOfAsteroids; i++){
    			asteroidRadii[i] = dataIn.readDouble();
    		}
	        
    		System.out.print("Asteroid Positions: ");
    		System.out.print("{");
    		int counter = 0;
    		for(Double d : asteroidPositions){
    			System.out.print(asteroidPositions[counter] + " , ");
    			counter++;
    		}
    		System.out.print("}\n");
    		
    		System.out.print("Velocites: {");
    		counter = 0;
    		for(Double d : asteroidVelocities){
    			System.out.print(asteroidVelocities[counter] + " , ");
    			counter++;
    		}
    		System.out.print("}\n");
    		
    		System.out.print("Radii: {");
    		counter = 0;
    		for(Double d : asteroidRadii){
    			System.out.print(asteroidRadii[counter] + " , ");
    			counter++;
    		}
    		System.out.print("}\n");
	        dataIn.close();
	        
		}catch(Exception e){
			System.out.println(e);
	        clientSocket.close();

		}
		}
	}
}