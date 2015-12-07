package bomb;

import java.awt.Color;

// Abrstakt klass f�r alla objekt p� spelplanen
public class Entity extends GameBoard {
		
	protected int xPos, yPos, ID;
	private Color color;
	
	Entity(int x, int y) {		
		this(x, y, 0, Color.white);		
	}
		
	Entity(int x, int y, int id, Color c) {
		xPos = x;
		yPos = y;
		ID = id;
		color = c;		
	}
	
	public void updatePos(int x, int y) {
		setSquare(x, y, this);		
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void setX(int x) {
		xPos = x;
		updatePos(xPos, yPos);
	}
	
	public void setY(int y) {
		yPos = y;
		updatePos(xPos, yPos);
	}
	
	public int getID() {
		return ID;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean update(long currentTime) {
		return false;		
	}
	
}