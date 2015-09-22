package controller;

import java.io.IOException;

import model.Field;
import model.Lemming;
import model.SocketConnection;

public class Driver {
	
	public static void main(String[] args){
		Field field1 = null;
		Field field2 = null;
		try {
			field1 = new Field();
			field2 = new Field();
		} catch (IOException e) {
			e.printStackTrace();
		}
		field1.neighbors.add(new SocketConnection(field1.address,field1.port));
		field1.neighbors.add(new SocketConnection(field2.address,field2.port));
		field2.neighbors.add(new SocketConnection(field1.address,field1.port));
		field2.neighbors.add(new SocketConnection(field2.address,field2.port));
		Thread thread = new Thread(field1);
		thread.start();
		Thread thread2 = new Thread(field2);
		thread2.start();
		Lemming L = new Lemming(field1);
		System.out.println("Serving at port: " + field1.port);
//		new FieldView(field1);
	}
} 
