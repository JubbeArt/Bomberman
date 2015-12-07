package bomb;

import java.awt.Color;
import java.util.Random;

/*
 * Subklass av Entity. Hanterar en spelare på spelplanen.
 * 
 * Har reda på all information om spelaren med över 9000 getter och setters.
 * Normalt så har spelaren en slumpmässad färg.
 * Update-funktionen kollar om spelaren står på en skadesam ruta (sten eller explosion) och minskar livet.
 * moveX och moveY-fuktionerna förflyttar spelaren på planen.
 * 
 * */
public class Player extends Entity {
	
	private int hitPoints;	// spelarens liv
	private int power;		// Hur kraftfulla spelarens bomber är
	private int bombLimit;	// Hur många bomber spelaren kan lägga ut samtidigt
	private int bombsUsed; 	// Hur många bomber spelaren nuvarande har utlagda
	private static int playerID = 5;	// Ett statiskt ID för spelaren. Detta ökar för varje skapade spelare
	
	// Ett random objekt för en random rgb-färg
	private static Random rand = new Random();	
	
	// Skapar en spelare med en random färg
	Player(int x, int y) {
		this(x, y, new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));		
	}
	
	// Skapar en spelare med en egen färg
	Player(int x, int y, Color c) {
		super(x, y, playerID, c);
		
		playerID++;
			
		power = 1;
		bombLimit = 1;
		bombsUsed = 0;	
		hitPoints = 1;
	}

	/*
	 * Förflyttar spelaren
	 * 
	 * Kollar först om platsen är inom spelplanen.
	 * Sedan om spelaren får flytta sig till rutan (måste vara antingen tom eller en explosion).
	 * 
	 * Denna funktion används av de publika funktionerna moveX och moveY
	 * */	
	private void move(int x, int y) {
		if(!checkInBounds(x, y))
			return;
		
		int id = get(x, y);
		
		if(id == Square.EMPTY.getID() || id == Square.EXPLOSION.getID()) {
			xPos = x;
			yPos = y;			
		}	
	}
	
	public void moveX(int x) {	
		move(xPos + x, yPos);
	}
		
	public void moveY(int y) {	
		move(xPos, yPos + y);		
	}
	
	// Kollar om spelaren står på en explosion eller en sten (tar då skada)
	public void update() {	
		int id = get(xPos, yPos);
		
		if(id == Square.EXPLOSION.getID() || id == Square.STONE.getID())
			hitPoints--;		
	}
	
	/*
	 * Getters och setter för spelaren 
	 * */
	
	public int getPower() {
		return power;
	}
	
	public void addPower(int p) {
		power += p;
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