package cutScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Player;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

//pg bendahara found GIRL blackout
public class Scene04 {

	private BufferedImage land;

	private AnimatedSprite pbsAni;
	private AnimatedSprite girlAni;

	private int speed = 10;

	//direction
	private int pgDir = 0;
	private int girlDir = 0;

	private Rectangle pbsRect;
	private Rectangle girlRect;
	private Rectangle rect;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	private String[] pgDialog =
		{
				"Hmm? what is this little girl doing sleeping here?",
				"Little girl, wake up!",
				"*tap* *tap*"
		};

	private int i = 0;

	public Scene04(Game game) {
		// land
		land = game.loadImage("/scene03.png");

		//pengiran bendahara sakam
		BufferedImage pbsImage = game.loadImage("/PgBS.png");
		SpriteSheet pbsSheet = new SpriteSheet(pbsImage);
		pbsSheet.loadSprites(16, 40);

		pbsAni = new AnimatedSprite(pbsSheet, speed);

		// female
		BufferedImage girlSheetImage = game.loadImage("/girl-blackout.png");
		SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
		girlSheet.loadSprites(24, 32);

		girlAni = new AnimatedSprite(girlSheet, speed);

		//pbs rectangle
		pbsRect = new Rectangle(400, 150, 16, 40);
		pbsRect.generateGraphics(3, 0x00ff00);

		//girl rect
		girlRect = new Rectangle(700, 320, 24, 32);
		girlRect.generateGraphics(3, 0x0000ff);

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

			}

			// GIRL MOVEMENT
			if(girlAni != null) {
				boolean didMove = false;
				int newDirection = girlDir;
			}

			if(pbsRect.x >= 640) {
				KeyBoardListener keyListener = game.getKeyListener();
				boolean didMove = false;

				if(keyListener.enter()) {
					didMove = true;
					Game.State = STATE.SCENE06;
				}

				if(didMove) {
					Thread.sleep(150);
				}
			}
			
			Thread.sleep(150);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	} 

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(land, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(girlAni, girlRect.x, girlRect.y, xZoom, yZoom, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, xZoom, yZoom, false);

		renderer.renderRectangle(rect, xZoom, yZoom, true);
	}

	public void render(Graphics graphics) {
		graphics.setFont(f);
		graphics.setColor(Color.GREEN);
		
		if(pbsRect.x < 640) {
			//		for(int i = 0; i < pgDialog.length; i++) {
			graphics.drawString(pgDialog[i], 60, 650);
			//		}
		}

		if(pbsRect.x >= 640) {
			graphics.drawString(pgDialog[i+1], 60, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("[press enter]", 800, 740);
		}


	}

	public Audio getAudio() {
		return null;
	}

}
