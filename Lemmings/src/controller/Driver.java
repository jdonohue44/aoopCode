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
		Field field4 = null;
		Field field5 = null;
		
		try {
			field1 = new Field("Strawberry Field");
			field2 = new Field("Moo Moo Meadows");
			field3 = new Field("Jelly Fish Field");
			field4 = new Field("Mushroom Gorge");
			field5 = new Field("Koopa Troopa Beach");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// Dont hard code in all of the fields to the fieldmap. Start a Field only knowing its
		// own field, and have the lemmings update the fieldmap when it moves
		
		field1.knownFields.add(new SocketConnection(field1.fieldName, field1.address,field1.port));
		field1.knownFields.add(new SocketConnection(field2.fieldName, field2.address,field2.port));
		Thread thread = new Thread(field1);
		thread.start();
		
		field2.knownFields.add(new SocketConnection(field2.fieldName, field2.address,field2.port));
		field2.knownFields.add(new SocketConnection(field3.fieldName, field3.address,field3.port));
		Thread thread2 = new Thread(field2);
		thread2.start();
		
		field3.knownFields.add(new SocketConnection(field3.fieldName, field3.address,field3.port));
		field3.knownFields.add(new SocketConnection(field4.fieldName, field4.address,field4.port));
		Thread thread3 = new Thread(field3);
		thread3.start();
		
		field4.knownFields.add(new SocketConnection(field4.fieldName, field4.address,field4.port));
		field4.knownFields.add(new SocketConnection(field5.fieldName, field5.address,field5.port));
		Thread thread4 = new Thread(field4);
		thread4.start();
		
		field5.knownFields.add(new SocketConnection(field5.fieldName, field5.address,field5.port));
		Thread thread5 = new Thread(field5);
		thread5.start();

		new Lemming(field1);
		field1.numberOfLemmings++;
		System.out.println("Serving at port: " + field1.port + "...");
		System.out.println("Serving at port: " + field2.port + "...");
		System.out.println("Serving at port: " + field3.port + "...");
		new FieldView(field1);
		new FieldView(field2);
		new FieldView(field3);
		new FieldView(field4);
		new FieldView(field5);
	}
} 
