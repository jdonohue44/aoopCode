package model;

import java.io.Serializable;

public class SocketConnection implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3002920225538942283L;
	public String address;
	public int port;
	public String name;
	
	public SocketConnection(String name,String a, int p){
		this.name = name;
		this.address = a;
		this.port = p;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String toString(){
		return this.name + "(" + this.address + ", port:" + this.port + ")";
	}
}
