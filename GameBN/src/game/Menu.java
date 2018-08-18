package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

public class Menu {
	
	public Rectangle playButton =  new Rectangle (340, 250, 300, 50);
	public Rectangle loadButton = new Rectangle (340, 350, 300, 50);
	public Rectangle helpButton = new Rectangle (340, 450, 300, 50);
	public Rectangle quitButton = new Rectangle (340, 550, 300, 50);
	
	
	
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		graphics.setFont(fnt0);
		graphics.setColor(Color.white);
		graphics.drawString("UP CLOSE", 360, 140);
		
		
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		graphics.setFont(fnt1);
		graphics.drawString("Play", playButton.x + 120, playButton.y + 30);
		g2d.draw(playButton);
		graphics.drawString("Load", loadButton.x + 120, loadButton.y + 30);
		g2d.draw(loadButton);
		graphics.drawString("Help", helpButton.x + 120, helpButton.y + 30);
		g2d.draw(helpButton);
		graphics.drawString("Quit", quitButton.x + 120, quitButton.y + 30);
		g2d.draw(quitButton);
		
	}
	
}
