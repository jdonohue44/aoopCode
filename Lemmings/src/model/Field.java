package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import view.FieldView;
import controller.FieldConnector;
import controller.InputHandler;

public class Field implements Runnable{

	public int capacity; 
	public String address = "localhost"; 
	public int port;
	public int numberOfLemmings;
	public ServerSocket serversocket;
	public FieldMap neighbors;
	public FieldConnector fieldconnector;
	public ArrayList<Lemming> lemmings;
	
	public Field() throws IOException{
		this.numberOfLemmings = 0;
		this.capacity = 10;
		this.lemmings = new ArrayList<Lemming>();
		this.serversocket = new ServerSocket(0);
		this.port = this.serversocket.getLocalPort();
		this.neighbors = new FieldMap();
		new Thread(this).start();
		}
	
	@Override
	public void run(){
		while(true){
			try {
				Socket incoming = this.serversocket.accept();
				Thread thread = new Thread(new InputHandler(this,incoming));
				thread.start();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
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
