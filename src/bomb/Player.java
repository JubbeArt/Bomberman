package bomb;

public class Player extends GameBoard{
	
	
	private int xPos, yPos;
	
	Player(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	public void moveX(int x) {
		
		if(checkSquare(xPos + x, yPos, 0)) {
			xPos += x;	
		} else {
			//"Bumpsound"
		}
	}
		
	public void moveY(int y) {
		
		if(checkSquare(xPos, yPos+y, 0)) {
			yPos += y;	
		} else {
			//"Bumpsound"
		}	
		
	}	
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
}
