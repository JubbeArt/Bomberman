package bomb;

public class GameBoard {
	
	private static int[][] board = new int[15][15];
	public enum square {Empty, Stone, Crate};
	
	// Återställer sprlplanen till grundläget
	public void setBoard(){
		
		// Lådor
		for(int i = 0; i < 15; i++)
			for(int j = 0; j < 15; j++)
				board[i][j] = 2;
		
		// Stenar
		for(int i = 1; i<14; i+=2)
			for(int j = 1; j<14; j+=2)
				board[i][j] = 1;

		// Tomma platser
		for(int i = 0; i < 2; i++) {
			board[0][i] = 0;
			board[0][i + 13] = 0;
			board[14][i] = 0;
			board[14][i + 13] = 0;
		}
		
		for(int i = 1; i < 14; i+=12) {
			board[i][0] = 0;
			board[i][14] = 0;
			
		}

	}
	
	// Retunerar en plats på planen
	public int at(int x, int y) {
		return board[x][y];
		
	}
	
	// Retunerar om en plats på planen stämmer överens med ett inskickat värde
	// Retunerar false om platsen är utanför planen
	public boolean checkSquare(int x, int y, int boxValue) {
		if(x < 0 || x > 14 || y < 0 || y > 14)
			return false;
		
		if(board[x][y] == boxValue)
			return true;
		else
			return false;
		
	}
	
	// Sätter en ruta på spelplanen till värdet som skickas in
	public void setSquare(int x, int y, int value){
		board[x][y] = value;
	}
	
	
}
