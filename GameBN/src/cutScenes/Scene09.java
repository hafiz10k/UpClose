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

// BOY - training
public class Scene09 {
	private BufferedImage training;
	private AnimatedSprite pbsAni;
	private AnimatedSprite boyAni;

	private Rectangle rect;

	private Rectangle boyRect;
	private Rectangle pbsRect;

	private Rectangle timerRect;

	private int speed = 10;
	private int boyDir = 0;
	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	// dialog
	private String[] pgDialog =
		{
				"This is the training ground where we train for upcoming war.",
				"Spain are planning to take over Brunei!",
				"There are going to be dangers, I'll teach you self-defense.",
		};

	private String[] boy = 
		{
				"You are preparing for war?!",
				"*inner thoughts* year 1578 + war with Spain = CASTILIAN WAR!"
		};

	private int pg = 0;	
	private int b = 0;

	private String addedPGChar = "";
	private String addedBoyChar = "";

	private int addedPGCharCounter = 0;
	private int addedBoyCharCounter = 0;

	private boolean beginPG = false;
	private boolean beginBoy = false;

	private Audio sfx;

	public Scene09(Game game) {
		//training bg
		training = game.loadImage("/DUMMY.png");

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
		pbsRect = new Rectangle(600, 150, 16, 40);
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

		System.out.println(boyRect.x + ", " + boyRect.y);

		try {


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


				if(timerRect.x > 350) {
					if(boyRect.x == 480 && boyRect.y == 270) {
						if(keyListener.a()) {
							Game.State = STATE.DUMMY;
						}
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
				// ANIMATING DIALOGS - BOY
				char boyChar[] = boy[b].toCharArray();
				if(beginBoy == false) {
					addedBoyChar = "";
					addedBoyCharCounter = 0;
					beginBoy = true;
					pg++;
				}
				if(addedBoyCharCounter <= boyChar.length-1) {
					addedBoyChar = addedBoyChar + boyChar[addedBoyCharCounter];
					addedBoyCharCounter++;
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
					b++;
				}
				if(addedPGCharCounter <= pgChar.length-1) {
					addedPGChar = addedPGChar + pgChar[addedPGCharCounter];
					addedPGCharCounter++;
					sfx.play();
				}
				else {
					beginBoy = false;
				}
			}

			if(timerRect.x > 180 && timerRect.x <= 250) {
				// ANIMATING DIALOGS - BOY
				char boyChar[] = boy[b].toCharArray();
				if(beginBoy == false) {
					addedBoyChar = "";
					addedBoyCharCounter = 0;
					beginBoy = true;
					pg++;
				}
				if(addedBoyCharCounter <= boyChar.length-1) {
					addedBoyChar = addedBoyChar + boyChar[addedBoyCharCounter];
					addedBoyCharCounter++;
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
		renderer.renderImage(training, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(boyAni, boyRect.x, boyRect.y, xZoom, yZoom, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, xZoom, yZoom, false);

		if(timerRect.x >= 0 && timerRect.x <= 350) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

		if(boyRect.x == 480 && boyRect.y == 270) {
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
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 60, 650);
		}

		if(timerRect.x > 130 && timerRect.x <= 180) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 180 && timerRect.x <= 250) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 60, 650);
		}

		if(timerRect.x > 250 && timerRect.x <= 350) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 350) {
			if(boyRect.x == 480 && boyRect.y == 270) {
				graphics.setFont(fontKey);
				graphics.drawString("[A] to interact", 60, 650);
			}
		}


	}

}
