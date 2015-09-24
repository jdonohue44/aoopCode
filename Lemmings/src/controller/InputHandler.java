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
		    	  if(this.field.numberOfLemmings<10){
			    	  this.field.add(myLemming);
			    	  myLemming.field = this.field;
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
