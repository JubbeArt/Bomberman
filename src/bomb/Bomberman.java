package bomb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bomb.GameBoard.Square;

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
 * 
 * Fixa coola utskrifter n�r spelet �r slut
 * Fixa alternativ att starta nytt spel
 * tiden / end---snurrent
 * spela flera spel
 * powerups
 * sounds
 * music
 * 
 * 
 * */
public class Bomberman {

	// Objekt för swing
	private JFrame frame;
	private JPanel windowContainer;
	
	private JPanel info;
	private JLabel infoTime, infoTitle;
	
	// Ritobjekt för panelen
	private GameGraphics gameGraphics;
	
	// Objekt för spelet
	private GameBoard gameBoard;	
	private List<Player> players;	
	private Set<Bomb> bombs;
	private Set<Explosion> explosions;
	
	//Ctrl-vars
	private int playersAlive;
	private int winnerID[] = new int[4];
	private boolean ongoing = true;
	
	// Storlekar p� f�nstret och alla containrar (JPanel)
	private final int WINDOW_WIDTH = 750, WINDOW_HEIGHT = 850;
	private final int GAME_WIDTH = 750, GAME_HEIGHT = 750;
	private final int INFO_WIDTH = 750, INFO_HEIGHT = 100;
	
	// Titlen p� spelet s�klart
	private String title = "BOMBERMAN!!�1!";
	
	public Bomberman() {				
		frame = new JFrame("Bomberman... Kappa"); 	// Sj�lva f�nstret
		
		// Inst�llningar f�r f�nsteret
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	public static void main(String[] args) {
		
		Bomberman game = new Bomberman();
		game.createGame();
		game.createContainers();
		game.showWindow();		
		game.startGame();		
	}
	
	public void createGame() {			
		// Initialisera alla spelobjekt
		gameBoard = new GameBoard();
		gameBoard.resetBoard();
					
		players = new ArrayList<Player>();
		players.add(new Player(0, 0));
		players.add(new Player(14, 0));
		
		bombs = new HashSet<Bomb>();
		explosions = new HashSet<Explosion>();	
	}	

	public void createContainers() {	
		//Main-container
		windowContainer = new JPanel();		
		
		// Best�mmer hur containern ska ska visas v�ra paneler (Y_AXIS = Uppifr�n och ner�t)
		windowContainer.setLayout(new BoxLayout(windowContainer, BoxLayout.Y_AXIS));		
		
		info = new JPanel();	// Den �vere panelen med info om spelet
		info.setPreferredSize(new Dimension(INFO_WIDTH, INFO_HEIGHT));	
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		
		infoTitle = new JLabel("<html><h1>" + title + "</h1></html>");
		infoTitle.setHorizontalAlignment(JLabel.CENTER);
		infoTitle.setForeground(new Color(0, 0, 130));
				
		infoTime = new JLabel("Time : 60 sec left");
		
		info.add(infoTitle);
		info.add(infoTime);			
		
		gameGraphics = new GameGraphics(GAME_WIDTH, GAME_HEIGHT);
		gameGraphics.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));	
				
		// L�gger till info-delen och spelplanen till v�r main-container
		windowContainer.add(info);
		windowContainer.add(gameGraphics);
	
		// Keyboard-input
		windowContainer.addKeyListener(new KeyListener() {
				
			int[] left  = {KeyEvent.VK_A,		KeyEvent.VK_LEFT, 	KeyEvent.VK_NUMPAD4, KeyEvent.VK_J};
			int[] right = {KeyEvent.VK_D, 		KeyEvent.VK_RIGHT,	KeyEvent.VK_NUMPAD6, KeyEvent.VK_L};
			int[] up    = {KeyEvent.VK_W, 		KeyEvent.VK_UP,		KeyEvent.VK_NUMPAD8, KeyEvent.VK_I};
			int[] down  = {KeyEvent.VK_S, 		KeyEvent.VK_DOWN,	KeyEvent.VK_NUMPAD2, KeyEvent.VK_K};
			int[] bomb  = {KeyEvent.VK_CONTROL,	KeyEvent.VK_ENTER,	KeyEvent.VK_NUMPAD5, KeyEvent.VK_SPACE};
			
			@Override
			public void keyPressed(KeyEvent key) {
			
				int k = key.getKeyCode();
				
				for(int i = 0; i < players.size(); i++) {				
				
					if(players.get(i).isAlive()) {
					
						// Input f�r player1
						if(k == left[i])
							players.get(i).moveX(-1);
						else if(k == right[i])
							players.get(i).moveX(1);
						else if(k == up[i])
							players.get(i).moveY(-1);
						else if(k == down[i])
							players.get(i).moveY(1);
						else if(k == bomb[i]) {
							if(players.get(i).getBombsUsed() < players.get(i).getBombLimit()) {
								bombs.add(new Bomb(	players.get(i).getX(), 
													players.get(i).getY(), 
													players.get(i).getPower(), 
													players.get(i).getID(),
													System.currentTimeMillis()
													));
								players.get(i).changeBombsUsed(1);
							}
						}
					}
				}				
			}
			
			@Override
			public void keyReleased(KeyEvent key) {}

			@Override
			public void keyTyped(KeyEvent key) {}
		});
		
		// R�tt container f�r fokus
		windowContainer.setFocusable(true);		
				
		// L�gger till containern till f�nstret 
		frame.add(windowContainer);
	}
	
	public void showWindow() {		
		// "Packeterar" och visar f�nstret
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void startGame() {	
		//1000 / 60 = 16.666... ms = 60fps
		long currentTime, diff;
		long oldTime = System.currentTimeMillis();		
	
		long gameTime = 120 * 1000;
		
		while(ongoing) {
			currentTime = System.currentTimeMillis(); // Nuvanrande tiden
			diff = currentTime - oldTime; // Skillnaden mellan f�rra sparade tid			
				
			// Det har g�tt 1000/60 millisekunder sedan senaste sparande
			if(diff > 17) {
				oldTime = currentTime; // Sparar undan tiden
				gameTime -= diff; // Minskar spelklockan med differensen
				

				int[][] tmpBoard = gameBoard.getBoard();
				
				// Ritar om spelplanen
				updateGame(tmpBoard, currentTime);				
				gameGraphics.drawGame(tmpBoard, players, bombs);	
				
				//Visa tid+Endgame
				if(gameTime > 0)
					infoTime.setText("Time : " + String.format( "%.2f", gameTime / 1000.0) +" s left");
				else if(!gameBoard.isEndgame()) {
					gameBoard.startEndgame(currentTime);					
					infoTime.setText("TIME IS RUNNING OUT!!!");
				} else {
					gameBoard.updateEndgame(currentTime);
					
				}	
			}				
		}
		System.out.println("Game has ended: Press *THIS BUTTON* to start a new game.");
	}
	
	public void updateGame(int[][] board, long currentTime) {
				
		Iterator<Bomb> itBomb = bombs.iterator();
		Bomb tmpBomb;
		//G�r igenom bomberna och kollar om n�gon detonerat. Isf tar bort bomben och ritar om planen
		while(itBomb.hasNext())	{
			tmpBomb = itBomb.next();
								
			// Kollar om bomben har exploderat än
			if(tmpBomb.update(currentTime)) {
					explosions.addAll(tmpBomb.explode());
					int owner = tmpBomb.getOwner();
					
					for(int i = 0; i < players.size(); i++) {
						if(players.get(i).getID() == owner)
							players.get(i).changeBombsUsed(-1);						
					}
									
					itBomb.remove();							
			}			
		}	
		Iterator<Explosion> itExpl = explosions.iterator();
		Explosion tmpExp;
		
		while(itExpl.hasNext()) {
			tmpExp = itExpl.next();
			
			if(tmpExp.update(currentTime)) {
				gameBoard.setSquare(tmpExp.getX(), tmpExp.getY(), Square.EMPTY.getID());				
				itExpl.remove();
			}	
		}
		
		playersAlive = 0;
		
		for(Player p : players) {
			if(p.isAlive()) {
				p.update();
				winnerID[playersAlive] = p.getID();
				playersAlive++;
			}
		}
		
		if(playersAlive == 0) { //Lika
			ongoing = false;
		} else if (playersAlive == 1) { //Vi har en vinnare
			System.out.println("Winner is: Player number " + (winnerID[0]-4) +  "!");
			ongoing = false;
		}
		
	}
	
	
}