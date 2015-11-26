package bomb;

public class GameBoard {

	
	private static int[][] board = new int[15][15];

	public void setBoard(){
		
		for(int i = 0; i < 15; i++)
			for(int j = 0; j < 15; j++)
				board[i][j] = 2;
		
		
		for(int i = 1; i<14; i+=2)
			for(int j = 1; j<14; j+=2)
				board[i][j] = 1;
		/*	
		for(int i = 13; i<15; i++)
			for (int j = 14; j<15; j++) {
				board[i][j] = 0;
				board[14 - i][14 - j] = 0;
			}
			*/	

			
		//p1 spwn
		board[0][0] = 0;
		board[0][1] = 0;
		board[1][0] = 0;
		
		//p2 spwn
		board[14][0] = 0;
		board[13][0] = 0;
		board[14][1] = 0;
		
		//p3 spwn
		board[0][14] = 0;
		board[0][13] = 0;
		board[1][14] = 0;
		
		//p4 spwn
		board[14][14] = 0;
		board[14][13] = 0;
		board[13][14] = 0;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	//Unused
	public int at(int x, int y) {
		return board[x][y];
		
	}
	
	public boolean checkSquare(int x, int y, int boxValue) {
		if(x<0 || x>14 || y<0 || y>14)
			return false;
		
		if(board[x][y] == boxValue)
			return true;
		else
			return false;
		
	}
	
	public void setSquare(int x, int y, int value){
		board[x][y] = value;
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
