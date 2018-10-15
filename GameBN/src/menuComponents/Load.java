package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entity.AnimatedSprite;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Load implements MenuObject{
	private Color titleColor;
	private Font titleFont;
	public String nameLoad;
	public int genderLoad;
	public String hpLoad;
	public int playerHP;
	public int playerEXP;
	private int playerPosX;
	private int playerPosY;

	private Font font;

	private int loadChoice = 0;

	private String[] load = 
		{
				"Yes",
				"No"
		};
	
	private BufferedImage bg;

	public Load(Game game) {
		try 
		{
			bg = game.loadImage("/name_bg.png");
			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 50);

			font = new Font("Arial", Font.PLAIN, 40);
			
			try {
				BufferedReader br = new BufferedReader(new FileReader("Details.txt"));
				nameLoad = br.readLine();
				genderLoad = Integer.parseInt(br.readLine());
				playerPosX = Integer.parseInt(br.readLine());
				playerPosY = Integer.parseInt(br.readLine());
				playerHP = Integer.parseInt(br.readLine());
				playerEXP = Integer.parseInt(br.readLine());
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getNameLoad() {
		return nameLoad;
	}

	public void setNameLoad(String nameLoad) {
		this.nameLoad = nameLoad;
	}

	public int getGenderLoad() {
		return genderLoad;
	}

	public void setGenderLoad(int genderLoad) {
		this.genderLoad = genderLoad;
	}

	public String getHpLoad() {
		return hpLoad;
	}

	public void setHpLoad(String hpLoad) {
		this.hpLoad = hpLoad;
	}
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
	}

	@Override
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;

		graphics.setFont(titleFont);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Load Game?", 320, 150);

		graphics.setFont(font);

		for(int i = 0; i < load.length; i++) 
		{
			if (i == loadChoice)
			{
				graphics.setColor(Color.RED);
			}
			else 
			{
				graphics.setColor(Color.BLACK);
			}
			graphics.drawString(load[i], 320 + i * 250, 350);
		}
	}

	@Override
	public void update(Game game) {
		try {
			try {
				loadData();
				
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			KeyBoardListener keyListener = game.getKeyListener();

			boolean loading = false;

			if(keyListener.enter()) {
				loading = true;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void select(Game game) {

		if(loadChoice == 0) {
			System.out.println(nameLoad);
			System.out.println(genderLoad);
			if(genderLoad == 2) {
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
