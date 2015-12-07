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

public class GameGraphics extends JPanel{

	private int GAME_WIDTH;
	private int GAME_HEIGHT;
	
	private Entity[][] board;
	private Set<Bomb> bombs;
	private List<Player> players;
	private Graphics2D g2;
	
	public GameGraphics(int width, int height) {
		GAME_WIDTH = width;
		GAME_HEIGHT = height;		
	}
			
	public void drawGame(Entity[][] board, List<Player> players, Set<Bomb> bombs) {
		this.board = board;	
		this.bombs = bombs;
		this.players = players;
		repaint();
	}
		
	// Ritar om hela panelen
	@Override
	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g; // "Bättre" objekt för ritning (nyare + mer funktionalitet, inte så viktigt)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // PIXIEGNOMS FRÅN CORNWALL
			
		int boardSize = 15; // Antal rutor på spelplanen (i x- och y-led)
		int boxWidth = GAME_WIDTH / boardSize; // Hur stor en ruta är
			
		for(Entity[] row : board) { //Ger error, kallas 2 ggr innan Entity[] har skapats..
			for(Entity e : row) {
				g2.setColor(e.getColor());
				g2.fillRect(e.getX() * boxWidth, e.getY() * boxWidth, boxWidth, boxWidth);	
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