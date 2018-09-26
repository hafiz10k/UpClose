package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import handler.Audio;
import handler.KeyBoardListener;
import handler.MouseEventListener;
import handler.MouseInput;
import handler.RenderHandler;
import menuComponents.CreateName;
import menuComponents.Gender;
import menuComponents.Help;
import menuComponents.Load;
import menuComponents.Menu;
import screenMap.Scene01;
import screenMap.Scene02;
import screenMap.Scene03;

@SuppressWarnings({ "serial", "unused" })
public class Game extends JFrame implements Runnable {

	public static int alpha = 0xFFFF00DC;

	public static Canvas canvas =  new Canvas();

	private RenderHandler renderer;

	private SpriteSheet sheet;
	private SpriteSheet boySheet;
	private SpriteSheet girlSheet;
	private SpriteSheet alphabetSheet;

	private BufferedImage bedroom;

	private int selectedTileID = 2;
	private int selectedLayer = 0;

	private Rectangle testRectangle = new Rectangle(30,30,100,100);

	private Tiles tiles;
	private Map map;

	private GameObject[] objects;
	private KeyBoardListener keyListener = new KeyBoardListener(this);
	private MouseEventListener mouseListener = new MouseEventListener(this);
	private MouseInput mouseListener2 = new MouseInput();

	public Player player;

	private Audio vilAud;

	private int xZoom = 3;
	private int yZoom = 3;

	private Menu menu;
	private CreateName name;
	private Gender gender;
	private Help help;
	private Load load;
	
	// cutscenes
	private Scene01 scene01;
	private Scene02 scene02;
	private Scene03 scene03;

	private boolean boy = true;
	private boolean playedGameMusic = false;

	public static enum STATE{
		MENU,
		NAME,
		GENDER,
		SCENE01,
		SCENE02,
		SCENE03,
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

		// add graphics component
		add(canvas);

		//make frame visible
		setVisible(true);

		//frame cannot be resize
		setResizable(true);

		getContentPane().setBackground(Color.BLACK);

		// create obj for buffer strat
		canvas.createBufferStrategy(3);	

		//set focus on canvas - so player dont have to click on screen everytime
		canvas.setFocusable(true);
		canvas.requestFocus();	

		renderer = new RenderHandler(getWidth(), getHeight());

		// load assets
		BufferedImage tilemapImage = loadImage("/Tiles3.png");
		sheet = new SpriteSheet(tilemapImage);
		sheet.loadSprites(16, 16);

		//male
		BufferedImage playerSheetImage = loadImage("/mainAnimated.png");
		boySheet = new SpriteSheet(playerSheetImage);
		boySheet.loadSprites(24, 32);

		// female
		BufferedImage girlSheetImage = loadImage("/girl-main-anim.png");
		girlSheet = new SpriteSheet(girlSheetImage);
		girlSheet.loadSprites(24, 32);

		//load player animated sprite
		AnimatedSprite boyAni = new AnimatedSprite(boySheet, 10);
		AnimatedSprite girlAni = new AnimatedSprite(girlSheet, 10);

		// load tiles
		tiles = new Tiles(new File("tile.txt"), sheet);

		// load map
		map = new Map(new File("Map.txt"), tiles);


		//load audio
		vilAud = new Audio("/Gentle-Closure.mp3.mp3");


		//		 testSprite = sheet.getSprite(4, 1);


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
		player = new Player(boyAni, xZoom, yZoom);
		objects[0] = player;
		objects[1] = gui;

		//new java class load
		menu = new Menu(this);
		name = new CreateName();
		gender = new Gender();
		help = new Help();
		load = new Load();

		// CUTSCENES
		scene01 = new Scene01(this);
		scene02 = new Scene02(this);
		scene03 = new Scene03(this);

		// add listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);

		addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int newWidth = canvas.getWidth();
				int newHeight = canvas.getHeight();

				if(newWidth > renderer.getMaxWidth())
					newWidth = renderer.getMaxWidth();

				if(newHeight > renderer.getMaxHeight())
					newHeight = renderer.getMaxHeight();

				renderer.getCamera().w = newWidth;
				renderer.getCamera().h = newHeight;
				canvas.setSize(newWidth, newHeight);
				pack();

			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			} 

			@Override
			public void componentShown(ComponentEvent e) {
			}

		});
	}


	public void update() { 
		//		for(int i = 0; i < objects.length; i++) {
		//			objects[i].update(this);
		//		}
		if(State == STATE.GAME) {
			for(int i = 0; i < objects.length; i++) {
				objects[i].update(this);

			}			

			if(!playedGameMusic) {
				menu.getAud().close();
				vilAud.play();
				playedGameMusic = true;
			}
		}

		if(State == STATE.MENU) {
			menu.update(this);
			
			vilAud.stop();
			playedGameMusic = false;
		}

		if(State == STATE.GENDER) {
			gender.update(this);
		}

		if(State == STATE.LOAD) {
			load.update(this);
		}

		if(State == STATE.HELP) {
			help.update(this);
		}

		if(State == STATE.NAME) {
			name.update(this);
		}

		if(State == STATE.SCENE01) {
			scene01.update(this, player);
		}
		
		if(State == STATE.SCENE02){
			scene02.update(this);
			menu.getAud().close();
		}
		
		if(State == STATE.SCENE03) {
			scene03.update(this, player);
		}
	}

	public BufferedImage loadImage(String path) {
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
			map.setTile(selectedLayer, x, y, selectedTileID);
		}
	}

	//remove tile 
	public void rightClick(int x, int y) {
		x = (int) Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
		y = (int) Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
		map.removeTile(selectedLayer, x, y);
	}

	public void render() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);

		renderer.render(graphics);

		if(State == STATE.GAME) {
			map.render(renderer, objects, xZoom, yZoom);
			int chosen1 = gender.getLoadChoice();
			//			for(int i = 0; i < objects.length; i++) {
			//				objects[i].render(renderer, xZoom, yZoom);
			//			}
			renderer.render(graphics);


		}

		if(State == STATE.MENU) {
			//			menu.render(graphics);

			menu.render(renderer, this, xZoom, yZoom); // sword
			renderer.render(graphics);	
			menu.render(graphics); // menu list

		}

		if(State == STATE.NAME) {
			name.render(graphics);
		}

		if(State == STATE.GENDER) {
			gender.render(graphics);
		}

		if(State == STATE.HELP) {
			help.render(graphics);
		}

		if(State == STATE.LOAD) {
			load.render(graphics);
		}

		if(State == STATE.SCENE01) {

			scene01.render(renderer, this, player, xZoom, yZoom); // dialogs
			renderer.render(graphics); // both
			scene01.render(this, graphics, player); // images, players
		}
		
		if(State == STATE.SCENE02) {
			scene02.render(renderer, graphics, xZoom, yZoom);
			renderer.render(graphics);
			scene02.render(graphics);
		}
		
		if(State == STATE.SCENE03) {
			scene03.render(renderer, this, player, xZoom, yZoom);
			renderer.render(graphics);
			scene03.render(graphics);
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

	public Map getMap() {
		return map;

	}

	public boolean isBoy() {
		return boy;
	}

	public void setBoy(boolean boy) {
		this.boy = boy;
	}

	public int getXZoom() {
		return xZoom;
	}

	public int getYZoom() {
		return yZoom;
	}


}