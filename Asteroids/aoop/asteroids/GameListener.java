package aoop.asteroids;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameListener implements Serializable{
	InetAddress clientAddress;
	int clientPort;
	GameListener(String clientAddress, int clientPort){
		try {
			this.clientAddress = InetAddress.getByName(clientAddress);
			this.clientPort = clientPort;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientAddress == null) ? 0 : clientAddress.hashCode());
		result = prime * result + clientPort;
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
		return true;
	}
}