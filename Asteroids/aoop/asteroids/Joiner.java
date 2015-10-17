package aoop.asteroids;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import aoop.asteroids.gui.Player;
import aoop.asteroids.model.MultiplayerGame;
import aoop.asteroids.model.Spaceship;

public class Joiner implements Runnable {
	
	int clientPort;
	String clientAddress;
	int serverPort;
	InetAddress serverAddress;
	
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	DataOutputStream dataOut;
	ByteArrayOutputStream bytesOut;
	ByteArrayInputStream bytesIn;
	byte[] byteData;
	
	Spaceship ship;
	
	public Joiner(String serverAddress, int serverPort) {
		try {
			this.ship = new Spaceship();
			this.serverAddress = InetAddress.getByName(serverAddress);
			this.serverPort = serverPort;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		ObjectOutputStream dataOut;
		try {
			dataOut = new ObjectOutputStream(bytesOut);
			dataOut.writeObject(this.ship);
		    byteData = bytesOut.toByteArray();	
			packet = new DatagramPacket(byteData, byteData.length, serverAddress, serverPort);
		    clientSocket.send(packet);
		    dataOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
