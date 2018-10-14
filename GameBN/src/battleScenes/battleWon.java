package battleScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Player;
import entity.Rectangle;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class battleWon {
	private Sprite box;
	private Sprite item;
	private Sprite potion;
	
	private BufferedImage itemImg ;
	private SpriteSheet itemImgSheet;
	
	private Font f;
	private Font fs;
	
	private Player player;
	private Game game;
	
	private Rectangle keyRect;	
	
	public battleWon(Game game) {
		this.player = game.player;
		this.game = game;
		
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
		
		keyRect = new Rectangle(0, 700, 400, 400);
		keyRect.generateGraphics(0xeff0f1);
	}
	
	public void update(Game game) {
		KeyBoardListener keyListener = game.getKeyListener();
		
		if(keyListener.a()) {
			Game.State = STATE.GAME;
		}
		
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderSprite(box, 200, 200, xZoom*3, yZoom*3, true);
		renderer.renderRectangle(keyRect, xZoom, yZoom, true);
		
		if(game.dummy.dummyDead == true) {
			renderer.renderSprite(item, 350, 200, xZoom, yZoom, true);
		}
	}
	
	public void render(Graphics graphics) {
		graphics.setFont(f);
		graphics.setColor(Color.WHITE);
		graphics.drawString("YOU WON THE BATTLE!", 200, 100);
		
		graphics.setFont(fs);
		graphics.setColor(Color.BLACK);
		
		graphics.drawString("Player gained: ", 280, 250);
		
		if(game.dummy.dummyDead == true) {
		graphics.drawString("A slice of BREAD", 300, 460);
		graphics.drawString("and", 300, 490);
		graphics.drawString("+ 15 EXP!", 300, 530);
		}
		
		graphics.drawString("Press [A] to proceed", 50, 740);
		
		
	}

}
