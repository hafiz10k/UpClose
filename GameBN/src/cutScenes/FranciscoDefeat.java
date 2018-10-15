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

public class FranciscoDefeat {

	private BufferedImage scene;

	private Rectangle rect;
	private Rectangle timerRect;


	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	// dialog
	private String[] francisco =
		{
				"FALLBACK!! FALBACK!! WE HAVE BEEN DEFEATED!",
		};


	private String key = "press [A]";

	private int fr = 0;	

	private String addedFransChar = "";

	private int addedFransCharCounter = 0;

	private boolean beginFrans = false;
	
	public Audio bgm;
	private Audio sfx;

	public FranciscoDefeat(Game game) {

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

			if(timerRect.x > 10 && timerRect.x <= 100) {
				// ANIMATING DIALOGS - soldier
				char fransChar[] = francisco[fr].toCharArray();
				if(beginFrans == false) {
					addedFransChar = "";
					addedFransCharCounter = 0;
					beginFrans = true;
				}
				if(addedFransCharCounter <= fransChar.length-1) {
					addedFransChar = addedFransChar + fransChar[addedFransCharCounter];
					addedFransCharCounter++;
					sfx.play();
				}


			}

			if(timerRect.x > 10) {
				KeyBoardListener keyListener = game.getKeyListener();

				if(keyListener.a()) {
					Game.State = Game.STATE.FRANSARRIVE;
				}

			}
			
			if(timerRect.x >= 110) {
				Game.State = Game.STATE.FRANSARRIVE;
			}

			Thread.sleep(80);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(timerRect.x > 10 && timerRect.x <= 100) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		if(timerRect.x > 10 && timerRect.x <= 100) {

			graphics.setColor(Color.RED);
			graphics.drawString(addedFransChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

		}
	}

}
