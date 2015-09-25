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
	public FieldMap knownFields;
	public FieldConnector fieldconnector;
	public ArrayList<Lemming> lemmings;
	public String fieldName;
	
	public Field(String fieldName) throws IOException{
		this.numberOfLemmings = 0;
		this.capacity = 10;
		this.lemmings = new ArrayList<Lemming>();
		this.serversocket = new ServerSocket(0);
		this.port = this.serversocket.getLocalPort();
		this.fieldName = fieldName;
		this.knownFields = new FieldMap();
		new Thread(this).start();
		}
	
	@Override
	public void run(){
		while(true){
			try {
				Socket incoming = this.serversocket.accept();
				Thread thread = new Thread(new InputHandler(this,incoming));
				thread.start();
				setChanged();
				notifyObservers();
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
	
	public synchronized int getNumberOfLemmings(){
		return this.numberOfLemmings;
	}
	
	public synchronized String getLemmingsListing(){
		StringBuilder sb = new StringBuilder();
		for(int i =0; i < this.lemmings.size(); i++){
			sb.append(this.lemmings.get(i)+ "  ");
		}
		return sb.toString();
	}
	
	public synchronized String getFieldsListing(){
		return this.knownFields.toString();
	}
	
	public String toString(){
		return this.getFieldName();
	}
	
	public String getFieldName(){
		return this.fieldName;
	}
	
	public synchronized void add(Lemming l){
		this.lemmings.add(l);
	}
	
	public synchronized void remove(Lemming l){
		this.lemmings.remove(l);
	}

}
