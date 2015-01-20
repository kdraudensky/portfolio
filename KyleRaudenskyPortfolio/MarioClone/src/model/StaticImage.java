package model;

/**
 * 
 * @author Chase
 *
 *Used for background images in the game that dont actually do anything but get drawn
 *behind/in fron of the rest of the game
 */
public class StaticImage extends VisibleObject {
	
	public StaticImage(int x, int y, String resourceString) {
		super(x, y, resourceString);
	}
	
	public void update() {}
	
}