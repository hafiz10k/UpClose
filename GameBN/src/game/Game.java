package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
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

import battleScenes.FranciscoBattle;
import battleScenes.battleWon;
import battleScenes.dummyBattle;
import battleScenes.lailaRatna;
import cutScenes.BruneiWon;
import cutScenes.Credit;
import cutScenes.Ending;
import cutScenes.FranciscoDefeat;
import cutScenes.FranciscoScene;
import cutScenes.FranciscoScene02;
import cutScenes.LailaRatnaScene;
import cutScenes.Scene01;
import cutScenes.Scene02;
import cutScenes.Scene03;
import cutScenes.Scene04;
import cutScenes.Scene05;
import cutScenes.Scene06;
import cutScenes.Scene07;
import cutScenes.Scene08;
import cutScenes.Scene09;
import cutScenes.Scene10;
import entity.AnimatedSprite;
import entity.Item;
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
import menuComponents.CharacterInfo;
import menuComponents.CharacterInfoFrancisco;
import menuComponents.CharacterInfoLaila;
import menuComponents.CreateName;
import menuComponents.Exit;
import menuComponents.Gender;
import menuComponents.Help;
import menuComponents.Load;
import menuComponents.Menu;
import menuComponents.Save;
import menuComponents.retryGame;
import otherScene.hospitalScene;

@SuppressWarnings({ "serial", "unused" })
public class Game extends JFrame implements Runnable {

	public static int alpha = 0xFFFF00DC;

	public static Canvas canvas =  new Canvas();

	private RenderHandler renderer;

	private SpriteSheet sheet;
	private SpriteSheet boySheet;
	private SpriteSheet girlSheet;

	private int selectedTileID = 2;
	private int selectedLayer = 1;

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

	//class
	private Menu menu;
	public CreateName name;
	public Gender gender;
	private Help help;
	public Load load;
	public Save save;
	private gamePlay gamePlay;
	private retryGame retry;

	// cutscenes
	private Scene01 scene01;
	private Scene02 scene02;
	private Scene03 scene03;
	private Scene04 scene04;
	private Scene05 scene05;
	private Scene06 scene06;
	private Scene07 scene07;
	private Scene08 scene08;
	private Scene09 scene09;
	private Scene10 scene10;
	private LailaRatnaScene LR;
	private FranciscoScene fransShip;
	private FranciscoScene02 fransArrive;
	private FranciscoDefeat fransDefeat;
	private BruneiWon bnWin;
	private Ending end;
	private Credit credit;

	//places Scenes
	private hospitalScene hosp;

	//battle scenes
	public dummyBattle dummy;
	public lailaRatna lailaRatna;
	public FranciscoBattle francisco;
	
	public CharacterInfo Cinfo;
	private Item item;
	public CharacterInfoLaila Cinfolaila;
	public CharacterInfoFrancisco Cinfofrancisco;
	private battleWon win;
	public Exit exit;
	private Font font;

	private Rectangle dialogRect;

	private boolean boy = true;
	private boolean playedGameMusic = false;

	public static enum STATE{
		MENU,
		NAME,
		GENDER,
		SCENE01,
		SCENE02,
		SCENE03,
		SCENE04,
		SCENE05,
		SCENE06,
		SCENE07,
		SCENE08,
		SCENE09,
		SCENE10,
		LRS,
		FRANSHIP,
		FRANSARRIVE,
		FRANSDEFEAT,
		BNWIN,
		DUMMY,
		LAILARATNA,
		FRANCISCO,
		GAME,
		GAMEPLAY,
		LOAD,
		HELP,
		SAVE,
		RETRY,
		HOSP,
		CINFO,
		ITEM,
		CINFOLAILA,
		CINFOFRANCISCO,
		WIN,
		ENDING,
		CREDIT,
		EXIT
	};

	public static STATE State = STATE.MENU;

	public Game() {
		//		Rectangle backButton =  new Rectangle (80, 80, 200, 50);
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
		setResizable(false);

		canvas.setBackground(Color.DARK_GRAY);

		// create obj for buffer strat
		canvas.createBufferStrategy(3);	

		//		font = new Font("Arial", Font.PLAIN, 12);

		//set focus on canvas - so player dont have to click on screen everytime
		canvas.setFocusable(true);
		canvas.requestFocus();	

		renderer = new RenderHandler(getWidth(), getHeight());

		// load assets
		BufferedImage tilemapImage = loadImage("/tilesheet_320x160.png");
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
		vilAud = new Audio("/bgm/inGame.mp3");

		//load SDK GUI
//		GUIButton[] buttons = new GUIButton[tiles.size()];
//		Sprite[] tileSprites = tiles.getSprites();
//
//		for(int i = 0; i < buttons.length; i++) {
//			Rectangle tileRectangle = new Rectangle(0, i*(16*xZoom), 16*xZoom, 16*yZoom);
//
//			buttons[i] = new SDKButton(this, i, tileSprites[i], tileRectangle);
//		}
//
//		GUI gui = new GUI(buttons, 5, 5, true);


		// load objects
		objects = new GameObject[1];
		player = new Player(boyAni, xZoom, yZoom);
		objects[0] = player;
//		objects[1] = gui;

		//new java class load
		menu = new Menu(this);
		name = new CreateName(this);
		gender = new Gender(this);
		help = new Help(this);
		load = new Load(this);
		save = new Save(this);
		retry = new retryGame(this);
		gamePlay = new gamePlay(this);
		Cinfo = new CharacterInfo(this);
		Cinfolaila = new CharacterInfoLaila(this);
		Cinfofrancisco = new CharacterInfoFrancisco(this);
		exit = new Exit(this);
		
		// CUTSCENES
		scene01 = new Scene01(this);
		scene02 = new Scene02(this);
		scene03 = new Scene03(this);
		scene04 = new Scene04(this);
		scene05 = new Scene05(this);
		scene06 = new Scene06(this);
		scene07 = new Scene07(this);
		scene08 = new Scene08(this);
		scene09 = new Scene09(this);
		scene10 = new Scene10(this);
		LR = new LailaRatnaScene(this);
		fransShip = new FranciscoScene(this);
		fransArrive = new FranciscoScene02(this);
		fransDefeat = new FranciscoDefeat(this);
		bnWin = new BruneiWon(this);
		end = new Ending(this);
		credit = new Credit(this);
		

		//BATTLE
		dummy = new dummyBattle(this);
		lailaRatna = new lailaRatna(this);
		francisco = new FranciscoBattle(this);
		item = new Item(this);
		win = new battleWon(this);

		//PLACES SCENES
		hosp = new hospitalScene(this);



		// add listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
//		canvas.addMouseListener(mouseListener);
//		canvas.addMouseMotionListener(mouseListener);

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
		if(State == STATE.GAME) {
			for(int i = 0; i < objects.length; i++) {
				objects[i].update(this);

			}		
			gamePlay.update(this);
			
			if(!playedGameMusic) {
				menu.getAud().stop();
				fransShip.bgm.stop();
				
				
				vilAud.play();
				playedGameMusic = true;
			}
			
		}

		if(State == STATE.MENU) {
			menu.update(this);

			vilAud.stop();
			fransShip.bgm.stop();
			playedGameMusic = false;
		}

		if(State == STATE.GENDER) {
			gender.update(this);
		}

		if(State == STATE.SAVE) {
			save.update(this);
		}

		if(State == STATE.LOAD) {
			load.update(this);
		}

		if(State == STATE.RETRY) {
			retry.update(this);
		}


		if(State == STATE.HELP) {
			help.update(this);
		}

		if(State == STATE.NAME) {
			name.update(this);
		}

		if(State == STATE.CINFO) {
			Cinfo.update(this);
		}
		
		if(State == STATE.CINFOLAILA) {
			Cinfolaila.update(this);
		}
		
		if(State == STATE.CINFOFRANCISCO) {
			Cinfofrancisco.update(this);
		}

		if(State == STATE.SCENE01) {
			menu.getAud().stop();
			scene01.update(this);
		}

		if(State == STATE.SCENE02){
			scene02.update(this);
			menu.getAud().close();
		}

		if(State == STATE.SCENE03) {
			scene03.update(this);
		}

		if(State == STATE.SCENE04) {
			scene04.update(this);
		}

		if(State == STATE.SCENE05) {
			scene05.update(this);
		}

		if(State == STATE.SCENE06) {
			scene06.update(this);
		}

		if(State == STATE.SCENE07) {
			scene07.update(this);
		}

		if(State == STATE.SCENE08) {
			scene08.update(this);
		}

		if(State == STATE.SCENE09) {
			scene09.update(this);
		}

		if(State == STATE.SCENE10) {
			scene10.update(this);
		}

		if(State == STATE.LRS) {
				menu.getAud().stop();
				vilAud.stop();
				
				playedGameMusic = false;
			
			LR.update(this);
		}
		
		if(State == STATE.FRANSHIP) {
				menu.getAud().stop();
				vilAud.stop();
				
				fransShip.bgm.play();
				playedGameMusic = true;
			
			fransShip.update(this);
		}
		
		if(State == STATE.FRANSARRIVE) {
				menu.getAud().stop();
				vilAud.stop();

				fransArrive.update(this);
		}
		
		if(State == STATE.FRANSDEFEAT) {
			menu.getAud().stop();
			vilAud.stop();
			fransDefeat.update(this);
		}
		
		if(State == STATE.BNWIN) {
			bnWin.update(this);
		}
		
		if(State == STATE.ENDING) {
			end.update(this);
		}
		
		if(State == STATE.CREDIT) {
			credit.update(this);
		}
		
		if(State == STATE.DUMMY) {
			dummy.update(this);
		}

		if(State == STATE.LAILARATNA) {
			menu.getAud().stop();
			vilAud.stop();
			
			playedGameMusic = false;
			
			lailaRatna.update(this);
		}

		if(State == STATE.FRANCISCO) {
			francisco.update(this);
		}
		
		if(State == STATE.WIN) {
			
			win.update(this);
		}

		if(State == STATE.ITEM) {
			item.update(this);
		}

		if(State == STATE.HOSP) {
			hosp.update(this);
		}
		
		if(State == STATE.EXIT) {
			exit.update(this);
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

//	public void handleCTRL(boolean[] keys) {
//		if(keys[KeyEvent.VK_S]) {
//			map.saveMap();
//		}
//	}
//
//	//set tile
//	public void leftClick(int x, int y) { 
//		Rectangle mouseRect = new Rectangle(x, y, 1, 1);
//		boolean stoppedChecking = false;
//
//		for(int i = 0; i < objects.length; i++) {
//			if(!stoppedChecking) {
//				stoppedChecking = objects[i].handleMouseClick(mouseRect, renderer.getCamera(), xZoom, yZoom);
//			}
//
//		}
//
//		if(!stoppedChecking) {
//			x = (int) Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
//			y = (int) Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
//			map.setTile(selectedLayer, x, y, selectedTileID);
//		}
//	}
//
//	//remove tile 
//	public void rightClick(int x, int y) {
//		x = (int) Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
//		y = (int) Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
//		map.removeTile(selectedLayer, x, y);
//	}

	public void render() {

		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);

		renderer.render(graphics);

		if(State == STATE.GAME) {
			map.render(renderer, objects, xZoom, yZoom);
			int chosen1 = gender.getLoadChoice();	

			gamePlay.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
		}

		if(State == STATE.MENU) {
			menu.render(renderer, xZoom, yZoom); // sword
			renderer.render(graphics);	
			menu.render(graphics); // menu list

		}

		if(State == STATE.NAME) {
			name.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			name.render(graphics);
		}

		if(State == STATE.GENDER) {
			gender.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			gender.render(graphics);
		}

		if(State == STATE.HELP) {
			help.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			help.render(graphics);
		}

		if(State == STATE.LOAD) {
			load.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			load.render(graphics);
		}

		if(State == STATE.SAVE) {
			save.render(graphics);
		}

		if(State == STATE.RETRY) {
			retry.render(graphics);
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

		if(State == STATE.SCENE04) {
			scene04.render(renderer, this, player, xZoom, yZoom);
			renderer.render(graphics);
			scene04.render(graphics);
		}

		if(State == STATE.SCENE05) {
			scene05.render(renderer, this, player, xZoom, yZoom);
			renderer.render(graphics);
			scene05.render(graphics, this);
		}

		if(State == STATE.SCENE06) {
			scene06.render(renderer, this, player, xZoom, yZoom);
			renderer.render(graphics);
			scene06.render(graphics, this); 
		}

		if(State == STATE.SCENE07) {
			scene07.render(renderer, this, player, xZoom, yZoom);
			renderer.render(graphics);
			scene07.render(graphics);
		}

		if(State == STATE.SCENE08) {
			scene08.render(renderer, this, player, xZoom, yZoom);
			renderer.render(graphics);
			scene08.render(graphics);
		}

		if(State == STATE.SCENE09) {
			scene09.render(renderer, this, player, xZoom, yZoom);
			renderer.render(graphics);
			scene09.render(graphics, this);
		}

		if(State == STATE.SCENE10) {
			scene10.render(renderer, this, player, xZoom, yZoom);
			renderer.render(graphics);
			scene10.render(graphics, this);
		}

		if(State == STATE.LRS) {

			LR.render(renderer, xZoom, yZoom); 
			renderer.render(graphics); 
			LR.render(graphics); 
		}
		
		if(State == STATE.FRANSHIP) {

			fransShip.render(renderer, xZoom, yZoom); 
			renderer.render(graphics); 
			fransShip.render(graphics); 
		}
		
		if(State == STATE.FRANSARRIVE) {

			fransArrive.render(renderer, xZoom, yZoom); 
			renderer.render(graphics); 
			fransArrive.render(graphics); 
		}
		
		if(State == STATE.FRANSDEFEAT) {

			fransDefeat.render(renderer, xZoom, yZoom); 
			renderer.render(graphics); 
			fransDefeat.render(graphics); 
		}
		
		if(State == STATE.BNWIN) {

			bnWin.render(renderer, xZoom, yZoom); 
			renderer.render(graphics); 
			bnWin.render(graphics); 
		}
		
		if(State == STATE.ENDING) {
			end.render(renderer, graphics, xZoom, yZoom);
			renderer.render(graphics);
			end.render(graphics);
		}
		
		if(State == STATE.CREDIT) {
			credit.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			credit.render(graphics);
		}

		if(State == STATE.DUMMY) {
			dummy.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			dummy.render(graphics, this);
		}

		if(State == STATE.LAILARATNA) {
			lailaRatna.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			lailaRatna.render(graphics);
		}

		if(State == STATE.FRANCISCO) {
			francisco.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			francisco.render(graphics);
		}
		
		if(State == STATE.WIN) {
			win.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			win.render(graphics);
		}

		if(State == STATE.ITEM) {
			item.render(renderer, player, xZoom, yZoom);
			renderer.render(graphics);
			item.render(graphics, this);
		}

		if(State == STATE.HOSP) {
			hosp.render(renderer, xZoom, yZoom, this);
			renderer.render(graphics);
			hosp.render(graphics);
		}
		if(State == STATE.CINFO) {
			Cinfo.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			Cinfo.render(graphics);
		}
		
		if(State == STATE.CINFOLAILA) {
			Cinfolaila.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			Cinfolaila.render(graphics);
		}
		
		if(State == STATE.CINFOFRANCISCO) {
			Cinfofrancisco.render(renderer, xZoom, yZoom);
			renderer.render(graphics);
			Cinfofrancisco.render(graphics);
		}
		
		if(State == STATE.EXIT) {
			exit.render(graphics);
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