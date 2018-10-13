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
	private String playerName;
	private int playerGender;
	private String gen;
	private int currentEXP;

	private Game game;

	private BufferedImage bg;
	
	boolean francisco = false;

	public CharacterInfoFrancisco(Game game) {

		try {
			bg = game.loadImage("/charmenu.png");
			
			this.game = game;
			font = new Font("Arial", Font.BOLD, 50);

			if(game.name.fullName != null && !game.name.fullName.isEmpty()) {
				playerName = game.name.getName();
			}
			
			else {
				playerName = game.load.nameLoad;
			}
			
			playerGender = game.load.getGenderLoad();

			if(playerGender == 0) {
				gen = "Boy";
			}
			else {
				gen = "Girl";
			}

			

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void update(Game game) {
		game.player.exp(currentEXP);
		
		try {
			KeyBoardListener keyListener = game.getKeyListener();
			STATE gameState = game.State;
			
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
	}

	public void render(Graphics graphics) {
		graphics.setColor(Color.WHITE);

		graphics.setFont(font);
		graphics.drawString("weapon", 160, 390);
		graphics.drawString(playerName, 450, 150);
//		graphics.drawString(gen, 450, 260);
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
