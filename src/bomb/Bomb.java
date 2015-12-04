package bomb;

public class Bomb extends Entity {

	private int power; // Hur många rutor bomben kan spränga
	private boolean detonated;
	private long startTime;	
	private long timeToExplode;
	private int owner;	
			
	Bomb(int x, int y, int power, long time, int owner) {
		super(x, y);
		this.power = power;
		this.owner = owner;
		startTime = time;
				
		timeToExplode = 1000;
		detonated = false;
	}
	
	// Exploderar bomben
	public void explode() {
		
		boolean hasHit[] = new boolean[4]; // clockvise (up,left,down,right)
					
		// Loopar igenom mer desto starkare bomben är
		for(int i = 1; i <= power; i++) {
			if(checkSquare(xPos, yPos - i, 2) && !hasHit[0]) { // up
				setSquare(xPos, yPos - i, 0);
				hasHit[0] = true;
			}
			if(checkSquare(xPos - i, yPos, 2) && !hasHit[1]) { // left
				setSquare(xPos - i, yPos, 0);
				hasHit[1] = true;
			}	
			if(checkSquare(xPos, yPos + i, 2) && !hasHit[2]) { // down
				setSquare(xPos, yPos + i, 0);	
				hasHit[2] = true;
			}
			if(checkSquare(xPos + i, yPos, 2) && !hasHit[3]) { // right
				setSquare(xPos + i, yPos, 0);
				hasHit[3] = true;
			}					
		}
		
		detonated = true;

	}
	
	// Kollar om det är dags att spränga skiten
	public boolean updateBomb(long currentTime) {	
		if(currentTime - startTime > timeToExplode) {
			explode();
			return true;
		}
		return false;		
	}
	
	
	public boolean hasDetonated(){
		return detonated;
	}
	
	public int getOwner() {
		return owner;
	}
	
	
}
