import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {

	public int x, y, circleRadius;
	
	public Circle(int x, int y, int circleRadius, Color col, boolean filled){
		super(filled, col);
		this.x = x;
		this.y = y;
		this.circleRadius = circleRadius;
	}
	
	public void draw(PaintContainer pc, int xoff, int yoff){		
		Graphics g = pc.getDrawingArea();
		g.setColor(this.getColor());
		if (this.getFilled()) {
		g.fillOval(xoff + x - circleRadius, yoff + y - circleRadius,
				circleRadius * 2, circleRadius * 2);
			}
		else {
		g.drawOval(xoff + x - circleRadius, yoff + y - circleRadius,
				circleRadius * 2, circleRadius * 2);
		}
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int setCircleRadius(){
		return this.circleRadius;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setCircleRadius(int circleRadius){
		this.circleRadius = circleRadius;
	}
}
