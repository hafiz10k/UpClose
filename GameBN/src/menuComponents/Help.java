package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Help implements MenuObject{

	private Color titleColor;
	private Font titleFont;

	private Font font;

	private int backChoice = 0;

	private String[] back = 
		{
				"Back"
		};
	
	private BufferedImage bg;

	public Help(Game game) {
		try 
		{

			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 80);

			font = new Font("Arial", Font.PLAIN, 30);
			
			bg = game.loadImage("/help.png");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, -20, 2, 2, true);
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.setFont(titleFont);
		graphics.drawString("Help", 400, 140);

		graphics.setFont(font);

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
			graphics.drawString(back[i], 50, 120);
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
			if(back) {
				Thread.sleep(150);
			}

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

