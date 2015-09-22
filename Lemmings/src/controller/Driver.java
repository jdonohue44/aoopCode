package controller;

import java.io.IOException;

import view.FieldView;
import model.Field;
import model.Lemming;

public class Driver {
	
	public static void main(String[] args){
		Field field1 = null;
		Field field2 = null;
		Field field3 = null;
		try {
			field1 = new Field();
			field2 = new Field();
			field3 = new Field();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(field1);
		thread.start();
		Lemming L = new Lemming(field1);
		System.out.println("Serving at port: " + field1.port);
//		new FieldView(field1);
	}
} 
