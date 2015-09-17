import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * PaintContainer is used to create a window which can be written to.
 */
public class PaintContainer extends JFrame {

	private BufferedImage canvasImage;

	private Graphics2D canvas;

	/**
	 * Create a new PaintContainer object
	 */
	public PaintContainer() {
		super();
		init();
	}

	/*
	 * Initialize the window etc.
	 */
	private void init() {
		JLabel ga = new GraphicsContainer();
		ga.setPreferredSize(new Dimension(400, 400));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.setLocation(20, 20);
		this.setTitle("JavaPaint");
		this.getContentPane().add(ga, BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
		canvasImage = (BufferedImage) createImage(400, 400);
		this.setResizable(false);

		canvas = canvasImage.createGraphics();
		canvas.setBackground(Color.WHITE);
		canvas.clearRect(0, 0, ga.getWidth(), ga.getHeight());
		repaint();
	}

	/**
	 * Return the Graphics context to use for drawing.
	 * 
	 * @return the Graphics context of the area of the window on which can be
	 *         drawn.
	 */
	public Graphics getDrawingArea() {
		return canvas;
	}

	/**
	 * The actual object to paint on is a JLabel with a slightly different paint
	 * method.
	 */
	private class GraphicsContainer extends JLabel {
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(canvasImage, 0, 0, this);
		}
	}
}
