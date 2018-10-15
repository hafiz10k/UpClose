package handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.Game;

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
//		if(event.getButton() == MouseEvent.BUTTON1) {
//			//button1 = left click
//			game.leftClick(event.getX(), event.getY());
//		}
//		if(event.getButton() == MouseEvent.BUTTON3) {
//			//button3= right click
//			game.rightClick(event.getX(), event.getY());
//		}		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}	

}
