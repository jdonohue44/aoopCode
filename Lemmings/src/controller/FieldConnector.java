package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import model.Lemming;

public class FieldConnector{

	int port;
	String address;
	
	public FieldConnector(String address, int port){
		this.address = address;
		this.port = port;
	}
	
	public void Send(Lemming L){
		try {
			Socket client  = new Socket();
			client.connect(new InetSocketAddress(this.address, this.port));
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			out.writeObject(L);
			client.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
