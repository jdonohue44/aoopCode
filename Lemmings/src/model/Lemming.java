package model;
import java.io.Serializable;

import controller.FieldConnector;


public class Lemming implements Serializable, Runnable{

	private static final long serialVersionUID = -5977684576375763088L;
	
	public FieldMap neighbors;
	public int energy;
	public Field field;
	public boolean alive;
	
	public Lemming(Field f){
		this.field = f;
		this.neighbors = f.neighbors;
		this.alive = true;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(this.isAlive()){
			double action = Math.random();
			if(action <= 0.20){
				this.giveBirth();
			}
			else if((action > 0.20) && (action <= 0.60)){
				this.changeField();
			}
			else{
				this.sleep();
			}
		}
	}
	
	public FieldMap getNeighbors(){
		return this.neighbors;
	}
	
	public void setNeighbors(FieldMap neighbors){
		this.neighbors = neighbors;
	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	public void sleep(){
		try {
			Thread.sleep((long)(Math.random()*3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void giveBirth(){
		new Lemming(this.field); // A baby is born!
		if (this.field.numberOfLemmings == this.field.capacity){
			this.alive = false; // The mother Lemming has comitted suicicde...
		}
		else{
			this.field.numberOfLemmings ++;
		}
	}
	
	public void changeField(){
		FieldConnector fc = new FieldConnector(this.field);
		if(!fc.Send(this)){
			this.alive = false;
		}
	}
	
	public String toString(){
		return "|||||   \n"
				+        "  O O    \n"
				+        "  \\_/    \n"
				+        " /  \\   \n"
				+        "||   ||  \n"
				+        "|/  \\|  \n"
				+        ")     (  \n"
				+        "_______  \n"
				+        "|__|__|  \n";
	}
}
