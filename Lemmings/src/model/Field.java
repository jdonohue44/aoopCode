package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import view.FieldView;
import controller.FieldConnector;
import controller.InputHandler;

public class Field extends Observable implements Runnable{

	public int capacity; 
	public String address = "localhost"; 
	public int port;
	public int numberOfLemmings;
	public ServerSocket serversocket;
	public FieldMap neighbors;
	public FieldConnector fieldconnector;
	public ArrayList<Lemming> lemmings;
	public String fieldName;
	
	public Field(String fieldName) throws IOException{
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
	
	public String getLemmingsListing(){
		StringBuilder sb = new StringBuilder();
		for(int i =0; i < this.lemmings.size()-1; i++){
			sb.append(this.lemmings.get(i)+", ");
		}
		sb.append(this.lemmings.get(this.lemmings.size()-1));
		return sb.toString();
	}
	
	public String getFieldsListing(){
		StringBuilder sb = new StringBuilder();
		for(int i =0; i < this.neighbors.map.size()-1; i++){
			sb.append(this.neighbors.map.get(i)+", ");
		}
		sb.append(this.neighbors.map.get(this.neighbors.map.size()-1));
		return sb.toString();
	}
	
	public String getFieldName(){
		return this.fieldName;
	}

}
