package bomb;

import java.awt.Color;

// Abrstakt klass för alla objekt på spelplanen
public class Entity extends GameBoard{
	
	protected static final int EMPTY = 0;
	protected static final int STONE = 1;
	protected static final int CRATE = 2;
	protected static final int EXPLOSION = 3;
	protected static int PLAYER1 = 4;
	
	protected int xPos, yPos, ID;
	protected Color color;
	
	Entity(int x, int y) {		
		this(x, y, 0);		
	}
	
	
	Entity(int x, int y, int ID) {
		this(x, y, ID, Color.white);
	}
	
	Entity(int x, int y, int ID, Color c) {
		xPos = 0;
		yPos = 0;
		ID = 0;
		color = c;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void setX(int x) {
		xPos = x;
	}
	
	public void setY(int y) {
		yPos = y;
	}
	
	public int getID() {
		return ID;
	}
	
}
