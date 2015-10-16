package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;


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
	
	int numberOfAsteroids;
	int numberOfBullets;
	
	Game game;
	
	public Server(Game game){
		this.game = game;
		this.spectators = new HashSet<Spectator>();
		this.packetReferenceNumber = 1;
		try {
			this.serverSocket = new DatagramSocket(0);
			this.address = InetAddress.getByName("localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.port = serverSocket.getLocalPort();
		this.game.port = this.port;
	}
	
	public void run(){
		boolean go = true;
		while(go){
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
		   
		        numberOfAsteroids = this.game.getAsteroids().size();
		        numberOfBullets = this.game.getBullets().size();
 
				ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
				ObjectOutputStream objOut = new ObjectOutputStream(bytesOut);	
				
				objOut.writeInt(this.game.getAsteroids().size());
				objOut.writeInt(this.game.getBullets().size());
				objOut.writeObject(this.game.getPlayer());
				objOut.writeObject(this.game.getAsteroids());
				objOut.writeObject(this.game.getBullets());
			    objOut.close();
				
		        byteData = bytesOut.toByteArray();
				packet = new DatagramPacket(byteData, byteData.length, clientAddress, clientPort);
			    serverSocket.send(packet);

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
