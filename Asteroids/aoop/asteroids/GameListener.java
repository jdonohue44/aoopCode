package aoop.asteroids;

import java.io.Serializable;
import java.net.InetAddress;

public class GameListener implements Serializable{
	InetAddress clientAddress;
	int clientPort;
	int id;
	
	GameListener(InetAddress clientAddress, int clientPort, int id){
			this.clientAddress = clientAddress;
			this.clientPort = clientPort;
			this.id = id;
	}

	public InetAddress getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(InetAddress clientAddress) {
		this.clientAddress = clientAddress;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientAddress == null) ? 0 : clientAddress.hashCode());
		result = prime * result + clientPort;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameListener other = (GameListener) obj;
		if (clientAddress == null) {
			if (other.clientAddress != null)
				return false;
		} else if (!clientAddress.equals(other.clientAddress))
			return false;
		if (clientPort != other.clientPort)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}