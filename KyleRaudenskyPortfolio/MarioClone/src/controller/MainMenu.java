//
// MainMenu
// Title Screen
// (c) 2013 Chase and the Cat Daddiez
//
// The title screen. Literally waits for a user to press
// enter. Much is copied from MainGame.
//

package controller;

import view.GameWindow;
import model.CollidableObject;
import model.Collision;
import model.Level;
import model.Mario;
import model.StaticImage;
import model.VisibleObject;
import controller.Control;
import controller.MainGame;

public class MainMenu extends State {
	
	private Level level;
	private Mario mario;
	
	StaticImage logo; // On screen images.
	StaticImage hudCoin;
	StaticImage hudX;
	StaticImage mushroom;
	
	private GameWindow handle;
	
	public MainMenu(GameWindow g) {
		
		level = new Level("levels/Title.txt");
		mario = new Mario(20,120,"mario-normal-stationary.png");
		
		handle = g;
		
		logo = new StaticImage(43, 24, "Title.png");
		hudCoin = new StaticImage(89, 16, "HUDCoin.png");
		hudX = new StaticImage(97, 18, "HUDX.png");
		mushroom = new StaticImage(73, 145, "TitleShroom.png");
		
		loadState();
		
	}
	
	public void update() {
		
		level.update();
		mario.update();
		
		handleMarioCollision();
		
	}
	
	public void draw() {
		
		level.draw();
		mario.drawWithViewOffset(level.getXOffset(), level.getYOffset());
		
		drawHUD();
		
	}
	
	public void drawHUD() {
		
		handle.drawString(mario.getName(), 24, 7);
		handle.drawString(mario.getScoreString(), 24, 15);
		handle.drawString("World", 143, 7);
		handle.drawString("Time", 199, 7);
		handle.drawString("1-1", 152, 15);
		handle.drawString("00", 103, 15);
		handle.drawString("", 207, 15);
		handle.drawString("©2013 CatCD", 130, 114);
		handle.drawString("1 Player Game", 89, 144);
		hudCoin.draw();
		hudX.draw();
		mushroom.draw();
		
		logo.draw();
		
	}
	
	public void passControls(Control c) {
		
		if (c.getKeyCode() == Control.START_BUTTON) {
			newState = new MainGame(handle);
		}
		
	}
	
	public void loadState() { }
	
	public void resetState() {
		loadState();
	}
	
	public void quitState() { }
	
	private void handleMarioCollision() {
		
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
	
}