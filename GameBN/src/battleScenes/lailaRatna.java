package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class lailaRatna {
	
	private AnimatedSprite laila;
	private AnimatedSprite ratna;
	
	private int speed = 3;
	
	private int currentChoice = 0;
	
	private String options[] = {
		"Attack",
		"Taunt",
		"Stats"
	};
	
	private Font font;
	
	public lailaRatna(Game game) {
		
		BufferedImage lailaImage = game.loadImage("/laila.png");
		SpriteSheet lailaSheet = new SpriteSheet(lailaImage);
		lailaSheet.loadSprites(24, 40);

		laila = new AnimatedSprite(lailaSheet, speed);
		
		BufferedImage ratnaImage = game.loadImage("/ratna.png");
		SpriteSheet ratnaSheet = new SpriteSheet(ratnaImage);
		ratnaSheet.loadSprites(24, 40);

		ratna = new AnimatedSprite(ratnaSheet, speed);
		
		font = new Font("Calibri", Font.BOLD, 40);
	}
	
	public void update(Game game) {
		try {

			KeyBoardListener keyListener = game.getKeyListener();

			boolean didMove = false;
			if(keyListener.enter()) {
				didMove = true;
				select();

			}

			if(keyListener.left()) {
				didMove = true;
				currentChoice --;
				if(currentChoice == -1)
				{
					currentChoice = options.length -1;
				}
			}

			if(keyListener.right()) {
				didMove = true;
				currentChoice ++;
				if(currentChoice == options.length)
				{
					currentChoice = 0;
				}
			}
			if(didMove) {
				Thread.sleep(150);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void select() {
		if(currentChoice == 0)
		{
			// attack	
			Game.State = Game.STATE.GAME;

		}
		if(currentChoice == 1)
		{
			// taunt
			Game.State = Game.STATE.LOAD;

		}
		if(currentChoice == 2)
		{
			// stats
			Game.State = Game.STATE.MENU;

		}
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderSprite(ratna, 450, 100, xZoom*3, yZoom*3, true);
		renderer.renderSprite(laila, 250, 100, xZoom*3, yZoom*3, true);
	}
	
	public void render(Graphics graphics) {
		graphics.setFont(font);
		for(int i = 0; i < options.length; i++) 
		{
			if (i == currentChoice)
			{
				graphics.setColor(Color.RED);
			}
			else 
			{
				graphics.setColor(Color.YELLOW);
			}
			graphics.drawString(options[i], 150 + i * 200 , 650);
		}
	}

}

