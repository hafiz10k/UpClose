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
				"You are currently in Jerudong, our hideout.",
				"Brother? I found you alone passed out in the woods",
				"I'm Pengiran Bendahara Sakam, Brunei's mighty warrior!",
				"Did you hit your head so hard? it's year 1578.",
				"Come, follow me outside, little girl?",
		};

	private String[] girl = 
		{
				"ugh.. where am i?",
				"where's my brother?!",
				"But I was in my brother's room! wait... who are you?",
				"Huh? warrior? stop joking. what year is this?!",
				"oh..... my.... god...."
		};

	private int pg = 0;	
	private int g = 0;

	private String addedPGChar = "";
	private String addedGirlChar = "";

	private int addedPGCharCounter = 0;
	private int addedGirlCharCounter = 0;

	private boolean beginPG = false;
	private boolean beginGirl = false;

	private Audio sfx;

	public Scene06(Game game) {
		//bedroom bg
		room = game.loadImage("/pbs-bedroom.png");

		//male
		BufferedImage playerSheetImage = game.loadImage("/girl-blackout.png");
		SpriteSheet girlSheet = new SpriteSheet(playerSheetImage);
		girlSheet.loadSprites(24, 32);

		girlAni = new AnimatedSprite(girlSheet, speed);


		//pengiran bendahara sakam
		BufferedImage pbsImage = game.loadImage("/pg-animation.png");
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

		//custscenes audio
		sfx = new Audio("/sfx/dialog.mp3");
	}

	public void update(Game game ) {
		timerRect.x++;
		System.out.println(timerRect.x);

		try {
			// PBS MOVEMENT
			if(pbsAni != null) {
				boolean didMove = false;
				int newDirection = pgDir;

				newDirection = 1;
				didMove = true;

				if(!didMove) {
					pbsAni.reset();
				}

				if(didMove) {
					pbsAni.incSprite();
					pbsRect.x -= 10;
				}

				if(newDirection != pgDir) {
					pgDir = newDirection;
					pbsAni.setAnimationRange(pgDir * 4, (pgDir * 4) + 4);

				}

				if(timerRect.x >= 22) {
					didMove = true;
					pbsAni.reset();

					pbsRect.x = 190;
				}
			}

			// GIRL MOVEMENT
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
			
			
			if(timerRect.x >= 25 && timerRect.x <= 60) {
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

			if(timerRect.x > 60 && timerRect.x <= 90) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
					pg++;
				}
				if(addedGirlCharCounter <= boyChar.length-1) {
					addedGirlChar = addedGirlChar + boyChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				} else {
					beginPG = false;
				}
			}

			if(timerRect.x > 90 && timerRect.x <= 150) {
				// ANIMATING DIALOGS - PG Bendahara
				char pgChar[] = pgDialog[pg].toCharArray();
				if(beginPG == false) {
					addedPGChar = "";
					addedPGCharCounter = 0;
					beginPG = true;
					g++;
				}
				if(addedPGCharCounter <= pgChar.length-1) {
					addedPGChar = addedPGChar + pgChar[addedPGCharCounter];
					addedPGCharCounter++;
					sfx.play();
				}
				else {
					beginGirl = false;
				}
			}

			if(timerRect.x > 150 && timerRect.x <= 180) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
					pg++;
				}
				if(addedGirlCharCounter <= boyChar.length-1) {
					addedGirlChar = addedGirlChar + boyChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				} else {
					beginPG = false;
				}
			}

			if(timerRect.x > 180 && timerRect.x <= 250) {
				// ANIMATING DIALOGS - PG Bendahara
				char pgChar[] = pgDialog[pg].toCharArray();
				if(beginPG == false) {
					addedPGChar = "";
					addedPGCharCounter = 0;
					beginPG = true;
					g++;
				}
				if(addedPGCharCounter <= pgChar.length-1) {
					addedPGChar = addedPGChar + pgChar[addedPGCharCounter];
					addedPGCharCounter++;
					sfx.play();
				}
				else {
					beginGirl = false;
				}
			}

			if(timerRect.x > 250 && timerRect.x <= 320) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
					pg++;
				}
				if(addedGirlCharCounter <= boyChar.length-1) {
					addedGirlChar = addedGirlChar + boyChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				}
				else {
					beginPG = false;
				}
			}

			if(timerRect.x > 320 && timerRect.x <= 390) {
				// ANIMATING DIALOGS - PG Bendahara
				char pgChar[] = pgDialog[pg].toCharArray();
				if(beginPG == false) {
					addedPGChar = "";
					addedPGCharCounter = 0;
					beginPG = true;
					g++;
				}
				if(addedPGCharCounter <= pgChar.length-1) {
					addedPGChar = addedPGChar + pgChar[addedPGCharCounter];
					addedPGCharCounter++;
					sfx.play();
				}
				else {
					beginGirl = false;
				}
			}

			if(timerRect.x > 390 && timerRect.x <= 450) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
					pg++;
				}
				if(addedGirlCharCounter <= boyChar.length-1) {
					addedGirlChar = addedGirlChar + boyChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				}
				else {
					beginPG = false;
				}
			}

			if(timerRect.x > 450 && timerRect.x <= 510) {
				// ANIMATING DIALOGS - PG Bendahara
				char pgChar[] = pgDialog[pg].toCharArray();
				if(beginPG == false) {
					addedPGChar = "";
					addedPGCharCounter = 0;
					beginPG = true;
					g++;
				}
				if(addedPGCharCounter <= pgChar.length-1) {
					addedPGChar = addedPGChar + pgChar[addedPGCharCounter];
					addedPGCharCounter++;
					sfx.play();
				}
				else {
					beginGirl = false;
				}
			}

			if(timerRect.x > 510 && timerRect.x <= 540) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
					pg++;
				}
				if(addedGirlCharCounter <= boyChar.length-1) {
					addedGirlChar = addedGirlChar + boyChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				}
				else {
					beginPG = false;
				}
			}

			if(timerRect.x > 540 && timerRect.x <= 610) {
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


			if(timerRect.x >= 0 ) {
				KeyBoardListener keyListener = game.getKeyListener();
				boolean didMove = false;

				if(keyListener.a()) {
					didMove = true;
					Game.State = Game.STATE.SCENE08;
				}

				if(didMove) {
					Thread.sleep(100);
				}
			}

			Thread.sleep(70);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}



	}

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(room, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(girlAni, girlRect.x, girlRect.y, xZoom, yZoom, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, xZoom, yZoom, false);

		if(timerRect.x >= 22) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

	}

	public void render(Graphics graphics, Game game) {
		graphics.setFont(f);
		if(timerRect.x >= 25 && timerRect.x <= 60) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 60 && timerRect.x <= 90) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 90 && timerRect.x <= 150) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 150 && timerRect.x <= 180) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 180 && timerRect.x <= 250) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 250 && timerRect.x <= 320) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 320 && timerRect.x <= 390) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 390 && timerRect.x <= 450) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 450 && timerRect.x <= 510) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 510 && timerRect.x <= 540) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x > 540 && timerRect.x <= 610) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

		if(timerRect.x >= 620) {
			Game.State = STATE.SCENE08;
		}

	}

}
