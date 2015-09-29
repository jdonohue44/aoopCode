package model;

import java.io.Serializable;
import java.util.ArrayList;

public class FieldMap implements Serializable{

	private static final long serialVersionUID = 5014743115202459792L;
	
	public ArrayList<SocketConnection> map;
	
	public FieldMap(){
		this.map = new ArrayList<SocketConnection>();
	}
	
	public void add(SocketConnection sc){
		this.map.add(sc);
	}
	
	public void remove(SocketConnection sc){
		this.map.remove(sc);
	}

	public ArrayList<SocketConnection> getMap() {
		return map;
	}
	
	public SocketConnection get(int i) {
		return map.get(i);
	}

	public void setMap(ArrayList<SocketConnection> map) {
		this.map = map;
	}
	
	@Override
	public boolean equals(Object o){
		FieldMap other = (FieldMap)o;
		for(int i = 0; i <this.getMap().size(); i++){
			if(!(this.getMap().get(i).equals(other.getMap().get(i)))){
				return false;
			}
		}
		return true;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i =0; i < this.getMap().size(); i++){
			sb.append(this.getMap().get(i) + "     ");
		}
		return sb.toString();
	}
}
