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
import menuComponents.CreateName;

// girl - dummy 
public class Scene10 {
	private Game game;

	private BufferedImage room;
	private BufferedImage bh;

	private AnimatedSprite pbsAni;
	private AnimatedSprite girlAni;

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

	// dialog
	private String[] pgDialog =
		{
				"Little girl, are you awake now?",
				"you are currently in Jerudong, our hideout.",
				"little sister? I found you alone passed out in the woods",
				"I'm Pengiran Bendahara Sakam, Brunei's mighty warrior!",
				"did you hit your head so hard? it's year 1578.",
				"what is your name, girl?",
		};

	private String key = "[press enter]";

	private int pg = 0;	
	private int b = 0;

	private String addedPBSChar = "";
	private String addedgirlChar = "";

	private int addedPBSCharCounter = 0;
	private int addedgirlCharCounter = 0;

	public Scene10(Game game) {



		//bedroom bg
		room = game.loadImage("/DUMMY.png");

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

		//girl rect
		girlRect = new Rectangle(80, 420, 24, 32);
		girlRect.generateGraphics(3, 0x0000ff);

		//pbs rectangle
		pbsRect = new Rectangle(600, 200, 16, 40);
		pbsRect.generateGraphics(3, 0x00ff00);

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
	}

	public void update(Game game) {
		//		timerRect.x++;

		System.out.println(girlRect.x + ", " + girlRect.y);

		try {

			// PBS MOVEMENT
			if(pbsAni != null) {

				//				pbsRect.x -= speed;

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

			}

			// girl MOVEMENT
			if(girlAni != null) {
				KeyBoardListener keyListener = game.getKeyListener();

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

				if(girlRect.x == 480 && girlRect.y == 270) {
					if(keyListener.a()) {
						Game.State = STATE.DUMMY;
					}
				}

			}

			if(girlRect.x <= 0) {
				girlRect.x = 0;
			}

			if(girlRect.x >= 910) {
				girlRect.x = 910;
			}

			if(girlRect.y > 530) {
				girlRect.y = 530;
			}

			if(girlRect.y < 270) {
				girlRect.y = 270;
			}

			Thread.sleep(100);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(room, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(girlAni, girlRect.x, girlRect.y, xZoom, yZoom, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, xZoom, yZoom, false);

		renderer.renderRectangle(timerRect, xZoom, yZoom, false);

		if(girlRect.x == 480 && girlRect.y == 270) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

	}

	public void render(Graphics graphics, Game game) {
		graphics.setFont(f);

		if(girlRect.x == 480 && girlRect.y == 270) {
			graphics.setFont(fontKey);
			graphics.drawString("[A] to interact", 60, 650);
		}

	}

}
