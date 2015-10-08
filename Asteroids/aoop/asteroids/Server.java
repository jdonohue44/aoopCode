package aoop.asteroids;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import aoop.asteroids.model.Game;

public class Server extends Thread{

	private ArrayList<Spectator> spectators;
	
	DatagramSocket serverSocket;
	int port;
	InetAddress address;
	
	byte[] receiveData = new byte[1024];  
	byte[] sendData = new byte[1024];
	Game game;
	
	public Server(Game game, int port){
		this.game = game;
		this.spectators = new ArrayList<Spectator>();
		try {
			this.serverSocket = new DatagramSocket(port);
			this.address = InetAddress.getByName("localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.port = port;
	}
	
	public void run(){
		while(true){
			try{
		        // receive request
		        byte[] buf = new byte[256];
		        DatagramPacket packet = new DatagramPacket(buf, buf.length);
		        serverSocket.receive(packet);
		        String received = new String(packet.getData(), 0, packet.getLength());
		        System.out.println("Recieved from Client: " + received);
		        
		        int portnumber = packet.getPort();
		        InetAddress address = packet.getAddress();
		        
		        // add this spectator to list if it is new.
		        ArrayList<Integer> spectatorPorts = new ArrayList<Integer>();
		        if(!(spectatorPorts.contains(portnumber))){
		        	this.spectators.add(new Spectator(address, portnumber));
		        }
		        
		        // receive game data and update servers game data
		        
		        
		        // send spectator most recent Game data
		        String s = "Here is some game data";
		        buf = s.getBytes();
				packet = new DatagramPacket(buf, buf.length, address, portnumber);
			    serverSocket.send(packet);
			}
			catch(IOException e){
				System.out.println(e);
			}
		}
	}

	public ArrayList<Spectator> getSpectators() {
		return spectators;
	}

	public void setSpectators(ArrayList<Spectator> spectators) {
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

	public byte[] getReceiveData() {
		return receiveData;
	}

	public void setReceiveData(byte[] receiveData) {
		this.receiveData = receiveData;
	}

	public byte[] getSendData() {
		return sendData;
	}

	public void setSendData(byte[] sendData) {
		this.sendData = sendData;
	}

}
