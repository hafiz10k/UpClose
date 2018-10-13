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
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

// BOY - outside hideout
public class Scene07 {

	private BufferedImage hideout;
	private BufferedImage bh;

	private AnimatedSprite pbsAni;
	private AnimatedSprite boyAni;
	private AnimatedSprite soldierAni;

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

	Timer timer;

	// dialog
	private String[] pgDialog =
		{
				"Follow me, let me show you the training ground.",
		};

	private int pg = 0;	
	private String addedPGChar = "";
	private int addedPGCharCounter = 0;

	private boolean beginPG = false;

	private Audio sfx;


	public Scene07(Game game) {
		//hideout bg
		hideout = game.loadImage("/pgbs-hideout.png");

		//male
		BufferedImage playerSheetImage = game.loadImage("/mainAnimated.png");
		SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
		boySheet.loadSprites(24, 32);

		boyAni = new AnimatedSprite(boySheet, speed);

		//pengiran bendahara sakam
		BufferedImage pbsImage = game.loadImage("/pg-animation.png");
		SpriteSheet pbsSheet = new SpriteSheet(pbsImage);
		pbsSheet.loadSprites(16, 40);

		pbsAni = new AnimatedSprite(pbsSheet, 7);

		//soldiers
		BufferedImage guyImage = game.loadImage("/soldiers.png");
		SpriteSheet guySheet = new SpriteSheet(guyImage);
		guySheet.loadSprites(24, 40);

		soldierAni = new AnimatedSprite(guySheet, speed);

		//boy rect
		boyRect = new Rectangle(430, 360, 24, 32);
		boyRect.generateGraphics(3, 0x0000ff);

		//pbs rectangle
		pbsRect = new Rectangle(500, 350, 16, 40);
		pbsRect.generateGraphics(3, 0x00ff00);

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);

		//custscenes audio
		sfx = new Audio("/sfx/dialog.mp3");
	}

	public void update(Game game ) {
		KeyBoardListener keyListener = game.getKeyListener();
		timerRect.x++;

		try {

			// PBS MOVEMENT
			if(pbsAni != null) {

				boolean didMove = false;
				int newDirection = pgDir;

				if(timerRect.x >= 10) {
					newDirection = 2;
					didMove = true;

					if(!didMove) {
						pbsAni.reset();
					}

					if(didMove) {
						pbsAni.incSprite();
						pbsRect.x += 7;
					}

					if(newDirection != pgDir) {
						pgDir = newDirection;
						pbsAni.setAnimationRange(pgDir * 4, (pgDir * 4) + 4);

					}

					if(timerRect.x >= 10) {
						pbsRect.x += 7;
					}

					if(pbsRect.x >= 850) {
						didMove = true;
						pbsAni.reset();

						newDirection = 1;
						pbsAni.setAnimationRange(newDirection * 4, (newDirection * 4) + 4);
						pbsRect.x = 850;
					}
				}


			}

			//BOY MOVEMENT
			// 0 - Down, 1 - Left, 2 - Right, 3 - Up
			if(boyAni != null) {
				if(timerRect.x >= 50) {
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
				}

			}

			if(timerRect.x >= 40 && timerRect.x <= 90) {
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


			// boundary at pgBs
			if(boyRect.x <= 380) {
				boyRect.x = 380;
			}

			if(boyRect.x >= 810) {
				boyRect.x = 810;
			}

			if(boyRect.y <= 300) {
				boyRect.y = 300;
			}

			if(boyRect.y >= 450) {
				boyRect.y = 450;
			}

			if(boyRect.x == 810 && boyRect.y == 360) {
				if(keyListener.a()) {
					Game.State = STATE.SCENE09;
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
		renderer.renderSprite(boyAni, boyRect.x, boyRect.y, 2, 2, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, 2, 2, false);
		renderer.renderSprite(soldierAni, 200, 200, 2, 2, false);
		renderer.renderSprite(soldierAni, 100, 300, 2, 2, false);
		renderer.renderSprite(soldierAni, 600, 450, 2, 2, false);
		renderer.renderSprite(soldierAni, 850, 450, 2, 2, false);

		if(timerRect.x >= 40 && timerRect.x <= 90) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

		if(boyRect.x == 810 && boyRect.y == 360) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

	}

	public void render(Graphics graphics) {

		graphics.setFont(f);
		if(timerRect.x >= 40 && timerRect.x <= 90) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(boyRect.x == 810 && boyRect.y == 360) {
			graphics.setFont(fontKey);
			graphics.drawString("[A] to interact", 60, 650);
		}

	}

}
