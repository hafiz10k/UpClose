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
				"Abang!!",
				"I am hereeee!!",
				"I dont know, I can't see anything."
		};

	private String[] boy = 
		{
				"HELLO?! ADIK WHERE ARE YOU?",
				"Where are you?",
				"Where is here? What do you mean? Ohh~~"
		};

	private int i = 0;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Scene02(Game game) {
		a = new Audio("/portal-scene1,2,3.wav");
//				a.play();

		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);

	}

	public void update(Game game) {

		timerRect.x++;

		System.out.println(timerRect.x);

		if(timerRect.x >= 80) {

			KeyBoardListener keyListener = game.getKeyListener();

			boolean didMove = false;
			if(keyListener.a()) {
				didMove = true;
				Game.State = Game.STATE.NAME;
			}

		}


	}

	public void render(RenderHandler renderer, Graphics graphics, int xZoom, int yZoom)
	{
		for(int index = 0; index < renderer.getPixels().length; index++) {
			renderer.getPixels()[index] = (int)(Math.random() * 0xFFFFFF);

		}

		graphics.drawImage(renderer.getView(), 0, 0, renderer.getView().getWidth(), renderer.getView().getHeight(), null);

		renderer.renderRectangle(rect, xZoom, yZoom, true);
		renderer.renderRectangle(timerRect, xZoom, yZoom, false);


	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		if(timerRect.x < 80) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(boy[i], 70, 650);
		}

		if(timerRect.x >= 80) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(girl[i], 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("press [A]", 800, 740);
		}

	}

	public Audio getAudio() {
		return a;
	}
}
