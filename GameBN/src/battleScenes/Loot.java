package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Player;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Loot {
	private Sprite box;
	private Sprite item;
	private Sprite potion;
	
	private BufferedImage itemImg ;
	private SpriteSheet itemImgSheet;
	
	private Font f;
	private Font fs;
	
	public Loot(Game game) {
		BufferedImage boxImg = game.loadImage("/lootbox.png");
		SpriteSheet boxImgSheet = new SpriteSheet(boxImg);
		boxImgSheet.loadSprites(64, 40);
		
		itemImg = game.loadImage("/loot_item.png");
		itemImgSheet = new SpriteSheet(itemImg);
		itemImgSheet.loadSprites(80, 96);

		box = boxImgSheet.getSprite(0, 0);
		item = itemImgSheet.getSprite(0, 0);
		
		f = new Font("Arial", Font.BOLD, 50);
		fs = new Font("Arial", Font.BOLD, 30);
	}
	
	public void update(Game game) {
		KeyBoardListener keyListener = game.getKeyListener();
		
		if(keyListener.esc()) {
			Game.State = STATE.GAME;
		}
		
		if(game.player.getRectangle().x >= 3950 && game.player.getRectangle().x <= 4035 && game.player.getRectangle().y >= -1090 && game.player.getRectangle().y <= -1005) {
			item = itemImgSheet.getSprite(1, 0);
		}
		
	}
	
	public void render(RenderHandler renderer, Player player, int xZoom, int yZoom) {
		renderer.renderSprite(box, 200, 200, xZoom*3, yZoom*3, true);
		
		
		if(player.getRectangle().x >= 3950 && player.getRectangle().x <= 4035 && player.getRectangle().y >= -1090 && player.getRectangle().y <= -1005) {
			renderer.renderSprite(item, 350, 250, xZoom, yZoom, true);
		}
	}
	
	public void render(Graphics graphics, Game game) {
		graphics.setFont(f);
		graphics.setColor(Color.WHITE);
		graphics.drawString("CONGRATS! YOU GOT...", 200, 100);
		
		graphics.setFont(fs);
		graphics.setColor(Color.BLACK);
		
		if(game.player.getRectangle().x >= 3950 && game.player.getRectangle().x <= 4035 && game.player.getRectangle().y >= -1090 && game.player.getRectangle().y <= -1005) {
			graphics.drawString("that's right, POTION!", 270, 250);
			graphics.drawString("HP +3", 430, 520);
		}
//		graphics.drawString("that's right, SLICE OF BREAD!", 270, 250);
//		graphics.drawString("HP +3", 430, 520);
	}

}
