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

// BOY - in pg bendahara house
public class Scene09 {
	private Game game;

	private BufferedImage room;
	private BufferedImage bh;

	private AnimatedSprite pbsAni;
	private AnimatedSprite boyAni;

	private Rectangle rect;

	private Rectangle boyRect;
	private Rectangle pbsRect;

	private Rectangle timerRect;

	private int x = 0;
	private int y = 0;
	private int speed = 10;
	private int boyDir = 0;
	private int pgDir = 0;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	// dialog
	private String[] pgDialog =
		{
				"We have arrived at my training ground. My soldiers are training hard to prepare for upcoming war.",
				"The spanish are interested in our land and they are planning to take over Brunei.",
				"Would you like to join us in this battle?",
				"Great, welcome to the team.",
		};
	
	private String[] boy = 
		{
				"What do you mean they are preparing for war?!",
				"*Speaking in own mind* This must be the Castilian War that I have learned before.",
				"Yesss! It would be an honour to fight for my own country."
		};

	private String key = "[press enter]";

	private int pg = 0;	
	private int b = 0;

	private String addedPBSChar = "";
	private String addedBoyChar = "";

	private int addedPBSCharCounter = 0;
	private int addedBoyCharCounter = 0;

	public Scene09(Game game) {



		//bedroom bg
		room = game.loadImage("/DUMMY.png");

		//male
		BufferedImage playerSheetImage = game.loadImage("/mainAnimated.png");
		SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
		boySheet.loadSprites(24, 32);

		boyAni = new AnimatedSprite(boySheet, speed);


		//pengiran bendahara sakam
		BufferedImage pbsImage = game.loadImage("/PgBS.png");
		SpriteSheet pbsSheet = new SpriteSheet(pbsImage);
		pbsSheet.loadSprites(16, 40);

		pbsAni = new AnimatedSprite(pbsSheet, speed);

		//boy rect
		boyRect = new Rectangle(80, 420, 24, 32);
		boyRect.generateGraphics(3, 0x0000ff);

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

	public void update(Game game, Player player ) {
		//		timerRect.x++;

		System.out.println(boyRect.x + ", " + boyRect.y);

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

			// BOY MOVEMENT
			if(boyAni != null) {
				KeyBoardListener keyListener = game.getKeyListener();

				boolean didMove = false;
				int newDirection = boyDir;

				if(keyListener.left()) {
					newDirection = 1;
					didMove = true;
					boyRect.x -= speed;			
				}

				if(keyListener.right()) {
					newDirection = 2;
					didMove = true;
					boyRect.x += speed;			
				}

				if(keyListener.up()) {
					newDirection = 3;
					didMove = true;
					boyRect.y -= speed;			
				}

				if(keyListener.down()) {
					newDirection = 0;
					didMove = true;
					boyRect.y += speed;			
				}

				if(!didMove) {
					boyAni.reset();
				}

				if(didMove) {
					boyAni.incSprite();

				}

				if(newDirection != boyDir) {
					boyDir = newDirection;
					boyAni.setAnimationRange(boyDir * 4, (boyDir * 4) + 4);
				}
				
				if(boyRect.x == 480 && boyRect.y == 270) {
					if(keyListener.a()) {
						Game.State = STATE.DUMMY;
					}
				}
			}

			if(boyRect.x <= 0) {
				boyRect.x = 0;
			}

			if(boyRect.x >= 910) {
				boyRect.x = 910;
			}

			if(boyRect.y > 530) {
				boyRect.y = 530;
			}

			if(boyRect.y < 270) {
				boyRect.y = 270;
			}


			Thread.sleep(100);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(room, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(boyAni, boyRect.x, boyRect.y, xZoom, yZoom, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, xZoom, yZoom, false);

		renderer.renderRectangle(timerRect, xZoom, yZoom, false);

		if(boyRect.x == 480 && boyRect.y == 270) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

	}

	public void render(Graphics graphics, Game game) {
		graphics.setFont(f);

		if(boyRect.x == 480 && boyRect.y == 270) {
			graphics.setFont(fontKey);
			graphics.drawString("[A] to interact", 60, 650);
		}

	}

}
