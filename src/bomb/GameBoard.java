package bomb;

public class GameBoard {

	
	private static int[][] board = new int[15][15];

	public void setBoard(){
		for(int i = 1; i<14; i+=2)
			for(int j = 1; j<14; j+=2)
				board[i][j] = 1;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public boolean checkSquare(int x, int y) {
		
		if(board[x][y] == 1)
			return false;
		else
			return true;
		
		
	}
	
	// FÖR TESTING
	public void printBoard() {
		for(int x = 0; x < 15; x++) {
			for(int y = 0; y < 15; y++)
				System.out.print(board[x][y]);
			System.out.print("\n");
		}
		
	}
	
}
