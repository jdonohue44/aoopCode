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
	    	  if(this.field.getNumberOfLemmings()<this.field.getCapacity()){
	    		  for(int i = 0 ; i < myLemming.getKnownFields().getMap().size(); i++){
	    			  if(!(this.field.getKnownFields().getMap().contains(myLemming.getKnownFields().getMap().get(i)))){
	    				  this.field.getKnownFields().getMap().add(myLemming.getKnownFields().getMap().get(i));
	    			  }
	    		  }
	    		  myLemming.setField(this.field);
	    		  myLemming.setKnownFields(this.field.getKnownFields());
	    		  this.field.add(myLemming);
	    		  this.field.setNumberOfLemmings(this.field.getNumberOfLemmings()+1);
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
