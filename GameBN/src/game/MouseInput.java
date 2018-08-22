package game;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener{

	//	private Game game;
	//
	//	public MouseInput(Game game) {
	//		this.game = game;
	//	}
	boolean buttonClicked = false;
	boolean buttonClickedLoad = false;

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

		

			if(mx >= 340 && mx <= 640 && buttonClicked == false) 
			{
				if(my >= 250 && my <= 300 && buttonClicked == false) 
				{
					buttonClicked = true;
					//Press Play Button
					Game.State = Game.STATE.GAME;	
					
				}			
			}			
			
		
			if(mx >= 340 && mx <= 640 && buttonClicked == false)  
			{
				if(my >= 350 && my <= 400 && buttonClicked == false) 
				{
					//Press Load Button
					Game.State = Game.STATE.LOAD;
					buttonClicked = true;
				}

			}


			//Load Button

			if(mx >= 340 && mx <= 640 && buttonClicked == false) 
			{
				if(my >= 350 && my <= 400 && buttonClicked == false) 
				{
					//Press Load Button
					Game.State = Game.STATE.LOAD;
					buttonClicked = true;

				}
			}

			// Help Button
			if(mx >= 340 && mx <= 640 && buttonClicked == false) 
			{
				if(my >= 450 && my <= 500 && buttonClicked == false) 
				{
					//Press Help Button	
					Game.State = Game.STATE.HELP;
					buttonClicked = true;
				}
			}


			// Quit Button
						if(mx >= 340 && mx <= 640 && buttonClicked == false) 
						{
							if(my >= 550 && my <= 600 && buttonClicked == false) 
							{
								//Press Play Button
								System.exit(1);
								
							}
						}
			//LOAD PAGE		
				// Back Button
//						if(mx >= 10 && mx <= 310 && buttonClicked == true && buttonClickedLoad == false) 
//						{
//							if(my >= 10 && my <= 60 && buttonClicked == true && buttonClickedLoad == false) 
//							{
//								//Press Help Button	
//								Game.State = Game.STATE.MENU;
//								buttonClicked = false;
//							}
//						}
						
	
	
	
	
	}
	

//	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//	@Override
	//	public void mouseDragged(MouseEvent arg0) {
	//		// TODO Auto-generated method stub
	//		
	//	}
	//
	//	@Override
	//	public void mouseMoved(MouseEvent arg0) {
	//		// TODO Auto-generated method stub
	//		
	//	}

}
