package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Tiles {
	private SpriteSheet spriteSheet;
	private ArrayList<Tile> tilesList = new ArrayList<Tile>();
	
	// only work if sprite in sprite sheet are loaded
	public Tiles(File tilesFile, SpriteSheet spriteSheet)
	{
		this.spriteSheet = spriteSheet;
		try 
		{
			Scanner scanner = new Scanner(tilesFile);
			while(scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				if(!line.startsWith("//"))
				{
					String[] splitString = line.split("-");
					String tileName = splitString[0];
					int spriteX = Integer.parseInt(splitString[1]);
					int spriteY = Integer.parseInt(splitString[2]);
					Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));
					tilesList.add(tile);
				}
			}
			scanner.close();
		} 
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void renderTile(int tileID, RenderHandler renderer, int xPos, int yPos, int xZoom, int yZoom) {
		if(tileID >= 0 && tilesList.size() > tileID) {
			renderer.renderSprite(tilesList.get(tileID).sprite, xPos, yPos, xZoom, yZoom, false);
		}
		else {
			System.out.println("TileID " + tileID + " is not within range " + tilesList.size());
		}
	}
	
	public int size() {
		return tilesList.size();
	}
	
	public Sprite[] getSprites() {
		Sprite[] sprites = new Sprite[size()];
		
		for(int i = 0; i < sprites.length; i++) {
			sprites[i] = tilesList.get(i).sprite;
		}
		
		return sprites;
	}
	
	//Struct
	class Tile{
		public String tileName;
		public Sprite sprite;
		
		public Tile(String tileName, Sprite sprite) {
			this.tileName = tileName;
			this.sprite = sprite;
		}
	}
}
