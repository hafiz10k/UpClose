package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Rectangle;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Menu implements MenuObject {
	private int currentChoice = 0;
	private String[] options = 
		{
				"New Game",
				"Load",
				"Help",
				"Quit"
		};

	private String[] key = 
		{
				"↑ - Up",
				"↓ - Down",
				"← - Left",
				"→ - Right",
				"[ENTER] - Select"
		};

	private Game game;

	private KeyBoardListener keyListener = new KeyBoardListener(game);

	private Color titleColor;
	private Font titleFont;

	private Font font;
	private Font fontKey;

	private BufferedImage logo;
	private Sprite logoSp;
	
	private BufferedImage bg;

	private Audio aud;
	private Audio sfx;

	private Rectangle keyRect;

	public Menu(Game game) {		


		try {

			titleColor = new Color(246, 246, 246);
			titleFont = new Font("Broadway", Font.BOLD, 100);

			font = new Font("Caviar Dreams", Font.BOLD, 50);
			fontKey = new Font("Arial", Font.PLAIN, 20);

			//load logo
			logo = game.loadImage("/title_logo.png");
			SpriteSheet logoSheet = new SpriteSheet(logo);
			logoSheet.loadSprites(104, 40);

			logoSp = logoSheet.getSprite(0, 0);

			bg = game.loadImage("/main_menu_bg.png");

			aud = new Audio("/bgm/menu_bgm.mp3");
			aud.play();
			
			sfx = new Audio("/sfx/menu_click.mp3");

			keyRect = new Rectangle(0, 720, 400, 20);
			keyRect.generateGraphics(0xebebeb);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void render(Graphics graphics) {

		//draw menu options
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
			graphics.drawString(options[i], 400 , 300 + i * 130);
		}
		
		for(int k = 0; k < key.length; k++) {
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("" + key[k], 150 + k * 150, 740);
		}
	}


	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		renderer.renderSprite(logoSp, 200, 15, xZoom*2, yZoom*2, true);

		renderer.renderRectangle(keyRect, xZoom, yZoom, true);


	}



	@Override
	public void update(Game game) {

		try {

			KeyBoardListener keyListener = game.getKeyListener();
			

			boolean didMove = false;
			if(keyListener.enter()) {
				didMove = true;
				sfx.play();
				
				select();

			}

			if(keyListener.up()) {
				didMove = true;
				sfx.play();
				
				currentChoice --;
				if(currentChoice == -1)
				{
					currentChoice = options.length -1;
				}
			}

			if(keyListener.down()) {
				didMove = true;
				sfx.play();
				
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

	private void select()
	{
		if(currentChoice == 0)
		{
			// start	
			Game.State = Game.STATE.SCENE01;

		}
		if(currentChoice == 1)
		{
			// load
			Game.State = Game.STATE.LOAD;

		}
		if(currentChoice == 2)
		{
			// help
			Game.State = Game.STATE.LAILARATNA;
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

	public Audio getAud() {
		return aud;
	}



}