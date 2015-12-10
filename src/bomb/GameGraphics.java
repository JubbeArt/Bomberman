package bomb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import bomb.GameBoard.Square;
/*
 * Subklass av swingklassen JPanel. Här ritas hela spelplanen ut. Tokfink ju!
 * 
 * Alla objekt som ska ritas ut måste skickas in i drawGame-funktionen.
 * Board innehåller själva spelplanen (stenar, lådor, explosioner och tomma platser) medan spelarna och bomberna
 * skickas in för sig. (Detta p.g.a. att spelare och bomber ligger "uppepå" spelplanen)
 * 
 * I drawBoard kallas repaint funktionen som i sin tur kallar på paintComponent-funktionen.
 * Denna funktioner överlagrar JPanels standard ritning av panelen.
 * 
 * Graphics2D objektet används för själva ritandet i panelen.
 * 
 * */
public class GameGraphics extends JPanel{

	// Slut klaga eclipse....
	private static final long serialVersionUID = -1482084293493657149L;
	
	// Storleken på panelen
	private int GAME_WIDTH;
	private int GAME_HEIGHT;
	
	// Alla objekt som ska ritas ut
	private int[][] board = new int[15][15];
	//private Set<Bomb> bombs = new HashSet<Bomb>();
	private List<Player> players = new ArrayList<Player>();

	// Swing-objekt för utritning
	private Graphics2D g2;
	
	public GameGraphics(int width, int height) {
		GAME_WIDTH = width;
		GAME_HEIGHT = height;		
	}
			
	// Hämtar alla objekt som ska skrivas ut och kallar på repaint, (som i sin tur kallar på paintComponent).
	public void drawGame(int[][] board, List<Player> players, Set<Bomb> bombs) {
		this.board = board;	
		//this.bombs = bombs;
		this.players = players;
		repaint();
	}
		
	// Ritar om hela panelen
	@Override
	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g; // "Bättre" objekt för ritning (nyare + mer funktionalitet, inte så viktigt)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // PIXIEGNOMS FRÅN CORNWALL
		g2.setStroke(new BasicStroke(2));	
		
		
		int boardSize = 15; // Antal rutor på spelplanen (i x- och y-led)
		int boxWidth = GAME_WIDTH / boardSize; // Hur stor en ruta är
					
		// Ritar ut varje ruta på spelplanen (t.ex. låder och stenar)
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[x].length; y++) {
				
				
				if(board[x][y] == Square.BOMB.getID()) {		
					g2.setColor(Square.EMPTY.getColor());
					g2.fillRect(x * boxWidth, y * boxWidth, boxWidth, boxWidth);	
				} else if(board[x][y] == Square.POWERUP.getID()){
					g2.setColor(Square.EMPTY.getColor());
					g2.fillRect(x * boxWidth, y * boxWidth, boxWidth, boxWidth);	
					
					g2.setColor(Square.POWERUP.getColor());					
					g2.fillRect(x * boxWidth + 15, y * boxWidth + 15, boxWidth - 30, boxWidth - 30);
					
					g2.setColor(Color.black);
					g2.drawRect(x * boxWidth + 15, y * boxWidth + 15, boxWidth - 30, boxWidth - 30);
				} else {
					g2.setColor(Square.values()[board[x][y]].getColor());
					g2.fillRect(x * boxWidth, y * boxWidth, boxWidth, boxWidth);
				}
				
			}			
		}

		// Ritar ut spelarna
		for(Player p : players) {			
			if(p.isAlive()) {			
				g2.setColor(p.getColor());
				g2.fillRect(p.getX() * boxWidth, p.getY() * boxWidth, boxWidth, boxWidth);	
			}
		}
		
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[x].length; y++) {
				if(board[x][y] == Square.BOMB.getID()) {
					g2.setColor(Square.BOMB.getColor());
					g2.fillArc(x * boxWidth + 18, y * boxWidth + 18, 15, 15, 0, 360);
				}
			}
		}
		
		// Sätter storleken på borsten till 4 pixlar
		g2.setStroke(new BasicStroke(4));
		g2.setColor(Color.black);
		
		int pos;	// Positionen där den ska börja rita
				
		// Ritar alla sträck
		for(int i = 0; i <= 15; i++) {
			pos = i * boxWidth;

			g2.drawLine(0, pos, GAME_WIDTH, pos);
			g2.drawLine(pos, 0, pos, GAME_HEIGHT);
		}	
			
	}
	
	
	
}