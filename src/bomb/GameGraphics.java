package bomb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Subklass av JPanel. Används för att rita spelplanen.  
 * 
 */
public class GameGraphics extends JPanel {

	// Storlek på panelen
	private int gameWidth, gameHeight;
	
	// Player 
	private Player player1 = null;
	private Player player2 = null;

	// Ritobjekt för panelen
	private Graphics2D g2 = null;
	
	// En bild
	private BufferedImage testImage = null;
		
	public GameGraphics(int width, int height) {
		
		// Sätter och sparar undan storleken på panelen
		setPreferredSize(new Dimension(width, height));
		gameWidth = width;
		gameHeight = height;
		
		// Test för att ladda in bilder
		try {
			testImage = ImageIO.read(getClass().getResource("../Images/Dickbutt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			// Kanske System.exit(0)?
		}
	}
	
	// Ritar om hela panelen
	@Override
	public void paintComponent(Graphics g) {
		
		// Kallar på superklassens ritfunktion (inte nödvändig egentiligen)
		super.paintComponents(g);

		g2 = (Graphics2D) g; // "Bättre" objekt för ritning (nyare + mer funktionalitet, inte så viktigt)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // PIXIEGNOMS FRÅN CORNWALL
		
		// Ritar ut grindnätet
		drawBoard();
		
		// TEST TEST skriver ut en bild
		//g2.drawImage(testImage, null, 0, 0);
		
	}
	
	public void updateGame(Player p1, Player p2) {
		player1 = p1;
		player2 = p2;
		
	}
	
	
	// Ritar ett grindnät
	public void drawBoard() {
		int boardSize = 15; // Antal rutor på spelplanen (i x- och y-led)
		int boxWidth = gameWidth / boardSize; // Hur stor en ruta är
		int pos;	// Positionen där den ska börja rita
		
		// Sätter storleken på borsten till 4 pixlar
		g2.setStroke(new BasicStroke(4));
		
		for(int i = 0; i <= boardSize; i++) {
			pos = i * boxWidth;
						
			// Horizontella sträck
			//		   x1  y1      x2      y2
			g2.drawLine(0, pos, gameWidth, pos);
			
			// Vertikala sträck
			//          x1  y1   x2      y2
			g2.drawLine(pos, 0, pos, gameHeight);
		}	
		
		
		/*
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
			
				if(board[i][j] == 1)
					g2.drawRect(i * boxWidth, j * boxWidth, boxWidth, boxWidth);
				
			}
		}
		*/
		g2.setColor(Color.gray);
		for(int i = 1; i<14; i+=2)
			for(int j = 1; j<14; j+=2)
				g2.fillRect(i * boxWidth, j * boxWidth, boxWidth, boxWidth);
		
		g2.setColor(Color.GREEN);
		g2.drawRect(player1.getX() * boxWidth, player1.getY() * boxWidth, boxWidth, boxWidth);
		
		g2.setColor(new Color(255, 105, 180));
		g2.drawRect(player2.getX() * boxWidth, player2.getY() * boxWidth, boxWidth, boxWidth);
	}
	
}
