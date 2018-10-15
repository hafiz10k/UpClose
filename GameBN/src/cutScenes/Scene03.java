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
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

// pg bendahara found BOY blackout
public class Scene03 {

	private BufferedImage land;

	private AnimatedSprite pbsAni;
	private AnimatedSprite boyAni;

	private int speed = 5;

	//direction
	private int pgDir = 0;
	private int boyDir = 0;

	private Rectangle pbsRect;
	private Rectangle boyRect;
	private Rectangle rect;
	private Rectangle timerRect;

	private static final int down = 0;
	private static final int right = 2;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	private String[] pgDialog =
		{
				"Hmm? what is this boy doing sleeping here?",
		};

	private int pg = 0;
	private String addedPGChar = "";
	private int addedPGCharCounter = 0;
	private boolean beginPG = false;

	private Audio sfx;

	public Scene03(Game game) {
		// land
		land = game.loadImage("/scene03.png");

		//pengiran bendahara sakam
		BufferedImage pbsImage = game.loadImage("/pg-animation.png");
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

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);

		//custscenes audio
		sfx = new Audio("/sfx/dialog.mp3");

	}

	public void update(Game game) {
		timerRect.x++;
		System.out.println(timerRect.x);

		try {
			// PBS MOVEMENT
			if(pbsAni != null) {
				boolean didMove = false;
				int newDirection = pgDir;

				if(timerRect.x >= 0) {
					newDirection = down;
					didMove = true;
					pbsRect.y += speed;

					if(!didMove) {
						pbsAni.reset();
					}

					if(didMove) {
						pbsAni.incSprite();
					}

					if(newDirection != pgDir) {
						pgDir = newDirection;
						if(pbsAni != null) {
							pbsAni.setAnimationRange(pgDir * 4, (pgDir * 4) + 4);
						}

					}

					if(pbsRect.y >= 300) {
						didMove = true;
						pbsAni.reset();
						pbsRect.y = 300;

						newDirection = right;
						pbsRect.x += speed;

						if(didMove) {
							pbsAni.incSprite();
							pbsAni.setAnimationRange(newDirection * 4, (newDirection * 4) + 4);
						}

						if(pbsRect.x >= 640) {
							pbsRect.x = 640;
						}

					}
				}
				if(pbsRect.y >= 300) {
					didMove = true;
					pbsAni.reset();
					pbsRect.y = 300;

					newDirection = right;
					pbsRect.x += speed;

					if(didMove) {
						pbsAni.incSprite();
						pbsAni.setAnimationRange(newDirection * 4, (newDirection * 4) + 4);
					}

					if(pbsRect.x >= 640) {
						pbsRect.x = 640;
					}

				}
			}


		//DIALOGS
		if(timerRect.x > 10 && timerRect.x <= 150) {
			// ANIMATING DIALOGS - PG Bendahara
			char pgChar[] = pgDialog[pg].toCharArray();
			if(beginPG == false) {
				addedPGChar = "";
				addedPGCharCounter = 0;
				beginPG = true;
			}
			if(addedPGCharCounter <= pgChar.length-1) {
				addedPGChar = addedPGChar + pgChar[addedPGCharCounter];
				addedPGCharCounter++;
				sfx.play();
			} 
	
		}

		if(timerRect.x >= 0) {
			KeyBoardListener keyListener = game.getKeyListener();
			boolean didMove = false;

			if(keyListener.a()) {
				didMove = true;
				Game.State = Game.STATE.SCENE05;
			}

			if(didMove) {
				Thread.sleep(100);
			}
		}
		
		if(timerRect.x >= 160) {
			Game.State = Game.STATE.SCENE05;
		}

		Thread.sleep(80);

	}
	catch(Exception e) {
		e.printStackTrace();
	}
} 

public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
	renderer.renderImage(land, 10, 150, xZoom, yZoom, true);
	renderer.renderSprite(boyAni, boyRect.x, boyRect.y, xZoom, yZoom, false);
	renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, xZoom, yZoom, false);

	if(timerRect.x > 10 && timerRect.x <= 150) {
		renderer.renderRectangle(rect, xZoom, yZoom, true);
	}
}

public void render(Graphics graphics) {
	graphics.setFont(f);
	graphics.setColor(Color.GREEN);

	if(timerRect.x > 10 && timerRect.x <= 150) {
		graphics.drawString(addedPGChar, 60, 650);

		graphics.setFont(fontKey);
		graphics.setColor(Color.BLACK);
		graphics.drawString("press [A]", 800, 740);
	}

}

public Audio getAudio() {
	return null;
}

}
