package bomb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Best game ever made... by the best guys ever made
 * REKT CHINA #HILLARY_FOR_PRESIDEN_KAPPA_NO_KAPPA
 * 
 * 
 * @author Baron Jesper Wrang (jeswr740) and Lord Samuel von Johansson (samjo788)
 *
 */

/* TODO:
 * 
 * ALLT
 * 
 * */
public class Bomberman extends JPanel {

	// Objekt för swing
	private JFrame frame;
	private JPanel windowContainer;
	
	private JPanel info;
	private JLabel infoTime, infoTitle;
	
	// Ritobjekt för panelen
	private Graphics2D g2;
		
	// Objekt för spelet
	private GameBoard gameBoard;	
	private Player[] players;	
	private Set<Bomb> bombs;
	
	// Storlekar på fönstret och alla containrar (JPanel)
	private final int WINDOW_WIDTH = 750, WINDOW_HEIGHT = 850;
	private final int GAME_WIDTH = 750, GAME_HEIGHT = 750;
	private final int INFO_WIDTH = 750, INFO_HEIGHT = 100;
	
	// Titlen på spelet såklart
	private String title = "BOMBERMAN!!½1!";
	
	public Bomberman() {		
		// Sätter och sparar undan storleken på panelen
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));	
		
		frame = new JFrame("Bomberman... Kappa"); 	// Själva fönstret
		
		// Inställningar för fönsteret
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	public static void main(String[] args) {
		
		Bomberman game = new Bomberman();
		game.createGame();
		game.createContainer();
		game.showWindow();		
		game.startGame();		
	}
	
	public void createGame() {			
		// Initialisera alla spelobjekt
		gameBoard = new GameBoard();
		gameBoard.resetBoard();
				
		players = new Player[]{new Player(0, 0), new Player(14, 0)};
		bombs = new HashSet<Bomb>();
	}	

	public void createContainer() {	
		//Main-container
		windowContainer = new JPanel();		
		
		// Bestämmer hur containern ska ska visas våra paneler (Y_AXIS = Uppifrån och neråt)
		windowContainer.setLayout(new BoxLayout(windowContainer, BoxLayout.Y_AXIS));		
		
		info = new JPanel();	// Den övere panelen med info om spelet
		info.setPreferredSize(new Dimension(INFO_WIDTH, INFO_HEIGHT));	
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		
		infoTitle = new JLabel("<html><h1>" + title + "</h1></html>");
		infoTitle.setHorizontalAlignment(JLabel.CENTER);
		infoTitle.setForeground(new Color(0, 0, 130));
		
		
		infoTime = new JLabel("Time : 60 sec left");
		
		info.add(infoTitle);
		info.add(infoTime);		
		
		// Lägger till info-delen och spelplanen till vår main-container
		windowContainer.add(info);
		windowContainer.add(this);
	
		// Keyboard-input
		windowContainer.addKeyListener(new KeyListener() {
				
			int[] left = {KeyEvent.VK_A, KeyEvent.VK_LEFT, KeyEvent.VK_NUMPAD4};
			int[] right = {KeyEvent.VK_D, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD6};
			int[] up = {KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_NUMPAD8};
			int[] down = {KeyEvent.VK_S, KeyEvent.VK_DOWN, KeyEvent.VK_NUMPAD2};
			int[] bomb = {KeyEvent.VK_SPACE, KeyEvent.VK_ENTER, KeyEvent.VK_NUMPAD5};
			
			@Override
			public void keyPressed(KeyEvent key) {
			
				int k = key.getKeyCode();
				
				for(int i = 0; i < players.length; i++) {				
					// Input för player1
					if(k == left[i])
						players[i].moveX(-1);
					else if(k == right[i])
						players[i].moveX(1);
					else if(k == up[i])
						players[i].moveY(-1);
					else if(k == down[i])
						players[i].moveY(1);
					else if(k == bomb[i]) {
						if(players[i].getBombsUsed() < players[i].getBombLimit()) {
							bombs.add(new Bomb(players[i].getX(), players[i].getY(), players[i].getPower(), System.currentTimeMillis(), i));
							players[i].changeBombsUsed(1);
						}
					}
				}				
			}
			
			@Override
			public void keyReleased(KeyEvent key) {}

			@Override
			public void keyTyped(KeyEvent key) {}
		});
		
		// Rätt container får fokus
		windowContainer.setFocusable(true);		
				
		// Lägger till containern till fönstret 
		frame.add(windowContainer);
	}
	
	public void showWindow() {		
		// "Packeterar" och visar fönstret
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void startGame() {	
		//1000 / 60 = 16.666... ms = 60fps
		long currentTime, diff;
		long oldTime = System.currentTimeMillis();		
	
		long gameTime = 60 * 1000;
		
		while(true) {
			currentTime = System.currentTimeMillis(); // Nuvanrande tiden
			diff = currentTime - oldTime; // Skillnaden mellan förra sparade tid			
				
			// Det har gått 1000/60 millisekunder sedan senaste sparande
			if(diff > 17) {
				oldTime = currentTime; // Sparar undan tiden
				gameTime -= diff; // Minskar spelklockan med differensen
				
				Iterator<Bomb> itBomb = bombs.iterator();
				Bomb tmpBomb;
				//Går igenom bomberna och kollar om någon detonerat. Isf tar bort bomben och ritar om planen
				while(itBomb.hasNext()) {
					tmpBomb = itBomb.next();
										
					// Kollar om bomben har exploderat än
					if(!tmpBomb.hasDetonated()) {
						if(tmpBomb.updateBomb(currentTime)) {
							int owner = tmpBomb.getOwner();
							players[owner].changeBombsUsed(-1);
						}						
					}
					else { // Kollar om vi fortfarande ska skriva ut bomben
						if(tmpBomb.updatePrintable(currentTime)) {
							itBomb.remove();			
						}						
					}			
				}	
				
				repaint();				
				
				if(gameTime > 0)
					infoTime.setText("Time : " + gameTime +" ms left");
				else
					infoTime.setText("Time : GameOver Bich");
			}
							
		}
		
	}
	
	// Ritar om hela panelen
	@Override
	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g; // "Bättre" objekt för ritning (nyare + mer funktionalitet, inte så viktigt)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // PIXIEGNOMS FRÅN CORNWALL
		
		// Ritar ut grindnätet
		drawBoard();
		
	}	
	
	// Ritar ut planen
	public void drawBoard() {
		int boardSize = 15; // Antal rutor på spelplanen (i x- och y-led)
		int boxWidth = GAME_WIDTH / boardSize; // Hur stor en ruta är
		int pos;	// Positionen där den ska börja rita
			
		// Ritar ut alla rutor
		for(int x = 0; x < 15; x++) {
			for(int y = 0; y < 15; y++) {
				if(gameBoard.at(x, y) == 2)
					g2.setColor(new Color(139, 69, 19));
				else if(gameBoard.at(x, y) == 1)
					g2.setColor(Color.gray);
				else	
					g2.setColor(Color.white);
				
					
				g2.fillRect(x * boxWidth,  y * boxWidth, boxWidth, boxWidth);
			}
		}
		
		// Ritar ut alla spelare
		for(int i = 0; i < players.length; i++) {
			g2.setColor(players[i].getColor());
			g2.fillRect(players[i].getX() * boxWidth, players[i].getY() * boxWidth, boxWidth, boxWidth);	
		}
				
		// Ritar alla bomber
		g2.setColor(Color.black);
		for(Bomb bomb : bombs)
			g2.fillRect(bomb.getX()*boxWidth+18, bomb.getY()*boxWidth+18, 15, 15);
		
		// Sätter storleken på borsten till 4 pixlar
		g2.setStroke(new BasicStroke(4));
		g2.setColor(Color.black);
		
		// Ritar alla sträck
		for(int i = 0; i <= boardSize; i++) {
			pos = i * boxWidth;

			g2.drawLine(0, pos, GAME_WIDTH, pos);
			g2.drawLine(pos, 0, pos, GAME_HEIGHT);
		}	
				
	}
}
