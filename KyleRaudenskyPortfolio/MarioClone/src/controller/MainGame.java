//
// MainGame
// Super Mario Bros. Main Game
// (c) 2013 Chase and the Cat Daddiez
//
// The main game!
//

package controller;
import view.GameWindow;

import model.Level;
import model.StaticImage;
import model.CollidableObject;
import model.Collision;
import model.Mario;
import controller.PauseMenu;
import controller.Sound;
import controller.State;
import controller.TimingController;
import model.VisibleObject;

public class MainGame extends State {
	
	private Level level;
	private Mario mario;
	private double timer;
	private boolean playerControl = true; // Does the player have control?
	
	StaticImage hudCoin; // The images in the HUD.
	StaticImage hudX;
	
	private GameWindow handle;
	
	private int majorLevel = 1; // The current level (like 1-1)
	private int minorLevel = 1;
	
	private int xMax = 124; // Used in screen-scrolling.
	
	// Timers and flags involved in ending the level.
	
	private boolean levelEnded = false;
	private boolean endedHitGround = false;
	private double endTimer = 2;
	private final int END_TIME = 2;
	private double nextTimer = 8;
	
	public MainGame(GameWindow g) {
		
		// Initialize the level.
		
		level = new Level("levels/1-1.txt");
		mario = new Mario(4,0,"mario-normal-stationary.png");
		
		handle = g;
		
		hudCoin = new StaticImage(89, 16, "HUDCoin.png");
		hudX = new StaticImage(97, 18, "HUDX.png");
		
		timer = 300;
		
		loadState();
		
	}
	
	public void update() {
		
		if (mario.isDoneDying() == true) { // Reset the level, if dead.
			this.resetState();
		}
		
		if (mario.getRealX() >= level.getEndX() && !levelEnded) {
			
			// Kick off the ending animation if Mario reaches the endpoint.
			
			levelEnded = true;
			
			Sound.MAINTHEME.stop();
			playerControl = false;
			Sound.FLAGPOLE.play();
			
			mario.setVelX(0);
			mario.setVelY(0);
			
		}
		
		if (timer <= 0) { // Time up, Mario dies.
			mario.setPowerUpState(0);
		}
		
		if (mario.getPowerUpState() == 0) { // Immediate death register.
			mario.update();
			return;
		}
		
		if (mario.getY() >= 224) { // Death via pit.
			mario.setPowerUpState(0);
		}
		
		level.update();
		mario.update();
		
		this.handleMarioCollision();
		
		if (mario.getX() > xMax) { // Handle side-scrolling.
			
			xMax = mario.getX();
			level.updateXOffset(xMax - 124);
			
		}
		
		if (mario.getRealX() < level.getXOffset()) { // Prevent the player from moving backward.
			mario.noSteppingBack(level.getXOffset());
		}
		
		if (!levelEnded) { // Run collision checks on the rest of the objects.
			
			for (VisibleObject v : level.getVisibleObjects()) {
			
				if (v instanceof CollidableObject) {
				
					CollidableObject fst = (CollidableObject) v;
					fst.setGrounded(false);
				
					for (VisibleObject x : level.getVisibleObjects()) {
					
						if (x instanceof CollidableObject) {
						
							CollidableObject snd = (CollidableObject) x;
						
							if (fst == snd) {
								continue;
							}
						
							if (fst.checksForCollision()) {
							
								Collision c = fst.detectCollision(snd);
							
								if (c.getSide() != Collision.NONE) {
									fst.collidesWith(snd, c);
								}
							
							}
						
						}
					
					}
				
				}
			
			}
			
			timer -= (TimingController.getTimeSinceLastUpdate() * 1.5); // Update the clock.
			
		}
		else {
			
			//
			// Ending animation code.
			//
			
			if (mario.getVelY() == 0 && endTimer == END_TIME) {
				
				if (endedHitGround) {
					Sound.STAGECLEAR.play();
					mario.setVelX(64.0);
					
				}
				
				endTimer = END_TIME - 0.1;
				
			}
			
			if (endTimer <= 0) {
				
				if (endedHitGround) {
					
					mario.setVelX(0.0);
					mario.setRealY(-200);
					nextTimer -= TimingController.getTimeSinceLastUpdate(); 
				}
				else {
					endedHitGround = true;
					endTimer = 2;
				}
				
			}
			
			if (endTimer < END_TIME) {
				endTimer -= TimingController.getTimeSinceLastUpdate(); 
			}
			
			if (nextTimer <= 0) {
				incrementLevel();
				resetState();
			}
			
		}
		
	}
	
	private void handleMarioCollision() {
		
		// Handle Mario's collision.
		
		mario.setGrounded(false);
		
		if (!mario.checksForCollision()) {
			return;
		}
		
		for (VisibleObject x : level.getVisibleObjects()) {
			
			if (x instanceof CollidableObject) {
				
				CollidableObject theObj = (CollidableObject) x;
				Collision s = mario.detectCollision(theObj);
				
				if (s.getSide() != Collision.NONE) {
					mario.collidesWith((CollidableObject) x, s);
				}
				
			}
			
		}
	}
	
	public void draw() {
		
		// Draw level objects, Mario, and the top text.
		
		level.draw();
		mario.drawWithViewOffset(level.getXOffset(), level.getYOffset());
		
		drawHUD();
		
	}
	
	public void drawHUD() {
		
		handle.drawString(mario.getName(), 24, 7);
		handle.drawString(mario.getScoreString(), 24, 15);
		handle.drawString("World", 143, 7);
		handle.drawString("Time", 199, 7);
		handle.drawString(Integer.toString(majorLevel) + "-" + Integer.toString(minorLevel), 152, 15);
		handle.drawString(mario.getCoins(), 103, 15);
		handle.drawString(Integer.toString((int) timer), 207, 15);
		hudCoin.draw();
		hudX.draw();
		
	}
	
	public void passControls(Control c) {
		
		// Handles the passed in controls.
		
		if (this.playerControl) {
			
			if (c.getKeyCode() == Control.START_BUTTON && c.keyIsDown()) {
				newState = new PauseMenu();
			}
			else {
				mario.recieveControl(c);
			}
			
		}
		
	}
	
	public void incrementLevel() { // 1-Up the level.
		
		minorLevel++;
		
		if (minorLevel > 4) {
			majorLevel++;
			minorLevel = 1;
		}
		
	}
	
	public void loadState() { // Reload the MainGame when switching the level.
		
		if (levelEnded) {
			level = new Level(majorLevel, minorLevel);
		}
		else {
			level.resetLevel();
		}
		
		mario = new Mario(4, 0, "mario-normal-stationary.png");
		
		xMax = 124;
		timer = 300;
		
		playerControl = true;
		
		levelEnded = false;
		endedHitGround = false;
		endTimer = 2;
		nextTimer = 8;
		
	}
	
	public void resetState() {
		loadState();
	}
	
	public void quitState() { }
	
}