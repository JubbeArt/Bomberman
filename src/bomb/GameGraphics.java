package bomb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
 * I drawBoard kallas repaint funktionen som i sin tur kallar på paintComponent-funktione
 * 
 * */
public class GameGraphics extends JPanel{

	// Storleken på panelen
	private int GAME_WIDTH;
	private int GAME_HEIGHT;
	
	// Alla objekt som ska ritas ut
	private int[][] board;
	private Set<Bomb> bombs;
	private List<Player> players;

	// Swing-objekt för utritning
	private Graphics2D g2;
	
	public GameGraphics(int width, int height) {
		GAME_WIDTH = width;
		GAME_HEIGHT = height;		
	}
			
	// Hämtar alla objekt som ska skrivas ut och kallar på repaint, (som i sin tur kallar på paintComponent).
	public void drawGame(int[][] board, List<Player> players, Set<Bomb> bombs) {
		this.board = board;	
		this.bombs = bombs;
		this.players = players;
		repaint();
	}
		
	// Ritar om hela panelen
	@Override
	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g; // "B�ttre" objekt f�r ritning (nyare + mer funktionalitet, inte s� viktigt)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // PIXIEGNOMS FR�N CORNWALL
			
		int boardSize = 15; // Antal rutor p� spelplanen (i x- och y-led)
		int boxWidth = GAME_WIDTH / boardSize; // Hur stor en ruta �r
					
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[x].length; y++) {
				g2.setColor(Square.values()[board[x][y]].getColor());
				g2.fillRect(x * boxWidth, y * boxWidth, boxWidth, boxWidth);					
			}			
		}
		
		for(Player p : players) {			
			if(p.isAlive()) {			
				g2.setColor(p.getColor());
				g2.fillRect(p.getX() * boxWidth, p.getY() * boxWidth, boxWidth, boxWidth);	
			}
		}
		
		for(Bomb b : bombs) {
			g2.setColor(b.getColor());
			g2.fillRect(b.getX() * boxWidth + 18, b.getY() * boxWidth + 18, 15, 15);		
		}
		
		
		// S�tter storleken p� borsten till 4 pixlar
		g2.setStroke(new BasicStroke(4));
		g2.setColor(Color.black);
		
		int pos;	// Positionen d�r den ska b�rja rita
				
		// Ritar alla str�ck
		for(int i = 0; i <= 15; i++) {
			pos = i * boxWidth;

			g2.drawLine(0, pos, GAME_WIDTH, pos);
			g2.drawLine(pos, 0, pos, GAME_HEIGHT);
		}	
			
	}
	
	
	
}