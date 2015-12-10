package bomb;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
 * Subklass av Entity. Är en bomb på spelplanen som läggs ut av spelarna.
 * 
 * Objektet vet hur mycket den ska spränga, när den ska sprängas och vem ägaren är.
 * Update-funktionen kollar om bomben ska sprängas.
 * Explode-funktionen exploderar bomben och retunerar en mängd med explosion-objekt.
 * 
 * */
public class Bomb extends Entity {

	private int power; 			// Hur många rutor bomben kan spränga
	private long startTime;		// När bomben lades ner
	private long timeToExplode;	// Hur lång tid innan bomben sprängs i millisekunder
	private int owner;			// Dess ägare
	private static Random rand = new Random();	// Random för powerups
	
	// Matematiska operatorer
	// add = addition
	// sub = subtraktion
	// same = gör ingenting
	private enum Change {add, sub, same};
	
	// Vad som krävs för att flytta sig i en riktning
	// Det första värdet i arrayen är x-värdets förändrning och det andra är y-värdet
	
	// Till exempel, om man vill komma uppåt så blir x-värdet det samma (same) och y-värdet minskar (sub)
	private Change[] up = {Change.same, Change.sub};
	private Change[] down = {Change.same, Change.add};
	private Change[] left = {Change.sub, Change.same};
	private Change[] right = {Change.add, Change.same};
	
	// Array för alla håll som bomben kan sprängas
	private Change[][] directions = {up, down, left, right};
	
	
	Bomb(int x, int y, int power, int owner, long time) {
		super(x, y, Square.BOMB.getID());
		this.power = power;
		this.owner = owner;
		startTime = time;
				
		timeToExplode = 1300;
	
		updatePos(xPos, yPos);
	}
	
	// Exploderar bomben och retunerar alla platser som blev explosions-objekt
	public Set<Explosion> explode() {		
		
		// Skapar en temporär mänga av explosion-objekt som ska retuneras
		Set<Explosion> tmpSet = new HashSet<Explosion>();
		
		// Hämtar den nuvarande tiden
		long currentTime = System.currentTimeMillis();
		
		// Sätter bombens plats till en explosion, detta kommer alltid ske ju.
		setSquare(xPos, yPos, Square.EXPLOSION.getID()); // Uppdaterar spelplanen
		tmpSet.add(new Explosion(xPos, yPos, currentTime)); // Skapar ett explosions-objekt
		
		
		 // Loopar igenom alla håll som bomben kan explodera
		for(int i = 0; i < directions.length; i++) { 
			
			// Loopar igenom hur långt bombmen ska sprängas
			for(int p = 1; p <= power; p++) {			
				
				// Mitten av explosionen
				int[] pos = {xPos, yPos};
								
				// Kolla vad man ska öka/miska för att gå åt ett håll
				for(int xy = 0; xy < pos.length; xy++) { 
					if(directions[i][xy] == Change.add)
						pos[xy] += p;
					else if(directions[i][xy] == Change.sub)
						pos[xy] -= p;				
				}
				
				// Om explosionen går utanför banan så går vi vidare till nästa riktnig
				if(!checkInBounds(pos[0], pos[1]))
					break;
				
				// Hämtar vilket slags block vi kollar på
				int id = get(pos[0], pos[1]);
								
				// Om det är en låda eller bara en tom plats så vill vi skapa en explosion
				if(id == Square.EMPTY.getID() || id == Square.EXPLOSION.getID()) {
					setSquare(pos[0], pos[1], Square.EXPLOSION.getID());
					tmpSet.add(new Explosion(pos[0], pos[1], currentTime));
				} else if(id == Square.CRATE.getID()) {

					if(rand.nextBoolean())
						setSquare(pos[0], pos[1], Square.POWERUP.getID());
					else {
						setSquare(pos[0], pos[1], Square.EXPLOSION.getID());
						tmpSet.add(new Explosion(pos[0], pos[1], currentTime));
					}
				}
				
				// Om den träffade en sten eller en låda så vill vi sluta explosionen i den riktningen
				if(id == Square.STONE.getID() || id == Square.CRATE.getID())
					break;				
			}
		}

		return tmpSet;
		
	}
	
	
	// Kollar om det är dags att spränga skiten
	public boolean update(long currentTime) {	
		if(currentTime - startTime > timeToExplode)
			return true;
		return false;		
	}
	
	// Retunerar bombens ägare
	public int getOwner() {
		return owner;
	}
	
	
}