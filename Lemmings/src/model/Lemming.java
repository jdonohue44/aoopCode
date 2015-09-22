package model;
import java.io.Serializable;

import controller.FieldConnector;


public class Lemming implements Serializable, Runnable{

	private static final long serialVersionUID = -5977684576375763088L;
	public FieldMap knownFields;
	public transient Field field;
	public boolean alive;
	
	public Lemming(Field field){
		this.field = field;
		this.alive = true;
		this.knownFields = field.neighbors;
		this.field.lemmings.add(this);
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(this.isAlive()){
			double action = Math.random();
			if(action <= 0.15){
				System.out.println("birth");
				this.giveBirth();
			}
			else if((action > 0.15) && (action <= 0.60)){
				System.out.println("move");
				this.changeField();

			}
			else{
				System.out.println("sleep");
				this.sleep();
			}
			System.out.println(this.field);
		}
	}
	
	public FieldMap getKnownFields(){
		return this.knownFields;
	}
	
	public void setKnownFields(FieldMap knownFields){
		this.knownFields = knownFields;
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
		this.field.lemmings.add(new Lemming(this.field));
		if (this.field.numberOfLemmings == this.field.capacity){ 
			this.alive = false; // The mother Lemming has comitted suicicde...
			this.field.lemmings.remove(this);
			System.out.println("suicide");
		}
		else{
			this.field.numberOfLemmings ++;
		}
	}
	
	public void changeField(){
		this.field.numberOfLemmings--;
		this.field.lemmings.remove(this);
		int fieldNumber = (int)(Math.random()*this.knownFields.getMap().size());
		FieldConnector fc = new FieldConnector(this.knownFields.get(fieldNumber).getAddress(),this.knownFields.get(fieldNumber).getPort());
		fc.Send(this);
		this.alive = false;
	}
	
//	public String toString(){
//		return " |||||   \n"
//				+        "  O O    \n"
//				+        "  \\_/    \n"
//				+        " /  \\   \n"
//				+        "||   ||  \n"
//				+        "|/  \\|  \n"
//				+        ")     (  \n"
//				+        "_______  \n"
//				+        "|__|__|  \n";
//	}
}
