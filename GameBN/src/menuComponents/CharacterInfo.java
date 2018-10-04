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
import java.io.PrintWriter;

import entity.Map;
import game.Game;
import handler.KeyBoardListener;

public class CharacterInfo {
	public Rectangle backButton =  new Rectangle (10, 10, 200, 50);
	private Load load;
	private Color titleColor;
	private Font titleFont;
	private Map map;
	private Font font;
	private String name;
	private int gender;
	private int  hp = 100;
	private int level = 1;
	private Save save;
	
	private int saveChoice = 0;

	
	
	private Game game;
	

	public CharacterInfo(Game game) {
		
		try 
		{

			titleColor = new Color(100, 128, 128);
			titleFont = new Font("Broadway", Font.BOLD, 80);

			font = new Font("Arial", Font.PLAIN, 30);
			this.game = game;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
//		String name = game.name.fullName;
//		Gender gender = new Gender();
//		
//		String fileName = "details.txt";
//		PrintWriter writer = null;
//		
//		try {
//			writer = new PrintWriter(fileName);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for(int i = 0; i < 5; i++) {
//			writer.println(gender.getLoadChoice() );
//			
//
//		}
//		writer.close();
		
	}




	public void render(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;

		graphics.setColor(Color.WHITE);
		graphics.setFont(titleFont);
		graphics.drawString("Character Info", 180, 120);
		graphics.setFont(font);
		
//		graphics.drawString((load.getHpLoad()), 300, 300);
//		graphics.drawString((Integer.toString(load.genderLoad)), 180, 200);
		
//
//		for(int i = 0; i < CharacterInfo; i++) 
//		{
//			if (i == saveChoice)
//			{
//				graphics.setColor(Color.YELLOW);
//			}
//			else 
//			{
//				graphics.setColor(Color.WHITE);
//			}
//			graphics.drawString(save[i], 350 + i * 250, 350);
//		}

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




//	public void update(Game game) {
//		try {
//			KeyBoardListener keyListener = game.getKeyListener();
//
//			boolean loading = false;
//
//			if(keyListener.enter()) {
//				loading = true;
//				select();
//
//			}
//
//			if(keyListener.left()) {
//				loading = true;
//				saveChoice --;
//				if(saveChoice == -1)
//				{
//					saveChoice = save.length -1;
//				}
//			}
//
//			if(keyListener.right()) {
//				loading = true;
//				saveChoice ++;
//				if(saveChoice == save.length)
//				{
//					saveChoice = 0;
//				}
//			}
//			Thread.sleep(150);
//
//		}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//	}



	private void select() {
		if(saveChoice == 0) {
			game.save.save();
			Game.State = Game.STATE.GAME;
		}

		if(saveChoice == 1) {
			Game.State = Game.STATE.MENU;
		}

	}

}