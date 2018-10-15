package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Item {
	private Sprite box;
	private Sprite item;
	private Sprite bread;


	private BufferedImage itemImg ;
	private SpriteSheet itemImgSheet;

	private Font f;
	private Font fs;

	private Rectangle keyRect;

	public Item(Game game) {
		BufferedImage boxImg = game.loadImage("/lootbox.png");
		SpriteSheet boxImgSheet = new SpriteSheet(boxImg);
		boxImgSheet.loadSprites(64, 40);

		itemImg = game.loadImage("/loot_item.png");
		itemImgSheet = new SpriteSheet(itemImg);
		itemImgSheet.loadSprites(80, 96);

		box = boxImgSheet.getSprite(0, 0);
		item = itemImgSheet.getSprite(1, 0);
		bread = itemImgSheet.getSprite(0, 0); 

		f = new Font("Arial", Font.BOLD, 50);
		fs = new Font("Arial", Font.BOLD, 30);

		keyRect = new Rectangle(0, 700, 400, 400);
		keyRect.generateGraphics(0xeff0f1);
	}

	public void update(Game game) {
		try {
			KeyBoardListener keyListener = game.getKeyListener();

			if(keyListener.enter()) {
				Game.State = STATE.GAME;
			}


			Thread.sleep(100);


			if(game.player.getRectangle().x >= 3950 && game.player.getRectangle().x <= 4035 && game.player.getRectangle().y >= -1090 && game.player.getRectangle().y <= -1005) {
				item = itemImgSheet.getSprite(1, 0);
			}
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void render(RenderHandler renderer, Player player, int xZoom, int yZoom) {
		renderer.renderSprite(box, 200, 200, xZoom*3, yZoom*3, true);
		renderer.renderRectangle(keyRect, xZoom, yZoom, true);

		if(player.getRectangle().x >= 3950 && player.getRectangle().x <= 4035 && player.getRectangle().y >= -1090 && player.getRectangle().y <= -1005) {
			renderer.renderSprite(item, 350, 250, xZoom, yZoom, true);
		}
		if(player.getRectangle().x >= 1809 && player.getRectangle().x <= 1954 && player.getRectangle().y >= -480 && player.getRectangle().y <= -460) {
			renderer.renderSprite(bread, 350, 250, xZoom, yZoom, true);
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
		if(game.player.getRectangle().x >= 1809 && game.player.getRectangle().x <= 1954 && game.player.getRectangle().y >= -480 && game.player.getRectangle().y <= -460) {
			graphics.drawString("that's right, SLICE OF BREAD!", 270, 250);
			graphics.drawString("HP +3", 430, 520);
		}
		graphics.drawString("Press [ENTER] to proceed", 50, 740);
	}

}
