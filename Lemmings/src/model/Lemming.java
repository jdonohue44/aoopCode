package model;
import java.io.Serializable;

import controller.FieldConnector;


public class Lemming implements Serializable, Runnable{

	private static final long serialVersionUID = -5977684576375763088L;
	public FieldMap knownFields;
	public transient Field field;
	public boolean alive;
	public int id;
	public String status;
	
	public static int counter = 1;
	
	public Lemming(Field field){
		this.field = field;
		this.alive = true;
		this.knownFields = field.knownFields;
		this.field.add(this);
		this.id = counter;
		this.status = "Just arrived";
		new Thread(this).start();
		
		counter++;
	}

	@Override
	public void run() {
		while(this.isAlive()){
			double action = Math.random();
			if(action <= 0.40){
				this.giveBirth();
				this.setStatus("Just gave birth");
			}
			else if((action > 0.40) && (action <= .50)){
				this.changeField();
			}
			else{
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
	
	public void setAlive(boolean alive) {
		this.alive = alive;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void sleep(){
		try {
			Thread.sleep((long)(Math.random()*3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void giveBirth(){
		if (this.getField().getNumberOfLemmings() >= this.getField().getCapacity()){ 
			this.setAlive(false);
			this.getField().remove(this);
		}
		new Lemming(this.getField());
	}
	
	/*
	 * int fieldNumber --> Get a random field in the range (2,n), where n is the number of fields in this field map.
	 */
	public void changeField(){
		this.getField().remove(this);
		int fieldNumber = (int)((Math.random()*this.getKnownFields().getMap().size())-1)+1;
		FieldConnector fc = new FieldConnector(this.getKnownFields().get(fieldNumber).getAddress(),this.getKnownFields().get(fieldNumber).getPort());
		fc.Send(this);
		this.setAlive(false);
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
