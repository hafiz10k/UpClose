package cutScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Player;
import entity.Rectangle;
import game.Game;
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class FranciscoScene02 {

	private BufferedImage scene;

	private Rectangle rect;
	private Rectangle timerRect;


	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	// dialog
	private String[] frans =
		{
				"BRUNEI NEED TO STOP SPREADING ISLAM IN PHILIPPINES!!",
				"We will take over your land, Pengiran Bendahara!",
				"Come on fellas, stick 'em with the pointy end!"
		};


	private String[] pbs = 
		{
				"You can't stop us with just this, Francisco!",
				"Not as long as i'm around, we will DEFEAT YOU!!",
				"LET'S GO WARRIORS!!"
		};

	private String key = "press [A]";

	private int fr = 0;	
	private int pg = 0;

	private String addedFransChar = "";
	private String addedPgChar = "";

	private int addedFransCharCounter = 0;
	private int addedPgCharCounter = 0;

	private boolean beginFrans = false;
	private boolean beginPg = false;
	
	public Audio bgm;
	private Audio sfx;

	public FranciscoScene02(Game game) {
		//bedroom bg
		scene = game.loadImage("/franciscoScene2.png");

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
		
		bgm = new Audio("/bgm/francisco.mp3");
		sfx = new Audio("/sfx/dialog.mp3");
	}

	public void update(Game game) {
		timerRect.x++;

		try {
			//DIALOGS

			if(timerRect.x > 10 && timerRect.x <= 80) {
				// ANIMATING DIALOGS - frans
				char lailaChar[] = frans[fr].toCharArray();
				if(beginFrans == false) {
					addedFransChar = "";
					addedFransCharCounter = 0;
					beginFrans = true;
				}
				if(addedFransCharCounter <= lailaChar.length-1) {
					addedFransChar = addedFransChar + lailaChar[addedFransCharCounter];
					addedFransCharCounter++;
					sfx.play();
				}
			}

			if(timerRect.x > 80 && timerRect.x <= 140) {
				// ANIMATING DIALOGS - pgbendahara
				char ratnaChar[] = pbs[pg].toCharArray();
				if(beginPg == false) {
					addedPgChar = "";
					addedPgCharCounter = 0;
					beginPg = true;
					fr++;
				}
				if(addedPgCharCounter <= ratnaChar.length-1) {
					addedPgChar = addedPgChar + ratnaChar[addedPgCharCounter];
					addedPgCharCounter++;
					sfx.play();
				} else {
					beginFrans = false;
				}

			}
			
			if(timerRect.x > 140 && timerRect.x <= 200) {
				// ANIMATING DIALOGS - frans
				char lailaChar[] = frans[fr].toCharArray();
				if(beginFrans == false) {
					addedFransChar = "";
					addedFransCharCounter = 0;
					beginFrans = true;
					pg++;
				}
				if(addedFransCharCounter <= lailaChar.length-1) {
					addedFransChar = addedFransChar + lailaChar[addedFransCharCounter];
					addedFransCharCounter++;
					sfx.play();
				}
				
				 else {
						beginPg = false;
					}
			}
			
			if(timerRect.x > 200 && timerRect.x <= 260) {
				// ANIMATING DIALOGS - pgbendahara
				char ratnaChar[] = pbs[pg].toCharArray();
				if(beginPg == false) {
					addedPgChar = "";
					addedPgCharCounter = 0;
					beginPg = true;
					fr++;
				}
				if(addedPgCharCounter <= ratnaChar.length-1) {
					addedPgChar = addedPgChar + ratnaChar[addedPgCharCounter];
					addedPgCharCounter++;
					sfx.play();
				} else {
					beginFrans = false;
				}

			}
			
			if(timerRect.x > 260 && timerRect.x <= 320) {
				// ANIMATING DIALOGS - frans
				char lailaChar[] = frans[fr].toCharArray();
				if(beginFrans == false) {
					addedFransChar = "";
					addedFransCharCounter = 0;
					beginFrans = true;
					pg++;
				}
				if(addedFransCharCounter <= lailaChar.length-1) {
					addedFransChar = addedFransChar + lailaChar[addedFransCharCounter];
					addedFransCharCounter++;
					sfx.play();
				}
				
				 else {
						beginPg = false;
					}
			}
			
			if(timerRect.x > 320 && timerRect.x <= 360) {
				// ANIMATING DIALOGS - pgbendahara
				char ratnaChar[] = pbs[pg].toCharArray();
				if(beginPg == false) {
					addedPgChar = "";
					addedPgCharCounter = 0;
					beginPg = true;
					fr++;
				}
				if(addedPgCharCounter <= ratnaChar.length-1) {
					addedPgChar = addedPgChar + ratnaChar[addedPgCharCounter];
					addedPgCharCounter++;
					sfx.play();
				} else {
					beginFrans = false;
				}

			}

			if(timerRect.x >= 10) {
				KeyBoardListener keyListener = game.getKeyListener();

				if(keyListener.a()) {
					Game.State = Game.STATE.FRANCISCO;
				}

			}
			
			if(timerRect.x >= 370) {
				Game.State = Game.STATE.FRANCISCO;
			}

			Thread.sleep(80);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(scene, 10, 150, xZoom, yZoom, true);;

		if(timerRect.x > 10 && timerRect.x <= 360) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		if(timerRect.x > 10 && timerRect.x <= 80) {

			graphics.setColor(Color.RED);
			graphics.drawString(addedFransChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}

		if(timerRect.x > 80 && timerRect.x <= 140) {

			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPgChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}

		if(timerRect.x > 140 && timerRect.x <= 200) {
			graphics.setColor(Color.RED);
			graphics.drawString(addedFransChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}
		
		if(timerRect.x > 200 && timerRect.x <= 260) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPgChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}
		
		if(timerRect.x > 260 && timerRect.x <= 320) {
			graphics.setColor(Color.RED);
			graphics.drawString(addedFransChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}
		
		if(timerRect.x > 320 && timerRect.x <= 360) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPgChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}
	}

}
