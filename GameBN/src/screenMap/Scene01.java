package screenMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import entity.AnimatedSprite;
import entity.Player;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Scene01 {

	private JPanel panel;
	private JTextArea textArea;

	private BufferedImage room;
	private BufferedImage bh;

	private AnimatedSprite boyAni;
	private AnimatedSprite girlAni;

	private AnimatedSprite bhAni;

	private Rectangle rect;
	private Rectangle boyRect;
	private Rectangle girlRect;

	private int x = 0;
	private int y = 0;
	private int speed = 10;
	private int boyDir = 0;
	private int girlDir = 0;

	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);


	public Player player;

	Timer timer;

	// dialog
	private String[] girl =
		{
				"Abang! Abang! Abang!",
				"adik dialog 2",
				"adik dialog 3"
		};

	private String[] boy = 
		{
				"adik! what?!",
				"abang dialog 2",
				"abang dialog 3"
		};

	private String key = "[press enter]";

	private int i = 0;	
	private String addedChar = "";
	private int addedCharCounter = 0;

	private String welcome = "Would you like to play a game?";

	public Scene01(Game game) {

		//bedroom bg
		room = game.loadImage("/bedroom-scene.png");

		//blackhole
		bh = game.loadImage("/blackHole.png");
		SpriteSheet bhSheet = new SpriteSheet(bh);
		bhSheet.loadSprites(128, 96);

		bhAni = new AnimatedSprite(bhSheet, 10);

		//male
		BufferedImage playerSheetImage = game.loadImage("/mainAnimated.png");
		SpriteSheet boySheet = new SpriteSheet(playerSheetImage);
		boySheet.loadSprites(24, 32);

		boyAni = new AnimatedSprite(boySheet, speed);

		// female
		BufferedImage girlSheetImage = game.loadImage("/girl-main-anim.png");
		SpriteSheet girlSheet = new SpriteSheet(girlSheetImage);
		girlSheet.loadSprites(24, 32);

		girlAni = new AnimatedSprite(girlSheet, speed);

		//boy rect
		boyRect = new Rectangle(320, 400, 24, 32);
		boyRect.generateGraphics(3, 0x0000ff);

		//girl rect
		girlRect = new Rectangle(1000, 400, 24, 32);
		girlRect.generateGraphics(3, 0xf303d2);

		// dialog
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

			// 0 - Down, 
			// 1 - Left, 
			// 2 - Right, 
			// 3 - Up
			
			// GIRL MOVEMENT
			if(girlAni != null) {
				boolean didMove = false;
				int newDirection = girlDir;

				newDirection = 1;
				didMove = true;

				if(!didMove) {
					girlAni.reset();
				}

				if(didMove) {
					girlAni.incSprite();
					girlRect.x -= speed;

				}

				if(newDirection != girlDir) {
					girlDir = newDirection;
					girlAni.setAnimationRange(girlDir * 4, (girlDir * 4) + 4);
				}

				if(girlRect.x <= 600) {
					didMove = true;
					girlAni.reset();
										
					girlRect.x = 600;
					newDirection = 0;

				}
				
				System.out.println(girlRect.x);

				Thread.sleep(150);
			}
			
			//BOY MOVEMENT
			// 0 - Down, 1 - Left, 2 - Right, 3 - Up

			if(boyAni != null) {
				boolean didMove = false;
				int newDirection = boyDir;

				newDirection = 3;

				if(!didMove) {
					boyAni.reset();
				}

				if(didMove) {
					boyAni.incSprite();
				}

				if(newDirection != boyDir) {
					boyDir = newDirection;
					boyAni.setAnimationRange(boyDir * 4, (boyDir * 4) + 4);
				}

				
				
				if(girlRect.x <= 600) {
					didMove = true;
					boyAni.reset();
					
					newDirection = 2;
					boyAni.setAnimationRange(newDirection * 4, (newDirection * 4) + 4);
				}
				
				Thread.sleep(50);
			}

			// ANIMATING DIALOGS

			char textChar[] = girl[i].toCharArray();
			if(addedCharCounter <= textChar.length-1) {
				addedChar = addedChar + textChar[addedCharCounter];
			}
			addedCharCounter++;
			
//			int arrayNumber = textChar.length;
//			
//			String blank = "";
//			addedChar = blank + textChar[i];
//			i++;

			
			System.out.println(addedChar);


			//			for (int i = 0; i < welcome.length(); i++) {
			//				
			//				addedChar = "" + welcome.charAt(i);
			//				Thread.sleep(500);
			//			    System.out.print(addedChar);
			//							   
			//				continue;
			//			    
			//			}


			if(girlRect.x <= 600) {
				KeyBoardListener keyListener = game.getKeyListener();
				boolean didMove = false;

				if(keyListener.enter()) {
					didMove = true;
					Game.State = Game.STATE.SCENE02;
					Scene02 scene02 = new Scene02(game);

					scene02.getAudio().play();
				}

				Thread.sleep(150);
			}

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}



	}

	public void render(RenderHandler renderer, Game game, Player player, int xZoom, int yZoom) {
		renderer.renderImage(room, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(boyAni, boyRect.x, boyRect.y, xZoom, yZoom, false);
		renderer.renderSprite(girlAni, girlRect.x, girlRect.y, xZoom, yZoom, false);

		renderer.renderRectangle(rect, xZoom, yZoom, true);

		if(girlRect.x <= 600) {

			renderer.renderSprite(bhAni, 600, 200, xZoom, yZoom, false);


		}

	}

	public void render(Game game, Graphics graphics, Player player) {

		graphics.setFont(f);

		if(girlRect.x > 600) {
			graphics.setColor(Color.MAGENTA);
//			graphics.drawString(girl[i], 70, 650);
			graphics.drawString(addedChar, 70, 650);
		}

		if(girlRect.x <= 600) {


			graphics.setColor(Color.BLUE);
			//				for(int i = 0; i < abang.length; i++) {
			graphics.drawString(boy[i], 70, 650);


			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);

			//			int i = 0;

			//			AffineTransform at = AffineTransform.getTranslateInstance(100, 100);
			//			at.rotate(Math.toRadians(i++), bh.getWidth()/2, bh.getHeight()/2);
			//			
			//			Graphics2D g2d = (Graphics2D) graphics;
			//			
			//			g2d.drawImage(bh, 100, 100, null);
			//			g2d.rotate(Math.toRadians(i++), bhAni.getWidth()/2, bhAni.getHeight()/2);
			//			g2d.rotate(-90, bhAni.getWidth()/2, bhAni.getHeight()/2);
			//				}

		}
	}

	//		graphics.drawString(addedChar, 70 + i * 100, 300);

}



