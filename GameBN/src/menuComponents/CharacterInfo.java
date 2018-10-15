package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import entity.Map;
import entity.Sprite;
import entity.SpriteSheet;
import game.Game;
import game.Game.STATE;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class CharacterInfo {
	private Font font;
	private String playerName;
	private int playerGender;
	private String gen;
	private int currentEXP;

	private Game game;

	private BufferedImage bg;

	boolean francisco = false;

	public CharacterInfo(Game game) {

		try {
			bg = game.loadImage("/charmenu.png");

			this.game = game;
			font = new Font("Arial", Font.BOLD, 50);

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
				Game.State = Game.STATE.DUMMY;
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
		//gender
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
