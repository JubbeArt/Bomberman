package bomb;

public class Player extends Entity{
	
	
	private int power;
	
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
	
}
