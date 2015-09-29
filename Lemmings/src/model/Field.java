package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

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
		this.capacity = (int)(((Math.random()) * 5) + 1) * 10;
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
				Socket incoming = this.getServersocket().accept();
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
		return this.getServersocket().getLocalSocketAddress().toString();
	}
	
	public int getPort(){
		return this.port;
	}

	public synchronized String getLemmingsListing(){
		StringBuilder sb = new StringBuilder();
		for(int i =0; i < this.getLemmings().size(); i++){
			sb.append(this.getLemmings().get(i)+ "  ");
		}
		return sb.toString();
	}
	
	public String getFieldsListing(){
		return this.getKnownFields().toString();
	}
	
	
	public ServerSocket getServersocket() {
		return serversocket;
	}

	public void setServersocket(ServerSocket serversocket) {
		this.serversocket = serversocket;
	}

	public synchronized FieldMap getKnownFields() {
		return knownFields;
	}

	public synchronized void setKnownFields(FieldMap knownFields) {
		this.knownFields = knownFields;
	}

	public FieldConnector getFieldconnector() {
		return fieldconnector;
	}

	public void setFieldconnector(FieldConnector fieldconnector) {
		this.fieldconnector = fieldconnector;
	}

	public synchronized ArrayList<Lemming> getLemmings() {
		return lemmings;
	}

	public synchronized void setLemmings(ArrayList<Lemming> lemmings) {
		this.lemmings = lemmings;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName(){
		return this.fieldName;
	}
	
	public synchronized void add(Lemming l){
		this.lemmings.add(l);
		this.numberOfLemmings ++;
	}
	
	public synchronized void remove(Lemming l){
		this.lemmings.remove(l);
		this.numberOfLemmings --;
	}
	
	public synchronized void setNumberOfLemmings(int number){
		this.numberOfLemmings = number;
	}
	
	public synchronized int getNumberOfLemmings(){
		return this.numberOfLemmings;
	}
	
	public String toString(){
		return this.getFieldName();
	}

}
