package otherScene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cutScenes.Scene02;
import entity.Rectangle;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;
import menuComponents.Gender;

public class hospitalScene {

	public Gender gender;
	private BufferedImage land;

	private Rectangle timerRect;
	private Rectangle rect;

	private boolean beginReception = false;

	private int r = 0;
	private String addedReceptionChar = "";
	private int addedReceptionCharCounter = 0;

	private int currentChoice = 0;

	// dialog
	private String[] reception =
		{
				"Hello, you seem tired, can I help you?"
		};

	private String[] options = 
		{
				"Heal",
				"Nevermind.",
		};

	private String key = "press [A]";

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public hospitalScene(Game game) {
		// land
		land = game.loadImage("/hospital_scene.png");

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);

	}

	public void update(Game game) {
		timerRect.x++;
		System.out.println(timerRect.x);

		KeyBoardListener keyListener = game.getKeyListener();
		boolean didMove = false;
		try {
			if(timerRect.x >= 10) {
				char receptionChar[] = reception[r].toCharArray();
				if(addedReceptionCharCounter <= receptionChar.length-1) {
					addedReceptionChar = addedReceptionChar + receptionChar[addedReceptionCharCounter];
					addedReceptionCharCounter++;
				}

				if(keyListener.a()) {
					didMove = true;
					timerRect.x = 75;
				}
			}
			if(timerRect.x >= 75) {
				if(keyListener.enter()) {
					didMove = true;
					select();
				}
				if(keyListener.up()) {
					didMove = true;
					currentChoice --;
					if(currentChoice == -1)
					{
						currentChoice = options.length -1;
					}
				}

				if(keyListener.down()) {
					didMove = true;
					currentChoice ++;
					if(currentChoice == options.length)
					{
						currentChoice = 0;
					}
				}
			}

			Thread.sleep(100);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom, Game game) {
		renderer.renderImage(land, 10, 150, xZoom, yZoom, true);
		renderer.renderRectangle(timerRect, xZoom, yZoom, false);

		if(timerRect.x >= 10) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}

	}
	
	public void select() {
		if(currentChoice == 0){
			System.out.println("NOOOO");

		}
		if(currentChoice == 1)
		{
			// load
			Game.State = Game.STATE.GAME;

		}
	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		if(timerRect.x >= 10 && timerRect.x < 75) {
			graphics.drawString(addedReceptionChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
		}

		if(timerRect.x >= 75) {
			for(int i = 0; i < options.length; i++) {
				if (i == currentChoice) {
					graphics.setColor(Color.RED);
				}
				else {
					graphics.setColor(Color.BLACK);
				}
				graphics.drawString(options[i], 70, 650 + i * 50);
			}
		}

	}

}
