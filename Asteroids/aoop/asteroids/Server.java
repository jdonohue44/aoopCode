package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;


public class Server extends Thread{

	private HashSet<Spectator> spectators;
	
	DatagramSocket serverSocket = null;
	int port;
	String host;
	InetAddress address;
	int packetReferenceNumber;
	
	byte[] byteData;
	DatagramPacket packet; 
	ByteArrayOutputStream bytesOut;
	DataOutputStream dataOut;
	ByteArrayInputStream byteIn;
	DataInputStream dataIn;
	
	int score;
	int numberOfAsteroids;
	int numberOfBullets;
	double shipDirection;
	boolean shipAccelerating;
    double[] asteroidPositions  = new double[4];
    int[]    asteroidRadii      = new int[2];      
    double[] bulletPositions  = new double[4];
    double[] shipPositions  = new double[2];
	
	Game game;
	
	public Server(Game game, String host, int port){
		this.game = game;
		this.port = port;
		this.spectators = new HashSet<Spectator>();
		this.packetReferenceNumber = 1;
		try {
			this.serverSocket = new DatagramSocket(port);
			this.address = InetAddress.getByName(host);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		boolean go = true;
		while(true && go){
			try{
		        // receive request from Client
				byteData = new byte[4];
				packet = new DatagramPacket(byteData, byteData.length);
		        serverSocket.receive(packet);
		        byteData = packet.getData();
		        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
				DataInputStream dataIn = new DataInputStream(bytesIn);
		        int clientId = dataIn.readInt();
		        dataIn.close();
		        
		        // Obtain Client's location
		        int clientPort = packet.getPort();
		        InetAddress clientAddress = packet.getAddress();
		        
           //  This is throwing an exception now ---> Too many files open on system.    
		   //  this.spectators.add(new Spectator(clientAddress.getHostAddress(), clientPort));
		   
		
		        score = this.game.getPlayer().getScore();
		        numberOfAsteroids = this.game.getAsteroids().size();
		        numberOfBullets = this.game.getBullets().size();
		        shipDirection = this.game.getPlayer().getDirection();
		        shipAccelerating = this.game.getPlayer().isUp();
		        
		        asteroidPositions  = new double[numberOfAsteroids*2];
		        asteroidRadii      = new int[numberOfAsteroids];      
		        bulletPositions  = new double[numberOfBullets * 2];
		        shipPositions  = new double[2];
		        
		        // Asteroid Positions
		        int counter = 0;
		        for(Asteroid a : this.game.getAsteroids()){
		        	asteroidPositions[counter] = a.getLocation().getX();
		        	asteroidPositions[counter + 1] = a.getLocation().getY();
		        	counter = counter += 2;
		        }
		        
		        // Asteroid Radii
		        counter = 0;
		        for(Asteroid a : this.game.getAsteroids()){
		        	asteroidRadii[counter] = a.getRadius();
		          	counter++;
		        }
		        
		        // Bullet Positions
		        counter = 0;
		        for (Bullet b : this.game.getBullets()) {
		            bulletPositions[counter] = b.getLocation().getX();
		            bulletPositions[counter + 1] = b.getLocation().getY();
		            counter = counter + 2;
		        }
		        
		        // Ship Positions
		        shipPositions[0] = this.game.getPlayer().getLocation().getX();
		        shipPositions[1] = this.game.getPlayer().getLocation().getY();
		          
		          
				ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
				DataOutputStream dataOut = new DataOutputStream(bytesOut);
				
		         dataOut.writeInt(numberOfAsteroids);
		         dataOut.writeInt(numberOfBullets);
		        
			    // Write Asteroid Data
		        for(int i = 0; i < asteroidPositions.length; i++){
		        	dataOut.writeDouble(asteroidPositions[i]);
		        } 
		        for(int i = 0; i < asteroidRadii.length; i++){
		        	dataOut.writeDouble(asteroidRadii[i]);
		        }
		        
		         // Write Bullet Data
		        for(int i = 0; i < bulletPositions.length; i++){
		        	dataOut.writeDouble(bulletPositions[i]);
		        }
		        
		         // Write Ship Data
	        	dataOut.writeDouble(shipPositions[0]);
	        	dataOut.writeDouble(shipPositions[1]);
		        dataOut.writeDouble(shipDirection);
		        dataOut.writeBoolean(shipAccelerating);
		        dataOut.writeInt(score);

				byteData = new byte[256];
		        byteData = bytesOut.toByteArray();	
				packet = new DatagramPacket(byteData, byteData.length, clientAddress, clientPort);
			    serverSocket.send(packet);
			    dataOut.close();
			}
			catch(Exception e){
				System.out.println(e.getStackTrace() + "SERVER");
				go = false;
				serverSocket.close();
			}
		}
	}

	public HashSet<Spectator> getSpectators() {
		return spectators;
	}

	public void setSpectators(HashSet<Spectator> spectators) {
		this.spectators = spectators;
	}

	public DatagramSocket getServersocket() {
		return serverSocket;
	}

	public void setServersocket(DatagramSocket serversocket) {
		this.serverSocket = serversocket;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public DatagramSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(DatagramSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public byte[] getByteData() {
		return byteData;
	}

	public void setByteData(byte[] byteData) {
		this.byteData = byteData;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
