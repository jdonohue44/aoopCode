package model;

public class SocketConnection {

	
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
