package aoop.asteroids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
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
	InetAddress address;
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	
	DataOutputStream dataOut;
	ByteArrayOutputStream bytesOut;
	ByteArrayInputStream bytesIn;
	DataInputStream dataIn;
	
	byte[] byteData = new byte[4096];
	Game game;
	
	public Spectator(int serverPort) {
		try {
			clientSocket = new DatagramSocket();
			this.address = InetAddress.getByName("localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.clientPort = clientSocket.getLocalPort();
		this.serverPort = serverPort;
	}
	
	public Spectator(String address, int port){
		try {
			this.address = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.clientPort = port;
	}
	
	public void run(){
		while(true){
		try {
			// request Game data
			byteData = "Connection Request from the Client".getBytes();
			DatagramPacket packet = new DatagramPacket(byteData, byteData.length, this.address, this.serverPort);
			clientSocket.send(packet);
			
	        // receive request
			byteData = new byte[264];
	        packet = new DatagramPacket(byteData, byteData.length);
	        clientSocket.receive(packet);
	        byteData = packet.getData();
	        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
			DataInputStream dataIn = new DataInputStream(bytesIn);
			
	        double[] asteroidPositions  = new double[20];
	        double[] asteroidVelocities = new double[20];
	        int[]    asteroidRadii      = new int[10];
	        double[] bulletPositions = new double[20];
	        double[] bulletVelocities = new double[20];
	        double[] shipPositions = new double[2];
	        double[] shipVelocities = new double[2];
	        int score = 0;
	        boolean EOF = false;
	        ArrayList<Double> list = new ArrayList<Double>();
	        
	        while(!EOF){
	        	try{
	        		list.add(dataIn.readDouble());
	        	}catch(EOFException e){
	        		EOF = true;
	        	}
	        }
	        
	        for(Double d : list){
	        	System.out.println(d);
	        }
		}catch(Exception e){
			System.out.println(e);
	        clientSocket.close();

		}
		}
	}
}