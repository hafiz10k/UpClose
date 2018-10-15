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
public class Ending {
	private Audio a;

	private Rectangle rect;
	private Rectangle timerRect;

	private String[] player =
		{
				"AGAIN?!! WHEN AM I GOING HOME?!!!",
		};

	private String[] narrator = 
		{
				"and so the journey continues...",
		};

	private String key = "press [A]";

	private int p = 0;	
	private int n = 0;

	private String addedPlayerChar = "";
	private String addedNChar = "";

	private int addedPlayerCharCounter = 0;
	private int addedNCharCounter = 0;

	private boolean beginP = false;
	private boolean beginN = false;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	private Audio sfx;

	public Ending(Game game) {
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

		//DIALOGS
		if(timerRect.x > 10 && timerRect.x <= 60) {

			// ANIMATING DIALOGS - BOY
			char pChar[] = player[p].toCharArray();
			if(beginP == false) {
				addedPlayerChar = "";
				addedPlayerCharCounter = 0;
				beginP = true;
			}
			if(addedPlayerCharCounter <= pChar.length-1) {
				addedPlayerChar = addedPlayerChar + pChar[addedPlayerCharCounter];
				addedPlayerCharCounter++;
				sfx.play();
			} 
		}

		if(timerRect.x > 60 && timerRect.x <= 120) {
			// ANIMATING DIALOGS - GIRL
			char nChar[] = narrator[n].toCharArray();
			if(beginN == false) {
				addedNChar = "";
				addedNCharCounter = 0;
				beginN = true;
			}
			if(addedNCharCounter <= nChar.length-1) {
				addedNChar = addedNChar + nChar[addedNCharCounter];
				addedNCharCounter++;
				sfx.play();
			}

		}
		
		if(timerRect.x >= 170) {
//			Game.State = Game.STATE.NAME;
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

		if(timerRect.x > 10 && timerRect.x <= 60) {
			graphics.setColor(Color.GREEN);
			graphics.drawString(addedPlayerChar, 70, 650);
			
		}

		if(timerRect.x > 60 && timerRect.x <= 120) {
			graphics.setColor(Color.BLACK);
			graphics.drawString(addedNChar, 70, 650);
		}

	}

}
