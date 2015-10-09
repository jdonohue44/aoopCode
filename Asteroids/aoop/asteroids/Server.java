package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;

public class Server extends Thread{

	private HashSet<Spectator> spectators;
	
	DatagramSocket serverSocket;
	int port;
	String host;
	InetAddress address;
	
	byte[] byteData = new byte[4056];  
	ByteArrayOutputStream bytesOut;
	DataOutputStream dataOut;
	ByteArrayInputStream byteIn;
	DataInputStream dataIn;
	
	Game game;
	
	public Server(Game game, String host, int port){
		this.game = game;
		this.port = port;
		this.spectators = new HashSet<Spectator>();
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
		        // receive request
		        DatagramPacket packet = new DatagramPacket(byteData, byteData.length);
		        serverSocket.receive(packet);
		        String received = new String(packet.getData(), 0, packet.getLength());
		        System.out.println(received);
		        
		        int clientPort = packet.getPort();
		        InetAddress clientAddress = packet.getAddress();
        
		        this.spectators.add(new Spectator(clientAddress.getHostAddress(), clientPort));
     
				ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
				DataOutputStream dataOut = new DataOutputStream(bytesOut);
				
		        double[] asteroidPositions  = new double[20];
		        double[] asteroidVelocities = new double[20];
		        int[]    asteroidRadii      = new int[10];
		        double[] bulletPositions = new double[20];
		        double[] bulletVelocities = new double[20];
		        double[] shipPositions = new double[2];
		        double[] shipVelocities = new double[2];
		        int score = this.game.getPlayer().getScore();
		        
		        // Asteroid Positions
		        int counter = 0;
		        for(Asteroid a : this.game.getAsteroids()){
		        	asteroidPositions[counter] = a.getLocation().getX();
		        	asteroidPositions[counter] = a.getLocation().getY();
		        	counter++;
		        }
		        
		        // Asteroid Velocities
		        counter = 0;
		        for(Asteroid a : this.game.getAsteroids()){
		        	asteroidVelocities[counter] = a.getVelocityX();
		          	asteroidVelocities[counter] = a.getVelocityY();
		          	counter++;
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
		            bulletPositions[counter] = b.getLocation().getY();
		        }
		        
		        // Bullet Velocities
		        counter = 0;
		        for (Bullet b : this.game.getBullets()) {
		            bulletVelocities[counter] = b.getVelocityX();
		            bulletVelocities[counter] = b.getVelocityY();
		        }
		        
		        // Ship Positions
		          shipPositions[0] = this.game.getPlayer().getLocation().getX();
		          shipPositions[1] = this.game.getPlayer().getLocation().getY();
		        
		        // Ship Velocities
		          shipVelocities[0] = this.game.getPlayer().getVelocityX();
		          shipVelocities[1] = this.game.getPlayer().getVelocityY();

		        for(int i = 0; i < asteroidPositions.length; i++){
		        	dataOut.writeDouble(asteroidPositions[i]);
		        }
		        for(int i = 0; i < asteroidVelocities.length; i++){
		        	dataOut.writeDouble(asteroidPositions[i]);
		        }
		        for(int i = 0; i < asteroidRadii.length; i++){
		        	dataOut.writeDouble(asteroidRadii[i]);
		        }
		        for(int i = 0; i < bulletPositions.length; i++){
		        	dataOut.writeDouble(bulletPositions[i]);
		        }
		        for(int i = 0; i < bulletVelocities.length; i++){
		        	dataOut.writeDouble(bulletVelocities[i]);
		        }
		        for(int i = 0; i < shipPositions.length; i++){
		        	dataOut.writeDouble(shipPositions[i]);
		        }
		        for(int i = 0; i < shipVelocities.length; i++){
		        	dataOut.writeDouble(shipVelocities[i]);
		        }
		        dataOut.writeInt(score);
		        dataOut.close();
				
		        byteData = bytesOut.toByteArray();
				packet = new DatagramPacket(byteData, byteData.length, clientAddress, clientPort);
			    serverSocket.send(packet);
			}
			catch(Exception e){
				System.out.println(e);
				go = false;
			}
		}
		serverSocket.close();
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
