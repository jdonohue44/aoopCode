package controller;

import java.io.IOException;
import model.Field;

public class Driver {
	
	public static void main(String[] args){
		Field field = null;
		try {
			field = new Field();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// testing git hub
		Thread thread = new Thread(field);
		thread.start();
	}
} 
