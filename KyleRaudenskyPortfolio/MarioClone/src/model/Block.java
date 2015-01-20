package model;

import model.Item;

/**
 * 
 */

/**
 * @author Kyle
 * an Item object that is a block in the game, includes regular blocks and ? blocks, includes
 * rules of how to interact with mario
 */
public abstract class Block extends Item {

	/**
	 * @param x
	 * @param y
	 * @param resourceString - file to load
	 */
	public Block(int x, int y, String resourceString) {
		super(x, y, resourceString);
		stationary = true;
	}

	/**
	 * @param x
	 * @param y
	 * @param resourceString - file to load
	 * @param checksForCollision - boolean that says whether the block is collidable
	 */
	public Block(int x, int y, String resourceString, boolean checksForCollision) {
		super(x, y, resourceString, checksForCollision);
	}

	@Override
	/**
	 * abstract because its different for each block
	 */
	abstract public void interactWithMario(Mario m, Collision c);
		


}
