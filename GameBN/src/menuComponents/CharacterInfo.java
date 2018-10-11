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
	public Rectangle backButton =  new Rectangle (10, 10, 200, 50);
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private String name;
	private int gender;
	private String gen;
	private int level = 1;
	private int exp;

	private int saveChoice = 0;
	private Game game;

	private BufferedImage bg;
	
	boolean francisco = false;

	public CharacterInfo(Game game) {

		try {
			this.game = game;
			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 80);

			font = new Font("Arial", Font.BOLD, 50);

			name = game.load.getNameLoad();
			gender = game.load.getGenderLoad();

			if(gender == 0) {
				gen = "Boy";
			}
			else {
				gen = "Girl";
			}

			bg = game.loadImage("/charmenu.png");

			//weapon
			//			BufferedImage weapon = Game.loadImage("/sword-tiles.png");
			//			SpriteSheet weaponSheet = new SpriteSheet(weapon);
			//			weaponSheet.loadSprites(48, 48);
			//
			//			bronzeSW = weaponSheet.getSprite(0, 0);
			//			silverSW = weaponSheet.getSprite(1, 0);
			//			goldSW = weaponSheet.getSprite(2, 0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void update(Game game) {
		try {
			KeyBoardListener keyListener = game.getKeyListener();
			boolean didMove = false;
			
			STATE gameState = game.State;
			
			

			if(keyListener.esc()) {
				if(gameState.equals(Game.STATE.DUMMY)) {
					Game.State = Game.STATE.DUMMY;
					didMove = true;
				}
				
				else if(gameState.equals(Game.STATE.LAILARATNA)) {
					Game.State = Game.STATE.LAILARATNA;
					didMove = true;
				}
				
				if(francisco == false) {
					francisco = true;
					if(gameState.equals(STATE.FRANCISCO)) {
						Game.State = Game.STATE.FRANCISCO;
						didMove = true;
					}
				}
				
				
				
				else {
					Game.State = Game.STATE.MENU;
					System.out.println("error in loading battle");
				}
		
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
		graphics.drawString(name, 450, 150);
		graphics.drawString(gen, 450, 260);
		graphics.drawString("" + game.player.HP, 570, 390);
		graphics.drawString("" + level, 570, 510);
		graphics.drawString("" + game.player.getExp(), 600, 620);

	}

	//	public void loadData() throws NumberFormatException, IOException {
	//		try {
	//			BufferedReader br = new BufferedReader(new FileReader("Details.txt"));
	//			name = br.readLine();
	//			gender = Integer.parseInt(br.readLine());
	//			
	//		} catch (FileNotFoundException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		
	//	}

	private void select() {
		if(saveChoice == 0) {
			game.save.save(game);
			Game.State = Game.STATE.GAME;
		}

		if(saveChoice == 1) {
			Game.State = Game.STATE.GAME;
		}

	}

}
