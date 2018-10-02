package game;

import java.awt.image.BufferedImage;

import entity.Player;
import entity.Sprite;
import entity.SpriteSheet;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class gamePlay {
	public Player player;

	private BufferedImage logo;
	private Sprite logoSp;

	public gamePlay(Game game) {
		//load dialog
		logo = game.loadImage("/dialogRect.png");
		SpriteSheet logoSheet = new SpriteSheet(logo);
		logoSheet.loadSprites(64, 40);

		logoSp = logoSheet.getSprite(0, 0);

	}

	public void update(Game game) {


		if(game.player.getRectangle().x >= 2220 && game.player.getRectangle().x <= 2290 && game.player.getRectangle().y <= 0 && game.player.getRectangle().y >= -155) {
			
			
			KeyBoardListener keyListener = game.getKeyListener();
			try {	
			if(keyListener.a()) {

					Game.State = Game.STATE.LRS;
					
					game.player.getRectangle().x = 2420;
					game.player.getRectangle().y = -20;
					
				} 
				
				Thread.sleep(150);

			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void render(RenderHandler renderer, Player player, int xZoom, int yZoom) {
		//		System.out.println(player.getRectangle().x + ", " + player.getRectangle().y);
		if(player.getRectangle().x <= -10 && player.getRectangle().x >= -40 && player.getRectangle().y <= -990) {
			renderer.renderSprite(logoSp, 0, 0, xZoom, yZoom, true);
		}

		if(player.getRectangle().x >= 2220 && player.getRectangle().x <= 2290 && player.getRectangle().y <= 0 && player.getRectangle().y >= -155) {
			renderer.renderSprite(logoSp, 0, 0, xZoom, yZoom, true);
		}
	}


}
