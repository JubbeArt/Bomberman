package bomb;

import java.awt.Color;

/*
 * Superklass för en ruta på spelplanen.
 * 
 * Subklass av GameBoard för att komma åt våran fina spelplan.
 * Vanliga getters och setter för dess position.
 * Varje ruta har också ett ID för att veta vad för slags ruta det är (se enum i GameBoard för mer info). 
 * Rutan håller också reda på sin egen färg. Är det inte tokfint eller?
 * 
 * */
public class Entity extends GameBoard {
		
	// Generella variabler för alla objekt i vårt spel
	protected int xPos, yPos;
	private int ID;
	private Color color;
	
	// Skapar en tom ruta
	Entity(int x, int y) {		
		this(x, y, 0, Color.white);		
	}
	
	// Skapar en ruta med hjälp av dess ID, t.ex. (  new Entity(3, 14, Square.BOMB.getID())  )
	Entity(int x, int y, int id) {
		this(x, y, id, Square.values()[id].getColor());
	}
		
	// Skapar en helt egen ruta
	Entity(int x, int y, int id, Color c) {
		xPos = x;
		yPos = y;
		ID = id;
		color = c;		
	}
	
	// Updaterar dess position i rutnätet, notera alla alla Entities inte är i rutnätet (spelarna och bomberna)
	public void updatePos(int x, int y) {
		setSquare(x, y, ID);		
	}
	
	/*
	 * Getters och setter för objektet
	 * */
	
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
		
}