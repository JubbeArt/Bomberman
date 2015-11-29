package bomb;

// Abrstakt klass för alla objekt på spelplanen
public abstract class Entity extends GameBoard{
	
	protected int xPos, yPos;
	
	Entity(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	
}
