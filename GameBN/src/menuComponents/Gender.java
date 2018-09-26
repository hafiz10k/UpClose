package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import entity.AnimatedSprite;
import entity.Player;
import entity.SpriteSheet;
import handler.KeyBoardListener;

public class Gender implements MenuObject{
	public Rectangle backButton =  new Rectangle (10, 10, 200, 50);

	private Color titleColor;
	private Font titleFont;
	
	
	private Font font;

	private int loadChoice = 0;

	private String[] gen = 
		{
				"Boy",
				"Girl"
		};
	



	@Override
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;

		graphics.setColor(Color.WHITE);
		graphics.setFont(titleFont);
		graphics.drawString("Are you a Boy or Girl?", 200, 120);

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
			graphics.drawString(gen[i], 350 + i * 250, 350);
			
		}
		
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
					loadChoice = gen.length -1;
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
					BufferedImage playerSheetImage = Game.loadImage("/mainAnimated.png");
					SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
					boySheet.loadSprites(24, 32);

					AnimatedSprite boyAni = new AnimatedSprite(boySheet, 10);
					
					game.player.changeSprite(boyAni);
				} else {
					// female
					BufferedImage girlSheetImage = Game.loadImage("/girl-main-anim.png");
					SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
					girlSheet.loadSprites(24, 32);
					
					AnimatedSprite girlAni = new AnimatedSprite(girlSheet, 10);
					game.player.changeSprite(girlAni);
				}
//				System.out.println(gen[loadChoice]);
				
				Game.State = Game.STATE.SAVE;

			}
			Thread.sleep(150);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	public Gender() {
		
		try 
		{
			
			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 50);

			font = new Font("Arial", Font.PLAIN, 30);

		}
		catch (Exception e)
		{
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

