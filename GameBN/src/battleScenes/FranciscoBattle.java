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
import game.Game.STATE;
import handler.Audio;
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


	public boolean enemyDead = false;
	public boolean playerDead = false;

	//player
	private String playerName;
	private int playerAttack;
	private int playerEXP;

	private boolean playerAttacking = false;
	private boolean enemyAttacking = false;

	public int achievedEXP;

	private Player player;
	private Game game;

	private Font font;
	private Font fontN;
	private Font deadFont;
	private Font fontKey;

	private Rectangle keyRect;
	private Rectangle timerRect;
	private Rectangle rect;

	private Audio sfx;

	public FranciscoBattle(Game game) {

		this.game = game;

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

		enemyAttack = 25;
		enemyHP = enemyMaxHP = 100;

		achievedEXP = 50;

		keyRect = new Rectangle(0, 650, 400, 400);
		keyRect.generateGraphics(0x000000);

		font = new Font("Calibri", Font.BOLD, 40);
		fontN = new Font("Calibri", Font.BOLD, 30);
		deadFont = new Font("Arial", Font.BOLD, 60);
		fontKey = new Font("Arial", Font.PLAIN, 20);

		// dialog
		rect = new Rectangle(40, 550, 300, 30);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);

		sfx = new Audio("/sfx/menu_click.mp3");
	}

	public void playerHitEnemy(int playerATK) {
		if(enemyDead) {
			return;			
		}

		enemyHP -= playerATK;

		if(enemyHP < 0) {
			enemyHP = 0;
		}

		if(enemyHP == 0) {
			enemyDead = true;

		}
	}

	public void enemyHitPlayer(int enemyATK) {
		if(playerDead) {
			return;
		}
		player.HP -= enemyATK;

		if(player.HP < 0) {
			player.HP = 0;
		}

		if(player.HP == 0) {
			playerDead = true;

		}
	}
	public void update(Game game) {
		timerRect.x++;

		player.exp(game.player.EXP);
		playerAttack = game.player.getAttack();

		try {

			KeyBoardListener keyListener = game.getKeyListener();

			boolean didMove = false;
			if(keyListener.enter()) {
				didMove = true;
				select();
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
			
			if(enemyDead == true) {
				playerAttacking = false;
				enemyAttacking = false;

				player.EXP += achievedEXP;
				System.out.println(player.EXP);

				if(player.EXP >= 100) {
					player.EXP = 100;
				}

				game.State = STATE.WIN;
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
			// check

		}
		if(currentChoice == 2)
		{
			// stats
			Game.State = Game.STATE.CINFOFRANCISCO;

		}
	}


	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		renderer.renderSprite(dfds, 400, 100, xZoom*3, yZoom*3, true);
		renderer.renderSprite(charInfo, 0, 350, 5, 4, true);
		renderer.renderSprite(enemyInfo, 610, 0, 6, 4, true);
		renderer.renderRectangle(keyRect, xZoom, yZoom, true);

		if(currentChoice == 1) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
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


		if(game.name.fullName != null && !game.name.fullName.isEmpty()) {
			playerName = game.name.getName();
		}
		else {
			playerName = game.load.nameLoad;
		}

		graphics.drawString(playerName, 30, 400);
		graphics.drawString("HP: " + player.HP, 30, 430);
		graphics.drawString("ATK: " + playerAttack, 30, 460);

		if(playerAttacking == true) {
			graphics.drawString("Player attacked!", 100, 280);
		}
		if(enemyAttacking == true) {
			graphics.setColor(Color.BLACK);
			graphics.drawString("enemy attacked!", 600, 220);
		}

		if(currentChoice == 1) {
			graphics.setColor(Color.BLACK);
			graphics.drawString("Dr. Francisco De Sande, leader of Spain Attackers.", 70, 580);
		}

		if(enemyDead == true) {
			graphics.setFont(deadFont);
			graphics.setColor(Color.RED);
			graphics.drawString("ENEMY DEAD!", 100, 100);

			graphics.setFont(fontN);
			graphics.setColor(Color.BLACK);
			graphics.drawString("Player gained " + playerEXP, 100, 100);
		}
	}

}

