package model;
import java.io.Serializable;

import controller.FieldConnector;


public class Lemming implements Serializable, Runnable{

	private static final long serialVersionUID = -5977684576375763088L;
	public FieldMap knownFields;
	public transient Field field;
	public boolean alive;
	
	public double name;
	
	public Lemming(Field field){
		this.field = field;
		this.alive = true;
		this.knownFields = new FieldMap();
		this.knownFields.add(new SocketConnection(this.field.address,this.field.port));
		this.field.lemmings.add(this);
		new Thread(this).start();
		
		this.name = Math.random()*100;
	}

	@Override
	public void run() {
		while(this.isAlive()){
			double action = Math.random();
<<<<<<< HEAD
			if(action <= 0.20){
				System.out.println(this.name+" birth");
				this.giveBirth();
			}
			else if((action > 0.20) && (action <= 0.60)){
				System.out.println(this.name+" move");
=======
			if(action <= 0.15){
				System.out.println("birth");
				this.giveBirth();
			}
			else if((action > 0.15) && (action <= 0.60)){
				System.out.println("move");
>>>>>>> origin/master
				this.changeField();
			}
			else{
				System.out.println(this.name+" sleep");
				this.sleep();
			}
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
		new Lemming(this.field); // A baby is born on this field
		if (this.field.numberOfLemmings == this.field.capacity){ 
			this.alive = false; // The mother Lemming has comitted suicicde...
			System.out.println(this.name+" suicide");
		}
		else{
			this.field.numberOfLemmings ++;
		}
	}
	
	public void changeField(){
		int fieldNumber = (int)Math.random()*this.knownFields.getMap().size();
		FieldConnector fc = new FieldConnector(this.knownFields.get(fieldNumber).getAddress(),this.knownFields.get(fieldNumber).getPort());
		fc.Send(this);
		this.field.numberOfLemmings--;
		this.field.lemmings.remove(this);
		this.alive = false;
	}
	
	public String toString(){
		return " |||||   \n"
				+        "  O O    \n"
				+        "  \\_/    \n"
				+        " /   \\   \n"
				+        "||   ||  \n"
				+        "|/   \\|  \n"
				+        ")     (  \n"
				+        "_______  \n"
				+        "|__|__|  \n";
	}
}
