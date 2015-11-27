package bomb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Bomb extends Entity {
	/*
	 * TODO : ABRACKTRAOR KLCASS
	 * Timer
	 * Explosion
	 * */
	
	
	
	
	private int power;
	private long startTime;
	private boolean detonated;
	private long timeToExplode;
	
	
	Bomb(int x, int y, int power, long time) {
		super(x, y);
		this.power = power;
		startTime = time;
		
		timeToExplode = 1000;
		detonated = false;
		
	/*	ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Exploading");
            	expload();
                
            }
        };
        Timer timer = new Timer(1000 ,taskPerformer);
        timer.setRepeats(false);
        timer.start();
*/
        //Thread.sleep(5000); G�r vad�?
	}
	
	public void expload() {
		
		for(int i = 1; i <= power; i++) {
			
			if(checkSquare(xPos + i, yPos, 2))
				setSquare(xPos + i, yPos, 0);
	
			if(checkSquare(xPos - i, yPos, 2))
				setSquare(xPos - i, yPos, 0);			
			
			if(checkSquare(xPos, yPos + i, 2))
				setSquare(xPos, yPos + i, 0);		
			
			if(checkSquare(xPos, yPos - i, 2))
				setSquare(xPos, yPos - i, 0);
			
		}
		
		detonated = true;
	}
	
	public void updateBomb(long currentTime) {
		if(currentTime - startTime > timeToExplode)
			detonated = true;
	}
	
	public boolean hasDetonated(){
		return detonated;
	}
	
}
