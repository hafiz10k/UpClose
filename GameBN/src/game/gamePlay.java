package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Player;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;
import menuComponents.Load;

public class gamePlay {
	public Player player;

	private Sprite dialogBtn;
	private Sprite charInfoRect;
	
	private Font f = new Font("arial", Font.PLAIN, 50);

	public gamePlay(Game game) {
		//load dialog
		BufferedImage buttonDialog = game.loadImage("/dialogRect.png");
		SpriteSheet buttonDialogSheet = new SpriteSheet(buttonDialog);
		buttonDialogSheet.loadSprites(64, 40);

		dialogBtn = buttonDialogSheet.getSprite(0, 0);

		//load dialog
		BufferedImage charInfo = game.loadImage("/character_info_rect.png");
		SpriteSheet charInfoSheet = new SpriteSheet(charInfo);
		charInfoSheet.loadSprites(64, 40);

		charInfoRect = charInfoSheet.getSprite(0, 0);

	}

	public void update(Game game) {

		KeyBoardListener keyListener = game.getKeyListener();
		try {	
			if(game.player.getRectangle().x <= -10 && game.player.getRectangle().x >= -40 && game.player.getRectangle().y <= -990) {
				if(keyListener.a()) {

					Game.State = Game.STATE.HOSP;
					Thread.sleep(150);
				} 
			}
			
			if(game.player.getRectangle().x >= 2220 && game.player.getRectangle().x <= 2290 && game.player.getRectangle().y <= 0 && game.player.getRectangle().y >= -155) {
				if(keyListener.a()) {

					Game.State = Game.STATE.LRS;

					game.player.getRectangle().x = 2890;
					game.player.getRectangle().y = -75;
					Thread.sleep(150);
				} 

			}
			
			if(game.player.getRectangle().x >= 1980 && game.player.getRectangle().x <= 2020 && game.player.getRectangle().y >= -200 && game.player.getRectangle().y <= -190) {
				if(keyListener.a()) {
					Game.State = STATE.SAVE;
				}
			}


			

		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void render(RenderHandler renderer, Player player, int xZoom, int yZoom) {
		//		renderer.renderSprite(charInfoRect, 0, 0, 5, 5, true);

		if(player.getRectangle().x <= -10 && player.getRectangle().x >= -40 && player.getRectangle().y <= -990) {
			renderer.renderSprite(dialogBtn, 40, 600, xZoom, yZoom, true);
		}

		if(player.getRectangle().x >= 2220 && player.getRectangle().x <= 2290 && player.getRectangle().y <= 0 && player.getRectangle().y >= -155) {
			renderer.renderSprite(dialogBtn, 40, 600, xZoom, yZoom, true);
		}

		if(player.getRectangle().x >= 1980 && player.getRectangle().x <= 2020 && player.getRectangle().y >= -200 && player.getRectangle().y <= -190) {
			renderer.renderSprite(dialogBtn, 40, 600, xZoom, yZoom, true);
		}
	}

	public void render(Graphics graphics, Game game) {
		graphics.setFont(f);
		graphics.setColor(Color.BLACK);
		graphics.drawString(game.load.nameLoad, 10, 60);
		System.out.println(game.load.nameLoad);
//		graphics.drawString(game.player.getHP() + "/ " + game.player.getMaxHP(), 10, 60);
	}


}
