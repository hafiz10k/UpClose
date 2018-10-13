package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import entity.AnimatedSprite;
import entity.Player;
import entity.Rectangle;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class FranciscoBattle {
	private BufferedImage bg;
	private Sprite enemyInfo;
	private Sprite charInfo;

	private AnimatedSprite dfds;
	private int speed = 3;
	private int dfDir = 0;

	private int currentChoice = 0;

	private String options[] = {
			"Attack",
			"Check",
			"Stats"
	};

	// enemy stats
	private String enemyName = "Dr. Francisco De Sande";
	private int enemyHP;
	private int enemyMaxHP;
	private int enemyAttack;
	private boolean dead = false;

	//player
	private String playerName;
	private int playerAttack;
	private int playerEXP;

	private Player player;

	private Font font;
	private Font fontN;
	private Font deadFont;
	private Font fontKey;
	
	private Rectangle timerRect;
	private Rectangle keyRect;

	public FranciscoBattle(Game game) {
		
		bg = game.loadImage("/franciscoBattle.png");

		BufferedImage dfdsImage = game.loadImage("/dfds-ani.png");
		SpriteSheet dfdsSheet = new SpriteSheet(dfdsImage);
		dfdsSheet.loadSprites(24, 40);

		dfds = new AnimatedSprite(dfdsSheet, speed);
		
		BufferedImage enemyInfoImg = game.loadImage("/enemy_info.png");
		SpriteSheet enemyInfoSheet = new SpriteSheet(enemyInfoImg);
		enemyInfoSheet.loadSprites(64, 40);

		enemyInfo = enemyInfoSheet.getSprite(0, 0);

		BufferedImage charInfoimg = game.loadImage("/char_info.png");
		SpriteSheet charInfoSheet = new SpriteSheet(charInfoimg);
		charInfoSheet.loadSprites(64, 40);

		charInfo = charInfoSheet.getSprite(0, 0);

		//enemy stats
		player = game.player;
		playerName = game.name.getName();

		game.player.exp(50);
		playerAttack = game.player.getAttack();

		enemyAttack = 25;
		enemyHP = enemyMaxHP = 100;

		keyRect = new Rectangle(0, 650, 400, 400);
		keyRect.generateGraphics(0x000000);

		font = new Font("Calibri", Font.BOLD, 40);
		fontN = new Font("Calibri", Font.BOLD, 30);
		deadFont = new Font("Arial", Font.BOLD, 60);
		fontKey = new Font("Arial", Font.PLAIN, 20);

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

			// df MOVEMENT
			if(dfds != null) {
				int newDirection = dfDir;

				if(timerRect.x >= 0) {
					didMove = true;

					if(!didMove) {
						dfds.reset();
					}

					if(didMove) {
						dfds.incSprite();

					}

					if(newDirection != dfDir) {
						dfDir = newDirection;
						dfds.setAnimationRange(dfDir * 4, (dfDir * 4) + 4);
					}

				}
			}
			Thread.sleep(150);

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

			TimerTask task = new TimerTask() {
				public void run() {
					System.out.println(enemyAttack + ", " + "player: " + player.HP);
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 1500);
		}
		if(currentChoice == 1)
		{
			// check
			Game.State = Game.STATE.GAME;

		}
		if(currentChoice == 2)
		{
			// stats
			Game.State = Game.STATE.CINFO;

		}
	}


	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		renderer.renderSprite(dfds, 400, 100, xZoom*3, yZoom*3, true);
		renderer.renderSprite(charInfo, 0, 350, 5, 4, true);
		renderer.renderSprite(enemyInfo, 610, 0, 6, 4, true);
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
		graphics.drawString(enemyName, 650, 50);
		graphics.drawString("HP: " + enemyHP + "/" + enemyMaxHP, 650, 80);
		graphics.drawString("ATK: " + enemyAttack, 650, 110);

		graphics.drawString(playerName, 30, 400);
		graphics.drawString("HP: " + player.HP, 30, 430);
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

