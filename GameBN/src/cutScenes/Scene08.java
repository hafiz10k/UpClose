package cutScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import entity.AnimatedSprite;
import entity.Player;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;

// girl - outside hideout
public class Scene08 {

	private BufferedImage hideout;
	private BufferedImage bh;

	private AnimatedSprite pbsAni;
	private AnimatedSprite girlAni;
	private AnimatedSprite soldierAni;

	private Rectangle rect;

	private Rectangle girlRect;
	private Rectangle pbsRect;

	private Rectangle timerRect;

	private int x = 0;
	private int y = 0;
	private int speed = 10;
	private int girlDir = 0;
	private int pgDir = 0;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	Timer timer;

	// dialog
	private String[] pgDialog =
		{
				"Follow me, let me show you the training ground.",
				"I will show you where I and my soldiers trains.",
				"No, the pleasure is mine."
		};
	
	private String[] girl = 
		{
				"Okay, let's go",
				"Sure it will be my pleasure",
				"*Smile*"
		};

	private String key = "[press enter]";

	private int pg = 0;	
	private int g = 0;

	private String addedPBSChar = "";
	private String addedgirlChar = "";

	private int addedPBSCharCounter = 0;
	private int addedgirlCharCounter = 0;

	public Scene08(Game game) {

		//hideout bg
		hideout = game.loadImage("/pgbs-hideout.png");

		//male
		BufferedImage playerSheetImage = game.loadImage("/girl-main-anim.png");
		SpriteSheet girlSheet = new SpriteSheet(playerSheetImage);
		girlSheet.loadSprites(24, 32);

		girlAni = new AnimatedSprite(girlSheet, speed);

		//pengiran bendahara sakam
		BufferedImage pbsImage = game.loadImage("/PgBS.png");
		SpriteSheet pbsSheet = new SpriteSheet(pbsImage);
		pbsSheet.loadSprites(16, 40);

		pbsAni = new AnimatedSprite(pbsSheet, speed);

		//soldiers
		BufferedImage guyImage = game.loadImage("/soldiers.png");
		SpriteSheet guySheet = new SpriteSheet(guyImage);
		guySheet.loadSprites(24, 40);

		soldierAni = new AnimatedSprite(guySheet, speed);

		//girl rect
		girlRect = new Rectangle(430, 360, 24, 32);
		girlRect.generateGraphics(3, 0x0000ff);

		//pbs rectangle
		pbsRect = new Rectangle(500, 350, 16, 40);
		pbsRect.generateGraphics(3, 0x00ff00);

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
	}

	public void update(Game game ) {
		KeyBoardListener keyListener = game.getKeyListener();
		
		timerRect.x++;
		try {

			// PBS MOVEMENT
			if(pbsAni != null) {

				//				boolean didMove = false;
				//				int newDirection = pgDir;
				//
				//				newDirection = 1;
				boolean didMove = true;
				//
				//				if(!didMove) {
				//					pbsAni.reset();
				//				}
				//
				//				if(didMove) {
				//					pbsAni.incSprite();
				//					pbsRect.x += speed;
				//
				//				}
				//
				//				if(newDirection != pgDir) {
				//					pgDir = newDirection;
				//					pbsAni.setAnimationRange(pgDir * 4, (pgDir * 4) + 4);
				//				}
				//

				if(timerRect.x >= 10) {
					pbsRect.x += speed;
				}
				
				if(pbsRect.x >= 850) {
					pbsRect.x = 850;
				}

			}
			
			System.out.println(girlRect.x + ", " + girlRect.y);
			
			//girl MOVEMENT
			// 0 - Down, 1 - Left, 2 - Right, 3 - Up
			if(girlAni != null) {
				if(timerRect.x >= 50) {
					

					boolean didMove = false;
					int newDirection = girlDir;

					if(keyListener.left()) {
						newDirection = 1;
						didMove = true;
						girlRect.x -= speed;			
					}

					if(keyListener.right()) {
						newDirection = 2;
						didMove = true;
						girlRect.x += speed;			
					}

					if(keyListener.up()) {
						newDirection = 3;
						didMove = true;
						girlRect.y -= speed;			
					}

					if(keyListener.down()) {
						newDirection = 0;
						didMove = true;
						girlRect.y += speed;			
					}

					if(!didMove) {
						girlAni.reset();
					}

					if(didMove) {
						girlAni.incSprite();

					}

					if(newDirection != girlDir) {
						girlDir = newDirection;
						girlAni.setAnimationRange(girlDir * 4, (girlDir * 4) + 4);
					}
				}
				
			}
			// boundary at pgBs
			if(girlRect.x <= 380) {
				girlRect.x = 380;
			}
			
			if(girlRect.x >= 810) {
				girlRect.x = 810;
			}

			if(girlRect.y <= 300) {
				girlRect.y = 300;
			}
			
			if(girlRect.y >= 450) {
				girlRect.y = 450;
			}
			
			if(girlRect.x == 810 && girlRect.y == 360) {
				if(keyListener.a()) {
					Game.State = STATE.SCENE10;
				}
			}
			
			Thread.sleep(100);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}



	}

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(hideout, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(girlAni, girlRect.x, girlRect.y, 2, 2, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, 2, 2, false);
		renderer.renderSprite(soldierAni, 200, 200, 2, 2, false);
		renderer.renderSprite(soldierAni, 100, 300, 2, 2, false);
		renderer.renderSprite(soldierAni, 600, 450, 2, 2, false);
		renderer.renderSprite(soldierAni, 850, 450, 2, 2, false);

		if(timerRect.x >= 50 && timerRect.x < 70) {
		renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
		
		if(girlRect.x == 810 && girlRect.y == 360) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
		
		renderer.renderRectangle(timerRect, xZoom, yZoom, false);

	}

	public void render(Graphics graphics) {
		
		graphics.setFont(f);
		if(timerRect.x >= 50 && timerRect.x < 70) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(pgDialog[pg], 60, 650);
		}
		
		if(girlRect.x == 810 && girlRect.y == 360) {
			graphics.setFont(fontKey);
			graphics.drawString("[A] to interact", 60, 650);
		}

	}

}
