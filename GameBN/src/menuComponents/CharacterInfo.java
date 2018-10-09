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
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class CharacterInfo {
	public Rectangle backButton =  new Rectangle (10, 10, 200, 50);
	private Load load;
	private Color titleColor;
	private Font titleFont;
	private Map map;
	private Font font;
	private String name;
	private int gender;
	private String gen;
	private int  hp = 100;
	private int level = 1;
	private int exp = 50;

	private int saveChoice = 0;
	private Game game;

	private BufferedImage bg;

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
			
			if(keyListener.esc()) {
				Game.State = Game.STATE.GAME;
				didMove = true;
			}
			
			Thread.sleep(150);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(bg, 0, 0, 2, 2, true);
	}




	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;

		graphics.setColor(Color.WHITE);

		graphics.setFont(font);
		graphics.drawString("weapon", 160, 390);
		graphics.drawString(name, 450, 150);
		graphics.drawString(gen, 450, 260);
		graphics.drawString("" + hp, 570, 390);
		graphics.drawString("" + level, 570, 510);
		graphics.drawString("" + exp, 600, 620);

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
