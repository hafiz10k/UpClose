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

	//player
	private String playerName;
	private String playerHP;
	private String playerLVL;

	private Font f = new Font("arial", Font.PLAIN, 50);

	public gamePlay(Game game) {
		player = game.player;
		
		//load dialog
		BufferedImage buttonDialog = game.loadImage("/dialogRect.png");
		SpriteSheet buttonDialogSheet = new SpriteSheet(buttonDialog);
		buttonDialogSheet.loadSprites(64, 40);

		dialogBtn = buttonDialogSheet.getSprite(0, 0);
		
		// load player stats
//		game.player.level(game.dummy.getAchievedExp());
		playerName = game.name.getName();
		playerHP = game.player.getHP() + "/" + game.player.getMaxHP();
		playerLVL = "" + game.player.EXP;

	}

	public void update(Game game) {
		player = game.player;KeyBoardListener keyListener = game.getKeyListener();
		try {	
			if(keyListener.esc()) {
				game.player.getRectangle().x = 0;
				game.player.getRectangle().y = -90;
			}
			
			if(keyListener.enter()) {
				game.player.getRectangle().x = 2890;
				game.player.getRectangle().y = -75;
			}

//			if(game.player.getRectangle().x <= -10 && game.player.getRectangle().x >= -40 && game.player.getRectangle().y <= -990) {
//				if(keyListener.a()) {
//
//					Game.State = Game.STATE.HOSP;
//					Thread.sleep(150);
//				} 
//			}

			if(game.player.getRectangle().x >= 2220 && game.player.getRectangle().x <= 2290 && game.player.getRectangle().y <= 0 && game.player.getRectangle().y >= -155) {
				if(keyListener.a()) {

					Game.State = Game.STATE.LRS;

					game.player.getRectangle().x = 2890;
					game.player.getRectangle().y = -75;
					Thread.sleep(150);
				} 

			}

			if(game.player.getRectangle().x >= 2139 && game.player.getRectangle().x <= 2184 && game.player.getRectangle().y >= -225 && game.player.getRectangle().y <= -145) {
				if(keyListener.a()) {
					Game.State = STATE.SAVE;
				}
			}
			
			if(game.player.getRectangle().x >= 7235 && game.player.getRectangle().x <= 7335 && game.player.getRectangle().y >= -140 && game.player.getRectangle().y <= -35) {
				if(keyListener.a()) {
					Game.State = STATE.SAVE;
				}
			}
			
			if(game.player.getRectangle().x >= 3950 && game.player.getRectangle().x <= 4035 && game.player.getRectangle().y >= -1090 && game.player.getRectangle().y <= -1005) {
				if(keyListener.a()) {
					Game.State = STATE.ITEM;
				}
			}




		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(player.getRectangle().x >= 1529 && player.getRectangle().x <= 1594 && player.getRectangle().y == -1040) {
			renderer.renderSprite(dialogBtn, 40, 600, xZoom, yZoom, true);
		}

		if(player.getRectangle().x >= 2220 && player.getRectangle().x <= 2290 && player.getRectangle().y <= 0 && player.getRectangle().y >= -155) {
			renderer.renderSprite(dialogBtn, 40, 600, xZoom, yZoom, true);
		}

		if(player.getRectangle().x >= 2139 && player.getRectangle().x <= 2184 && player.getRectangle().y >= -225 && player.getRectangle().y <= -145) {
			renderer.renderSprite(dialogBtn, 40, 600, xZoom, yZoom, true);
		}
		
		if(player.getRectangle().x >= 3950 && player.getRectangle().x <= 4035 && player.getRectangle().y >= -1090 && player.getRectangle().y <= -1005) {
			renderer.renderSprite(dialogBtn, 40, 600, xZoom, yZoom, true);
		}
		
		if(player.getRectangle().x >= 7235 && player.getRectangle().x <= 7335 && player.getRectangle().y >= -140 && player.getRectangle().y <= -35) {
			renderer.renderSprite(dialogBtn, 40, 600, xZoom, yZoom, true);
		}
	}

	public void render(Graphics graphics, Game game) {
//				graphics.setFont(f);
//				graphics.setColor(Color.WHITE);
//				graphics.drawString(game.load.nameLoad, 10, 60);
//				graphics.drawString(playerHP, 10, 100);
//				graphics.drawString(playerLVL, 10, 160);
	}


}
