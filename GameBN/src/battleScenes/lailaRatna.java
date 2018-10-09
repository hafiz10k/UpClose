package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Rectangle;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class lailaRatna {
	private BufferedImage bg;
	private Sprite enemyInfo;
	private Sprite charInfo;

	private AnimatedSprite laila;
	private AnimatedSprite ratna;

	private int speed = 3;
	private int lailaDir = 0;
	private int ratnaDir = 0;

	private int currentChoice = 0;

	private String options[] = {
			"Attack",
			"Check",
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

	//player
	private String playerName;
	private int playerHP;
	private int playerAttack;
	private int playerEXP;

	private Font font;
	private Font fontKey;
	private Font fontN;
	private Font deadFont;

	private Rectangle timerRect;
	private Rectangle keyRect;

	public lailaRatna(Game game) {
		
		bg = game.loadImage("/LRBattle.png");

		BufferedImage lailaImage = game.loadImage("/laila-ani.png");
		SpriteSheet lailaSheet = new SpriteSheet(lailaImage);
		lailaSheet.loadSprites(32, 40);

		laila = new AnimatedSprite(lailaSheet, speed);

		BufferedImage ratnaImage = game.loadImage("/ratna-ani.png");
		SpriteSheet ratnaSheet = new SpriteSheet(ratnaImage);
		ratnaSheet.loadSprites(32, 40);

		ratna = new AnimatedSprite(ratnaSheet, speed);

		BufferedImage enemyInfoImg = game.loadImage("/enemy_info.png");
		SpriteSheet enemyInfoSheet = new SpriteSheet(enemyInfoImg);
		enemyInfoSheet.loadSprites(64, 40);

		enemyInfo = enemyInfoSheet.getSprite(0, 0);

		BufferedImage charInfoimg = game.loadImage("/char_info.png");
		SpriteSheet charInfoSheet = new SpriteSheet(charInfoimg);
		charInfoSheet.loadSprites(64, 40);

		charInfo = charInfoSheet.getSprite(0, 0);

		//enemy stats
		playerName = game.name.getName();
		playerHP = game.player.getHP();
		game.player.level(15);
		playerAttack = game.player.getAttack();

		enemyAttack = 10;
		enemyHP = enemyMaxHP = 60;

		font = new Font("Calibri", Font.BOLD, 40);
		fontN = new Font("Calibri", Font.BOLD, 30);
		deadFont = new Font("Arial", Font.BOLD, 60);
		fontKey = new Font("Arial", Font.PLAIN, 20);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
		
		keyRect = new Rectangle(0, 650, 400, 400);
		keyRect.generateGraphics(0x000000);
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

	public void hitEnemy(int enemyAttack) {
		if(dead) {
			return;
		}
		playerHP -= enemyAttack;

		if(playerHP < 0) {
			playerHP = 0;
		}

		if(playerHP == 0) {
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
			this.hit(playerAttack);
			System.out.println(playerAttack + ", " + "enemy: " + enemyHP);
			this.hitEnemy(enemyAttack);
			System.out.println(enemyAttack + ", " + "player: " + playerHP);

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
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		renderer.renderSprite(ratna, 500, 100, xZoom*3, yZoom*3, true);
		renderer.renderSprite(laila, 175, 100, xZoom*3, yZoom*3, true);
		renderer.renderSprite(charInfo, 0, 350, 5, 4, true);
		renderer.renderSprite(enemyInfo, 650, 0, 5, 4, true);
		renderer.renderRectangle(keyRect, xZoom, yZoom, true);
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
			graphics.drawString(options[i], 150 + i * 300 , 700);
		}

		graphics.setFont(fontN);
		graphics.setColor(Color.WHITE);
		graphics.drawString(lailaName, 680, 50);
		graphics.drawString("HP: " + enemyHP + "/" + enemyMaxHP, 680, 80);
		graphics.drawString("ATK: " + "0", 680, 110);

		graphics.drawString(playerName, 30, 400);
		graphics.drawString("" + playerHP, 30, 430);
		graphics.drawString("ATK: " + playerAttack, 30, 460);

		if(dead == true) {
			graphics.setFont(deadFont);
			graphics.setColor(Color.RED);
			graphics.drawString("ENEMY DEAD!", 100, 100);

			graphics.setFont(fontN);
			graphics.setColor(Color.BLACK);
			graphics.drawString("Player gained " + playerEXP, 100, 100);
		}
	}

}

