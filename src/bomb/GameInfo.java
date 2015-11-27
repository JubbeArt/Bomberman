package bomb;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Subklass av JPanel. Används för att visa information om spelet, 
 * till exempel tid, namn på spel och poäng
 *
 */
public class GameInfo extends JPanel {
	
	// Kanske inte behövs, bara om vi vill ha någon fancy bakgrund eller så
	private Graphics2D g2 = null;
	
	// Textrutor med strängar som skrivs ut
	private JLabel gameTitle = null;
	private JLabel gameTime = null;
	private JLabel gameOver = null;
	
	// Information om spelet
	private String name = "BOMBERMAN!!!";
	//private String time = "";
	
	public GameInfo(int width, int height) {
		
		// Sätter storleken på panelen
		setPreferredSize(new Dimension(width, height));
		// Sätter så textruterna kommer uppifrån och ner (BORDE NOG ÄNDRA DETTA)
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
		
		// Skapar textrutorna med den text de ska skriva ut
		gameTitle = new JLabel(name); // Kan skriva HTML-kod här... wtf?
		gameTime = new JLabel("00:00");
		gameOver = new JLabel("NOT GAME OVER... DAMM hey sam sam ^-^ so kawaiiIII how yo doin?");
		
		// Lägger till textrutorna i panelen
		add(gameTitle);
		add(gameTime);
		add(gameOver);
	}
	
	// Uppdaterar informationen i textrutorna
	public void updateInfo() {
		// typ gameTime.setText(text);
		
	}
	


}
