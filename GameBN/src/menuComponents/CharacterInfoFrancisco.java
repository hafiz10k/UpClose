package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class CharacterInfoFrancisco {
	private Font font;
	private Font lootFont;
	
	private String playerName;
	private Game game;

	private BufferedImage bg;
	
	boolean francisco = false;

	public CharacterInfoFrancisco(Game game) {

		try {
			bg = game.loadImage("/charmenu.png");
			
			this.game = game;
			font = new Font("Arial", Font.BOLD, 50);
			lootFont = new Font("Arial", Font.PLAIN, 20);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void update(Game game) {
		game.player.exp(game.player.EXP);
		
		try {
			KeyBoardListener keyListener = game.getKeyListener();
			if(keyListener.esc()) {
					Game.State = Game.STATE.FRANCISCO;

			}


			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
		renderer.renderSprite(game.player.weapon, 210, 120, xZoom, yZoom, true);
		
		if(game.player.EXP == 100) {
			renderer.renderSprite(game.player.loot, 110, 370, 2, 2, true);
			renderer.renderSprite(game.player.loot2, 110, 550, 2, 2, true);
		}
		else {
			renderer.renderSprite(game.player.loot, 110, 370, 2, 2, true);
		}
			
		
	}

	public void render(Graphics graphics) {
		graphics.setColor(Color.WHITE);

		graphics.setFont(font);
		graphics.drawString("LOOT:", 160, 370);
		
		graphics.setFont(lootFont);
		if(game.player.EXP == 100) {
			graphics.drawString("LEMBING TEMBAGA", 230, 460);
			graphics.drawString("DAN TAMING", 230, 490);
			graphics.drawString("TUMBAK", 250, 630);
			graphics.drawString("BENDERANGAN", 250, 660);
		}
		
		else {
			graphics.setFont(lootFont);
			graphics.drawString("LEMBING TEMBAGA", 230, 460);
			graphics.drawString("DAN TAMING", 230, 490);
			
			graphics.setFont(font);
			graphics.drawString("N/A", 250, 660);
		}

		
		graphics.setFont(font);
		if(game.name.fullName != null && !game.name.fullName.isEmpty()) {
			playerName = game.name.fullName;
			graphics.drawString(playerName, 450, 150);
		}
		
		else{
			playerName = game.load.nameLoad;
			graphics.drawString(playerName, 450, 150);
		}
		
		graphics.drawString("" + game.player.HP, 570, 390);
		graphics.drawString("" + game.player.level, 570, 510);
		graphics.drawString("" + game.player.EXP, 600, 620);
		if(game.gender.genderchosen == 0) {
			if(game.load.genderLoad == 1) {
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.drawString("Girl", 450, 260);
			}
			else if(game.load.genderLoad == 2){
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.drawString("Boy", 450, 260);
			}
			
		}
		else {
			if(game.gender.getLoadChoice() == 1) {
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.drawString("Girl", 450, 260);
			}
			else if(game.gender.getLoadChoice() == 0){
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.drawString("Boy", 450, 260);
			}
		}
		
	}
}
