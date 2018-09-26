package screenMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Player;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Scene03 {

	private BufferedImage land;

	private AnimatedSprite pbsAni;
	private AnimatedSprite boyAni;

	private int speed = 4;

	//direction
	private int pgDir = 0;
	private int boyDir = 0;

	private Rectangle pbsRect;
	private Rectangle boyRect;
	private Rectangle rect;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	private String[] pgDialog =
		{
				"Hmm? what is this boy doing sleeping in the middle of the woods?",
				"Little boy! WAKE UP!!",
				"adik dialog 3"
		};

	private String[] boy = 
		{
				"ugh.. hmm?",
				"abang dialog 2",
				"abang dialog 3"
		};

	private int i = 0;

	public Scene03(Game game) {
		// land
		land = game.loadImage("/scene03.png");

		//pengiran bendahara sakam
		BufferedImage pbsImage = game.loadImage("/PgBS.png");
		SpriteSheet pbsSheet = new SpriteSheet(pbsImage);
		pbsSheet.loadSprites(16, 40);

		pbsAni = new AnimatedSprite(pbsSheet, speed);

		//male
		BufferedImage playerSheetImage = game.loadImage("/blackout.png");
		SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
		boySheet.loadSprites(24, 32);

		boyAni = new AnimatedSprite(boySheet, speed);

		//pbs rectangle
		pbsRect = new Rectangle(400, 150, 16, 40);
		pbsRect.generateGraphics(3, 0x00ff00);

		//boy rect
		boyRect = new Rectangle(700, 320, 24, 32);
		boyRect.generateGraphics(3, 0x0000ff);

		//DIALOG BOX
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

	}

	public void update(Game game, Player player) {
		try {

			// PBS MOVEMENT
			if(pbsAni != null) {

				pbsRect.y += speed;

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
				if(pbsRect.y >= 300) {
					pbsRect.y = 300;

					pbsRect.x += speed;
					System.out.println(pbsRect.x);

					if(pbsRect.x >= 640) {
						pbsRect.x = 640;
					}

				}

				Thread.sleep(50);

			}

			// BOY MOVEMENT
			if(boyAni != null) {
				boolean didMove = false;
				int newDirection = boyDir;



				if(pbsRect.x >= 640) {
					didMove = true;

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
					Thread.sleep(200);

				}
			}
			
			if(pbsRect.x >= 640) {
				KeyBoardListener keyListener = game.getKeyListener();
				boolean didMove = false;

				if(keyListener.enter()) {
					didMove = true;
					Game.State = Game.STATE.GAME;
				}
				
				if(didMove) {
				Thread.sleep(150);
				}
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	} 

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(land, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(boyAni, boyRect.x, boyRect.y, xZoom, yZoom, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, xZoom, yZoom, false);

		renderer.renderRectangle(rect, xZoom, yZoom, true);
	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		if(pbsRect.x < 640) {
			graphics.setColor(Color.GREEN);

			//		for(int i = 0; i < pgDialog.length; i++) {
			graphics.drawString(pgDialog[i], 60, 650);
			//		}
		}

		if(pbsRect.x >= 640) {

			graphics.setColor(Color.BLUE);
			graphics.drawString(boy[i], 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("[press enter]", 800, 740);
		}


	}

	public Audio getAudio() {
		return null;
	}

}
