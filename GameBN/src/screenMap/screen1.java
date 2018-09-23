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

public class screen1 {

	private JPanel panel;
	private JTextArea textArea;

	private Background bedroom;
	private BufferedImage room;

	private AnimatedSprite boyAni;
	private AnimatedSprite girlAni;

	private Rectangle rect;

	private int x = 0;
	private int y = 0;
	private int speed = 4;
	private int direction = 0;

	public Player player;

	Timer timer;

	private String text = "hello this is our rpg game";
	private int i = 0;	
	private String addedChar;

	public screen1(Game game) {

		bedroom = new Background("/bedroom-scene.png", 1);

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
		rect.generateGraphics(0x69edfc);



	}

	public void update(Game game ) {
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
		if(girlAni != null) {
			girlAni.setAnimationRange(direction * 4, (direction * 4) + 4);

			boolean didMove = false;
			int newDirection = direction;
			
			newDirection = 2;
			didMove = true;
			x += speed;		
			
			girlAni.incSprite();

			if(newDirection != direction) {
				direction = newDirection;
				girlAni.setAnimationRange(direction * 4, (direction * 4) + 4);
			}
		}

		try {
			char textChar[] = text.toCharArray();
			int arrayNumber = textChar.length;


			String blank = "";
			addedChar = blank + textChar[i];
			i++;

			Thread.sleep(500);
			System.out.println(addedChar);


		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}



	}

	public void render(Game game, Graphics graphics) {
		graphics.setColor(Color.WHITE);
		Font fnt0 = new Font("arial", Font.BOLD, 30);
		graphics.setFont(fnt0);


		graphics.drawString(addedChar, 70, 650);

		//		graphics.drawString(addedChar, 70 + i * 100, 300);
	}

	public void render(RenderHandler renderer, Game game, int xZoom, int yZoom) {
		renderer.renderImage(room, 10, 150, xZoom, yZoom, true);
		renderer.renderSprite(boyAni, x + 350, 400, xZoom, yZoom, true);
		renderer.renderSprite(girlAni, x + 300, 400, xZoom, yZoom, false);

		renderer.renderRectangle(rect, xZoom, yZoom, true);



	}

}
