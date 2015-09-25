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
	@Override
	public void run(){
	      try {
	    	  ObjectInputStream in= new ObjectInputStream(this.incoming.getInputStream());
	    	  Lemming myLemming = ((Lemming)in.readObject());
	    	  if(this.field.numberOfLemmings<this.field.getCapacity()){
	    		  for(int i = 0 ; i < myLemming.knownFields.getMap().size(); i++){
	    			  if(!(this.field.knownFields.getMap().contains(myLemming.knownFields.getMap().get(i)))){
	    				  this.field.knownFields.getMap().add(myLemming.knownFields.getMap().get(i));
	    			  }
	    		  }
	    		  myLemming.field = this.field;
	    		  myLemming.knownFields = this.field.knownFields;
	    		  this.field.add(myLemming);
	    		  this.field.numberOfLemmings ++;
		    	  Thread thread = new Thread(myLemming);
		    	  thread.start();
	    	  }
	    	  else{
	    		  myLemming.alive = false;
	    	  }
	    	  in.close();
	      	  }
	      catch (Exception e) {
	          e.printStackTrace();
	       }
	}
}
