package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.Game;
import handler.KeyBoardListener;

public class Help implements MenuObject{

	private Color titleColor;
	private Font titleFont;

	private Font font;

	private String[] help = 
		{
				"↑ arrow Up - to move Up",
				"↓ arrow Down - to move Down",
				"← arrow Left - to move Left",
				"→ arrow Right - to move Right"
		};

	private int backChoice = 0;

	private String[] back = 
		{
				"Back"
		};

	public Help() {
		try 
		{

			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 80);

			font = new Font("Arial", Font.PLAIN, 30);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		graphics.setFont(titleFont);
		graphics.drawString("Help", 400, 120);

		graphics.setFont(font);
		for(int i = 0; i < help.length; i++) 
		{
			graphics.drawString(help[i], 300, 270 + i * 130);
		}

		for(int i = 0; i < back.length; i++) 
		{
			if (i == backChoice)
			{
				graphics.setColor(Color.RED);
			}
			else 
			{
				graphics.setColor(Color.WHITE);
			}
			graphics.drawString(back[i], 50, 70);
		}

	}

	@Override
	public void update(Game game) {
		try {
			KeyBoardListener keyListener = game.getKeyListener();

			boolean back = false;
			if(keyListener.enter()) {
				back = true;
				select();

			}
			if(back) {
				Thread.sleep(150);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void select() {
		if(backChoice == 0)
		{
			Game.State = Game.STATE.MENU;

		}

	}
}

