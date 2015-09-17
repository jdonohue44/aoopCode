import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape {

	public int width, height, x, y;

	public Rectangle(int x, int y, int width, int height, Color col, boolean filled){
		super(filled, col);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(PaintContainer pc, int xoff, int yoff){
		Graphics g = pc.getDrawingArea();
		g.setColor(this.getColor());
		if (this.getFilled()) {
			g.fillRect(xoff + x, yoff + y, width, height);
		} else {
			g.drawRect(xoff + x, yoff + y, width, height);
		}
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
