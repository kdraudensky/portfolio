//
// Blair
// Block-Stair
// (c) 2013 Chase and the Cat Daddiez
//
// A collidable object that is part of the terrain in the game, does not interact
// with anything besides not let things go through it. Combination of stairs and ground
// blocks since they behave the same.
//
	 
package model;

import model.Terrain;

public class Blair extends Terrain {

	/**
	 * @param x
	 * @param y
	 * @param resource - image file to load
	 */
	public Blair(int x, int y, String resource) {
		super(x, y, resource);
	}


}
