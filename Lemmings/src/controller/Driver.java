package controller;

import java.io.IOException;

import view.FieldView;
import model.Field;
import model.Lemming;

public class Driver {
	
	public static void main(String[] args){
		Field field = null;
		try {
			field = new Field();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(field);
		thread.start();
		Lemming L = new Lemming(field);
    	FieldConnector connector = new FieldConnector(field);
	    connector.Send(L);
		System.out.println("Serving at port: " + field.port);
		//new FieldView(field);
	}
} 
