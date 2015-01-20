package model;

import controller.Sound;
import model.Item;

/**
 * 
 */

/**
 * @author Kyle
 * an Item object that will disappear when mario touches it and up his coin counter
 *
 */
public class Coin extends Item {

	/**
	 * @param x
	 * @param y
	 * @param resourceString - image file to load
	 */
	public Coin(int x, int y) {
		super(x, y, "Items_74.png");
		ignore = true;
	}

	@Override
	/**
	 * @param m - mario
	 * @param c - the collision itself
	 */
	public void interactWithMario(Mario m, Collision c) {
		//Play the coin sound, make the coin disappear, and up marios coin count
		Sound.COIN.play();
		this.deleteOnNextUpdate = true;
		m.updateCoinCount();
		
	}


}
