package menuComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import game.Game;
import handler.KeyBoardListener;

public class Load implements MenuObject{
	public Rectangle backButton =  new Rectangle (10, 10, 200, 50);

	private Color titleColor;
	private Font titleFont;
	public String nameLoad;
	public String genderLoad;
	public String hpLoad;

	private Font font;

	private int loadChoice = 0;

	private String[] load = 
		{
				"Yes",
				"No"
		};

	public Load() {
		try 
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader("Details.txt"));
				nameLoad = br.readLine();
				genderLoad = br.readLine();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 80);

			font = new Font("Arial", Font.PLAIN, 30);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getNameLoad() {
		return nameLoad;
	}

	public void setNameLoad(String nameLoad) {
		this.nameLoad = nameLoad;
	}

	public String getGenderLoad() {
		return genderLoad;
	}

	public void setGenderLoad(String genderLoad) {
		this.genderLoad = genderLoad;
	}

	public String getHpLoad() {
		return hpLoad;
	}

	public void setHpLoad(String hpLoad) {
		this.hpLoad = hpLoad;
	}

	@Override
	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;

		graphics.setColor(Color.WHITE);
		graphics.setFont(titleFont);
		graphics.drawString("Load", 380, 120);

		graphics.setFont(font);
		graphics.drawString("load game?", 420, 250);

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
			graphics.drawString(load[i], 350 + i * 250, 350);
		}
	}

	@Override
	public void update(Game game) {
		try {
			KeyBoardListener keyListener = game.getKeyListener();

			boolean loading = false;

			if(keyListener.enter()) {
				loading = true;
				select();

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
	
//	public void loadData() throws NumberFormatException, IOException {
//		try {
//			BufferedReader br = new BufferedReader(new FileReader("Details.txt"));
//			nameLoad = br.readLine();
//			genderLoad = Integer.parseInt(br.readLine());
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	private void select() {

		if(loadChoice == 0) {
			System.out.println(nameLoad);
			System.out.println(genderLoad);
			Game.State = Game.STATE.GAME;
		}

		if(loadChoice == 1) {
			Game.State = Game.STATE.MENU;
		}


	}
}
