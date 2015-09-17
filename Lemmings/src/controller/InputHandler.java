package controller;

import java.io.ObjectInputStream;
import java.net.Socket;

import model.Lemming;

public class InputHandler implements Runnable{

	
    private Socket incoming ;
    
    public InputHandler(Socket s){
    	this.incoming = s;
    }
	@Override
	public void run(){
	      try {
	    	  ObjectInputStream in= new ObjectInputStream(this.incoming.getInputStream());
	    	  Lemming myLemming = ((Lemming)in.readObject());
	    	  System.out.println(myLemming);
	    	  in.close();
	      	  }
	      catch (Exception e) {
	          e.printStackTrace();
	       }
	}
}
