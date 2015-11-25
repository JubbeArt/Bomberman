package bomb;

public class Player extends GameBoard {
	private int xPos, yPos;
	
	Player() {
		xPos = 0;
		yPos = 0;
	}
	
	public void moveX(int x) {
		if(checkSquare(xPos+x, yPos))
			xPos += x;		
		
	}
	
	
	
	public void moveY(int y) {
		yPos += y;		
		
	}	
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
}
