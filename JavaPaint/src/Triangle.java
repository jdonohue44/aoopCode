import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends Shape {

	/*
	 * Corner coordinates
	 */
	public int x1, y1, x2, y2, x3, y3;

	public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color col, boolean filled){
		super(filled, col);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
	}
	
	public void draw(PaintContainer pc, int xoff, int yoff){		
		Graphics g = pc.getDrawingArea();
		g.setColor(this.getColor());
		int[] tpx = { x1 + xoff, x2 + xoff, x3 + xoff };
		int[] tpy = { y1 + yoff, y2 + yoff, y3 + yoff };
		if (this.getFilled()) {
			g.fillPolygon(tpx, tpy, 3);
		} else {
			g.drawPolygon(tpx, tpy, 3);
		}
	}
	
	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getX3() {
		return x3;
	}

	public void setX3(int x3) {
		this.x3 = x3;
	}

	public int getY3() {
		return y3;
	}

	public void setY3(int y3) {
		this.y3 = y3;
	}
}
