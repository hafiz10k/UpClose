package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import entity.AnimatedSprite;
import entity.Player;
import entity.Rectangle;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class dummyBattle {
	private Player player;
	private BufferedImage bg;

	private Sprite enemyInfo;
	private Sprite charInfo;

	private AnimatedSprite dummy;
	private int speed = 3;

	//enemy
	private String enemyName = "Training Dummy";
	private int enemyHP;
	private int enemyMaxHP;
	//player
	private String playerName;
	private int playerAttack;
	public int achievedEXP;

	public boolean dummyDead = false;
	private boolean playerAttacking = false;
	private boolean enemyAttacking = false;

	private int currentChoice = 0;

	private String options[] = {
			"Attack",
			"Check",
			"Stats"
	};

	private Rectangle keyRect;	
	private Rectangle rect;

	private Font font;
	private Font fontN;
	private Font deadFont;
	private Font fontKey;

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
		//		enemyAttack = 1;

		// load player stats
		player = game.player;

		achievedEXP = 15;

		// dialog
		rect = new Rectangle(40, 550, 300, 30);
		rect.generateGraphics(0xeff0f1);

		keyRect = new Rectangle(0, 650, 400, 400);
		keyRect.generateGraphics(0x000000);

		font = new Font("Calibri", Font.BOLD, 40);
		fontN = new Font("Calibri", Font.BOLD, 30);
		deadFont = new Font("Arial", Font.BOLD, 60);
		fontKey = new Font("Arial", Font.PLAIN, 20);

		sfx = new Audio("/sfx/menu_click.mp3");
	}

	public void playerHitEnemy(int playerATK) {
		if(dummyDead) {
			return;			
		}

		enemyHP -= playerATK;

		if(enemyHP < 0) {
			enemyHP = 0;
		}

		if(enemyHP == 0) {
			dummyDead = true;

		}
	}

	public void enemyHitPlayer(int enemyATK) {
		if(dummyDead) {
			return;
		}
		player.HP -= enemyATK;

		if(player.HP < 0) {
			player.HP = 0;
		}

		if(player.HP == 0) {
			dummyDead = true;

		}
	}

	public void update(Game game) {
		player.exp(game.player.EXP);
		playerAttack = game.player.getAttack();

		try {
			KeyBoardListener keyListener = game.getKeyListener();

			boolean didMove = false;
			if(keyListener.enter()) {
				didMove = true;
				select(game);
				
				sfx.play();
			}

			if(keyListener.left()) {
				didMove = true;
				currentChoice --;
				if(currentChoice == -1)
				{
					currentChoice = options.length -1;
				}
				sfx.play();
			}

			if(keyListener.right()) {
				didMove = true;
				currentChoice ++;
				if(currentChoice == options.length)
				{
					currentChoice = 0;
				}
				sfx.play();
			}
			if(didMove) {
				Thread.sleep(200);
			}

			if(dummyDead == true) {
				playerAttacking = false;
				enemyAttacking = false;

				player.EXP = 0 + achievedEXP;
				System.out.println(player.EXP);

				game.State = STATE.WIN;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void select(Game game) {
		if(currentChoice == 0)
		{
			// attack	
			playerHitEnemy(playerAttack);

			playerAttacking = true;
			enemyAttacking = false;

			TimerTask task = new TimerTask() {
				public void run() {
					enemyAttacking = true;
					playerAttacking = false;
				}

			};
			Timer timer = new Timer();
			timer.schedule(task, 1500);

		}
		if(currentChoice == 1)
		{
			//CHECK

		}
		if(currentChoice == 2)
		{
			// stats
			Game.State = Game.STATE.CINFO;

		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		renderer.renderSprite(dummy, 300, 100, xZoom*2, yZoom*2, true);
		renderer.renderSprite(charInfo, 0, 350, 5, 4, true);
		renderer.renderSprite(enemyInfo, 650, 0, 5, 4, true);
		renderer.renderRectangle(keyRect, xZoom, yZoom, true);
		
		if(currentChoice == 1) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
	}

	public void render(Graphics graphics, Game game) {
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

		if(game.name.fullName != null && !game.name.fullName.isEmpty()) {
			playerName = game.name.getName();
		}
		else {
			playerName = game.load.nameLoad;
		}

		graphics.drawString(playerName, 30, 400);
		graphics.drawString("HP: " + game.player.HP + "/" + game.player.getMaxHP(), 30, 430);
		graphics.drawString("ATK: " + playerAttack, 30, 460);

		if(playerAttacking == true) {
			graphics.drawString("Player attacked!", 100, 280);
		}
		if(enemyAttacking == true) {
			graphics.setColor(Color.RED);
			graphics.drawString("dummy CAN'T attack", 550, 220);
		}

		if(currentChoice == 1) {
			graphics.setColor(Color.BLACK);
			graphics.drawString("This is only a dummy, no ATK points", 70, 580);
		}

		if(dummyDead == true) {
			graphics.setFont(deadFont);
			graphics.setColor(Color.RED);
			graphics.drawString("ENEMY DEAD!", 100, 100);

			graphics.setFont(fontN);
			graphics.setColor(Color.BLACK);
			graphics.drawString("Player gained " + achievedEXP + "exp", 100, 200);
		}

	}

}
