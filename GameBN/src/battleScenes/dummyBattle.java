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
import game.Game.STATE;
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class dummyBattle {
	
	private BufferedImage bg;
	
	private Sprite enemyInfo;
	private Sprite charInfo;

	private AnimatedSprite dummy;
	private int speed = 3;

	//enemy
	private String enemyName = "Training Dummy";
	private int enemyHP;
	private int enemyMaxHP;
	private boolean dead = false;
	
	//player
	private String playerName;
	private String playerHP;
	private int playerAttack;
	private int playerEXP;
	
	private boolean playerTurn = false;

	private int currentChoice = 0;

	private String options[] = {
			"Attack",
			"Check",
			"Stats"
	};

	private String pgb[] = {
			""	
	};

	private String key = "[Enter] to select";
	private Rectangle keyRect;

	private Font font;
	private Font fontN;
	private Font deadFont;
	private Font fontKey;
	
	private Audio bgm;
	private Audio sfx;

	public dummyBattle(Game game) {
		BufferedImage dummyImage = game.loadImage("/dummyBattle.png");
		SpriteSheet dummySheet = new SpriteSheet(dummyImage);
		dummySheet.loadSprites(64, 64);

		dummy = new AnimatedSprite(dummySheet, speed);
		
		bg = game.loadImage("/dummy_bg.png");
		
		BufferedImage enemyInfoImg = game.loadImage("/enemy_info.png");
		SpriteSheet enemyInfoSheet = new SpriteSheet(enemyInfoImg);
		enemyInfoSheet.loadSprites(64, 40);

		enemyInfo = enemyInfoSheet.getSprite(0, 0);
		
		BufferedImage charInfoimg = game.loadImage("/char_info.png");
		SpriteSheet charInfoSheet = new SpriteSheet(charInfoimg);
		charInfoSheet.loadSprites(64, 40);

		charInfo = charInfoSheet.getSprite(0, 0);

		// dummy stats
		enemyHP = enemyMaxHP = 20;

		// load player stats
		playerName = game.name.getName();
		playerHP = "HP: " + game.player.getHP() + "/" + game.player.getMaxHP();
		playerEXP = game.player.getExp();
		
		game.player.level(0);
		playerAttack = game.player.getAttack();

		keyRect = new Rectangle(0, 650, 400, 400);
		keyRect.generateGraphics(0x000000);

		font = new Font("Calibri", Font.BOLD, 40);
		fontN = new Font("Calibri", Font.BOLD, 30);
		deadFont = new Font("Arial", Font.BOLD, 60);
		fontKey = new Font("Arial", Font.PLAIN, 20);
		
		bgm = new Audio ("/bgm/battle_bgm.mp3");
		bgm.play();
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
		
		try {
			KeyBoardListener keyListener = game.getKeyListener();

			boolean didMove = false;
			if(keyListener.enter()) {
				didMove = true;
				select(game);
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
				Thread.sleep(200);
			}
			
			if(dead == true) {
				int achievedEXP = playerEXP + 15;
				System.out.println(achievedEXP);
				if(keyListener.enter()) {
					game.State = STATE.GAME;
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void select(Game game) {
		if(currentChoice == 0)
		{
			this.hit(playerAttack);
			System.out.println(playerAttack + ", " + "enemy: " + enemyHP);

		}
		if(currentChoice == 1)
		{
			// taunt
			Game.State = Game.STATE.LOAD;

		}
		if(currentChoice == 2)
		{
			// stats
			Game.State = Game.STATE.DUMMY;

		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		renderer.renderSprite(dummy, 300, 100, xZoom*2, yZoom*2, true);
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
		graphics.drawString(enemyName, 680, 50);
		graphics.drawString("HP: " + enemyHP + "/" + enemyMaxHP, 680, 80);
		graphics.drawString("ATK: " + "0", 680, 110);
		
		graphics.drawString(playerName, 30, 400);
		graphics.drawString(playerHP, 30, 430);
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

	public boolean isDead() {
		return dead;
	}

	public Audio getBgm() {
		return bgm;
	}

}
