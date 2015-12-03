package bomb;

public class Bomb extends Entity {

	private int power; // Hur många rutor bomben kan spränga
	private long startTime;
	private boolean detonated;
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
		
		// Loopar igenom mer desto starkare bomben är
		for(int i = 1; i <= power; i++) {
			
			if(checkSquare(xPos + i, yPos, 2)) // Kollar om det är en låda
				setSquare(xPos + i, yPos, 0); // Tar bort lådan
	
			if(checkSquare(xPos - i, yPos, 2))
				setSquare(xPos - i, yPos, 0);			
			
			if(checkSquare(xPos, yPos + i, 2))
				setSquare(xPos, yPos + i, 0);		
			
			if(checkSquare(xPos, yPos - i, 2))
				setSquare(xPos, yPos - i, 0);
			
		}
		
		detonated = true;
	}
	
	// Kollar om det är dags att spränga skiten
	public boolean updateBomb(long currentTime) {
		if(currentTime - startTime > timeToExplode) {
			detonated = true;
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
