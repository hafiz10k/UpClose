package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class lailaRatna {

	private AnimatedSprite laila;
	private AnimatedSprite ratna;

	private int speed = 3;
	private int lailaDir = 0;
	private int ratnaDir = 0;

	private int currentChoice = 0;

	private String options[] = {
			"Attack",
			"Taunt",
			"Stats"
	};
	
	//laila stats
	private String lailaName = "Seri Laila";
	
	//ratna stats
	private String ratnaName = "Seri Ratna";
	
	// both enemy stats
	private int enemyHP;
	private int enemyMaxHP;
	private int enemyAttack;
	private boolean dead = false;
	
	private Font font;
	private Rectangle timerRect;
	
	public lailaRatna(Game game) {

		BufferedImage lailaImage = game.loadImage("/laila-ani.png");
		SpriteSheet lailaSheet = new SpriteSheet(lailaImage);
		lailaSheet.loadSprites(32, 40);

		laila = new AnimatedSprite(lailaSheet, speed);

		BufferedImage ratnaImage = game.loadImage("/ratna-ani.png");
		SpriteSheet ratnaSheet = new SpriteSheet(ratnaImage);
		ratnaSheet.loadSprites(32, 40);

		ratna = new AnimatedSprite(ratnaSheet, speed);
		
		//enemy stats
		enemyHP = enemyMaxHP = 60;

		font = new Font("Calibri", Font.BOLD, 40);
		
		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
	}
	
	public void hit(int damage) {
		if(dead) {
			return;
		}
		enemyHP -= damage;

		if(enemyHP < 0) {
			enemyHP = 0;
		}

		if(enemyHP == 0) {
			dead = true;
		}
	}

	public void update(Game game) {
		timerRect.x++;
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

			if(laila != null) {
				int newDirection = lailaDir;

				if(timerRect.x >= 0) {
					didMove = true;

					if(!didMove) {
						laila.reset();
					}

					if(didMove) {
						laila.incSprite();

					}

					if(newDirection != lailaDir) {
						lailaDir = newDirection;
						laila.setAnimationRange(lailaDir * 4, (lailaDir * 4) + 4);
					}

				}
			}
			
			if(ratna != null) {
				int newDirection = ratnaDir;

				if(timerRect.x >= 0) {
					didMove = true;

					if(!didMove) {
						ratna.reset();
					}

					if(didMove) {
						ratna.incSprite();

					}

					if(newDirection != ratnaDir) {
						ratnaDir = newDirection;
						ratna.setAnimationRange(ratnaDir * 4, (ratnaDir * 4) + 4);
					}

				}
			}
			
			Thread.sleep(200);

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
		renderer.renderSprite(ratna, 500, 100, xZoom*3, yZoom*3, true);
		renderer.renderSprite(laila, 175, 100, xZoom*3, yZoom*3, true);
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
			graphics.drawString(options[i], 150 + i * 300 , 650);
		}
	}

}

