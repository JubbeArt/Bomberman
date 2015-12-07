package bomb;

import java.util.HashSet;
import java.util.Set;


public class Bomb extends Entity {

	private int power; // Hur många rutor bomben kan spränga
	private boolean detonated;
	private long startTime;	
	private long timeToExplode;
	private int owner;	
		
	
	private enum Change {add, sub, same};
	
	private Change[] up = {Change.same, Change.sub};
	private Change[] down = {Change.same, Change.add};
	private Change[] left = {Change.sub, Change.same};
	private Change[] right = {Change.add, Change.same};
	
	private Change[][] directions = {up, down, left, right};
	
	
	Bomb(int x, int y, int power, int owner, long time) {
		super(x, y, Square.BOMB.getID());
		this.power = power;
		this.owner = owner;
		startTime = time;
				
		timeToExplode = 1300;
		detonated = false;
	}
	
	// Exploderar bomben
	public Set<Explosion> explode() {		
		
		Set<Explosion> tmpSet = new HashSet<Explosion>();
		long currentTime = System.currentTimeMillis();
		
		setSquare(xPos, yPos, Square.EXPLOSION.getID());
		tmpSet.add(new Explosion(xPos, yPos, currentTime));
		
		
		 // Loopar igenom alla håll som bomben kan explodera
		for(int i = 0; i < directions.length; i++) { 
			
			// Loopar igenom hur långt bombmen ska sprängas
			for(int p = 1; p <= power; p++) {			
							
				int[] pos = {xPos, yPos};
								
				for(int xy = 0; xy < pos.length; xy++) { // Kolla vad man ska öka/miska för att åt ett håll
					if(directions[i][xy] == Change.add)
						pos[xy] += p;
					else if(directions[i][xy] == Change.sub)
						pos[xy] -= p;				
				}
				
				if(!checkInBounds(pos[0], pos[1]))
					break;
				
				int id = get(pos[0], pos[1]);
								
				if(id == Square.CRATE.getID() || id == Square.EMPTY.getID()) {
					setSquare(pos[0], pos[1], Square.EXPLOSION.getID());
					tmpSet.add(new Explosion(pos[0], pos[1], currentTime));
				}
				
				if(id == Square.STONE.getID() || id == Square.CRATE.getID())
					break;				
			}
		}
		
		detonated = true;

		return tmpSet;
		
	}
	
	private boolean checkInBounds(int x, int y) {
		if(x < 0 || x > 14 || y < 0 || y > 14)
			return false;
		return true;
				
	}
	
	// Kollar om det är dags att spränga skiten
	public boolean update(long currentTime) {	
		if(currentTime - startTime > timeToExplode)
			return true;
		return false;		
	}
	
	
	public boolean hasDetonated(){
		return detonated;
	}
	
	public int getOwner() {
		return owner;
	}
	
	
}