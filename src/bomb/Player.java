package bomb;

public class Player extends Entity{
	
	
	private int power; // Hur kraftfulla spelarens bomber är
	private int health = 1;
	private int bombLimit = 1;
	private int bombsUsed = 0; 
	
	Player(int x, int y) {
		super(x, y);
		power = 1;
	}
	
	Player(int x, int y, int p) {
		super(x, y);	 
		power = p;
	}
	
	public void moveX(int x) {		
		if(checkSquare(xPos + x, yPos, 0))
			xPos += x;	

	}
		
	public void moveY(int y) {		
		if(checkSquare(xPos, yPos+y, 0))
			yPos += y;		
	}	
	
	public int getPower() {
		return power;
	}
	
	public void died() {
		if(health<1)
			System.out.println("You diededededed owated wam!");
	}
	
	public void changeBombsUsed(int x) {
		bombsUsed += x;
	}
	
	public int getBombLimit() {
		return bombLimit;
	}
	
	public int getBombsUsed() {
		return bombsUsed;
	}
}
