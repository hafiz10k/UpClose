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

// girl - training 
public class Scene10 {
	private BufferedImage room;

	private AnimatedSprite pbsAni;
	private AnimatedSprite girlAni;

	private Rectangle rect;

	private Rectangle girlRect;
	private Rectangle pbsRect;

	private Rectangle timerRect;

	private int speed = 10;
	private int girlDir = 0;
	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	// dialog
	private String[] pgDialog =
		{
				"This is the training ground where we train for upcoming war.",
				"Spain are planning to take over Brunei!",
				"There are going to be dangers, I'll teach you self-defense."
		};

	private String[] girl = 
		{
				"You are preparing for war?!",
				"*inner thoughts* year 1578 + war with Spain = CASTILIAN WAR!"
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

		//custscenes audio
		sfx = new Audio("/sfx/dialog.mp3");
	}

	public void update(Game game) {
		timerRect.x++;

		System.out.println(girlRect.x + ", " + girlRect.y);

		try {
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

				if(timerRect.x > 350) {

					if(girlRect.x == 480 && girlRect.y == 270) {
						if(keyListener.a()) {
							Game.State = STATE.DUMMY;
						}
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

			if(timerRect.x >= 10 && timerRect.x <= 80) {
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

			if(timerRect.x > 80 && timerRect.x <= 130) {
				// ANIMATING DIALOGS - GIRL
				char girlChar[] = girl[g].toCharArray();
				if(beginGirl == false) {
					addedGirlChar = "";
					addedGirlCharCounter = 0;
					beginGirl = true;
					pg++;
				}
				if(addedGirlCharCounter <= girlChar.length-1) {
					addedGirlChar = addedGirlChar + girlChar[addedGirlCharCounter];
					addedGirlCharCounter++;
					sfx.play();
				} else {
					beginPG = false;
				}
			}

			if(timerRect.x > 130 && timerRect.x <= 180) {
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

			if(timerRect.x > 180 && timerRect.x <= 250) {
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

			if(timerRect.x > 250 && timerRect.x <= 350) {
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

		if(timerRect.x >= 0 && timerRect.x <= 350) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

		if(girlRect.x == 480 && girlRect.y == 270) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}


	}

	public void render(Graphics graphics, Game game) {
		graphics.setFont(f);


		if(timerRect.x >= 10 && timerRect.x <= 80) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 80 && timerRect.x <= 130) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 60, 650);
		}

		if(timerRect.x > 130 && timerRect.x <= 180) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 180 && timerRect.x <= 250) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 60, 650);
		}

		if(timerRect.x > 250 && timerRect.x <= 350) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 350) {
			if(girlRect.x == 480 && girlRect.y == 270) {
				graphics.setFont(fontKey);
				graphics.drawString("[A] to interact", 60, 650);
			}
		}
	}

}
