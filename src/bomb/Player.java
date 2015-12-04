package bomb;

import java.awt.Color;
import java.util.Random;

public class Player extends Entity{
	
	private boolean isAlive;
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
			
		isAlive = true;
		power = 3;
		bombLimit = 1;
		bombsUsed = 0;		
	}

	public void moveX(int x) {		
		if(checkSquare(xPos + x, yPos, 0)) {
			setSquare(xPos, yPos, 0); // Sätter gamla rutan till en tom
			xPos += x;			
			updatePos(xPos, yPos); // Flyttar spelaren till nya rutan
		}
	}
		
	public void moveY(int y) {		
		if(checkSquare(xPos, yPos+y, 0)) {
			setSquare(xPos, yPos, 0);
			yPos += y;		
			updatePos(xPos, yPos);
		}
	}	

	public int getPower() {
		return power;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean a) {
		isAlive = a;
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
