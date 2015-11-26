package bomb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Bomb extends GameBoard{
	/*
	 * TODO : ABRACKTRAOR KLCASS
	 * Timer
	 * Explosion
	 * */
	
	
	
	
	private int xPos;
	private int yPos;
	private int time;
	
	Bomb(int x, int y) {
		xPos = x;
		yPos = y;
		
		ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Exploading");
            	expload();
                
            }
        };
        Timer timer = new Timer(100 ,taskPerformer);
        timer.setRepeats(false);
        timer.start();

        //Thread.sleep(5000); Gör vadå?
	}
	
	public void expload() {
		if(checkSquare(xPos+1, yPos, 2))
			setSquare(xPos+1, yPos, 0);

		if(checkSquare(xPos-1, yPos, 2))
			setSquare(xPos-1, yPos, 0);			
		
		if(checkSquare(xPos, yPos+1, 2))
			setSquare(xPos, yPos+1, 0);		
		
		if(checkSquare(xPos, yPos-1, 2))
			setSquare(xPos, yPos-1, 0);
		
		//Call for removal
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
}
