package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Gender implements MenuObject{
	private AnimatedSprite boy;
	private AnimatedSprite girl;
	
	private int speed = 10;
	
	private BufferedImage bg;
	
	private BufferedImage boyRect;
	private BufferedImage girlRect;
	
	private String[] key = 
		{
				"↑ - Up",
				"↓ - Down",
				"← - Left",
				"→ - Right",
				"[ENTER] - Select"
		};
	
	private Color titleColor;
	private Font titleFont;
	private Font font;

	public int loadChoice;

	private String[] gen = 
		{
				"Boy",
				"Girl"
		};

	public Gender(Game game) {

		try {
			
			//male
			BufferedImage playerSheetImage = game.loadImage("/boy-main.png");
			SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
			boySheet.loadSprites(24, 32);

			boy = new AnimatedSprite(boySheet, speed);

			// female
			BufferedImage girlSheetImage = game.loadImage("/girl-main.png");
			SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
			girlSheet.loadSprites(24, 32);

			girl = new AnimatedSprite(girlSheet, speed);

			boyRect = game.loadImage("/gender-boy-rect.png");
			
			girlRect = game.loadImage("/gender-girl-rect.png");
			
			bg = game.loadImage("/gender_bg.png");
			
			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 50);

			font = new Font("Arial", Font.BOLD, 35);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;

		graphics.setColor(Color.BLACK);
		graphics.setFont(titleFont);
		graphics.drawString("Are you a Boy or Girl?", 200, 150);

		graphics.setFont(font);

		for(int i = 0; i < gen.length; i++) 
		{
			if (i == loadChoice)
			{
				graphics.setColor(Color.YELLOW);
			}
			else 
			{
				graphics.setColor(Color.WHITE);
			}
			graphics.drawString(gen[i], 300 + i * 310, 350);

		}

	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		
		renderer.renderImage(boyRect, -20, 230, xZoom*3, yZoom*3, true);
		renderer.renderImage(girlRect, 590, 230, xZoom*3, yZoom*3, true);
		
		renderer.renderSprite(boy, 100, 300, xZoom*2, yZoom*2, true);
		renderer.renderSprite(girl, 700, 300, xZoom*2, yZoom*2, true);
		
		
		
	}

	@Override
	public void update(Game game) {
		try {
			KeyBoardListener keyListener = game.getKeyListener();

			boolean loading = false;

			if(keyListener.left()) {
				loading = true;
				loadChoice --;
				if(loadChoice == -1)
				{
					loadChoice = gen.length - 1;
				}
			}

			if(keyListener.right()) {
				loading = true;
				loadChoice ++;
				if(loadChoice == gen.length)
				{
					loadChoice = 0;
				}

			}
			if(keyListener.enter()) {
				loading = true;
				if(loadChoice == 0) {
					//male
					BufferedImage playerSheetImage = game.loadImage("/mainAnimated.png");
					SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
					boySheet.loadSprites(24, 32);

					AnimatedSprite boyAni = new AnimatedSprite(boySheet, 10);

					game.player.changeSprite(boyAni);
					
					Game.State = STATE.SCENE03;
				} 
				
				else {
					// female
					BufferedImage girlSheetImage = game.loadImage("/girl-main-anim.png");
					SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
					girlSheet.loadSprites(24, 32);

					AnimatedSprite girlAni = new AnimatedSprite(girlSheet, 10);
					game.player.changeSprite(girlAni);
					
					Game.State = STATE.SCENE04;
				}

			}
			
			if(loading) {
				Thread.sleep(150);
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	
	public void setLoadChoice(int loadChoice) {
		this.loadChoice = loadChoice;
	}
	
	public int getLoadChoice() {

		return loadChoice;
	}

}

