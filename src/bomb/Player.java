package bomb;

public class Player extends GameBoard{
	
	
	private int xPos, yPos;
	
	Player() {
		xPos = 0;
		yPos = 0;
	}
	
	public void moveX(int x) {
		
		if(checkSquare(xPos+x, yPos)) {
			xPos += x;	
		} else {
			//"Bumpsound"
		}
	}
		
	public void moveY(int y) {
		
		if(checkSquare(xPos, yPos+y)) {
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
