package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.TimeUnit;

import game.Game;
import handler.KeyBoardListener;

public class Menu {

	private int currentChoice = 0;
	private String[] options = 
		{
				"Play",
				"Load",
				"Help",
				"Quit"
		};
	
	private Game game;
	
	private KeyBoardListener keyListener = new KeyBoardListener(game);
	
	private Color titleColor;
	private Font titleFont;

	private Font font;
	
	public Menu() {		

		try 
		{
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Arial", Font.PLAIN, 100);
			
			font = new Font("Arial", Font.BOLD, 50);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}


	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		
		// draw title
		graphics.setColor(titleColor);
		graphics.setFont(titleFont);
		graphics.drawString("UpClose", 325, 120);
		
		//draw menu options
		graphics.setFont(font);
		for(int i = 0; i < options.length; i++) 
		{
			if (i == currentChoice)
			{
				graphics.setColor(Color.YELLOW);
			}
			else 
			{
				graphics.setColor(Color.RED);
			}
			graphics.drawString(options[i], 425, 250 + i * 100);
		}

	}
	
	public void update(Game game) {
		
		try {
		KeyBoardListener keyListener = game.getKeyListener();
		
		boolean didMove = false;
		if(keyListener.enter()) {
			didMove = true;
			select();
			
		}
		
		if(keyListener.up()) {
			didMove = true;
			currentChoice --;
			if(currentChoice == -1)
			{
				currentChoice = options.length -1;
			}
		}
		
		if(keyListener.down()) {
			didMove = true;
			currentChoice ++;
			if(currentChoice == options.length)
			{
				currentChoice = 0;
			}
		}
		
		Thread.sleep(110);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void select()
	{
		if(currentChoice == 0)
		{
			// start
			Game.State = Game.STATE.GAME;	
//			Game.State = Game.STATE.NAME;
			
		}
		if(currentChoice == 1)
		{
			// load
			Game.State = Game.STATE.LOAD;
			
		}
		if(currentChoice == 2)
		{
			// help
			Game.State = Game.STATE.HELP;
		}
		if(currentChoice == 3)
		{
			// quit
			System.exit(0);
		}

}
	
	public KeyBoardListener getKeyListener() {
		return keyListener;
	}
}