package bomb;

/*
 * Subklass av Entity. En ruta som representerar en explosion på spelplanen.
 * 
 * Håller reda på sin starttid. 
 * Update-funktionen kollar när det är dags för explosionen att försvinna
 *  
 * */
public class Explosion extends Entity{

	private long startTime;			// Tiden då explosionen börja
	private long timeToDisappear;	// Hur länga explosionen varar
	
	public Explosion(int x, int y, long time) {
		super(x, y, Square.EXPLOSION.getID());
		
		startTime = time;
		timeToDisappear = 400; // 400 millisekunder
	}

	// Retunerar sant om explosione nska försvinna
	public boolean update(long currentTime) {
		if(currentTime - startTime > timeToDisappear)
			return true;
		return false;
	}
	
}