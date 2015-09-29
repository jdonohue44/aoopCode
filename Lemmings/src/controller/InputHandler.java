package controller;

import java.io.ObjectInputStream;
import java.net.Socket;

import model.Field;
import model.Lemming;
public class InputHandler implements Runnable{

	
    private Socket incoming ;
    public Field field;
    
    public InputHandler(Field field, Socket s){
    	this.incoming = s;
    	this.field = field;
    }
	public Socket getIncoming() {
		return incoming;
	}
	public void setIncoming(Socket incoming) {
		this.incoming = incoming;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	@Override
	public void run(){
	      try {
	    	  ObjectInputStream in= new ObjectInputStream(this.getIncoming().getInputStream());
	    	  Lemming myLemming = ((Lemming)in.readObject());
	    	  if(this.getField().getNumberOfLemmings()<this.getField().getCapacity()){
	    		  for(int i = 0 ; i < myLemming.getKnownFields().getMap().size(); i++){
	    			  if(!(this.getField().getKnownFields().getMap().contains(myLemming.getKnownFields().getMap().get(i)))){
	    				  this.getField().getKnownFields().getMap().add(myLemming.getKnownFields().getMap().get(i));
	    			  }
	    		  }
	    		  myLemming.setField(this.getField());
	    		  myLemming.setKnownFields(this.getField().getKnownFields());
	    		  this.getField().add(myLemming);
		    	  Thread thread = new Thread(myLemming);
		    	  thread.start();
	    	  }
	    	  else{
	    		  myLemming.setAlive(false);
	    	  }
	    	  in.close();
	      	  }
	      catch (Exception e) {
	          e.printStackTrace();
	       }
	}
}
