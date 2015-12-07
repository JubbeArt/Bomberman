package bomb;

import java.awt.Color;
import java.util.Random;


public class Player extends Entity {
	
	private int hitPoints;
	private int power; // Hur kraftfulla spelarens bomber är
	private int bombLimit;
	private int bombsUsed; 
	private static int playerID = 5;
	
	private static Random rand = new Random();	
	
	Player(int x, int y) {
		this(x, y, new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));		
	}
	
	Player(int x, int y, Color c) {
		super(x, y, playerID, c);
		
		playerID++;
			
		power = 10;
		bombLimit = 1;
		bombsUsed = 0;	
		hitPoints = 1;
	}

	public void moveX(int x) {	
			
		if(checkSquare(xPos + x, yPos, Square.EMPTY.getID()) || checkSquare(xPos + x, yPos, Square.EXPLOSION.getID()))
			xPos += x;			
	}
		
	public void moveY(int y) {	
		if(checkSquare(xPos, yPos + y, Square.EMPTY.getID()) || checkSquare(xPos, yPos + y, Square.EXPLOSION.getID())) 
			yPos += y;	
		
	}
	
	// Updeterar spelaren
	public boolean update() {
	
		int id = get(xPos, yPos);
		
		if(id == Square.EXPLOSION.getID() || id == Square.STONE.getID())
			hitPoints--;
		
		
		return isAlive();
	}
	
	public int getPower() {
		return power;
	}
	
	public boolean isAlive() {
		if(hitPoints > 0)
			return true;
		return false;
	}
	
	public void changeBombLimit(int b) {
		bombLimit += b;
	}
	
	public int getBombLimit() {
		return bombLimit;
	}
		
	public void changeBombsUsed(int b) {
		bombsUsed += b;
	}
	
	public int getBombsUsed() {
		return bombsUsed;
	}
}