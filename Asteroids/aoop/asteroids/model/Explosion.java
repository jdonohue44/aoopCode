package aoop.asteroids.model;

import java.awt.Point;

public class Explosion extends GameObject{

	public int stepsLeft;
	
	public Explosion(Point location, double velocityX, double velocityY, int radius, int stepsLeft) {
		super (location, velocityX, velocityY, radius);
		this.stepsLeft = stepsLeft;
	}

	@Override
	public void nextStep() {
		
		System.out.println("NEXT STEP");
		this.stepsLeft--;
		if (this.stepsLeft < 0)
			this.destroy ();
	}

}
