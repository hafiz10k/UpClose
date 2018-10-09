package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import handler.RenderHandler;

public class Loot {
	private Sprite box;
	private Sprite bread;
	private Sprite potion;
	
	private Font f;
	private Font fs;
	
	public Loot(Game game) {
		BufferedImage boxImg = game.loadImage("/lootbox.png");
		SpriteSheet boxImgSheet = new SpriteSheet(boxImg);
		boxImgSheet.loadSprites(64, 40);
		
		BufferedImage breadImg = game.loadImage("/item-bread.png");
		SpriteSheet breadImgSheet = new SpriteSheet(breadImg);
		breadImgSheet.loadSprites(56, 56);

		box = boxImgSheet.getSprite(0, 0);
		bread = breadImgSheet.getSprite(0, 0);
		
		f = new Font("Arial", Font.BOLD, 50);
		fs = new Font("Arial", Font.BOLD, 30);
	}
	
	public void update(Game game) {
		
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderSprite(box, 200, 200, xZoom*3, yZoom*3, true);
		renderer.renderSprite(bread, 400, 300, xZoom, yZoom, true);
	}
	
	public void render(Graphics graphics) {
		graphics.setFont(f);
		graphics.setColor(Color.WHITE);
		graphics.drawString("CONGRATS! YOU GOT...", 200, 100);
		
		graphics.setFont(fs);
		graphics.setColor(Color.BLACK);;
		graphics.drawString("that's right, SLICE OF BREAD!", 270, 270);
		graphics.drawString("HP +3", 430, 500);
	}

}
