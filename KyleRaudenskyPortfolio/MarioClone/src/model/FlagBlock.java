package model;

import model.Terrain;

/**
 * 
 */

/**
 * @author Kyle
 * separate block object that will hold the flag at the end of each level
 */
public class FlagBlock extends Terrain {
	


	/**
	 * @param x - position
	 * @param y - position
	 * @param resourceString - image to load
	 */
	public FlagBlock(int x, int y) {
		super(x, y, "stairs.png");

	}

	


}
