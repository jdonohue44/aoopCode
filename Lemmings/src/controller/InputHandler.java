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
	    	  this.field.lemmings.add(myLemming);
	    	  myLemming.field = this.field;
	    	  Thread thread = new Thread(myLemming);
	    	  thread.start();
	    	  System.out.println("reading  object...");
	    	  in.close();
	      	  }
	      catch (Exception e) {
	    	  // flag something went wrong.
	          e.printStackTrace();
	       }
	}
}
