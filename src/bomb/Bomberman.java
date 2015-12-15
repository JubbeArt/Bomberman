package bomb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bomb.GameBoard.Square;

/**
 * Best game ever made... by the best guys ever made
 * REKT CHINA #HILLARY_FOR_PRESIDEN_KAPPA_NO_KAPPA
 * 
 * My Grandfather smoked his whole life. 
 * I was about 10 years old when my mother said to him, 
 * "If you ever want to see your grandchildren graduate, you have to stop immediately." 
 * Tears welled up in his eyes when he realized what exactly was at stake. He gave it up immediately. 
 * Three years later he died of lung cancer. It was really sad and destroyed me. 
 * My mother said to me- "Don't ever smoke. Please don't put your family through what your Grandfather put us through." 
 * I agreed. At 28, I have never touched a cigarette. 
 * I must say, I feel a very slight sense of regret for never having done it, because your game gave me cancer anyway.
 * 
 * @author Baron Jesper Wrang (jeswr740) and Lord Samuel von Johansson (samjo788)
 *
 */

/*
 * Mainklassen för vårt tonflinka spel. Supersmasikt ju, eller vad snycks?
 * 
 * Klassen tar hand om alla swing- samt spelobjekt.
 * 
 * Fönstret skapas i konstruktorn.
 * Resten av objekten skapas i create-funktionerna (createGame och createContainers)
 * 
 * Spelet startas när startGame-funktionen kallas på. I den finns en while(true) loop
 * som körs till spelet är slut.
 * 
 * I spelloopen kallas updateGame och drawGame som ser till att spelet går på.
 * */

/*
 * TODO
 * TODO
 * TODO TODO TODO
 * TO DO DODO
 * DO DODO
 * TO DO DO DODO
 * 
 * Fixa stöd för andra spelplaner som textfiler
 * Skapa spelare styrs av variable
 * Skriva ut antal vinster för varje spelare (i realtid)
 * Autogenerera klassdiagram
 * 
 * Fixa fularvet
 * 
 * 
 * 
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
	private static boolean playAgain = true;
	
	// Den valda banan
	private static String currentMap;
	
	// Storlekar på fönstret och alla containrar (JPanel)
	
	private final int GAME_WIDTH = 750, GAME_HEIGHT = 750;
	private final int INFO_WIDTH = 750, INFO_HEIGHT = 75;	
	private final int WINDOW_WIDTH = 750, WINDOW_HEIGHT = GAME_HEIGHT + INFO_HEIGHT;
	
	
	// Titlen på spelet såklart
	private String title = "BOMBERMAN";
	
	public Bomberman() {				
		frame = new JFrame("Bomberman! av Sam und Jazz"); 	// Själva fönstret
		
		// Inställningar för fönsteret
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
	}
	
	public static void main(String[] args) {
		 
		Bomberman game = new Bomberman();	// Skapar fönstret
		Boolean hasErrors;
		int amountOfPlayers = 0;
		
		// Hämtar input från användren.... vilket jävla idiot, bara kolla på hur många checks vi många göra...
		do {
			hasErrors = false;
			String answer = JOptionPane.showInputDialog("The amout of players sir (2-4).... Don't be an idiot!");

			if(answer == null) // Någon tryckte på avbryt
				System.exit(0);
			
			try{
				amountOfPlayers = Integer.parseInt(answer);			
			} catch(InputMismatchException e) {
				hasErrors = true;
			} catch(NumberFormatException e) {
				hasErrors = true;
			}	
			
			if(amountOfPlayers > 4 || amountOfPlayers < 2)
				hasErrors = true;
	
		} while(hasErrors);
		
		// Map input
		currentMap = JOptionPane.showInputDialog("The map of your choice SIRRRR!!", "default.txt");
		if(currentMap == null)
			System.exit(0);
		
		game.createContainers();	// Skapar containrar och lägger till dom till fönstret
		game.showWindow();			// Visar fönstret
		game.createPlayers(amountOfPlayers);
		
		while(true) {
		
			if(playAgain){
				game.resetGame();			// Skapar alla spelobjekt
				game.startGame();			// Kör vårat tokfinskaa spel
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		
		}	
	}
	
	// Initialisera alla spelarobjekt
	public void createPlayers(int amountOfPlayers) {			
		players = new ArrayList<Player>();		
		
		int startPos[][] = {
				{0, 0},
				{14, 0},
				{0, 14},
				{14, 14}				
		};
		
		for(int i = 0; i < amountOfPlayers; i++) {
			players.add(new Player(startPos[i][0], startPos[i][1]));
		}

	}	
	
	//Återställer alla objekt
	public void resetGame() {			
		gameBoard = new GameBoard(); // Skapar en spelplan
		gameBoard.resetBoard(currentMap);		// Återställer spelplanen till grundläget
		
		for(Player p: players)
			p.resetPlayer();
		
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
								players.get(i).addTotalBombs();
							}
						}
					}
				}		
				
				// Keyinput när spelet är över
				if(!playAgain)  {
					if(k == KeyEvent.VK_ENTER) {
						playAgain = true;
					} else if (k == KeyEvent.VK_ESCAPE) {
						saveResults();
						System.exit(0);
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
	
	// "Paketerar" och visar fönstret
	public void showWindow() {
		frame.pack();
		frame.setVisible(true);
	}
	
	
	// Startar spelet!!!!
	public void startGame() {	
		infoTitle.setText("<html><h2>" + title + "</h2></html>");
		long currentTime, diff;
		long oldTime = System.currentTimeMillis();		
		
		long gameTime = 90 * 1000;
				
		while(true) {
			
			currentTime = System.currentTimeMillis(); // Nuvanrande tiden
			diff = currentTime - oldTime; // Skillnaden mellan förra sparade tid			
			
			//1000 / 60 = 16.666... ms = 60fps
			// Det har gått 1000/60 ms sedan senaste sparande
			if(diff > 17) {
				oldTime = currentTime; // Sparar undan tiden
				gameTime -= diff; // Minskar spelklockan med differensen
							
				
				// Kollar om det är dags att börja "slutspelet", då en stenorm kommer och äter upp världen
				if(gameTime <= 0 && !gameBoard.isEndgame())
					gameBoard.startEndgame(currentTime);				
				
				// Uppdaterar spelormen
				if(gameBoard.isEndgame()) {
					gameBoard.updateEndgame(currentTime);
					gameTime = 0;
				}	
									
				// Uppdaterar infon om tiden och spelarvinster
				updateGameInfo(gameTime);
				
				// Hämtar spelplanen
				int[][] tmpBoard = gameBoard.getBoard();
				
				// Updaterar spelet
				if(!updateGame(tmpBoard, currentTime)) {
					updateGameInfo(gameTime);
					break;
				}
				// Ritar om spelplanen
				// Här skapar vi nya objekt eftersom vi inte vill att objektet ska ändras
				// under utskriften. Trådar är fan farliga!!!				
				gameGraphics.drawGame(tmpBoard, new ArrayList<Player>(players), new HashSet<Bomb>(bombs));	
				
			}
			//Vill du spela igen?
		  playAgain = false;
		}
		//infoTitle.setText("<html><h2>GAME OVER! PRESS ENTER TO PLAY AGIEN! ESC TO QUIT.</h2></html>");
	}
	
	// Uppdaterar infon om tiden och spelarvinster
	public void updateGameInfo(long gameTime) {
		
		// Skriver ut tid och hur många gånger spelarna har vunnit
		String infoString = "";
		
		for(int i = 0; i < players.size(); i++) {
			infoString += String.format("%-33s", "Player " + (i+1) + " : " + players.get(i).getTotalWins());
			if(i == 1 || players.size() < 2)
				infoString += String.format("%-30s", "Time : " + String.format( "%.2f", gameTime / 1000.0) + " s");
			
		}
		
		// Skriver ut texten
		infoTime.setText(infoString);
		
	}
	
	
	//Uppdaterar spelet
	public boolean updateGame(int[][] board, long currentTime) {
						
		boolean shouldContinue = true;
		
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
				playersAlive++;
			}
		}
			
		
		// Spelet slutar i remi
		if(playersAlive == 0) {
			infoTitle.setText("<html><h2>You all suck!!!!! ENTER TO PLAY AGAIN!!! Esc to exit!</h2></html>");
			shouldContinue = false;
		} 
		//Vi har en vinnare
		else if (playersAlive == 1) { 
			for(Player p : players) {
				if(p.isAlive()) {
					infoTitle.setText("<html><h2>Player " + (p.getID() - 4) +" won! ENTER TO PLAY AGAIN!!! Esc to exit!</h2></html>");
					p.addTotalWins();
				}
			}
			
			shouldContinue = false;
		}
		
		
		return shouldContinue;
	}
	
	//Sparar senaste resultaten i en textfil
	public void saveResults() {
		
		PrintWriter writer;
		
		try {
			writer = new PrintWriter("BMResult.txt", "UTF-8");
			writer.println("Game stats\n");
			for(Player p : players) {
				writer.println("Player " + (p.getID()-4));
				writer.println("\tWins : " + p.getTotalWins());
				writer.println("\tBombs : " + p.getTotalBombs());
				writer.println("\tPowerups : " + p.getTotalPowerUps());
			}
			writer.close();	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	
	}
	
}