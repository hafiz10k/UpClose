package cutScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entity.Player;
import entity.Rectangle;
import game.Game;
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;
import menuComponents.Menu;

// player being sucked in the blackhole
public class Scene02 {
	private Audio a;

	private Rectangle rect;
	private Rectangle timerRect;

	private String[] girl =
		{
				"Abang!! I am hereeee!!",
				"I don't know, I can't see anything.",
		};

	private String[] boy = 
		{
				"HELLO?! ADIK WHERE ARE YOU?",
				"Where is 'here'? What do you mean?",
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

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	private Audio sfx;

	public Scene02(Game game) {
		a = new Audio("/sfx/portal-scene1,2,3.wav");

		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);

		sfx = new Audio("/sfx/dialog.mp3");

	}

	public void update(Game game) {
		timerRect.x++;

		System.out.println(timerRect.x);

		//DIALOGS
		if(timerRect.x > 10 && timerRect.x <= 40) {

			// ANIMATING DIALOGS - BOY
			char boyChar[] = boy[b].toCharArray();
			if(beginBoy == false) {
				addedBoyChar = "";
				addedBoyCharCounter = 0;
				beginBoy = true;
			}
			if(addedBoyCharCounter <= boyChar.length-1) {
				addedBoyChar = addedBoyChar + boyChar[addedBoyCharCounter];
				addedBoyCharCounter++;
				sfx.play();
			} 
		}

		if(timerRect.x > 40 && timerRect.x <= 70) {
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


		if(timerRect.x > 70 && timerRect.x <= 110) {
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

		if(timerRect.x > 110 && timerRect.x <= 160) {
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

		if(timerRect.x >= 0) {

			KeyBoardListener keyListener = game.getKeyListener();

			boolean didMove = false;
			if(keyListener.a()) {
				didMove = true;
				Game.State = Game.STATE.NAME;
			}

		}
		
		if(timerRect.x >= 170) {
			Game.State = Game.STATE.NAME;
		}

		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void render(RenderHandler renderer, Graphics graphics, int xZoom, int yZoom)
	{
		for(int index = 0; index < renderer.getPixels().length; index++) {
			renderer.getPixels()[index] = (int)(Math.random() * 0xFFFFFF);
		}

		graphics.drawImage(renderer.getView(), 0, 0, renderer.getView().getWidth(), renderer.getView().getHeight(), null);

		if(timerRect.x > 10 && timerRect.x <= 160) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
		
	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		if(timerRect.x > 10 && timerRect.x < 40) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}

		if(timerRect.x >= 40 && timerRect.x <= 70) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}

		if(timerRect.x > 70 && timerRect.x <= 110) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(addedBoyChar, 70, 650);
			
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}

		if(timerRect.x > 110 && timerRect.x <= 160) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedGirlChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}
	}

	public Audio getAudio() {
		return a;
	}
}
