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

public class LailaRatnaScene {

	private BufferedImage scene;

	private Rectangle rect;
	private Rectangle timerRect;


	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	// dialog
	private String[] laila =
		{
				"Seri Ratna, I heard Pengiran Bendahara has trained somebody.",
				"Yeah, hmmm but something seems off..",
				"That's true, I think it's time we choose side now.",
				"Yes.. Us, Pengiran Seri Laila and Pengiran Seri Ratna.",
				"CATCH THAT PERSON!!"
		};


	private String[] ratna = 
		{
				"Really, Seri Laila?!",
				"Scratch that, we have bigger problems going on.",
				"Yes! if Spain wins, DR. Francisco promises, Brunei will be ours!",
				"*saw you* HEY! WHO ARE YOU!"
				
		};

	private String key = "press [A]";

	private int l = 0;	
	private int r = 0;

	private String addedlailaChar = "";
	private String addedratnaChar = "";

	private int addedlailaCharCounter = 0;
	private int addedratnaCharCounter = 0;

	private boolean beginratna = false;
	private boolean beginlaila = false;
	
	private Audio bgm;

	public LailaRatnaScene(Game game) {
		//bedroom bg
		scene = game.loadImage("/lailaRatna_Scene.png");

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
	}

	public void update(Game game) {
		timerRect.x++;

		try {
			//DIALOGS

			if(timerRect.x > 10 && timerRect.x <= 75) {
				// ANIMATING DIALOGS - laila
				char lailaChar[] = laila[l].toCharArray();
				if(beginlaila == false) {
					addedlailaChar = "";
					addedlailaCharCounter = 0;
					beginlaila = true;
				}
				if(addedlailaCharCounter <= lailaChar.length-1) {
					addedlailaChar = addedlailaChar + lailaChar[addedlailaCharCounter];
					addedlailaCharCounter++;
				}
			}

			if(timerRect.x > 75 && timerRect.x <= 100) {
				// ANIMATING DIALOGS - ratna
				char ratnaChar[] = ratna[r].toCharArray();
				if(beginratna == false) {
					addedratnaChar = "";
					addedratnaCharCounter = 0;
					beginratna = true;
					l++;
				}
				if(addedratnaCharCounter <= ratnaChar.length-1) {
					addedratnaChar = addedratnaChar + ratnaChar[addedratnaCharCounter];
					addedratnaCharCounter++;
				} else {
					beginlaila = false;
				}


			}


			if(timerRect.x > 100 && timerRect.x <= 160) {
				// ANIMATING DIALOGS - laila
				char lailaChar[] = laila[l].toCharArray();
				if(beginlaila == false) {
					addedlailaChar = "";
					addedlailaCharCounter = 0;
					beginlaila = true;
					r++;
				}
				if(addedlailaCharCounter <= lailaChar.length-1) {
					addedlailaChar = addedlailaChar + lailaChar[addedlailaCharCounter];
					addedlailaCharCounter++;
				}
				else {
					beginratna = false;
				}
			}
			
			if(timerRect.x > 160 && timerRect.x <= 220) {
				// ANIMATING DIALOGS - ratna
				char ratnaChar[] = ratna[r].toCharArray();
				if(beginratna == false) {
					addedratnaChar = "";
					addedratnaCharCounter = 0;
					beginratna = true;
					l++;
				}
				if(addedratnaCharCounter <= ratnaChar.length-1) {
					addedratnaChar = addedratnaChar + ratnaChar[addedratnaCharCounter];
					addedratnaCharCounter++;
				} else {
					beginlaila = false;
				}


			}
			
			if(timerRect.x > 220 && timerRect.x <= 280) {
				// ANIMATING DIALOGS - laila
				char lailaChar[] = laila[l].toCharArray();
				if(beginlaila == false) {
					addedlailaChar = "";
					addedlailaCharCounter = 0;
					beginlaila = true;
					r++;
				}
				if(addedlailaCharCounter <= lailaChar.length-1) {
					addedlailaChar = addedlailaChar + lailaChar[addedlailaCharCounter];
					addedlailaCharCounter++;
				}
				else {
					beginratna = false;
				}
			}
			
			if(timerRect.x > 280 && timerRect.x <= 350) {
				// ANIMATING DIALOGS - ratna
				char ratnaChar[] = ratna[r].toCharArray();
				if(beginratna == false) {
					addedratnaChar = "";
					addedratnaCharCounter = 0;
					beginratna = true;
					l++;
				}
				if(addedratnaCharCounter <= ratnaChar.length-1) {
					addedratnaChar = addedratnaChar + ratnaChar[addedratnaCharCounter];
					addedratnaCharCounter++;
				} else {
					beginlaila = false;
				}

			}
			
			if(timerRect.x > 350 && timerRect.x <= 420) {
				// ANIMATING DIALOGS - laila
				char lailaChar[] = laila[l].toCharArray();
				if(beginlaila == false) {
					addedlailaChar = "";
					addedlailaCharCounter = 0;
					beginlaila = true;
					r++;
				}
				if(addedlailaCharCounter <= lailaChar.length-1) {
					addedlailaChar = addedlailaChar + lailaChar[addedlailaCharCounter];
					addedlailaCharCounter++;
				}
				else {
					beginratna = false;
				}
			}
			
			if(timerRect.x > 420 && timerRect.x <= 470) {
				// ANIMATING DIALOGS - ratna
				char ratnaChar[] = ratna[r].toCharArray();
				if(beginratna == false) {
					addedratnaChar = "";
					addedratnaCharCounter = 0;
					beginratna = true;
					l++;
				}
				if(addedratnaCharCounter <= ratnaChar.length-1) {
					addedratnaChar = addedratnaChar + ratnaChar[addedratnaCharCounter];
					addedratnaCharCounter++;
				} else {
					beginlaila = false;
				}

			}
			
			if(timerRect.x > 470 && timerRect.x <= 520) {
				// ANIMATING DIALOGS - laila
				char lailaChar[] = laila[l].toCharArray();
				if(beginlaila == false) {
					addedlailaChar = "";
					addedlailaCharCounter = 0;
					beginlaila = true;
				}
				if(addedlailaCharCounter <= lailaChar.length-1) {
					addedlailaChar = addedlailaChar + lailaChar[addedlailaCharCounter];
					addedlailaCharCounter++;
				}
			}
			


			if(timerRect.x >= 10) {
				KeyBoardListener keyListener = game.getKeyListener();

				if(keyListener.a()) {
					Game.State = Game.STATE.LAILARATNA;
				}

			}

			Thread.sleep(80);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(scene, 10, 150, xZoom, yZoom, true);;

		if(timerRect.x > 10) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
		
		renderer.renderRectangle(timerRect, xZoom, yZoom, false);

	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		if(timerRect.x > 10 && timerRect.x <= 75) {

			graphics.setColor(Color.BLUE);
			graphics.drawString(addedlailaChar, 70, 650);


		}

		if(timerRect.x > 75 && timerRect.x <= 100) {
			graphics.setColor(Color.RED);
			graphics.drawString(addedratnaChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}

		if(timerRect.x > 100 && timerRect.x <= 160) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedlailaChar, 70, 650);
		}
		
		if(timerRect.x > 160 && timerRect.x <= 220) {
			graphics.setColor(Color.RED);
			graphics.drawString(addedratnaChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}
		
		if(timerRect.x > 220 && timerRect.x <= 280) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedlailaChar, 70, 650);
		}
		
		if(timerRect.x > 280 && timerRect.x <= 350) {
			graphics.setColor(Color.RED);
			graphics.drawString(addedratnaChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}
		
		if(timerRect.x > 350 && timerRect.x <= 420) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedlailaChar, 70, 650);
		}
		
		if(timerRect.x > 420 && timerRect.x <= 470) {
			graphics.setColor(Color.RED);
			graphics.drawString(addedratnaChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}
		
		if(timerRect.x > 470 && timerRect.x <= 520) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedlailaChar, 70, 650);
		}
	}

}
