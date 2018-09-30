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

public class FranciscoBattle {
	
	private AnimatedSprite dfds;
	
	private int speed = 3;
	
	private int currentChoice = 0;
	
	private String options[] = {
		"Attack",
		"Taunt",
		"Items",
		"Stats"
	};
	
	private Font font;
	
	public FranciscoBattle(Game game) {
		
		BufferedImage dfdsImage = game.loadImage("/DFDS.png");
		SpriteSheet dfdsSheet = new SpriteSheet(dfdsImage);
		dfdsSheet.loadSprites(24, 40);

		dfds = new AnimatedSprite(dfdsSheet, speed);
				
		font = new Font("Calibri", Font.BOLD, 40);
	}
	
	public void update(Game game) {
		try {

			KeyBoardListener keyListener = game.getKeyListener();

			boolean didMove = false;
//			if(keyListener.enter()) {
//				didMove = true;
//				select();
//
//			}

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
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderSprite(dfds, 300, 100, xZoom*3, yZoom*3, true);
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

