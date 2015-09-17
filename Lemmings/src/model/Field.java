package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.FieldConnector;
import controller.InputHandler;
import view.FieldView;

public class Field implements Runnable{

	public int capacity; 
	public String address; 
	public int port;
	public int numberOfLemmings;
	public ServerSocket serversocket;
	public FieldMap neighbors;
	
	public Field() throws IOException{
		this.numberOfLemmings = 0;
		this.capacity = 10;
		this.serversocket = new ServerSocket(0);
		this.port = this.serversocket.getLocalPort();
		this.neighbors = new FieldMap();
	}
	
	@Override
	public void run(){
		try {
			Lemming Steve = new Lemming("Steve",10);
	    	FieldConnector connector = new FieldConnector(this);
		    connector.Send(Steve);
			System.out.println("Serving at port: " + this.port);
			Socket incoming = this.serversocket.accept();
			Thread thread = new Thread(new InputHandler(incoming));
			thread.start();
			this.neighbors.add(new SocketConnection(this.address, this.port));
			Steve.neighbors = this.neighbors;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getCapacity(){
		return this.capacity;
	}
	
	public String getAddress(){
		return this.serversocket.getLocalSocketAddress().toString();
	}
	
	public int getPort(){
		return this.port;
	}
	
	public int getNumberOfLemmings(){
		return this.numberOfLemmings;
	}
	
	public void setTitle(int address){
		
	}
	
	public String getLemmingsListing(){
		return "<no lemmings>";
	}
	
	public String getFieldsListing(){
		return "<no fields>";
	}

	public void addObserver(FieldView fieldView) {
		// TODO Auto-generated method stub
	}
}