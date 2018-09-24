package screenMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import entity.AnimatedSprite;
import entity.Player;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import handler.Background;
import handler.RenderHandler;

public class Scene01 {

	private JPanel panel;
	private JTextArea textArea;

	private BufferedImage room;

	private AnimatedSprite boyAni;
	private AnimatedSprite girlAni;

	private Rectangle rect;

	private int x = 0;
	private int y = 0;
	private int speed = 8;
	private int direction = 0;

	public Player player;

	Timer timer;

	// dialog
	private String adik = "Abang! Abang! Abang!";
	private String abang = "adik! what?!";

	private int i = 0;	
	private String addedChar = "";

	private String welcome = "Would you like to play a game?";

	public Scene01(Game game) {

		//bedroom bg
		room = game.loadImage("/bedroom-scene.png");

		//male
		BufferedImage playerSheetImage = game.loadImage("/mainAnimated.png");
		SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
		boySheet.loadSprites(24, 32);

		boyAni = new AnimatedSprite(boySheet, 10);

		// female
		BufferedImage girlSheetImage = game.loadImage("/girl-main-anim.png");
		SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
		girlSheet.loadSprites(24, 32);

		girlAni = new AnimatedSprite(girlSheet, 10);

		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);



	}

	public void update(Game game, Player player ) {
		//		timer = new Timer(10000, new ActionListener() {
		//
		//			@Override
		//			public void actionPerformed(ActionEvent arg0) {
		//				char textChar[] = text.toCharArray();
		//				int arrayNumber = textChar.length;
		//
		//				String blank = "";
		//
		//				addedChar = blank + textChar[i];
		//				i++;
		//				
		//				textArea.append(addedChar);
		//				
		//			}
		//
		//		});
		//		timer.start();
		//		
		//		System.out.println(addedChar);

		//		x += speed;
		try {
			if(girlAni != null && boyAni != null) {
				boolean didMove = false;
				int newDirection = direction;

				newDirection = 2;
				didMove = true;
				//			x += speed;		

				if(!didMove) {
					girlAni.reset();
					boyAni.reset();
				}

				if(didMove) {
					girlAni.incSprite();
					boyAni.incSprite();
					player.getRectangle().x += speed;
				}

				// 0 - Down, 1 - Left, 2 - Right, 3 - Up

				if(newDirection != direction) {
					direction = newDirection;
					girlAni.setAnimationRange(direction * 4, (direction * 4) + 4);
					boyAni.setAnimationRange(direction * 4, (direction * 4) + 4);
				}

				System.out.println(player.getRectangle().x);

				if(player.getRectangle().x >= 50) {
					didMove = true;
					girlAni.reset();
					boyAni.reset();
					player.getRectangle().x = 50;
					newDirection = 1;
					boyAni.setAnimationRange(newDirection * 4, (newDirection * 4) + 4);

				}
			}


			//			char textChar[] = adik.toCharArray();
			//			int arrayNumber = textChar.length;
			//
			//			String blank = "";
			//			addedChar = blank + textChar[i];
			//			i++;

			Thread.sleep(500);
			//			System.out.println(addedChar);


			//			for (int i = 0; i < welcome.length(); i++) {
			//				
			//				addedChar = "" + welcome.charAt(i);
			//				Thread.sleep(500);
			//			    System.out.print(addedChar);
			//							   
			//				continue;
			//			    
			//			}

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}



	}

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(room, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(boyAni, player.getRectangle().x + 350, 400, xZoom, yZoom, true);
		renderer.renderSprite(girlAni, player.getRectangle().x + 300, 400, xZoom, yZoom, false);

		renderer.renderRectangle(rect, xZoom, yZoom, true);



	}

	public void render(Game game, Graphics graphics, Player player) {

		Font f = new Font("arial", Font.PLAIN, 30);
		graphics.setFont(f);

		if(player.getRectangle().x < 50) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(adik, 70, 650);
		}

		if(player.getRectangle().x >= 50) {
			graphics.setColor(Color.BLUE);
			graphics.drawString(abang, 70, 650);
		}

		//		graphics.drawString(addedChar, 70 + i * 100, 300);

	}



}
