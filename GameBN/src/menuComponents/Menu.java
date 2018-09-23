package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import handler.Audio;
import handler.Background;
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

	private Game game;

	private KeyBoardListener keyListener = new KeyBoardListener(game);

	private Color titleColor;
	private Font titleFont;

	private Font font;

	private BufferedImage logo;
	private Background bg;

	private Audio aud;


	public Menu(Game game) {		


		try {

			titleColor = new Color(246, 246, 246);
			titleFont = new Font("Broadway", Font.BOLD, 100);

			font = new Font("Caviar Dreams", Font.PLAIN, 50);

			//load bg
			logo = game.loadImage("/title_logo_nobg.png");


			bg = new Background("/bg.png", 1);
			bg.setVector(-1, 0);

			aud = new Audio("/army-forTitlePage.mp3.mp3");
			aud.play();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}


	public void render(RenderHandler renderer, Graphics graphics, Game game, int xZoom, int yZoom) {

//		renderer.renderImage(logo, 230, 10, xZoom, yZoom, true);
		bg.render(game, graphics);
		bg.update();


		// draw title
		//		graphics.setColor(titleColor);
		//		graphics.setFont(titleFont);
		//		graphics.drawString("UpClose", 275, 120);
		
		graphics.drawImage(logo, (logo.getWidth()/2) % (game.getWidth()/2), 10, game);
//		System.out.println((logo.getWidth()/2) % (game.getWidth()/2));


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
			graphics.drawString(options[i], 400 , 270 + i * 130);
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

	public Audio getAud() {
		return aud;
	}
	
	public Background getBg() {
		return bg;
	}


	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub

	}
}