package model;

/**
 * 
 */

/**
 * @author Kyle
 *Similar to Blair, bushes and clouds are the same graphic with different colors so
 *they are created as the same object but different image files
 *
 */
public class Bloud extends VisibleObject {

	/**
	 * @param x
	 * @param y
	 * @param resourceString - image file to load
	 */
	public Bloud(int x, int y, String color) {
		super(x, y, color.equals("white") ? "Cloud2.png" : "Bush2Cut.png");
	}

	@Override
	public void update() {
		
	}


}
