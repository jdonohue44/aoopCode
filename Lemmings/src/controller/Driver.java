package controller;

import java.io.IOException;

import view.FieldView;
import model.Field;
import model.Lemming;
import model.SocketConnection;

public class Driver {
	
	public static void main(String[] args){
		Field field1 = null;
		Field field2 = null;
		Field field3 = null;
		
		try {
			field1 = new Field("Strawberry Field");
			field2 = new Field("Moo Moo Meadows");
			field3 = new Field("Jelly Fish Field");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		field1.knownFields.add(new SocketConnection(field1.fieldName, field1.address,field1.port));
		field1.knownFields.add(new SocketConnection(field2.fieldName, field2.address,field2.port));
		field1.knownFields.add(new SocketConnection(field3.fieldName, field3.address,field3.port));
		Thread thread = new Thread(field1);
		thread.start();
		
		field2.knownFields.add(new SocketConnection(field2.fieldName, field2.address,field2.port));
		field2.knownFields.add(new SocketConnection(field3.fieldName, field3.address,field3.port));
		field2.knownFields.add(new SocketConnection(field1.fieldName, field1.address,field1.port));
		Thread thread2 = new Thread(field2);
		thread2.start();
		
		field3.knownFields.add(new SocketConnection(field3.fieldName, field3.address,field3.port));
		field3.knownFields.add(new SocketConnection(field1.fieldName, field1.address,field1.port));
		field3.knownFields.add(new SocketConnection(field2.fieldName, field2.address,field2.port));
		Thread thread3 = new Thread(field3);
		thread3.start();

		Lemming L = new Lemming(field1);
		field1.numberOfLemmings++;
		System.out.println("Serving at port: " + field1.port + "...");
		System.out.println("Serving at port: " + field2.port + "...");
		System.out.println("Serving at port: " + field3.port + "...");
		new FieldView(field1);
		new FieldView(field2);
		new FieldView(field3);
	}
} 
