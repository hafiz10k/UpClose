package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.sun.glass.events.KeyEvent;

@SuppressWarnings({ "serial", "restriction", "unused" })
public class Game extends JFrame implements Runnable {

	public static int alpha = 0xFFFF00DC;
	
	private Canvas canvas =  new Canvas();
	
	private RenderHandler renderer;
	private BufferedImage background = null;
	
	private SpriteSheet sheet;
	private SpriteSheet playerSheet;

	private int selectedTileID = 2;

	private Rectangle testRectangle = new Rectangle(30,30,100,100);

	private Tiles tiles;
	private Map map;

	private GameObject[] objects;
	private KeyBoardListener keyListener = new KeyBoardListener(this);
	private MouseEventListener mouseListener = new MouseEventListener(this);

	private Player player;

	private int xZoom = 3;
	private int yZoom = 3;

	private Menu menu;
	private Help help;
	private Load load;
	
	public enum STATE{
		MENU,
		GAME,
		LOAD,
		HELP
	};
	
	public static STATE State = STATE.MENU;
	

	
	
	
	public Game() {
		
		setBackground(Color.BLACK);

//		c.setBackground(Color.red);
		// make our prog shutdown when we exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set pos n size of frame
		setBounds(0, 0, 1000, 800);

		//put our frame in center of screen
		setLocationRelativeTo(null);

		// add graphics component
		add(canvas);

		//make frame visible
		setVisible(true);

		
		
		// create obj for buffer strat
		canvas.createBufferStrategy(3);	

		renderer = new RenderHandler(getWidth(), getHeight());

		// load assets
		BufferedImage sheetImage = loadImage("/Tiles1.png");
		sheet = new SpriteSheet(sheetImage);
		sheet.loadSprites(16, 16);
		
		background = loadImage("/background.png");

		BufferedImage playerSheetImage = loadImage("/girl-main-anim.png");
		playerSheet = new SpriteSheet(playerSheetImage);
		playerSheet.loadSprites(24, 32);

		//animated sprite
		AnimatedSprite playerAni = new AnimatedSprite(playerSheet, 10);

		// load tiles
		tiles = new Tiles(new File("tile.txt"), sheet);

		// load map
		map = new Map(new File("Map.txt"), tiles);

		// testImage = loadImage("/GrassTile.png");
		// testSprite = sheet.getSprite(4, 1);

		testRectangle.generateGraphics(2, 12234);

		//load SDK GUI
		GUIButton[] buttons = new GUIButton[tiles.size()];
		Sprite[] tileSprites = tiles.getSprites();

		for(int i = 0; i < buttons.length; i++) {
			Rectangle tileRectangle = new Rectangle(0, i*(16*xZoom + 2), 16*xZoom, 16*yZoom);

			buttons[i] = new SDKButton(this, i, tileSprites[i], tileRectangle);
		}

		GUI gui = new GUI(buttons, 5, 5, true);

		// load objects
		objects = new GameObject[2];
		player = new Player(playerAni);
		objects[0] = player;
		objects[1] = gui;
		
		//new java class load
		menu = new Menu();
		help = new Help();
		load = new Load();
		


		// add listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
		
		this.addMouseListener(new MouseInput());

	}
	
	
	public void update() { 
		for(int i = 0; i < objects.length; i++) {
			objects[i].update(this);
		}
	}

	private BufferedImage loadImage(String path) {
		try {
			BufferedImage loadedImage = ImageIO.read(Game.class.getResourceAsStream(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

			return formattedImage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void handleCTRL(boolean[] keys) {
		if(keys[KeyEvent.VK_S]) {
			map.saveMap();
		}
	}

	//set tile
	public void leftClick(int x, int y) { 
		Rectangle mouseRect = new Rectangle(x, y, 1, 1);
		boolean stoppedChecking = false;

		for(int i = 0; i < objects.length; i++) {
			if(!stoppedChecking) {
				stoppedChecking = objects[i].handleMouseClick(mouseRect, renderer.getCamera(), xZoom, yZoom);
			}

		}

		if(!stoppedChecking) {
			x = (int) Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
			y = (int) Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
			map.setTile(x, y, selectedTileID);
		}
	}

	//remove tile 
	public void rightClick(int x, int y) {
		x = (int) Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
		y = (int) Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
		map.removeTile(x, y);
	}

	public void render() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);

		map.render(renderer, xZoom, yZoom);
		
		graphics.drawImage(background, 0, 0, null);
		
		for(int i = 0; i < objects.length; i++) {
			objects[i].render(renderer, xZoom, yZoom);
		}
		
		if(State == STATE.GAME) {
		renderer.render(graphics);
		
		}else if(State == STATE.MENU) {
			menu.render(graphics);
		}else if(State == STATE.HELP) {
			help.render(graphics);
		}else if(State == STATE.LOAD) {
			load.render(graphics);
		}
		graphics.dispose();
		bufferStrategy.show();
		renderer.clear();
		
	
	}

	public void changeTile(int tileID) {
		selectedTileID = tileID;
	}
	
	public int getSelectedTile() {
		return selectedTileID;
	}


	@Override
	public void run() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;

		long lastTime = System.nanoTime();

		double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
		double changeInSeconds = 0;

		while(true) {
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion;

			while(changeInSeconds >= 1) {
				update();
				changeInSeconds = 0;
			}

			render();

			lastTime = now;
		}

	}

	public static void main(String[] args) {

		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();

	}

	public KeyBoardListener getKeyListener() {
		return keyListener;
	}

	public MouseEventListener getMouseListener() {
		return mouseListener;
	}

	public RenderHandler getRenderer() {
		return renderer;
	}


}
