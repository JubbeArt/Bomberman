package bomb;


public class Explosion extends Entity{

	private long startTime;
	private long timeToDisappear;
	
	public Explosion(int x, int y, long time) {
		super(x, y, Square.EXPLOSION.getValue(), Square.EXPLOSION.getColor());
		
		startTime = time;
		timeToDisappear = 400;
	}
	
	@Override
	public boolean update(long currentTime) {
		if(currentTime - startTime > timeToDisappear)
			return true;
		return false;
		
	}
	
	
	
}
