package model;

// VisibleObject
// Visible Object Base Class
// (c) 2013 Chase and the Cat Daddiez
//
// This class defines several properties of a drawable object.
//

import java.awt.Image;
import java.awt.Color;

import view.GameWindow;
import view.ImageManager;
import model.GameObject;

public abstract class VisibleObject extends GameObject {
	
	private static GameWindow window;
	
	public static void linkWindow(GameWindow g) {
		window = g;
	}
	
	protected boolean visible;
	protected Image image;
	protected boolean flipped;
	protected boolean shown;
	protected boolean firstShown;
	protected boolean active = true;
	
	public VisibleObject(int x, int y, String resourceString) {
		
		super(x, y);
		image = ImageManager.loadImage(resourceString);
		shown = false;
		firstShown = false;
	}
	
	/**
	 * changes the image of an object to something else. used when updating mario and
	 * animations
	 * @param res
	 */
	public void switchImage(String res) {
		image = ImageManager.loadImage(res);
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisibility(boolean v) {
		visible = v;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void draw() {
		
		if (active) {
			window.drawVisibleObject(this, 0, 0, flipped);
		}
		
	}
	/**
	 * used for scrolling through the game
	 * @param xOffset
	 * @param yOffset
	 */
	public void drawWithViewOffset(int xOffset, int yOffset) {
		
		if (active) {
			window.drawVisibleObject(this, xOffset, yOffset, flipped);
		}
		
	}
	
	// Overridden Methods:
	
	public int getWidth() {
		return image.getWidth(null);
	}
	
	public int getHeight() {
		return image.getHeight(null);
	}
	
	public boolean updateIfOutOfVisibleBounds() {
		return true;
	}
	
	public boolean deleteIfOutOfVisibleBounds() {
		return false; // Can be overridden in subclasses. When updating
					  // in the main game, determine if the object is visible
					  // and use these to determine if 
					  // the objects are updated or not or deleted...
	}
	
	public static void drawBackground(Color c) {
		window.drawBackgroundColor(c);
	}
	
	public boolean beenOnScreen() {
		return shown;
	}
	
	public void onScreen() {
		shown = true;
	}
	
}