package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	public Rectangle playButton = new Rectangle (Game.WIDTH /2 + 120, 150, 100, 50);
	public Rectangle LoadButton = new Rectangle (Game.WIDTH /2 + 120, 250, 100, 50);
	public Rectangle HelpButton = new Rectangle (Game.WIDTH /2 + 120, 250, 100, 50);
	public Rectangle QuitButton = new Rectangle (Game.WIDTH /2 + 120, 350, 100, 50);
	
	
	
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		graphics.setFont(fnt0);
		graphics.setColor(Color.black);
		graphics.drawString("UP CLOSE", Game.WIDTH / 2, 100);
		
		
		
		
	}
	
}
