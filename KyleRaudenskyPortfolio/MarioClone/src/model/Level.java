package model;

import controller.Sound;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * @author Chase and the Catdaddiez
 *
 *This class represents an actual level in the game. It reads in a text file
 *representing the level grid and loads images onto the board based on where certain
 *characters appear in the text file (see level_key.txt). This is also where switching
 *between levels is handled.
 */


//this enum is not used in the final game, but was used for testing
enum Levels {
	
	LEVEL_ONE("sample_level1.txt");
	
	String fileName;
	
    Levels(String fn) {
		fileName = "levels/" + fn;
    }
	
	public String getFileName() {
		return fileName;
	}
	
}

public class Level {
	
	private String fileName;
	private LinkedList<VisibleObject> levelGrid;
	private ArrayList<VisibleObject> backgroundGrid;
	
	private Color backgroundColor;
	
	private int xOffset;
	private int yOffset;
	
	private int endX;
	
	/**
	 * creates a new level based on a level text file
	 * @param fileName
	 * @throws IOException 
	 */
	public Level(String fileName) {
		
		levelGrid = new LinkedList<VisibleObject>();
		backgroundGrid = new ArrayList<VisibleObject>();
		
		this.fileName = fileName;
		
		initLevel();
		
	}
	
	public Level(int major, int minor) {
		
		levelGrid = new LinkedList<VisibleObject>();
		backgroundGrid = new ArrayList<VisibleObject>();
		
		this.fileName = "levels/" + major + "-" + minor + ".txt";
		
		initLevel();
		
	}
	
	/**
	 * Reads in the text file representing a level and fills two lists with the objects
	 * it creates based on the level. One list for background objects, one for real game
	 * objects
	 * @throws IOException
	 */
	public void readFile() throws IOException {
		FileReader read = new FileReader(this.fileName);
		BufferedReader br = new BufferedReader(read);
		
		//Read in the file, looking for the first 2 lines to be the
		//background and music files
		
		int y = 0;
		int x = 0;
		int realX;
		int realY;
		int pipeX = 0;
		
		String background;
		String music;
		
		//changed and used later for creating pipes
		boolean inPipe = false;
		
		//get the background color and music file
		background = br.readLine();
		music = br.readLine();
		
		if (!music.equals("")) {
			Sound.valueOf(music).changeVolume(.3);
			Sound.valueOf(music).playLoop();
		}
		
		String rgb[] = background.split(" ");
		
		backgroundColor = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		
		//read the file one character at a time
		char atSpot = (char) br.read();
		
		
		//based on the character at each spot, create an object accordingly and place it in
		//its proper linked list
		do {
			if (atSpot == '\\') {
				y++;
				x = -2;
				
				
			}
			realX = 16*x;
			realY = 16*y;
			
			if (inPipe) {
				int i = Character.getNumericValue(atSpot);
				this.levelGrid.add(new Blair(realX, realY, "bluefuckingblock.png"));
				this.levelGrid.add(new Blair(realX, realY-16, "bluefuckingblock.png"));
				this.levelGrid.add(new Blair(pipeX, realY, "bluefuckingblock.png"));
				this.levelGrid.add(new Blair(pipeX, realY-16, "bluefuckingblock.png"));
				this.levelGrid.add(new Pipe(pipeX, realY, i));
				inPipe = false;
			}
			else if (atSpot == 'G' ) {
				this.levelGrid.add(new Blair(realX, realY, "level-floor.png"));
				
			}
			else if (atSpot == 'B') {
				this.levelGrid.add(new RegBlock(realX, realY));
				
			}
			else if (atSpot == '?') {
				this.levelGrid.add(new QuestionBlock(realX, realY, "coin", null));
				
			}
			else if (atSpot == '&') {
				Mushroom mush = new Mushroom(realX, realY-16);
				this.levelGrid.add(mush);
				this.levelGrid.add(new QuestionBlock(realX, realY, "mushroom", mush));
				
				
			}
			
			else if (atSpot == '%') {
				this.levelGrid.add(new QuestionBlock(realX, realY, "star", null));
			}
			else if (atSpot == 'g') {
				this.levelGrid.add(new Goomba(realX, realY));
				
			}
			else if (atSpot == 'k') {
				this.levelGrid.add(new Koopa(realX,realY));
				
			}
			else if (atSpot == 'c') {
				this.levelGrid.add(new Coin(realX, realY));
				
			}
			else if (atSpot == '!') {
				//this.levelGrid.add(Bowser(x,y));
				
			}
			else if (atSpot == 'M') {
				//this.levelGrid.add(new Mario(realX, realY,"mario-normal-stationary.png"));
				
			}
			else if (atSpot == 'F') {
				this.levelGrid.add(new FlagBlock(realX, realY));
				this.backgroundGrid.add(new StaticImage(realX+4, realY-152, "flagpole.png"));
				this.backgroundGrid.add(new StaticImage(realX-9, realY-143, "flag.png"));
				this.endX = realX+2;
				
				
			}
			else if (atSpot == 'P') {
				inPipe = true;
				pipeX = realX;
				
			}
			else if (atSpot == 'C') {
				this.backgroundGrid.add(new Bloud(realX, realY, "white"));
			}
			else if (atSpot == 'b') {
				this.backgroundGrid.add(new Bloud(realX, realY, "green"));
			}
			else if (atSpot == 'E') {
				this.backgroundGrid.add(new StaticImage(realX-5, realY-60, "castle.png"));
			}
			else if (atSpot == 'S') {
				this.levelGrid.add(new Blair(realX, realY, "stairs.png"));
			}
			x++;
			
			atSpot = (char) br.read();
			
		} while((int) atSpot != 65535);
			
		br.close();
	}
	
	/**
	 * updates all the objects in the level grid and background lists. called at every
	 * main game update
	 */
	public void update() {
		
		for (VisibleObject v : levelGrid) {
		    
		    if (v.getX()- xOffset >= 0 ) {
		    	v.onScreen();
		    }
		    v.update();
		    
		}
		
		for (int i = 0; i < levelGrid.size(); i++) {
			
			VisibleObject v = levelGrid.get(i);
			
			if (v.getDeleteOnNextUpdate()) {
				
				levelGrid.remove(i);
				i--;
				
			}
			
		}
		
	}
	/**
	 * called by update, draws all the images for the objects on the game grid
	 */
	public void draw() {
		
		VisibleObject.drawBackground(backgroundColor);
		
		for (VisibleObject o : backgroundGrid) {
			o.drawWithViewOffset(xOffset, yOffset);
		}
		for (VisibleObject v : levelGrid) {
		    v.drawWithViewOffset(xOffset, yOffset);
		}
		
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	public LinkedList<VisibleObject> getVisibleObjects() {
		return levelGrid;
	}
	
	public void updateXOffset(int newOffset) {
		xOffset = newOffset;
	}
	
	public int getEndX() {
		return this.endX;
	}
	
	/**
	 * loads/resets a new level to the beginning
	 */
	private void initLevel() {
		
		levelGrid.clear();
		backgroundGrid.clear();
		
		try {
			this.readFile();
		}
		catch(IOException e) {
			System.out.println("IO Exception: error reading file");
			e.printStackTrace();
		}
		
		xOffset = 0;
		
	}
	
	public void resetLevel() {
		initLevel();
	}
	
}
