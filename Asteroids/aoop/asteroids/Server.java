package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;

import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiplayerGame;
import aoop.asteroids.model.Spaceship;


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
			this.serverSocket = new DatagramSocket(0);
			this.address = InetAddress.getByName("localhost");
			this.game.setHost(serverSocket.getLocalAddress().getLocalHost());
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
				byteData = new byte[800];
				packet = new DatagramPacket(byteData, byteData.length);
				try{
					serverSocket.setSoTimeout(5);
			        serverSocket.receive(packet);
			        byteData = packet.getData();
			        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteData);
					ObjectInputStream objIn = new ObjectInputStream(bytesIn);
			        GameListener listener = (GameListener) objIn.readObject();
		        
			        if(listener.getId() == 0){
			        	if(!this.gameListeners.contains(listener)){
			        		this.gameListeners.add(listener);
			        	}
			        	else{
			        		this.gameListeners.remove(listener);
			        	}
				        objIn.close();
			        }
			        else if(listener.getId() == 1) {
			        	this.gameListeners.add(listener);
			        	Spaceship ship = (Spaceship) objIn.readObject();
			        	this.game.addShip(ship);
				        objIn.close();
			        }
			        else if(listener.getId() == 2) {
			        	Spaceship s = (Spaceship) objIn.readObject();
			        	this.game.ships.toArray()[1] = s;
				        objIn.close();
			        }
			        else{
			        	throw new Exception("I'm sorry, I don't recognize that packet ID.");
			        }
				}catch(IOException e){
				}
		        
				ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
				ObjectOutputStream objOut = new ObjectOutputStream(bytesOut);
				objOut.writeObject(this.game.getShips());
				objOut.writeObject(this.game.getAsteroids());
				objOut.writeObject(this.game.getBullets());
			    objOut.close();
				
		        for(GameListener gl : this.gameListeners){
			        InetAddress clientAddress = gl.getClientAddress();
			        int clientPort = gl.getClientPort();
			        byteData = bytesOut.toByteArray();
			        packet = new DatagramPacket(byteData, byteData.length, clientAddress, clientPort);
			        serverSocket.send(packet);
		        }
			}
			catch(Exception e){
				System.out.println(e +  " on the Server");
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
