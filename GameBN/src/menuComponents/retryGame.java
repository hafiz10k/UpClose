package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.Game;

public class retryGame implements MenuObject{
	
	private Font titleFont;
	private Color titleColor;
	
	private Font font;
	
	public retryGame(Game game) {
		titleColor = new Color(100, 128, 128);
		titleFont = new Font("Broadway", Font.BOLD, 80);

		font = new Font("Arial", Font.PLAIN, 30);
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setFont(titleFont);
		graphics.setColor(titleColor);
		graphics.drawString("PLAYER IS DEAD", 100, 100);
		
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		
	}

}
