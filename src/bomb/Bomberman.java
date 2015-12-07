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

/*
 * Mainklassen för vårt tonfika spel. Supersmasikt ju, eller vad snycks?
 * 
 * Klassen tar hand om alla swing- samt spelobjekt.
 * 
 * Fönstret skapas i konstruktorn.
 * Resten av objektet skapas i create-funktionerna (createGame och createContainers)
 * 
 * Spelet startas när startGame-funktionen kallas på. I den finns en while(true) loop
 * som körs till spelet är slut.
 * 
 * I spelloopen kallas updateGame och drawGame som ser till att spelet
 * blir går som de ska.
 * */

public class Bomberman {

	// Objekt för swing
	private JFrame frame;
	private JPanel windowContainer;
	
	// Objekt för text-utskrifter (den övre panelen)
	private JPanel info;
	private JLabel infoTime, infoTitle;
	
	// Ritobjekt för panelen (den undre panelen)
	private GameGraphics gameGraphics;
	
	// Objekt för spelet
	private GameBoard gameBoard;	
	private List<Player> players;	
	private Set<Bomb> bombs;
	private Set<Explosion> explosions;
	
	//Kontroll variabler för spelets gång
	private int playersAlive;
	private int winnerID[] = new int[4];
	private boolean ongoing = true;
	
	// Storlekar på fönstret och alla containrar (JPanel)
	private final int WINDOW_WIDTH = 750, WINDOW_HEIGHT = 850;
	private final int GAME_WIDTH = 750, GAME_HEIGHT = 750;
	private final int INFO_WIDTH = 750, INFO_HEIGHT = 100;
	
	// Titlen på spelet såklart
	private String title = "BOMBERMAN!!�1!";
	
	public Bomberman() {				
		frame = new JFrame("Bomberman! av Sam und Jazz"); 	// Själva fönstret
		
		// Inställningar för fönsteret
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	public static void main(String[] args) {
		
		Bomberman game = new Bomberman();	// Skapar fönstret
		game.createGame();			// Skapar alla spelobjekt
		game.createContainers();	// Skapar containrar och lägger till dom till fönstret
		game.showWindow();			// Visar fönstret
		game.startGame();			// Kör vårat tokfinskaa spel
	}
	
	// Initialisera alla spelobjekt
	public void createGame() {			
		
		gameBoard = new GameBoard(); // Skapar en spelplan
		gameBoard.resetBoard();		// Återställer spelplanen till grundläget
					
		players = new ArrayList<Player>();
		players.add(new Player(0, 0));		// Lägger till två spelare vid olika kordinater
		players.add(new Player(14, 0));
		
		// Mängder för bomber/explosioner
		bombs = new HashSet<Bomb>();
		explosions = new HashSet<Explosion>();	
	}	

	// Skapar alla containrar för spelet samt lägger till dom i fönstret
	public void createContainers() {	
		//Main-container
		windowContainer = new JPanel();		
		
		// Bestämmer hur containern ska visa våra paneler (Y_AXIS = Uppifrån och neråt)
		windowContainer.setLayout(new BoxLayout(windowContainer, BoxLayout.Y_AXIS));		
		
		// Den övere panelen med info om spelet
		info = new JPanel();	
		info.setPreferredSize(new Dimension(INFO_WIDTH, INFO_HEIGHT));	
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		
		// Textrutor som läggs till i den övrepanelen
		infoTitle = new JLabel("<html><h1>" + title + "</h1></html>");
		infoTitle.setHorizontalAlignment(JLabel.CENTER);
		infoTitle.setForeground(new Color(0, 0, 130));
				
		infoTime = new JLabel("Time : 60 sec left");
		
		info.add(infoTitle);
		info.add(infoTime);			
		
		// Den undre panelen där spelet ritas
		gameGraphics = new GameGraphics(GAME_WIDTH, GAME_HEIGHT);
		gameGraphics.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));	
				
		// Lägger till info-delen och spelplanen till vår main-container
		windowContainer.add(info);
		windowContainer.add(gameGraphics);
	
		// Keyboard-input för spelet
		windowContainer.addKeyListener(new KeyListener() {
				
			// Varje kolumn är knapparna för en spelare (alltså max 4 spelare nu)
			int[] left  = {KeyEvent.VK_A,		KeyEvent.VK_LEFT, 	KeyEvent.VK_NUMPAD4, KeyEvent.VK_J};
			int[] right = {KeyEvent.VK_D, 		KeyEvent.VK_RIGHT,	KeyEvent.VK_NUMPAD6, KeyEvent.VK_L};
			int[] up    = {KeyEvent.VK_W, 		KeyEvent.VK_UP,		KeyEvent.VK_NUMPAD8, KeyEvent.VK_I};
			int[] down  = {KeyEvent.VK_S, 		KeyEvent.VK_DOWN,	KeyEvent.VK_NUMPAD2, KeyEvent.VK_K};
			int[] bomb  = {KeyEvent.VK_CONTROL,	KeyEvent.VK_ENTER,	KeyEvent.VK_NUMPAD5, KeyEvent.VK_SPACE};
			
			@Override
			public void keyPressed(KeyEvent key) { // En knapp har trycks. Oj oj
			
				int k = key.getKeyCode();
				
				// Loopar igenom alla spelare
				for(int i = 0; i < players.size(); i++) {				
				
					// Spelaren måste vara vid liv för att göra saker ju!
					if(players.get(i).isAlive()) {

						// Jämför den tryckta knappen med spelarnas tangenter.
						if(k == left[i])
							players.get(i).moveX(-1);
						else if(k == right[i])
							players.get(i).moveX(1);
						else if(k == up[i])
							players.get(i).moveY(-1);
						else if(k == down[i])
							players.get(i).moveY(1);
						else if(k == bomb[i]) {
							// Kollar om spelaren får lägga en bomb
							if(players.get(i).getBombsUsed() < players.get(i).getBombLimit()) {
								bombs.add(new Bomb(	players.get(i).getX(),		// Lägger till en bomb 
													players.get(i).getY(), 
													players.get(i).getPower(), 
													players.get(i).getID(),
													System.currentTimeMillis()
													));
								players.get(i).changeBombsUsed(1);	// Ökar spelarens lagda bomber med 1
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
		
		// Rätt container får fokus, så vi faktist kan få keyboard-input
		windowContainer.setFocusable(true);		
				
		// Lägger till containern till fönstret 
		frame.add(windowContainer);
	}
	
	public void showWindow() {		
		// "Packeterar" och visar fönstret
		frame.pack();
		frame.setVisible(true);
	}
	
	
	// Startar spelet!!!! WOOHOOHOHOHOO LETS PLAY BITCHES
	public void startGame() {	
		//1000 / 60 = 16.666... ms = 60fps
		long currentTime, diff;
		long oldTime = System.currentTimeMillis();		
	
		long gameTime = 120 * 1000;
		
		while(ongoing) {
			currentTime = System.currentTimeMillis(); // Nuvanrande tiden
			diff = currentTime - oldTime; // Skillnaden mellan förra sparade tid			
				
			// Det har gått 1000/60 millisekunder sedan senaste sparande
			if(diff > 17) {
				oldTime = currentTime; // Sparar undan tiden
				gameTime -= diff; // Minskar spelklockan med differensen
				

				// Hämtar spelplanen
				int[][] tmpBoard = gameBoard.getBoard();
				
				// Updaterar spelet
				updateGame(tmpBoard, currentTime);				

				// Ritar om spelplanen
				// Här skapar vi nya objekt eftersom vi inte vill att objektet ska ändras
				// under utskriften. Trådar är fan farliga!!!				
				gameGraphics.drawGame(tmpBoard, new ArrayList<Player>(players), new HashSet<Bomb>(bombs));	
				
				// Uppdaterar tiden
				if(gameTime > 0)
					infoTime.setText("Time : " + String.format( "%.2f", gameTime / 1000.0) +" s left"); 
								
				// Om tiden tar slut är det dags att börja skapa "stenormen" som går genom spelplanen. Spelet måste ju ta slut ju!!!
				else if(!gameBoard.isEndgame()) {
					gameBoard.startEndgame(currentTime);					
					infoTime.setText("TIME IS RUNNING OUT!!!");
				} 
				// Tar bort stenar om endgame är på
				else {
					gameBoard.updateEndgame(currentTime);
					
				}	
			}				
		}
		
		
		System.out.println("Game has ended: Press *THIS BUTTON* to start a new game.");
	}
	
	public void updateGame(int[][] board, long currentTime) {
				
		Iterator<Bomb> itBomb = bombs.iterator();
		Bomb tmpBomb;
		//Går igenom bomberna och kollar om någon har detonerat. I så fall tar vi bort bomben
		while(itBomb.hasNext())	{
			tmpBomb = itBomb.next();
								
			// Kollar om bomben har exploderat än
			if(tmpBomb.update(currentTime)) {
					explosions.addAll(tmpBomb.explode()); // Exploderar bomben och lägger till explosions-objekt i våran mängd
				
					int owner = tmpBomb.getOwner();
					
					// Minskar antal bomber som spelaren har lagt ut
					for(int i = 0; i < players.size(); i++) {
						if(players.get(i).getID() == owner)
							players.get(i).changeBombsUsed(-1);						
					}
									
					itBomb.remove();							
			}			
		}	
		// Samma sak för explosionerna
		Iterator<Explosion> itExpl = explosions.iterator();
		Explosion tmpExp;
		
		while(itExpl.hasNext()) {
			tmpExp = itExpl.next();
			
			if(tmpExp.update(currentTime)) {
				gameBoard.setSquare(tmpExp.getX(), tmpExp.getY(), Square.EMPTY.getID());				
				itExpl.remove();
			}	
		}
		
		// Uppdeterar spelarna och kollar hur många spelare som är vid liv
		playersAlive = 0;
		
		for(Player p : players) {
			if(p.isAlive()) {
				p.update(); 	// Kollar om spelaren ska ta skada
				
				winnerID[playersAlive] = p.getID();
				playersAlive++;
			}
		}
		
		// Spelet slutar i remi
		if(playersAlive == 0) {
			ongoing = false;
		} 
		//Vi har en vinnare
		else if (playersAlive == 1) { 
			System.out.println("Winner is: Player number " + (winnerID[0]-4) +  "!");
			ongoing = false;
		}
		
	}
	
	
}