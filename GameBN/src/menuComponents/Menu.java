package menuComponents;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import entity.AnimatedSprite;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Menu extends JFrame implements MenuObject {
	private int currentChoice = 0;
	private String[] options = 
		{
				"New Game",
				"Load",
				"Help",
				"Quit"
		};

	private Game game;

	private KeyBoardListener keyListener = new KeyBoardListener(game);

	private Color titleColor;
	private Font titleFont;

	private Font font;
	
	private BufferedImage logo;
	private BufferedImage grass;
	
	private Image girl;
	
	private AnimatedSprite boyAni;
	private AnimatedSprite girlAni;
	
	private SpriteSheet boySheet;
	private SpriteSheet girlSheet;

	public Menu() {		

		
		try 
		{
			getContentPane().setBackground(Color.CYAN);

			titleColor = new Color(246, 246, 246);
			titleFont = new Font("Broadway", Font.BOLD, 100);

			font = new Font("Caviar Dreams", Font.PLAIN, 50);

			//load bg
			logo = Game.loadImage("/title_logo_nobg.png");
			grass = Game.loadImage("/grass_bg.png");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void render(Graphics graphics) {

		graphics.drawImage(logo, 230, 10, game);
	//	graphics.drawImage(grass, 0, 300, game);
		
		graphics.drawImage(girl, 100, 100, game);

		// draw title
		//		graphics.setColor(titleColor);
		//		graphics.setFont(titleFont);
		//		graphics.drawString("UpClose", 275, 120);


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
			graphics.drawString(options[i], 375 , 270 + i * 130);
		}

	}
	
	

	@Override
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
			//			Game.State = Game.STATE.GAME;	
			Game.State = Game.STATE.NAME;
			//			Game.State = Game.STATE.GENDER;

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