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

public class lailaRatna {
	private Player player;
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

	//player
	private String playerName;
	private int playerAttack;
	private int playerEXP;
	public int achievedEXP;

	private boolean enemyDead = false;
	private boolean playerDead = false;
	private boolean playerAttacking = false;
	private boolean enemyAttacking = false;

	private Font font;
	private Font fontKey;
	private Font fontN;
	private Font deadFont;

	private Rectangle timerRect;
	private Rectangle keyRect;

	private Audio sfx;

	public lailaRatna(Game game) {
		player = game.player;
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

		// dummy stats
		enemyHP = enemyMaxHP = 100;

		// load player stats
		player = game.player;

		if(game.name.fullName != null && !game.name.fullName.isEmpty()) {
			playerName = game.name.getName();
		}
		else {
			playerName = game.load.nameLoad;
		}

		
		game.player.exp(15);
		playerAttack = game.player.getAttack();
		
		//enemy stats
		enemyAttack = 15;
		enemyHP = enemyMaxHP = 60;


		achievedEXP = 35;


		font = new Font("Calibri", Font.BOLD, 40);
		fontN = new Font("Calibri", Font.BOLD, 30);
		deadFont = new Font("Arial", Font.BOLD, 60);
		fontKey = new Font("Arial", Font.PLAIN, 20);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);

		keyRect = new Rectangle(0, 650, 400, 400);
		keyRect.generateGraphics(0x000000);

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
	
	public static int enemyATKRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
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
			
			if(enemyDead == true) {
				playerAttacking = false;
				enemyAttacking = false;
				
				player.EXP += achievedEXP;
				System.out.println(player.EXP);
				
				if(player.EXP >= 50) {
					player.EXP = 50;
				}

				if(keyListener.a()) {
					game.State = STATE.GAME;
				}
			}

			Thread.sleep(200);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void select() {
		if(currentChoice == 0){
			// attack	
						playerHitEnemy(playerAttack);
						System.out.println(playerAttack + ", " + "enemy: " + enemyHP);
						
						playerAttacking = true;
						enemyAttacking = false;

						TimerTask task = new TimerTask() {
							public void run() {
								enemyHitPlayer(enemyATKRange(5, 15));

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
			Game.State = Game.STATE.GAME;

		}
		if(currentChoice == 2)
		{
			// stats
			Game.State = Game.STATE.CINFOLAILA;

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
		graphics.drawString("HP: " + player.HP + "/" + player.getMaxHP(), 30, 430);
		graphics.drawString("ATK: " + playerAttack, 30, 460);
		
		if(playerAttacking == true) {
			graphics.drawString("Player attacked!", 100, 300);
		}
		if(enemyAttacking == true) {
			graphics.drawString("enemy attacked!", 100, 350);
		}
		
		if(enemyDead == true) {
			graphics.setFont(deadFont);
			graphics.setColor(Color.RED);
			graphics.drawString("ENEMY DEAD!", 100, 100);

			graphics.setFont(fontN);
			graphics.setColor(Color.BLACK);
			graphics.drawString("Player gained " + achievedEXP + "exp", 100, 200);
	}

}
}

