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

// siblings fight in room
public class Scene01 {
	
	private BufferedImage room;
	private BufferedImage bh;

	private AnimatedSprite boyAni;
	private AnimatedSprite girlAni;

	private AnimatedSprite bhAni;

	private Rectangle rect;
	private Rectangle boyRect;
	private Rectangle girlRect;
	private Rectangle timerRect;

	private int x = 0;
	private int y = 0;
	private int speed = 10;
	private int boyDir = 0;
	private int girlDir = 0;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	// dialog
	private String[] girl =
		{
				"Abang! Abang! Abaaaang!",
				"please help me with my history project",
				"Ugh!",
		};


	private String[] boy = 
		{
				"adik! what?! stop coming into my room as you please..",
				"I can't. i'm busy rehearsing for my History play.",
				"whoa! what is that?"
		};

	private String key = "press [A]";

	private int g = 0;	
	private int b = 0;

	private String addedGirlChar = "";
	private String addedBoyChar = "";

	private int addedGirlCharCounter = 0;
	private int addedBoyCharCounter = 0;
	
	private boolean beginBoy = false;
	private boolean beginGirl = false;
	
	private Audio sfx;
	
	public Scene01(Game game) {

		//bedroom bg
		room = game.loadImage("/bedroom-scene.png");

		//blackhole
		bh = game.loadImage("/blackHole.png");
		SpriteSheet bhSheet = new SpriteSheet(bh);
		bhSheet.loadSprites(128, 96);

		bhAni = new AnimatedSprite(bhSheet, 10);

		//male
		BufferedImage playerSheetImage = game.loadImage("/mainAnimated.png");
		SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
		boySheet.loadSprites(24, 32);

		boyAni = new AnimatedSprite(boySheet, speed);

		// female
		BufferedImage girlSheetImage = game.loadImage("/girl-main-anim.png");
		SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
		girlSheet.loadSprites(24, 32);

		girlAni = new AnimatedSprite(girlSheet, speed);

		//boy rect
		boyRect = new Rectangle(320, 400, 24, 32);
		boyRect.generateGraphics(3, 0x0000ff);

		//girl rect
		girlRect = new Rectangle(1000, 400, 24, 32);
		girlRect.generateGraphics(3, 0xf303d2);

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
		
		sfx = new Audio("/sfx/dialog.mp3");
		
	}

	public void update(Game game) {
		timerRect.x++;

		try {

			// 0 - Down, 
			// 1 - Left, 
			// 2 - Right, 
			// 3 - Up

			// GIRL MOVEMENT
			if(girlAni != null) {
				boolean didMove = false;
				int newDirection = girlDir;

				newDirection = 1;
				didMove = true;

				if(!didMove) {
					girlAni.reset();
				}

				if(didMove) {
					girlAni.incSprite();
					girlRect.x -= speed;

				}

				if(newDirection != girlDir) {
					girlDir = newDirection;
					girlAni.setAnimationRange(girlDir * 4, (girlDir * 4) + 4);
				}

				if(girlRect.x <= 600) {
					didMove = true;
					girlAni.reset();

					girlRect.x = 600;
					newDirection = 0;

				}

			}


			//BOY MOVEMENT
			// 0 - Down, 1 - Left, 2 - Right, 3 - Up

			if(boyAni != null) {
				boolean didMove = false;
				int newDirection = boyDir;

				newDirection = 3;

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



				if(girlRect.x <= 600) {
					didMove = true;
					boyAni.reset();

					newDirection = 2;
					boyAni.setAnimationRange(newDirection * 4, (newDirection * 4) + 4);
				}

			}
			System.out.println(timerRect.x);

			//DIALOGS
			if(timerRect.x > 10 && timerRect.x <= 40) {
				
				// ANIMATING DIALOGS - GIRL
				char girlChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
				}
				if(addedGirlCharCounter <= girlChar.length-1) {
					addedGirlChar = addedGirlChar + girlChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				}
			}

			if(timerRect.x > 40 && timerRect.x < 100) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = boy[b].toCharArray();
				if(beginBoy == false) {
					addedBoyChar = "";
					addedBoyCharCounter = 0;
					beginBoy = true;
					g++;
				}
				if(addedBoyCharCounter <= boyChar.length-1) {
					addedBoyChar = addedBoyChar + boyChar[addedBoyCharCounter];
					addedBoyCharCounter++;
					sfx.play();
				} else {
					beginGirl = false;
				}


			}


			if(timerRect.x > 100 && timerRect.x <= 145) {

				// ANIMATING DIALOGS - GIRL
				char girlChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
					b++;
				}
				if(addedGirlCharCounter <= girlChar.length-1) {
					addedGirlChar = addedGirlChar + girlChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				}
				else {
					beginBoy = false;
				}
			}
			
			if(timerRect.x > 145 && timerRect.x <= 200) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = boy[b].toCharArray();
				if(beginBoy == false) {
					addedBoyChar = "";
					addedBoyCharCounter = 0;
					beginBoy = true;
					g++;
				}
				if(addedBoyCharCounter <= boyChar.length-1) {
					addedBoyChar = addedBoyChar + boyChar[addedBoyCharCounter];
					addedBoyCharCounter++;
					sfx.play();
				} else {
					beginGirl = false;
				}
			}
			
			if(timerRect.x > 200 && timerRect.x <= 210) {
				// ANIMATING DIALOGS - GIRL
				char girlChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
					b++;
				}
				if(addedGirlCharCounter <= girlChar.length-1) {
					addedGirlChar = addedGirlChar + girlChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				}
				else {
					beginBoy = false;
				}
			}
			
			if(timerRect.x > 210 && timerRect.x <= 245) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = boy[b].toCharArray();
				if(beginBoy == false) {
					addedBoyChar = "";
					addedBoyCharCounter = 0;
					beginBoy = true;
					g++;
				}
				if(addedBoyCharCounter <= boyChar.length-1) {
					addedBoyChar = addedBoyChar + boyChar[addedBoyCharCounter];
					addedBoyCharCounter++;
					sfx.play();
				} else {
					beginGirl = false;
				}
			}


			if(timerRect.x >= 0) {
				KeyBoardListener keyListener = game.getKeyListener();

				if(keyListener.a()) {
					Game.State = Game.STATE.SCENE02;
					Scene02 scene02 = new Scene02(game);
					scene02.getAudio().play();
				}

			}
			
			if(timerRect.x >= 255) {
				Game.State = Game.STATE.SCENE02;
				Scene02 scene02 = new Scene02(game);
				scene02.getAudio().play();
			}

			Thread.sleep(80);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(room, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(boyAni, boyRect.x, boyRect.y, xZoom, yZoom, false);
		renderer.renderSprite(girlAni, girlRect.x, girlRect.y, xZoom, yZoom, false);

		if(timerRect.x > 10 && timerRect.x <= 245) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
		
		if(timerRect.x >= 200) {
			renderer.renderSprite(bhAni, 600, 200, xZoom, yZoom, false);
		}

	}

	public void render(Game game, Graphics graphics, Player player) {

		graphics.setFont(f);

		if(timerRect.x > 10 && timerRect.x < 40) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}

		if(timerRect.x >= 40 && timerRect.x < 100) {

			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}

		if(timerRect.x > 100 && timerRect.x <= 145) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}
		
		if(timerRect.x > 145 && timerRect.x <= 200) {

			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}
		
		if(timerRect.x > 200 && timerRect.x <= 210) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}
		
		if(timerRect.x > 210 && timerRect.x <= 245) {

			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}

	}

}





