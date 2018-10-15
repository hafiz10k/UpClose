package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entity.AnimatedSprite;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;

public class Exit implements MenuObject{
	
	private Font titleFont;
	private Color titleColor;
	
	private Font font;
	public String nameLoad;
	public int genderLoad;
	public String hpLoad;
	public int playerHP;
	public int playerEXP;
	private int playerPosX;
	private int playerPosY;
	private Font f;
	
	private int loadChoice = 0;

	private String[] load = 
		{
				"Yes",
				"No"
		};
	
	public Exit(Game game) {
		titleFont = new Font("Broadway", Font.BOLD, 80);
		font = new Font("Arial", Font.PLAIN, 40);
		
		f = new Font("Arial", Font.PLAIN, 50);
	}

	@Override
	public void render(Graphics graphics) {
//		graphics.setFont(titleFont);
//		graphics.setColor(Color.RED);
//		graphics.drawString("PLAYER IS DEAD :(", 90, 120);
		
		graphics.setFont(f);
		graphics.setColor(Color.WHITE);
		graphics.drawString("Exit Game?", 330, 300);

		
		graphics.setFont(font);
		for(int i = 0; i < load.length; i++) 
		{
			if (i == loadChoice)
			{
				graphics.setColor(Color.YELLOW);
			}
			else 
			{
				graphics.setColor(Color.WHITE);
			}
			graphics.drawString(load[i], 300 + i * 300, 430);
		}
	}

	public void loadData() throws NumberFormatException, IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Details.txt"));
			nameLoad = br.readLine();
			genderLoad = Integer.parseInt(br.readLine());
			playerPosX = Integer.parseInt(br.readLine());
			playerPosY = Integer.parseInt(br.readLine());
			playerHP = Integer.parseInt(br.readLine());
			playerEXP = Integer.parseInt(br.readLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(Game game) {
		try {
			try {
				loadData();
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			KeyBoardListener keyListener = game.getKeyListener();

			boolean loading = false;

			if(keyListener.enter()) {
				loading = true;
				select(game);
			}

			if(keyListener.left()) {
				loading = true;
				loadChoice --;
				if(loadChoice == -1)
				{
					loadChoice = load.length -1;
				}
			}

			if(keyListener.right()) {
				loading = true;
				loadChoice ++;
				if(loadChoice == load.length)
				{
					loadChoice = 0;
				}
			}

				Thread.sleep(150);


		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	private void select(Game game) {

		if(loadChoice == 0) {
			System.exit(1);
		}
		if(loadChoice == 1) {
			Game.State = Game.STATE.GAME;
		}


	}
	}
