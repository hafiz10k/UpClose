package entity;

public class Font extends Sprite{
	private Sprite[] sprites;
	private int currentSprite = 0;
	
	private static String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + "0123456789.,:;'\"!?$%()-=+/      ";
	
	public Font(SpriteSheet sheet) {
		sprites = sheet.getLoadedSprites();
	}
	
	public int getWidth() {
		return sprites[currentSprite].getWidth();
	}
	
	public int getHeight() {
		return sprites[currentSprite].getHeight();
	}
	
	public int[] getPixels() {
		return sprites[currentSprite].getPixels();
	}
	
	
}
