package game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
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
import menuComponents.CharacterInfo;
import menuComponents.CreateName;
import menuComponents.Gender;
import menuComponents.Help;
import menuComponents.Load;
import menuComponents.Menu;
import menuComponents.MenuObject;
import menuComponents.Save;

import java.awt.Color;
import java.awt.Font;

@SuppressWarnings({ "serial", "unused" })
public class Game extends JFrame implements Runnable {

	public static int alpha = 0xFFFF00DC;

	public static Canvas canvas =  new Canvas();

	private RenderHandler renderer;

	private SpriteSheet sheet;
	private SpriteSheet boySheet;
	private SpriteSheet girlSheet;
	private SpriteSheet alphabetSheet;

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

	private int xZoom = 3;
	private int yZoom = 3;

	private Menu menu;
	public CreateName name;
	public Gender gender;
	private Help help;
	private Load load;
	public Save save;
	public CharacterInfo Cinfo;
	private Font font;
	
	private boolean boy = true;

	public static enum STATE{
		MENU,
		NAME,
		GENDER,
		GAME,
		LOAD,
		HELP,
		SAVE,
		CINFO
	};

	public static STATE State = STATE.MENU;

	public Game() {
		Rectangle backButton =  new Rectangle (80, 80, 200, 50);
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


		font = new Font("Arial", Font.PLAIN, 12);

		//color bg for panel
		//		getContentPane().setBackground(Color.CYAN);

		//		canvas.setBackground(Color.GRAY);


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

		// font
		BufferedImage alphabetSheetImage = loadImage("/alpha.png");
		alphabetSheet = new SpriteSheet(alphabetSheetImage);
		alphabetSheet.loadSprites(8, 8);

		//load player animated sprite
		AnimatedSprite boyAni = new AnimatedSprite(boySheet, 10);
		AnimatedSprite girlAni = new AnimatedSprite(girlSheet, 10);

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
		player = new Player(boyAni, xZoom, yZoom);
		objects[0] = player;
		objects[1] = gui;

		//new java class load
		menu = new Menu();
		name = new CreateName();
		gender = new Gender();
		help = new Help();
		load = new Load();
		save = new Save(this);
		Cinfo = new CharacterInfo(this);
		
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
		}
		else if(State == STATE.MENU) {
			menu.update(this);
		}

		else if(State == STATE.GENDER) {
			gender.update(this);
		}

		else if(State == STATE.LOAD) {
			load.update(this);
		}

		else if(State == STATE.HELP) {
			help.update(this);
		}

		else if(State == STATE.NAME) {
			name.update(this);
		}
		else if(State == STATE.SAVE) {
			save.update(this);
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

		map.render(renderer, objects, xZoom, yZoom);
		
		//		for(int i = 0; i < objects.length; i++) {
		//			objects[i].render(renderer, xZoom, yZoom);
		//		}

		if(State == STATE.GAME) {
			renderer.render(graphics);
			graphics.fillRect(0, 0, 140, 180);
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawString("Name =", 20, 50);
			
			if(name.fullName != null && !name.fullName.isEmpty()) {
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.drawString(name.fullName, 80, 50);
				
			}else {
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.drawString(load.nameLoad, 80, 50);
			}
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawString("Gender =", 20, 70);
			if(gender.getLoadChoice() == 1) {
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.drawString("Girl", 80, 70);
			}
			else if(gender.getLoadChoice() == 0){
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.drawString("Boy", 80, 70);
			}
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawString("Level =", 20, 90);
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawString("1", 80, 90);
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawString("Experience =", 20, 110);
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawString("0", 90, 110);
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawString("Weapon =", 20, 130);
			
			
			
//			System.out.println(name.fullName);

		}

		else if(State == STATE.MENU) {
			menu.render(graphics);
			//menu.render(renderer, xZoom, yZoom);
		}

		else if(State == STATE.NAME) {
			name.render(graphics);
		}

		else if(State == STATE.GENDER) {
			gender.render(graphics);
		}

		else if(State == STATE.HELP) {
			help.render(graphics);
		}

		else if(State == STATE.LOAD) {
			load.render(graphics);
		}
		else if(State == STATE.SAVE) {
			save.render(graphics);
		}
		else if(State == STATE.CINFO) {
			Cinfo.render(graphics);
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