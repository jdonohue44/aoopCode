import java.util.Iterator;
import java.util.Vector;

/*
 * A compound shape is a special shape that consists of zero or more other
 * shapes. The other types represent primitive geometric shapes.
 */
public class Compound {
	
	/*
	 * The list of subShapes, i.e. member shapes, in case this shape is used as
	 * a compound shape.
	 */
	private Vector<Shape> subShapes;

	public Compound() {
		this.subShapes = new Vector<Shape>();
	}
	
	public void add(Shape subShape){
		this.subShapes.add(subShape);
	}

	public void draw(PaintContainer pc, int xoff, int yoff){
		Iterator<Shape> i = subShapes.iterator();
		while (i.hasNext()) {
			Shape subShape = (Shape) i.next();
			subShape.draw(pc, xoff, yoff);
		}
	}
	
	public Vector<Shape> getSubShapes(){
		return this.subShapes;
	}
	
	public void setSubShapes(Vector<Shape> subShapes){
		this.subShapes = subShapes;
	}
}
