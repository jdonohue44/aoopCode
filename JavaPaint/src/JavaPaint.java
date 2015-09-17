import java.awt.Color;

/**
 * The main program class of JavaPaint
 */
public class JavaPaint {

	/** Create a program instance */
	PaintContainer paintContainer;
	
	public JavaPaint() {
		paintContainer = new PaintContainer();
	}
	
	public void drawP(PaintContainer paintContainer, int x, int y){
		Shape rect1 = new Rectangle(0, 0, 25, 125, Color.GREEN, true);
		Shape circle1 = new Circle(45,30,30,Color.GREEN, true);
		Shape circle2 = new Circle(45,30,15,Color.WHITE, true);		
		Compound letterP = new Compound();
		letterP.add(circle1);
		letterP.add(circle2);
		letterP.add(rect1);
		letterP.draw(paintContainer, x, y);
	}
	
	public void drawI(PaintContainer paintContainer, int x, int y){
		Shape circle3 = new Circle(10,10,10,Color.RED,false);
		Shape rect2 = new Rectangle(0, 25, 20, 100,Color.RED,true);
		Compound letterI = new Compound();
		letterI.add(circle3);
		letterI.add(rect2);
		letterI.draw(paintContainer, x, y);
	}
	
	public void drawK(PaintContainer paintContainer, int x, int y){
		Shape rectangle1 = new Rectangle(0, 0, 25, 125,Color.BLUE, true);
		Shape triangle1 = new Triangle(70,0,10,70,70,125,Color.BLUE, true);
		Shape triangle2 = new Triangle(70,20,40,70,70,105,Color.WHITE, true);
		Compound letterK = new Compound();
		letterK.add(rectangle1);
		letterK.add(triangle1);
		letterK.add(triangle2);
		letterK.draw(paintContainer, x, y);
	}
	
	public void drawExclamation(PaintContainer paintContainer, int x, int y){
		Shape quad = new Quadrangle(0,0,30,10,25,100,5,90,Color.YELLOW,true);
		Shape square = new Rectangle(5, 105, 20, 20,Color.YELLOW, false);
		Compound exclamationPoint = new Compound();
		exclamationPoint.add(quad);
		exclamationPoint.add(square);
		exclamationPoint.draw(paintContainer, x, y);
	}
	
	public static void main(String args[]) {
		JavaPaint jP = new JavaPaint();
		jP.drawP(jP.paintContainer, 10, 10);
		jP.drawI(jP.paintContainer, 80, 55);
		jP.drawI(jP.paintContainer, 120, 90);
		jP.drawI(jP.paintContainer, 160, 130);
		jP.drawI(jP.paintContainer, 280, 210);
		jP.drawK(jP.paintContainer, 200, 170);
		jP.drawExclamation(jP.paintContainer, 320, 250);
	}
}
