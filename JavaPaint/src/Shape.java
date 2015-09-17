import java.awt.Color;

/**
 * This class is used to represent two-dimensional geometric shapes which can be
 * drawn in a PaintContainer.
 */
public abstract class Shape {
	
	/*
	 * Specifies whether this shape should be outlined or filled.
	 */
	private boolean filled = false;

	/*
	 * The color of the shape
	 */
	private Color col = Color.BLACK;

	public Shape(boolean filled, Color col) {
		this.filled = filled;
		this.col = col;
	}
	
	/**
	 * Draw the shape in the specified paintcontainer, with a specified extra
	 * offset from the top-left corner of the paintcontainer.
	 * 
	 * @param pc
	 *            the PaintContainer to draw in
	 * @param xoff
	 *            the x offset from the top-left corner of pc
	 * @param yoff
	 *            the y offset from the top-left corner of pc
	 */
	public abstract void draw(PaintContainer pc, int xoff, int yoff);
	
	public boolean getFilled(){
		return this.filled;
	}
	
	public Color getColor(){
		return this.col;
	}
	
	public void setProperties(boolean fill, Color color) {
		filled = fill;
		col = color;
	}
}
