package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

import entity.Sprite;
import entity.SpriteSheet;

public class CreateName {
	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public Rectangle backButton =  new Rectangle (10, 10, 300, 50);
	
	private Sprite[] sprites;
	
	public CreateName(SpriteSheet sheet) {
		sprites = sheet.getLoadedSprites();
		
//		JOptionPane.showInputDialog(this, "Please enter a username");
		
		try 
		{
			titleColor = new Color(0, 128, 0);
			titleFont = new Font("Century Gothic", Font.BOLD, 80);
			
			font = new Font("Arial", Font.BOLD, 30);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		// draw title
				graphics.setColor(titleColor);
				graphics.setFont(titleFont);
				graphics.drawString("What's your name?", 200, 150);
				
				graphics.setFont(font);
				graphics.drawString("Back", backButton.x + 120, backButton.y + 30);
				g2d.draw(backButton);
	}

}
