package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

import model.Field;
import model.Lemming;

public class FieldConnector implements Serializable{

	private static final long serialVersionUID = 6159903509176495008L;
	int port;
	String address;
	Socket client;
	
	public FieldConnector(Field field){
		this.port = field.getPort();
		this.address = "localhost";
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

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}
}
