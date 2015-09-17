package model;
import java.io.Serializable;


public class Lemming implements Serializable, Runnable{

	private static final long serialVersionUID = -5977684576375763088L;
	
	public FieldMap neighbors;
	public String name;
	public int energy;
	
	public Lemming(String name, int energy){
		this.name = name;
		this.energy = energy;
	}

	@Override
	public void run() {
		try {
			Thread.sleep((long)Math.random()*3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public FieldMap getNeighbors(){
		return this.neighbors;
	}
	
	public void setNeighbors(FieldMap neighbors){
		this.neighbors = neighbors;
	}
	
	public String toString(){
		return " I AM A LEMMING. MY NAME IS " + this.name;
	}
	
	public void sleep(){
	}
	
	public void eat(){
		
	}
	
	public void giveBirth(){
		
	}
	
	public void changeField(){
		
	}

}
