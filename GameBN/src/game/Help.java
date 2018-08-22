package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Help {
	
//	public Rectangle playButton =  new Rectangle (340, 250, 300, 50);
//	public Rectangle loadButton = new Rectangle (340, 350, 300, 50);
//	public Rectangle helpButton = new Rectangle (340, 450, 300, 50);
//	public Rectangle quitButton = new Rectangle (340, 550, 300, 50);
	
	public void render(Graphics graphics) {
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		graphics.setFont(fnt0);
		graphics.setColor(Color.white);
		graphics.drawString("Help", 440, 140);
	}
}
