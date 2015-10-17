package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;

import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiplayerGame;


public class Server extends Thread{

	private HashSet<GameListener> gameListeners; // List of Spectators
	
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
	
	MultiplayerGame game;
	
	public Server(MultiplayerGame game){
		this.game = game;
		this.gameListeners = new HashSet<GameListener>();
		this.packetReferenceNumber = 1;
		try {
			this.serverSocket = new DatagramSocket(58762);
			this.address = InetAddress.getByName("localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.port = serverSocket.getLocalPort();
		this.game.setPort(this.port);
	}
	
	public void run(){
		boolean go = true;
		while(go){
			try{
		        // receive request from Client
				byteData = new byte[120];
				packet = new DatagramPacket(byteData, byteData.length);
		        serverSocket.receive(packet);
		        byteData = packet.getData();
		        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
				ObjectInputStream dataIn = new ObjectInputStream(bytesIn);
		        GameListener listener = (GameListener) dataIn.readObject();
		        dataIn.close();
		        
		        this.gameListeners.add(listener);

		        int clientPort = packet.getPort();
		        InetAddress clientAddress = packet.getAddress();

				ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
				ObjectOutputStream objOut = new ObjectOutputStream(bytesOut);	

				objOut.writeObject(this.game.getShips());
				objOut.writeObject(this.game.getAsteroids());
				objOut.writeObject(this.game.getBullets());
			    objOut.close();
				
		        byteData = bytesOut.toByteArray();
		        packet = new DatagramPacket(byteData, byteData.length, clientAddress, clientPort);
		        serverSocket.send(packet);
			}
			catch(Exception e){
				System.out.println(e +  "SERVER");
				go = false;
				serverSocket.close();
			}
		}
	}

	public HashSet<GameListener> getSpectators() {
		return this.gameListeners;
	}

	public void setSpectators(HashSet<GameListener> gameListeners) {
		this.gameListeners = gameListeners;
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

	public void setGame(MultiplayerGame game) {
		this.game = game;
	}

}
