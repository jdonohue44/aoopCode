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
	    	  myLemming.field = this.field;
	    	  System.out.println(myLemming.id + " " + myLemming.field + "...");
	    	  if(this.field.numberOfLemmings<10){
	    		  for(int i = 0 ; i < myLemming.field.knownFields.getMap().size(); i++){
	    			  if(!this.field.knownFields.getMap().contains(myLemming.field.knownFields.getMap().get(i))){
	    				  this.field.knownFields.getMap().add(myLemming.field.knownFields.getMap().get(i));
	    			  }
	    		  }
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
