package model;
import java.io.Serializable;

import controller.FieldConnector;


public class Lemming implements Serializable, Runnable{

	private static final long serialVersionUID = -5977684576375763088L;
	public FieldMap knownFields;
	public transient Field field;
	public boolean alive;
	public static int counter = 0;
	public int id;
	public String status;
	
	public Lemming(Field field){
		this.field = field;
		this.alive = true;
		this.knownFields = field.knownFields;
		this.field.add(this);
		this.id = counter;
		counter++;
		this.status = "Just born";
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(this.isAlive()){
			double action = Math.random();
			if(action <= 0.10){
				this.status = "Giving birth";
				this.giveBirth();
			}
			else if((action > 0.10) && (action <= 0.60)){
				this.status = "Moving to another field";
				this.changeField();

			}
			else{
				this.status = "Sleeping";
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
	
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Lemming.counter = counter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void sleep(){
		try {
			Thread.sleep((long)(Math.random()*3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void giveBirth(){
		new Lemming(this.field);
		if (this.field.numberOfLemmings >= this.field.capacity){ 
			this.alive = false; // The mother Lemming has comitted suicicde...
			this.field.remove(this);
		}
		else{
			this.field.numberOfLemmings ++;
		}
	}
	
	public void changeField(){
		this.field.numberOfLemmings--;
		this.field.remove(this);
		
		/*
		 * fieldNumber --> Get a random field in the range (2,n), where n is the number of fields in this field map.
		 */
		int fieldNumber = (int)((Math.random()*this.knownFields.getMap().size())-1)+1;
		FieldConnector fc = new FieldConnector(this.knownFields.get(fieldNumber).getAddress(),this.knownFields.get(fieldNumber).getPort());
		fc.Send(this);
		this.alive = false;
	}
	
	public String toString(){
		return 	         
						 "ID:  " + this.getId() + "\nStatus: " + status
				+        "\n O O    \n"
				+        "  \\_/    \n"
				+        "  /  \\   \n"
				+        " ----  \n"
				+        "|__|__|  \n\n\n";
	}
}
