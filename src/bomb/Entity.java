package bomb;

// Abrstakt klass för alla objekt på spelplanen
public abstract class Entity extends GameBoard{
	
	protected int xPos, yPos;
	
	Entity() {
		xPos = 0;
		yPos = 0;
	}
	
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
	
	public void setX(int x) {
		xPos = x;
	}
	
	public void setY(int y) {
		yPos = y;
	}
	
}
