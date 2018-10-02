package placeScene;

import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Player;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.RenderHandler;
import menuComponents.Gender;

public class hospitalScene {

	public Gender gender;
	
	private BufferedImage land;

	public hospitalScene(Game game) {
		// land
		land = game.loadImage("/hospital_scene.png");

	}

	public void update(Game game) {
		game.player.update(game);
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom, Game game) {
		int chosen1 = game.gender.getLoadChoice();
		renderer.renderImage(land, 10, 150, xZoom, yZoom, true);
		
		if(chosen1 == 0) {
			//male
			BufferedImage playerSheetImage = game.loadImage("/mainAnimated.png");
			SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
			boySheet.loadSprites(24, 32);

			AnimatedSprite boyAni = new AnimatedSprite(boySheet, 10);

			renderer.renderSprite(boyAni, 100, 100, xZoom, yZoom, false);
		} 

		else {
			// female
			BufferedImage girlSheetImage = game.loadImage("/girl-main-anim.png");
			SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
			girlSheet.loadSprites(24, 32);

			AnimatedSprite girlAni = new AnimatedSprite(girlSheet, 10);
			game.player.changeSprite(girlAni);

			Game.State = STATE.SCENE04;
		}

	}

}
