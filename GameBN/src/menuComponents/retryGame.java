package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import game.Game;
import handler.KeyBoardListener;

public class retryGame implements MenuObject{
	
	private Font titleFont;
	private Color titleColor;
	
	private Font font;
	
	private Font f;
	
	private int loadChoice = 0;

	private String[] load = 
		{
				"Yes",
				"No"
		};
	
	public retryGame(Game game) {
		titleFont = new Font("Broadway", Font.BOLD, 80);
		font = new Font("Arial", Font.PLAIN, 40);
		
		f = new Font("Arial", Font.PLAIN, 50);
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setFont(titleFont);
		graphics.setColor(Color.RED);
		graphics.drawString("PLAYER IS DEAD :(", 90, 120);
		
		graphics.setFont(f);
		graphics.setColor(Color.WHITE);
		graphics.drawString("Retry game?", 330, 300);

		
		graphics.setFont(font);
		for(int i = 0; i < load.length; i++) 
		{
			if (i == loadChoice)
			{
				graphics.setColor(Color.YELLOW);
			}
			else 
			{
				graphics.setColor(Color.WHITE);
			}
			graphics.drawString(load[i], 300 + i * 300, 430);
		}
	}

	@Override
	public void update(Game game) {
		try {
			
			KeyBoardListener keyListener = game.getKeyListener();

			boolean loading = false;

			if(keyListener.enter()) {
				loading = true;

			}

			if(keyListener.left()) {
				loading = true;
				loadChoice --;
				if(loadChoice == -1)
				{
					loadChoice = load.length -1;
				}
			}

			if(keyListener.right()) {
				loading = true;
				loadChoice ++;
				if(loadChoice == load.length)
				{
					loadChoice = 0;
				}
			}

				Thread.sleep(150);


		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
