package bomb;

import java.awt.Color;

public class GameBoard {
	
	// Spelplanen
	private static Entity[][] board = new Entity[15][15];
		
	// Hur våra rutor ska se ut, används för att skapa objekt av Entity-klassen
	public enum Square {EMPTY(0, Color.white), 
						STONE(1, Color.gray), 
						CRATE(2, new Color(139, 69, 19)),
						BOMB(3, Color.black),
						EXPLOSION(4, Color.orange);
		
		private int ID;
		private Color color;
		
		private Square(int ID, Color c) {
			this.ID = ID;
			color = c;
		}
		
		public int getValue() {
			return ID;
		}
		
		public Color getColor() {
			return color;
		}
	
	} 
	
	// Återställer sprlplanen till grundläget
	public void resetBoard(){
		
		// Lådor
		for(int i = 0; i < 15; i++)
			for(int j = 0; j < 15; j++)
				board[i][j] = new Entity(i, j, Square.CRATE.getValue(), Square.CRATE.getColor());
		
		// Stenar
		for(int i = 1; i<14; i+=2)
			for(int j = 1; j<14; j+=2)
				board[i][j] = new Entity(i, j, Square.STONE.getValue(), Square.STONE.getColor());

		
		// Tomma platser
		for(int i = 0; i < 2; i++) {
			board[0][i] = new Entity(0, i);
			board[0][i + 13] = new Entity(0, i + 13);
			board[14][i] = new Entity(14, i);
			board[14][i + 13] = new Entity(14, i + 13);
		}
		
		for(int i = 1; i < 14; i+=12) {
			board[i][0] = new Entity(i, 0);
			board[i][14] = new Entity(i, 14);
			
		}	
		
	}	
		
	// Retunerar en plats på planen
	public Entity get(int x, int y) {
		return board[x][y];		
	}
	
	// Sätter en ruta på spelplanen till den entity som skickas in
	protected void setSquare(int x, int y, Entity e){
		board[x][y] = e;
	}
	
	// Sätter en ruta på spelplanen till en nya entity
	protected void setSquare(int x, int y, int ID) {
		board[x][y] = new Entity(x, y, ID, Square.values()[ID].getColor());		
	}
	
	public Entity[][] getBoard() {
		return board;
	}
	
	// Retunerar om en plats på planen stämmer överens med ett inskickat värde
	// Retunerar false om platsen är utanför planen
	protected boolean checkSquare(int x, int y, int squareID) {
		if(x < 0 || x > 14 || y < 0 || y > 14)
			return false;
		
		if(board[x][y].getID() == squareID)
			return true;
		else
			return false;
		
	}
	
	private int stoneX = 0;
	private int stoneY = 14;
	private boolean isEndgame = false;
	private long startStoneTime;
	
	public void startEndgame(long time) {
		startStoneTime = time;		
		isEndgame = true;
	}
	
	
	private void addStone() {

		if(stoneX <= 14 && stoneY >= 0) {
		
			setSquare(stoneX, stoneY, new Entity(stoneX, stoneY, Square.STONE.getValue(), Square.STONE.getColor()));
			
			if(stoneY % 2 == 0 && stoneX == 14)
				stoneY--;
			else if(stoneY % 2 == 0)
				stoneX++;
			else if(stoneX == 0)
				stoneY--;
			else
				stoneX--;			
			}
		}
	
	public boolean isEndgame() {
		return isEndgame;
	}
	
	public void updateEndgame(long currentTime) {
		if((startStoneTime - currentTime) % 5 == 0)
			addStone();
	}
	
}