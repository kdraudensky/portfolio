package model;

/**
 * 
 */

/**
 * @author Kyle
 *
 * a Terrain object used to create the ground for the levels. Does not interact with
 * other objects other than keep them from falling through the floor
 */
public class GroundBlock extends Terrain {

	/**
	 * @param x
	 * @param y
	 * @param resourceString - image file to load
	 */
	public GroundBlock(int x, int y) {
		super(x, y, "level-floor.png");
	}


}
