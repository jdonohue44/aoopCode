package controller;

import java.io.ObjectInputStream;
import java.net.Socket;

import model.Lemming;
import model.SocketConnection;

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
	    	  System.out.println("reading  object...");
	    	  System.out.println(myLemming);
	    	  System.out.print(myLemming.field);
	    	  in.close();
	      	  }
	      catch (Exception e) {
	          e.printStackTrace();
	       }
	}
}
