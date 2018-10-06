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

// girl - in pg bendahara house
public class Scene06 {
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

	// dialog
	private String[] pgDialog =
		{
				"Little girl, are you awake now?",
				"you are currently in Jerudong, our hideout.",
				"brother? I found you alone passed out in the woods",
				"I'm Pengiran Bendahara Sakam, Brunei's mighty warrior!",
				"did you hit your head so hard? it's year 1578.",
				"what is your name, little girl?",
		};

	private String[] girl = 
		{
				"ugh.. where am i?",
				"where's my brother?!",
				"in the woods? i was just in my brother's bedroom...",
				"wait why do you look so familiar.. who are you?",
				"huh? warrior? wait.. you mean THAT pengiran bendahara sakam?!",
				"what year is this?!",
				"oh..... my.... god...."
		};

	private String key = "[press enter]";

	private int pg = 0;	
	private int g = 0;

	private String addedPBSChar = "";
	private String addedgirlChar = "";

	private int addedPBSCharCounter = 0;
	private int addedgirlCharCounter = 0;

	public Scene06(Game game) {
		
		

		//bedroom bg
		room = game.loadImage("/pbs-bedroom.png");

		//male
		BufferedImage playerSheetImage = game.loadImage("/girl-blackout.png");
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
		pbsRect = new Rectangle(400, 400, 16, 40);
		pbsRect.generateGraphics(3, 0x00ff00);

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
	}

	public void update(Game game) {
		timerRect.x++;

		try {

			// PBS MOVEMENT
			if(pbsAni != null) {

				pbsRect.x -= speed;
				System.out.println(timerRect.x);

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

				if(timerRect.x >= 22) {
					pbsRect.x = 190;
				}
			}

			// girl MOVEMENT
			if(girlAni != null) {
				boolean didMove = false;
				int newDirection = girlDir;



				if(timerRect.x >= 22) {
					didMove = true;

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


			Thread.sleep(150);

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
		
		if(timerRect.x >= 22) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

	}

	public void render(Graphics graphics, Game game) {
		graphics.setFont(f);
		if(timerRect.x >= 22 && timerRect.x < 32) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(pgDialog[pg], 60, 650);
		}
		
		if(timerRect.x >= 32 && timerRect.x < 42) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(girl[g], 60, 650);
		}
		
		if(timerRect.x >= 42 && timerRect.x < 52) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(pgDialog[pg+1], 60, 650);
		}
		
		if(timerRect.x >= 52 && timerRect.x < 62) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(girl[g+1], 60, 650);
		}
		
		if(timerRect.x >= 62 && timerRect.x < 72) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(pgDialog[pg+2], 60, 650);
		}
		
		if(timerRect.x >= 72 && timerRect.x < 82) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(girl[g+2], 60, 650);
		}
		
		if(timerRect.x >= 82 && timerRect.x < 92) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(girl[g+3], 60, 650);
		}
		
		if(timerRect.x >= 92 && timerRect.x < 102) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(pgDialog[pg+3], 60, 650);
		}
		
		if(timerRect.x >= 102 && timerRect.x < 112) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(girl[g+4], 60, 650);
		}
		
		if(timerRect.x >= 112 && timerRect.x < 122) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(girl[g+5], 60, 650);
		}
		
		if(timerRect.x >= 122 && timerRect.x < 132) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(pgDialog[pg+4], 60, 650);
		}
		
		if(timerRect.x >= 132 && timerRect.x < 142) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(girl[g+6], 60, 650);
		}
		
		if(timerRect.x >= 142 && timerRect.x < 152) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(pgDialog[pg+5], 60, 650);
		}
		
		if(timerRect.x >= 155 && timerRect.x < 175) {
			String pg6 = "alright, " + game.name.getName() + " follow me";
			graphics.setColor(Color.GREEN);
			graphics.drawString(pg6, 60, 650);

		}
		
		if(timerRect.x >= 180) {
			Game.State = STATE.SCENE08;
		}

	}

}
