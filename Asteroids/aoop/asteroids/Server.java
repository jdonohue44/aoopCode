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
	
	byte[] receiveData = new byte[1024];  
	byte[] sendData = new byte[1024];
	Game game;
	
	public Server(){
		this.spectators = new ArrayList<Spectator>();
		try {
			this.serverSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.port = serverSocket.getLocalPort();
	}
	
	public void run(){
		while(true){
			try{
		        byte[] buf = new byte[256];
		        // receive request
		        DatagramPacket packet = new DatagramPacket(buf, buf.length);
		        serverSocket.receive(packet);
		        
		        // Get Game data and load it into the buffer
		   
		        
		        // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                serverSocket.send(packet);
			}
			catch(IOException e){
			}
			serverSocket.close();
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
