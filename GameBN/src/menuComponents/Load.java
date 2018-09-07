package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.Game;
import handler.KeyBoardListener;

public class Load implements MenuObject{
	public Rectangle backButton =  new Rectangle (10, 10, 200, 50);
	
	private Color titleColor;
	private Font titleFont;

	private Font font;
	
	private int backChoice = 0;
	
	private String[] back = 
		{
				"Back"
		};

	
	
	public Load() {
		try 
		{
			
			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 80);
			
			font = new Font("Arial", Font.PLAIN, 30);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(titleFont);
		graphics.drawString("Load", 400, 120);
		
		graphics.setFont(font);
		graphics.drawString("load game?", 420, 270);
		
		for(int i = 0; i < back.length; i++) 
		{
			if (i == backChoice)
			{
				graphics.setColor(Color.RED);
			}
			else 
			{
				graphics.setColor(Color.WHITE);
			}
			graphics.drawString(back[i], 50, 70);
		}
	}

	@Override
	public void update(Game game) {
		try {
			KeyBoardListener keyListener = game.getKeyListener();

			boolean back = false;
			if(keyListener.enter()) {
				back = true;
				select();

			}

			Thread.sleep(130);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void select() {
		if(backChoice == 0)
		{
			Game.State = Game.STATE.MENU;

		}

	}
}
