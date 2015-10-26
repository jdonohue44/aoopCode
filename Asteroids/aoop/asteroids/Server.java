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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import aoop.asteroids.gui.AsteroidsFrame;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiplayerGame;
import aoop.asteroids.model.Participant;
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
	
	EntityManagerFactory emf;
	EntityManager em;
	AsteroidsFrame frame;
	
	public Server(MultiplayerGame game, AsteroidsFrame frame){
		
		this.emf = Persistence.createEntityManagerFactory("$objectdb/db/participantsTest4.odb");
		this.em = emf.createEntityManager();
		Query query = em.createQuery("SELECT p FROM Participant p ORDER by p.highScore DESC", Participant.class);
        List<Participant> participants = query.getResultList();
        HashSet<Participant> set = new HashSet<Participant>();
        set.addAll(participants);
        participants.clear();
        participants.addAll(set);
        Collections.sort(participants);
        frame.getHighScoresPanel().setParticipants((ArrayList) participants);
		this.frame = frame;
		
		this.game = game;
		this.gameListeners = new HashSet<GameListener>();
		this.packetReferenceNumber = 1;
		try {
			this.serverSocket = new DatagramSocket(57653);
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
		        
			        // Spectator
			        if(listener.getId() == 0){
			        	if(!this.gameListeners.contains(listener)){
			        		this.gameListeners.add(listener);
			        	}
			        	else{
			        		System.out.println(gameListeners.size());
			        		this.gameListeners.remove(listener);
			        		System.out.println(gameListeners.size());
			        	}
				        objIn.close();
			        }
			        
			        // Joiner
			        else if(listener.getId() == 1) {
			        	this.gameListeners.add(listener);
			        	Spaceship ship = (Spaceship) objIn.readObject();
			        	boolean containsShip = false;
			        	int counter = 0;
			        	for(Spaceship s : this.game.ships){
				        	if((s.getId() == ship.getId())){
				        		synchronized (this.game.getLock()) { 
				        			ship.setStepsTilFire(((ArrayList<Spaceship>)this.game.ships).get(counter).getStepsTilFire());
				        			ship.setScore(((ArrayList<Spaceship>)this.game.ships).get(counter).getScore());
				        			((ArrayList <Spaceship>) this.game.ships).set(counter,ship);
				        		}
				        		containsShip = true;
				        	}
				        	counter++;
			        	}
			        	if(!containsShip && ship.stepsTilCollide() > 0) this.game.ships.add(ship);
				        objIn.close();
			        }
			        else{
			        	throw new Exception("I'm sorry, I don't recognize that packet ID.");
			        }
				}catch(IOException e){
					
				}
				
				ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
				ObjectOutputStream objOut = new ObjectOutputStream(bytesOut);
				
				if (this.game.isRunning()) {
					objOut.writeObject(1);
					objOut.writeObject(this.game.getRound());
	 				objOut.writeObject(this.game.getShips());
					objOut.writeObject(this.game.getAsteroids());
					objOut.writeObject(this.game.getBullets());
					objOut.writeObject(this.game.getExplosions());
				    objOut.close();
				} else if(this.game.getRound() == 11){		
						objOut.writeObject(0);
						objOut.close();
						em.getTransaction().begin();
					        for (Spaceship s : game.getShips()) {
					            em.persist(new Participant(s.getScore(),s.getNickname()));
					            System.out.println(new Participant(s.getScore(),s.getNickname()));
					        }   
					        em.getTransaction().commit();			        
							Query query = em.createQuery("SELECT p FROM Participant p ORDER by p.highScore DESC", Participant.class);
					        List<Participant> participants = query.getResultList();
					        HashSet<Participant> set = new HashSet<Participant>();
					        set.addAll(participants);
					        participants.clear();
					        participants.addAll(set);
					        Collections.sort(participants); 
					        em.close();
					        emf.close();
					        this.game.abort();
					        go = false;
					}
				else{
					objOut.writeObject(0);
					objOut.close();
				}
				
		        for(GameListener gl : this.gameListeners){
			        InetAddress clientAddress = gl.getClientAddress();
			        int clientPort = gl.getClientPort();
			        byteData = bytesOut.toByteArray();
			        packet = new DatagramPacket(byteData, byteData.length, clientAddress, clientPort);
			        serverSocket.send(packet);
		        }
			}
			catch(Exception e){
				System.out.println(  "On the Server: " + e);
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
