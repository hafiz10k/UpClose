package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class dummyBattle {

	private AnimatedSprite dummy;
	private int speed = 3;

	//enemy
	private String enemyName = "Training Dummy";
	private int enemyHP;
	private int enemyMaxHP;
	private boolean dead = false;
	
	//player
	private int playerenemyHP;
	private int playerMaxenemyHP;
	private int playerAttack;
	
	private boolean playerTurn = false;

	private int currentChoice = 0;

	private String options[] = {
			"Attack",
			"Taunt",
			"Stats"
	};

	private String pgb[] = {
			""	
	};

	private String key = "[Enter] to select";
	private Rectangle keyRect;

	private Font font;
	private Font deadFont;
	private Font fontKey;

	public dummyBattle(Game game) {
		BufferedImage dummyImage = game.loadImage("/dummyBattle.png");
		SpriteSheet dummySheet = new SpriteSheet(dummyImage);
		dummySheet.loadSprites(64, 64);

		dummy = new AnimatedSprite(dummySheet, speed);

		// dummy stats
		enemyHP = enemyMaxHP = 20;

		// load player stats
		playerenemyHP = game.player.getHP();
		playerAttack = game.player.getAttack();

		keyRect = new Rectangle(0, 720, 400, 20);
		keyRect.generateGraphics(0xebebeb);

		font = new Font("Calibri", Font.BOLD, 40);
		deadFont = new Font("Arial", Font.BOLD, 60);
		fontKey = new Font("Arial", Font.PLAIN, 20);
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
		renderer.renderSprite(dummy, 300, 100, xZoom*2, yZoom*2, true);
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
			graphics.drawString(options[i], 150 + i * 300 , 650);
		}
		
		graphics.setColor(Color.WHITE);
		graphics.drawString(enemyName, 0, 30);
		graphics.drawString(enemyHP + "/" + enemyMaxHP, 0, 60);
		
		if(dead == true) {
			graphics.setFont(deadFont);
			graphics.setColor(Color.RED);
			graphics.drawString("ENEMY DEAD!", 100, 100);
		}

		graphics.setFont(fontKey);
		graphics.setColor(Color.BLACK);
		graphics.drawString(key, 400, 740);
	}

	public boolean isDead() {
		return dead;
	}

}
