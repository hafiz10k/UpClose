package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import entity.AnimatedSprite;
import entity.Map;
import entity.Player;
import entity.Rectangle;
import entity.Sprite;
import entity.SpriteSheet;
import entity.Tiles;
import gui.GUI;
import gui.GUIButton;
import gui.SDKButton;
import handler.KeyBoardListener;
import handler.MouseEventListener;
import handler.MouseInput;
import handler.RenderHandler;
import menuComponents.CreateName;
import menuComponents.Help;
import menuComponents.Load;
import menuComponents.Menu;

import javax.swing.JButton;
import java.awt.BorderLayout;

@SuppressWarnings({ "serial", "unused" })
public class Game extends JFrame implements Runnable {

	public static int alpha = 0xFFFF00DC;
	
	private Canvas canvas =  new Canvas();
	
	private RenderHandler renderer;
	private BufferedImage testImage = null;
	
	private SpriteSheet sheet;
	private SpriteSheet playerSheet;
	private SpriteSheet alphabetSheet;

	private int selectedTileID = 2;

	private Rectangle testRectangle = new Rectangle(30,30,100,100);

	private Tiles tiles;
	private Map map;

	private GameObject[] objects;
	private KeyBoardListener keyListener = new KeyBoardListener(this);
	private MouseEventListener mouseListener = new MouseEventListener(this);
	private MouseInput mouseListener2 = new MouseInput();

	private Player player;

	private int xZoom = 3;
	private int yZoom = 3;

	private Menu menu;
	private CreateName name;
	private Help help;
	private Load load;
	
	public static enum STATE{
		MENU,
		NAME,
		GAME,
		LOAD,
		HELP
	};
	
	public static STATE State = STATE.MENU;
	
	public Game() {
		setTitle("UpClose");
		
		// make our prog shutdown when we exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set pos n size of frame
		setBounds(0, 0, 1000, 800);

		//put our frame in center of screen
		setLocationRelativeTo(null);
		
		setResizable(false);

		// add graphics component
		getContentPane().add(canvas);
		
		//set focus on canvas - so player dont have to click on screen everytime
		canvas.setFocusable(true);
		canvas.requestFocus();	

		//make frame visible
		setVisible(true);
		
		

		// create obj for buffer strat
		canvas.createBufferStrategy(3);	
		
		getContentPane().setBackground(Color.BLACK);

		renderer = new RenderHandler(getWidth(), getHeight());

		// load assets
		BufferedImage tilemapImage = loadImage("/Tiles3.png");
		sheet = new SpriteSheet(tilemapImage);
		sheet.loadSprites(16, 16);
		
		//male
		BufferedImage playerSheetImage = loadImage("/mainAnimated.png");
		playerSheet = new SpriteSheet(playerSheetImage);
		playerSheet.loadSprites(24, 32);

		// female
//		BufferedImage playerSheetImage = loadImage("/girl-main-anim.png");
//		playerSheet = new SpriteSheet(playerSheetImage);
//		playerSheet.loadSprites(24, 32);
		
		BufferedImage alphabetSheetImage = loadImage("/alpha.png");
		alphabetSheet = new SpriteSheet(alphabetSheetImage);
		alphabetSheet.loadSprites(8, 8);

		//load player animated sprite
		AnimatedSprite playerAni = new AnimatedSprite(playerSheet, 10);

		// load tiles
		tiles = new Tiles(new File("tile.txt"), sheet);

		// load map
		map = new Map(new File("Map.txt"), tiles);

	
		// testSprite = sheet.getSprite(4, 1);


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
		name = new CreateName(alphabetSheet);
		help = new Help();
		load = new Load();

		// add listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
//		canvas.addMouseListener(mouseListener2);
//		this.addMouseListener(new MouseInput());
//		this.addMouseListener(new MouseInput());

	}
	
	
	public void update() { 
//		for(int i = 0; i < objects.length; i++) {
//			objects[i].update(this);
//		}
		if(State == STATE.GAME) {
			for(int i = 0; i < objects.length; i++) {
				objects[i].update(this);
			}
		}
		else if(State == STATE.MENU) {
			menu.update(this);
		}
		
		else if(State == STATE.LOAD) {
			load.update(this);
		}
		
		else if(State == STATE.HELP) {
			help.update(this);
		}
	}

	public static BufferedImage loadImage(String path) {
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
		
		for(int i = 0; i < objects.length; i++) {
			objects[i].render(renderer, xZoom, yZoom);
		}
		
		if(State == STATE.GAME) {
		renderer.render(graphics);
		
		}
		
		else if(State == STATE.MENU) {
			menu.render(graphics);
		}
		
		else if(State == STATE.NAME) {
			name.render(graphics);
		}
		
		else if(State == STATE.HELP) {
			help.render(graphics);
		}
		
		else if(State == STATE.LOAD) {
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