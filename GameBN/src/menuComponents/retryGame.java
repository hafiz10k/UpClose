package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entity.AnimatedSprite;
import entity.SpriteSheet;
import game.Game;
import handler.Audio;
import handler.KeyBoardListener;

public class retryGame implements MenuObject{

	private Font titleFont;
	private Color titleColor;

	private Font font;
	public String nameLoad;
	public int genderLoad;
	public String hpLoad;
	public int playerEXP;
	public int playerHP;
	private Audio aud;
	private Audio sfx;

	private int playerPosX;
	private int playerPosY;
	private Font f;

	private int loadChoice = 0;

	private String[] load = 
		{
				"Yes",
				"No"
		};

	public retryGame(Game game) {
		titleFont = new Font("Broadway", Font.BOLD, 80);
		font = new Font("Arial", Font.PLAIN, 40);

		f = new Font("Arial", Font.PLAIN, 50);
		aud = new Audio("/bgm/menu_bgm.mp3");
		aud.play();

		sfx = new Audio("/sfx/menu_click.mp3");
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setFont(titleFont);
		graphics.setColor(Color.RED);
		graphics.drawString("PLAYER IS DEAD :(", 90, 120);

		graphics.setFont(f);
		graphics.setColor(Color.WHITE);
		graphics.drawString("Retry game?", 330, 300);


		graphics.setFont(font);
		for(int i = 0; i < load.length; i++) 
		{
			if (i == loadChoice)
			{
				graphics.setColor(Color.YELLOW);
			}
			else 
			{
				graphics.setColor(Color.WHITE);
			}
			graphics.drawString(load[i], 300 + i * 300, 430);
		}
	}

	public void loadData() throws NumberFormatException, IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Details.txt"));
			nameLoad = br.readLine();
			genderLoad = Integer.parseInt(br.readLine());
			playerPosX = Integer.parseInt(br.readLine());
			playerPosY = Integer.parseInt(br.readLine());
			playerHP = Integer.parseInt(br.readLine());
			playerEXP = Integer.parseInt(br.readLine());
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

	}
	@Override
	public void update(Game game) {
		try {

			KeyBoardListener keyListener = game.getKeyListener();
			try {
				loadData();
			} catch (NumberFormatException | IOException e) {
				
				e.printStackTrace();
			}
			boolean loading = false;

			if(keyListener.enter()) {
				loading = true;
				sfx.play();

				select(game);
			}

			if(keyListener.left()) {
				loading = true;
				loadChoice --;
				if(loadChoice == -1)
				{
					loadChoice = load.length -1;
				}
			}

			if(keyListener.right()) {
				loading = true;
				loadChoice ++;
				if(loadChoice == load.length)
				{
					loadChoice = 0;
				}
			}

			Thread.sleep(150);


		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	private void select(Game game) {

		if(loadChoice == 0) {


			if(game.load.genderLoad == 2) {
				//male
				BufferedImage playerSheetImage = game.loadImage("/mainAnimated.png");
				SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
				boySheet.loadSprites(24, 32);

				AnimatedSprite boyAni = new AnimatedSprite(boySheet, 10);

				game.player.changeSprite(boyAni);
				game.player.playerRectangle.x = playerPosX;
				game.player.playerRectangle.y = playerPosY;
				game.player.HP = playerHP;
				game.player.EXP = playerEXP;


				Game.State = Game.STATE.GAME;
			} 
			else {
				// female
				BufferedImage girlSheetImage = game.loadImage("/girl-main-anim.png");
				SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
				girlSheet.loadSprites(24, 32);

				AnimatedSprite girlAni = new AnimatedSprite(girlSheet, 10);
				game.player.changeSprite(girlAni);
				game.player.playerRectangle.x = playerPosX;
				game.player.playerRectangle.y = playerPosY;
				Game.State = Game.STATE.GAME;
			}


		}

		if(loadChoice == 1) {
			Game.State = Game.STATE.MENU;
		}


	}


}
