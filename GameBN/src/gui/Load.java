package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Load {
//	public Rectangle playButton =  new Rectangle (340, 250, 300, 50);
//	public Rectangle loadButton = new Rectangle (340, 350, 300, 50);
//	public Rectangle helpButton = new Rectangle (340, 450, 300, 50);
//	public Rectangle quitButton = new Rectangle (340, 550, 300, 50);
	public Rectangle backButton =  new Rectangle (10, 10, 300, 50);
	
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		graphics.setFont(fnt0);
		graphics.setColor(Color.white);
		graphics.drawString("Load", 440, 140);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		graphics.setFont(fnt1);
		graphics.drawString("Back", backButton.x + 120, backButton.y + 30);
		g2d.draw(backButton);
	}
}
