package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Rectangle;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;


public class CreateName implements MenuObject{

	private Color titleColor;
	private Font titleFont;

	private Font font;
	private Font fontKey;
	
	private BufferedImage bg;

	private char[] name = {'A', 'A', 'A', 'A', 'A'};
	private String[] key = 
		{
				"↑ - Up",
				"↓ - Down",
				"← - Left",
				"→ - Right",
				"[ENTER] - Select"
		};
	
	private int currentChoice = 0;
	public String fullName = "";
	
	private Rectangle keyRect;

	public CreateName(Game game) {
		try 
		{

			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 50);
			
			bg = game.loadImage("/name_bg.png");

			font = new Font("Arial", Font.BOLD, 40);		
			fontKey = new Font("Arial", Font.PLAIN, 20);
			
			keyRect = new Rectangle(0, 720, 400, 20);
			keyRect.generateGraphics(0xebebeb);


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.setFont(titleFont);
		graphics.drawString("What is your name?", 200, 150);

		graphics.setFont(font);	

		for(int i = 0; i < name.length; i++) 
		{
			if (i == currentChoice) {
				graphics.setColor(Color.RED);
			}
			else {
				graphics.setColor(Color.BLACK);
			}

			graphics.drawString("" + name[i], 260 + i * 100, 350);

		}
		
		for(int k = 0; k < key.length; k++) {
			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString("" + key[k], 150 + k * 150, 740);
		}
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		
		renderer.renderRectangle(keyRect, xZoom, yZoom, true);
	}

	@Override
	public void update(Game game) {
		try {
			KeyBoardListener keyListener = game.getKeyListener();
			boolean didMove = false;
			
			if(keyListener.enter()) {
				fullName = new String(name);

				Game.State = Game.STATE.GENDER;
			}

			if(keyListener.up()) {
				name[currentChoice]++;
			}

			if(keyListener.down()) {
				name[currentChoice]--;
			}

			if(keyListener.left()) {
				currentChoice --;
				if(currentChoice == -1)
				{
					currentChoice = name.length - 1;
				}
			}

			if(keyListener.right()) {
				currentChoice ++;
				if(currentChoice == name.length)
				{
					currentChoice = 0;
				}
			}

				Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return fullName.toLowerCase();
	}

}