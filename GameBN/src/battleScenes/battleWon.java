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
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class battleWon {
	private Sprite box;
	private Sprite bread;
	private Sprite lembing;
	private Sprite tumbak;

	private BufferedImage itemImg ;
	private SpriteSheet itemImgSheet;

	private Font f;
	private Font fs;
	private Font detail;

	private Player player;
	private Game game;

	private Rectangle keyRect;	
	
	public Audio bgm;

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
		bread = itemImgSheet.getSprite(0, 0);
		lembing = itemImgSheet.getSprite(2, 0);
		tumbak = itemImgSheet.getSprite(3, 0);

		f = new Font("Arial", Font.BOLD, 50);
		fs = new Font("Arial", Font.BOLD, 30);
		detail = new Font("Arial", Font.PLAIN, 20);

		keyRect = new Rectangle(0, 700, 400, 400);
		keyRect.generateGraphics(0xeff0f1);
		
		bgm = new Audio("/bgm/Victory-Theme-WinBattle.mp3");
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
			renderer.renderSprite(bread, 350, 200, xZoom, yZoom, true);
		}

		if(game.lailaRatna.enemyDead == true) {
			renderer.renderSprite(lembing, 200, 210, xZoom, yZoom, true);
		}
		
		if(game.francisco.enemyDead == true) {
			renderer.renderSprite(tumbak, 200, 270, 2, 2, true);
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
			graphics.drawString("BATTLE: +35 EXP!", 300, 530);
		}

		if(game.lailaRatna.enemyDead == true) {
			graphics.setColor(Color.RED);
			graphics.drawString("Lembing Tembaga", 360, 300);

			graphics.setColor(Color.BLACK);
			graphics.setFont(detail);
			graphics.drawString("8 \"Lembing\" (spears) with brass band", 370, 330);
			graphics.drawString("and 8 \"Taming\" (shields) were carried", 370, 360);
			graphics.drawString("by 8 \"Awang-Awang\" (Aristocrats)", 370, 390);

			graphics.setFont(fs);
			graphics.drawString("and", 300, 490);
			graphics.drawString("BATTLE: +35 EXP!", 300, 530);
		}
		
		if(game.francisco.enemyDead == true) {
			graphics.setColor(Color.RED);
			graphics.drawString("Tumbak Benderangan", 360, 300);
			graphics.drawString("Dan Taming", 360, 330);

			graphics.setColor(Color.BLACK);
			graphics.setFont(detail);
			graphics.drawString("16 Tumbak Benderangan (Royal Ceremonial", 350, 370);
			graphics.drawString("Spears) plated in gold and silver which", 350, 390);
			graphics.drawString("are borne by 16 \"Awang-Awang\" ", 350, 410);
			graphics.drawString("(Aristocrats) decked in ceremonial attires.", 350, 430);

			graphics.setFont(fs);
			graphics.drawString("and", 300, 490);
			graphics.drawString("BATTLE: +50 EXP!", 300, 530);
		}

		graphics.drawString("Press [A] to proceed", 50, 740);


	}

}
