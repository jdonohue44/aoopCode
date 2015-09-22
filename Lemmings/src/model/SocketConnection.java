package model;

import java.io.Serializable;

public class SocketConnection implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3002920225538942283L;
	public String address;
	public int port;
	
	public SocketConnection(String a, int p){
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
	
}
