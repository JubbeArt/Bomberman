package bomb;

import java.awt.Color;
/*
 * Huvudklass för spelplanen och alla dess rutor.
 * 
 * Har getter och setters för rutorna.
 * Mallen för hur rutorna ska se ut finns i en enumeration.
 * Hanterar också "stenormen" som kommer när spelet börjar närma sitt slut.
 * 
 */
public class GameBoard {
	
	// Spelplanen
	private static int[][] board = new int[15][15];
		
	// Variabler för slutet på ett spel, då planen sakta görs till STEN!
	private int stoneX = 0;
	private int stoneY = 14;
	private boolean isEndgame = false;
	private long startStoneTime;
	
	
	// Mall för hur våra rutor ska se ut
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
		
		public int getID() {
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
				board[i][j] = Square.CRATE.getID();
		
		// Stenar
		for(int i = 1; i<14; i+=2)
			for(int j = 1; j<14; j+=2)
				board[i][j] = Square.STONE.getID();

		int empty = Square.EMPTY.getID();
		
		// Tomma platser (alla hörn)
		for(int i = 0; i < 2; i++) {
			board[0][i] = empty;
			board[0][i + 13] = empty;
			board[14][i] = empty;
			board[14][i + 13] = empty;
		}
		
		for(int i = 1; i < 14; i+=12) {
			board[i][0] = empty;
			board[i][14] = empty;			
		}	
		
	}	
		
	// Retunerar en plats på planen
	public int get(int x, int y) {
		return board[x][y];		
	}
	
	// Sätter en ruta på spelplanen till den entity som skickas in
	protected void setSquare(int x, int y, int id){
		board[x][y] = id;
	}

	// Retunerar hela spelplanen 
	public int[][] getBoard() {
		return board;
	}
	
	// Kommer om en punkt är inom våran spelplan
	protected boolean checkInBounds(int x, int y) {
		if(x < 0 || x > 14 || y < 0 || y > 14)
			return false;
		return true;
	}
	
	// Retunerar sant om det inskickade id stämmer överrens med platsens id
	protected boolean checkSquare(int x, int y, int id) {
		return board[x][y] == id;		
	}
	

	// Börjar slutet av spelet då rutorna börjar förvandlas till stenar...
	public void startEndgame(long time) {
		startStoneTime = time;		
		isEndgame = true;
	}
	
	
	// Lägger till en sten
	private void addStone() {

		if(stoneX <= 14 && stoneY >= 0) {
		
			// Sätter nuvarande plats till en sten
			board[stoneX][stoneY] = Square.STONE.getID();
			
			// Tokfin algoritm för att ta fram vägen som den här "stenormen" tar
			// Lite sammanfattat så börjar den på plats (0, 14), vänstra nedre hörs alltså
			
			// Då y-värdet är jämt ska den gå från vänster till höger (till ruta 14)
			// Om y-värder är ojämt går den från 14 till 0
			
			// Om x kommer till 0 eller 14 så ska y-värdet alltid minska
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
	
	// Retunerar om slutdelen av spelat har börjat
	public boolean isEndgame() {
		return isEndgame;
	}
	
	// Kollar om den ska lägga till en till sten
	public void updateEndgame(long currentTime) {
		if((startStoneTime - currentTime) % 50 == 0)
			addStone();
	}
	
}