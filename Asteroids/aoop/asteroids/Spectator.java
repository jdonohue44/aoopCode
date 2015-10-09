package aoop.asteroids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import aoop.asteroids.model.Game;


public class Spectator extends Thread {

	int port;
	InetAddress address;
	DatagramSocket clientSocket = null;
	DatagramPacket packet;
	byte[] sendBuf = new byte[256];
	Game game;
	
	public Spectator(InetAddress address, int port) {
			this.port = port;	
			this.address = address;
	}
	
	public void run(){
		try {
			clientSocket = new DatagramSocket();
			byte[] buf = new byte[256];
			
			// request Game data
            Integer id = 1;
            byte byteId = id.byteValue();
            buf[0] = byteId;
			DatagramPacket packet = new DatagramPacket(buf, buf.length, this.address, 4720);
			clientSocket.send(packet);
			
	        // receive request
	        buf = new byte[256];
	        packet = new DatagramPacket(buf, buf.length);
	        clientSocket.receive(packet);
	        String received = new String(packet.getData(), 0, packet.getLength());
	        System.out.println("Recieved from Server: " + received);
	        clientSocket.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
