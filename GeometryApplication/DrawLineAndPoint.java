package Application;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 
 * @author Jaemarie Solyst
 * @contributor Ioanna Deni
 * 
 * This small application can draw a line and a point when the user drags (for a line)
 * and clicks (for a point).
 *
 */
public class DrawLineAndPoint extends Canvas implements MouseListener {

	/**
	 * The size of a point when the mouse is clicked
	 */
	private static final int POINT_SIZE = 10;
	
	/**
	 * The size of points on the ends of lines
	 */
	private static final int ENDPOINT_SIZE = 5;
	
	// Takes x and y values from press and release methods
	private Point[] lineArray = new Point[2];
	
	// boolean to keep track of if the user has already clicked
	private boolean released = false;
	
	//These two instances allow the storing of the database
	//added 3/5/2018 by Ioanna Deni
	private DB database;

	/**
	 * Constructor adds the mouseListner so that the mouseEvents 
	 * will go through.
	 */
	public DrawLineAndPoint(DB database) {		
		addMouseListener(this);
		this.setBackground(Color.WHITE);
		
		//allow the parameter to match the instance object in the class
		//added 3/5/2018 by Ioanna Deni
		this.database = database;
	}
	
	/**
	 * The paint method draws shapes based on how many points are stored in the database.
	 * @param g takes in graphics
	 */
	@Override
	public void paint(Graphics g) {
		//method modified by demo provided by Louis Conover 
		//added 3/5/2018 by Ioanna Deni
		super.paint(g);
	
		//added 3/6/18 by Jaemarie Solyst
		if (released==true) {
			System.out.println("in paint");
			
			//for all the point arrays (that will be drawn into shapes) saved in data base
			for (int i=0; i<database.Size(); i++){
				Point[] shapeArray = database.Get(i);
				
				// POINT: length of the array is 1
				if (shapeArray.length == 1) {
					
					Point p =  shapeArray[0];
					g.fillOval(p.x - (int)(POINT_SIZE/2), p.y - (int)(POINT_SIZE/2), POINT_SIZE, POINT_SIZE);
				}
				
				// LINE: length of the array is 2
				else if (shapeArray.length == 2) {
					
					//Save the two line end points from the array
					Point p1 = shapeArray[0];
					Point p2 = shapeArray[1];
					
					//Draw the line with two ovals on the end points
					g.fillOval(p1.x - (int)(ENDPOINT_SIZE/2), p1.y - (int)(ENDPOINT_SIZE/2), ENDPOINT_SIZE, ENDPOINT_SIZE);
					g.fillOval(p2.x - (int)(ENDPOINT_SIZE/2), p2.y - (int)(ENDPOINT_SIZE/2), ENDPOINT_SIZE, ENDPOINT_SIZE);
					g.drawLine(p1.x, p1.y, p2.x, p2.y);
				}
			}
		}
		
		// Note for the future: depending on the database, the array may be an array of ints, for both the X, Y coordinates
		// of points, and the angles. For example, if the array is length 9 (Triangle), the first two ints are X, Y, third is angle,
		// the next two are X, Y, then angle, etc.
	}
	
	
	/** saves X and Y values where the mouse is initially pressed
	 * @param e mouse event passed in to record location of press
	 * written 3/6/2018 by Jaemarie Solyst
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		//Add the first point of a line to the array
		lineArray[0] = new Point(e.getX(),e.getY());
		
	}

	/** 
	 * Saves X and Y values where the mouse is released
	 * written 3/6/18 by Jaemarie Solyst
	 * @param e mouse event passed in to record location of release
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		
		// save release X and Y values
		lineArray[1] = new Point(e.getX(), e.getY());
			
		//only add to data if the user clicked in the same place (this is not a press and release, it is a click)
		if (!(lineArray[0].equals(lineArray[1]))) {
			
			// add a clone of the array to avoid null pointer when lineArray is reset
			database.Add(lineArray.clone());
		}

		//reset the lineArray
		lineArray[0] = null;
		lineArray[1] = null;
		
		// set boolean to true so that it may draw a line (and not draw anything prior to this)
		released = true;
		repaint();
	}
	
	/**
	 * When the mouse is just clicked, add a Point array of length 1 to the database for a single point.
	 * added 3/6/18 Jaemarie Solyst
	 * @param e mouse event passed in to record location of release
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		// Create a point array of length 1 to save the point
		Point[] pointArray = new Point[1];
		pointArray[0] = new Point(e.getX(), e.getY());
		
		// Add the array to the database and update the canvas
		database.Add(pointArray);
		repaint();
		
	}
	
	/**
	 * imported for MouseListener
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * imported for MouseListener
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
