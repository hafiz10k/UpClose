package handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.Game;

public class Background {

	private BufferedImage image;

	private int x;
	private int y;
	private int dx;
	private int dy;

	private int moveScale;

	public Background(String path, int ms) 
	{
		try 
		{
			image = ImageIO.read(getClass().getResourceAsStream(path));	
			moveScale = ms;
		}


		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setPosition(Game game, int x, int y) 
	{
		this.x = (int) ((x * moveScale) % game.getWidth());
		this.y = (int) ((y * moveScale) % game.getHeight());
	}

	public void setVector(int dx, int dy)
	{
		this.dx = dx;
		this.dy = dy;
	}

	public void update()
	{
		x += dx;
		y += dy;
	}

	public void render(Game game, Graphics graphics)
	{
		graphics.drawImage(image, (int)x, (int)y, game);

		if (x <= 0)
		{
			graphics.drawImage(image, (int)x - 100, (int)y, game);
		} 
		if (x >= 0)
		{
			graphics.drawImage(image, (int)x + 100, (int)y, game);
		}
	}

}
