package bomb;

public class Bomb extends GameBoard{
	/*
	 * TODO : ABRACKTRAOR KLCASS
	 * */
	
	private int xPos;
	private int yPos;
	private int time;
	
	Bomb(int x, int y){
		xPos = x;
		yPos = y;	
	}
	
	public void expload() {
		if(checkSquare(xPos+1, yPos, 2))
			setSquare(xPos+1, yPos, 0);

		if(checkSquare(xPos-1, yPos, 2))
			setSquare(xPos-1, yPos, 0);			
		
		if(checkSquare(xPos, yPos+1, 2))
			setSquare(xPos, yPos+1, 0);		
		
		if(checkSquare(xPos, yPos-1, 2))
			setSquare(xPos, yPos-1, 0);
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
}
