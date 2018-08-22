package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEventListener implements MouseListener, MouseMotionListener{
	private Game game;
	
	public MouseEventListener(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		
	} 

	
	public void mousePressed(MouseEvent event) {
		if(event.getButton() == MouseEvent.BUTTON1) {
			//button1 = left click
			game.leftClick(event.getX(), event.getY());
		}
		if(event.getButton() == MouseEvent.BUTTON3) {
			//button3= right click
			game.rightClick(event.getX(), event.getY());
		}
		
		int mx = event.getX();
		int my = event.getY();

		//		public Rectangle playButton =  new Rectangle (340, 250, 300, 50);
		//		public Rectangle loadButton = new Rectangle (340, 350, 300, 50);
		//		public Rectangle helpButton = new Rectangle (340, 450, 300, 50);
		//		public Rectangle quitButton = new Rectangle (340, 550, 300, 50);
		


		
//		if(mx >= 340 && mx <= 640) 
//		{
//			if(my >= 250 && my <= 300) 
//			{
//				//Press Play Button
//				Game.State = Game.STATE.GAME;
//			}
//		}
//		
//		//Load Button
//		
//		if(mx >= 340 && mx <= 640) 
//		{
//			if(my >= 350 && my <= 400) 
//			{
//				//Press Load Button
//				Game.State = Game.STATE.LOAD;
//			}
//		}
//		
//		// Help Button
//		if(mx >= 340 && mx <= 640) 
//		{
//			if(my >= 450 && my <= 500) 
//			{
//				//Press Help Button	
//				Game.State = Game.STATE.HELP;
//			}
//		}
//		
//		
//		// Quit Button
//		if(mx >= 340 && mx <= 640) 
//		{
//			if(my >= 550 && my <= 600) 
//			{
//				//Press Play Button
//				System.exit(1);
//			}
//		}
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}	

}
