package cutScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.Timer;
import javax.xml.stream.events.EndDocument;

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

// BOY - in pg bendahara house
public class Scene05 {

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
	private int boyDir = 0;
	private int pgDir = 0;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	// dialog
	private String[] pgDialog =
		{
				"Little boy, are you awake now?",
				"You are currently in Jerudong, our hideout.",
				"Little sister? I found you alone passed out in the woods",
				"I'm Pengiran Bendahara Sakam, Brunei's mighty warrior!",
				"Did you hit your head hard? it's year 1578.",
				"Come, follow me outside if you are already feeling better",
		};

	private String[] boy = 
		{
				"Ugh.. where am i?",
				"Where's my little sister?!",
				"But I was in my room! how come... wait, who are you?",
				"Huh? warrior? stop joking. what year is this?!",
				"oh..... my.... god...."
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

	public Scene05(Game game) {
		//bedroom bg
		room = game.loadImage("/pbs-bedroom.png");

		//male
		BufferedImage playerSheetImage = game.loadImage("/blackout.png");
		SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
		boySheet.loadSprites(24, 32);

		boyAni = new AnimatedSprite(boySheet, 2);


		//pengiran bendahara sakam
		BufferedImage pbsImage = game.loadImage("/pg-animation.png");
		SpriteSheet pbsSheet = new SpriteSheet(pbsImage);
		pbsSheet.loadSprites(16, 40);

		pbsAni = new AnimatedSprite(pbsSheet, 10);

		//boy rect
		boyRect = new Rectangle(80, 420, 24, 32);
		boyRect.generateGraphics(3, 0x0000ff);

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

			// BOY MOVEMENT
			if(boyAni != null) {
				boolean didMove = false;
				int newDirection = boyDir;



				if(timerRect.x >= 22) {
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

			if(timerRect.x > 90 && timerRect.x <= 150) {
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

			if(timerRect.x > 150 && timerRect.x <= 180) {
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

			if(timerRect.x > 180 && timerRect.x <= 250) {
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

			if(timerRect.x > 250 && timerRect.x <= 320) {
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

			if(timerRect.x > 390 && timerRect.x <= 450) {
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
				}
				else {
					beginPG = false;
				}
			}

			if(timerRect.x > 450 && timerRect.x <= 500) {
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

			if(timerRect.x > 500 && timerRect.x <= 530) {
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
				}
				else {
					beginPG = false;
				}
			}

			if(timerRect.x > 530 && timerRect.x <= 600) {
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
					Game.State = Game.STATE.SCENE07;
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
		renderer.renderSprite(boyAni, boyRect.x, boyRect.y, xZoom, yZoom, false);
		renderer.renderSprite(pbsAni, pbsRect.x, pbsRect.y, xZoom, yZoom, false);

		if(timerRect.x >= 25) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

	}

	public void render(Graphics graphics, Game game) {
		graphics.setFont(f);
		if(timerRect.x >= 25 && timerRect.x <= 60) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 60 && timerRect.x <= 90) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 60, 650);
		}

		if(timerRect.x > 90 && timerRect.x <= 150) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 150 && timerRect.x <= 180) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 60, 650);
		}

		if(timerRect.x > 180 && timerRect.x <= 250) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 250 && timerRect.x <= 320) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 60, 650);
		}

		if(timerRect.x > 320 && timerRect.x <= 390) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 390 && timerRect.x <= 450) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 60, 650);
		}

		if(timerRect.x > 450 && timerRect.x <= 500) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x > 500 && timerRect.x <= 530) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 60, 650);
		}

		if(timerRect.x > 530 && timerRect.x <= 600) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPGChar, 60, 650);
		}

		if(timerRect.x >= 610) {
			Game.State = STATE.SCENE07;
		}

	}

}
