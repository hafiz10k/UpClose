package game;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {


	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		//		public Rectangle playButton =  new Rectangle (340, 250, 300, 50);
		//		public Rectangle loadButton = new Rectangle (340, 350, 300, 50);
		//		public Rectangle helpButton = new Rectangle (340, 450, 300, 50);
		//		public Rectangle quitButton = new Rectangle (340, 550, 300, 50);
		
//		public Rectangle playButton =  new Rectangle (320, 150, 100, 50);
//		public Rectangle loadButton = new Rectangle (320, 250, 100, 50);
//		public Rectangle helpButton = new Rectangle (320, 350, 100, 50);
//		public Rectangle quitButton = new Rectangle (320, 450, 100, 50);

		// Play Button
		if(mx >= 320 && mx <= 420) 
		{
			if(my >= 150 && my <= 200) 
			{
				//Press Play Button
				Game.State = Game.STATE.GAME;
			}
		}
		//Load Button
		
		// Help Button
		if(mx >= 320 && mx <= 420) 
		{
			if(my >= 350 && my <= 400) 
			{
				//Press Play Button
				Game.State = Game.STATE.HELP;
			}
		}
		
		// Quit Button
		if(mx >= 320 && mx <= 420) 
		{
			if(my >= 450 && my <= 500) 
			{
				//Press Play Button
				System.exit(1);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
