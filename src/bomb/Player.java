package bomb;

import java.awt.Color;
import java.util.Random;

public class Player extends Entity{
	
	private int power; // Hur kraftfulla spelarens bomber är
	private int health;
	private int bombLimit;
	private int bombsUsed; 
	private Color color;
	private static Random rand = new Random();
	
	
	Player(int x, int y) {
		this(x, y, new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));		
	}
	
	Player(int x, int y, Color c) {
		super(x, y);
		power = 1;
		health = 1;
		bombLimit = 1;
		bombsUsed = 0;
		
		color = c;
	}

	public void moveX(int x) {		
		if(checkSquare(xPos + x, yPos, 0))
			xPos += x;	
	}
		
	public void moveY(int y) {		
		if(checkSquare(xPos, yPos+y, 0))
			yPos += y;		
	}	

	public Color getColor() {
		return color;
	}
	
	public int getPower() {
		return power;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public void changeHealth(int h) {
		health += h;
	}
		
	public void changeBombLimit(int b) {
		bombLimit += b;
	}
	
	public int getBombLimit() {
		return bombLimit;
	}
		
	public void changeBombsUsed(int x) {
		bombsUsed += x;
	}
	
	public int getBombsUsed() {
		return bombsUsed;
	}
}
