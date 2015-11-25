package bomb;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Best game ever made... by the best guys ever made
 * REKT CHINA #HILLARY_FOR_PRESIDEN_KAPPA_NO_KAPPA
 * 
 * 
 * @author Baron Jesper Wrang (jeswr740) and Lord Samuel von Johansson (samjo788)
 *
 */
public class Bomberman {

	// Objekt för swing
	private JFrame frame = null;
	private JPanel windowContainer = null;
	private GameInfo info = null;
	private GameGraphics game = null;
		
	// Objekt för spelet aaa
	private GameBoard gameBoard = null;
	private Player player1 = null;
	private Player player2 = null;
	
	// Storlekar på fönstret och alla containrar (JPanel)
	private final int WINDOW_WIDTH = 750, WINDOW_HEIGHT = 900;
	private final int GAME_WIDTH = 750, GAME_HEIGHT = 750;
	private final int INFO_WIDTH = 750, INFO_HEIGHT = 150;
	
	public Bomberman() {
		// Initialisera (Puh vilket ord) alla swing-objekt 
		
		frame = new JFrame("Bomberman... Kappa"); 	// Själva fönstret
		windowContainer = new JPanel();	//Main-container
		
		
		info = new GameInfo(INFO_WIDTH, INFO_HEIGHT);	// Den övere panelen med info om spelet
		game = new GameGraphics(GAME_WIDTH, GAME_HEIGHT);	// Den nedre panelen med själv spelplanen
		
		// Initialisera alla spelobjekt
		gameBoard = new GameBoard();
		player1 = new Player();
		player2 = new Player();
	
		game.updateGame(player1, player2);
		
	}	
	
	public static void main(String[] args) {
		
		// Skapar alla objekt
		Bomberman bombGame = new Bomberman();
		// Skapar fönstret
		bombGame.createWindow();
		// Går in i spel-loopen
		bombGame.startGame();
		
	}
	
	public void createWindow() {
		
		// Inställningar för fönsteret
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Bestämmer hur containern ska ska visas våra paneler (Y_AXIS = Uppifrån och neråt)
		windowContainer.setLayout(new BoxLayout(windowContainer, BoxLayout.Y_AXIS));
		
		// Lägger till info-delen och spelplanen till vår main-container
		windowContainer.add(info);
		windowContainer.add(game);
	
		
		windowContainer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent key) {
			
				int k = key.getKeyCode();
				
				
				if(k== KeyEvent.VK_LEFT)
					player1.moveX(-1);
				else if(k == KeyEvent.VK_RIGHT)
					player1.moveX(1);
				else if(k == KeyEvent.VK_UP)
					player1.moveY(-1);
				else if(k == KeyEvent.VK_DOWN)
					player1.moveY(1);
				
				//player2
				if(k == KeyEvent.VK_A)
					player2.moveX(-1);
				else if(k == KeyEvent.VK_D)
					player2.moveX(1);
				else if(k == KeyEvent.VK_W)
					player2.moveY(-1);
				else if(k == KeyEvent.VK_S)
					player2.moveY(1);
				
				game.updateGame(player1, player2);
				game.repaint();
			
			}
		}); 
		windowContainer.setFocusable(true);		
		
		
		// Lägger till containern till fönstret 
		frame.add(windowContainer);

		
		// "Packeterar" och visar fönstret
		frame.pack();
		frame.setVisible(true);
	}

	public void startGame() {
		// AYYYY LMAO - Abraham Lincon
		// "Någon loop som loopar till spelet är slut" här
		
		//System.exit(0);
		
		// Hämtar den nuvarande tiden i millisekunder
		long currentTime = System.currentTimeMillis();
		
	}

}
