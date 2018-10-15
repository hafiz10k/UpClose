package cutScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Player;
import entity.Rectangle;
import game.Game;
import handler.Audio;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class Credit {

	private BufferedImage scene;

	private Rectangle rect;
	private Rectangle timerRect;


	private Font f = new Font("arial", Font.PLAIN, 40);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	public Audio bgm;
	private Audio sfx;

	public Credit(Game game) {

		// dialog
		rect = new Rectangle(40, 700, 300, 300);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);

		bgm = new Audio("/bgm/francisco.mp3");
		sfx = new Audio("/sfx/dialog.mp3");
	}

	public void update(Game game) {
		rect.y--;
		timerRect.x++;
		System.out.println(timerRect.x);
		
		if(timerRect.x >= 1600) {
			System.exit(0);
		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		graphics.setColor(Color.YELLOW);
		graphics.drawString("THIS IS THE END OF THE GAME", rect.x + 100 , rect.y + 50);
		
		graphics.setColor(Color.WHITE);
		graphics.drawString("DEVELOPER: ", rect.x + 100 , rect.y + 150);
		
		graphics.drawString("HERNIE ABDUL WAHAB ", rect.x + 100 , rect.y + 200);
		graphics.drawString("HAFIZ MD ZAIN ", rect.x + 100 , rect.y + 250);
		
		graphics.setColor(Color.RED);
		graphics.drawString("DESIGNER: ", rect.x + 100 , rect.y + 350);
		
		graphics.drawString("CHONG ZHEN JYE ", rect.x + 100 , rect.y + 400);
		graphics.drawString("HAJAH HUWAIDA", rect.x + 100 , rect.y + 450);
		graphics.drawString("HERNIE ABDUL WAHAB ", rect.x + 100 , rect.y + 500);
		
		graphics.setColor(Color.YELLOW);
		graphics.drawString("SPECIAL THANKS TO:", rect.x + 100 , rect.y + 600);
		graphics.drawString("SIR HAJI JAILANI HAJI ABDUL RAHMAN ", rect.x + 100 , rect.y + 650);
		graphics.drawString("MISS SITI NUR'AFIFAH BINTI SAIT", rect.x + 100 , rect.y + 700);
		graphics.drawString("SIR BAHIT BIN HAMID", rect.x + 100 , rect.y + 750);
		

	}

}
