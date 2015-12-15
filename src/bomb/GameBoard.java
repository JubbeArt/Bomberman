package bomb;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JOptionPane;
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
	
	// Mall för hur våra rutor ska se ut (id, färg)
	public enum Square {EMPTY(0, Color.white), 
						STONE(1, Color.gray), 
						CRATE(2, new Color(139, 69, 19)),
						BOMB(3, Color.black),
						EXPLOSION(4, Color.orange),
						POWERUP(5, new Color(114, 16, 35));
		
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
	
	// Återställer spelplanen till grundläget
	public void resetBoard(String currentMap){
					
		// Läser in en bana (en textfil)
		Scanner file = null;
		try {
			file = new Scanner(new FileReader(currentMap));
			String line;
			
			int y = 0;
			
			while(file.hasNextLine()) {
				line = file.nextLine(); // Läser in en rad av av spelplanen
				
				// Loopar igeno en rad
				for(int x = 0; x < line.length() && x < board.length; x++) {
					board[x][y] =  Character.getNumericValue(line.charAt(x));		
			
					// Koll för felaktiga värden
					if(board[x][y] < 0 || board[x][y] > Square.POWERUP.getID()) {
						JOptionPane.showMessageDialog(null, "Unallowed character in file.... (0-5 is allowed)");
						System.exit(1);						
					}
				}		
				
				y++;
				if(y >= board.length) // Säkerhetskoll om vi skulle ha för lång textfil
					break;
			}	
						
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found.... -.- closing down boys!!!");
			System.exit(1);
		} finally {
			if(file != null)
				file.close();
		}
				
	}	
		
	// Retunerar en plats på planen
	public int get(int x, int y) {
		return board[x][y];		
	}
	
	// Sätter en ruta på spelplanen till det id som skickas in
	protected void setSquare(int x, int y, int id){
		board[x][y] = id;
	}

	// Retunerar hela spelplanen 
	public int[][] getBoard() {
		return board;
	}
	
	// Kollar om en punkt är inom våran spelplan
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
		if((currentTime - startStoneTime) % 150 < 17)
			addStone();
	}
	
}