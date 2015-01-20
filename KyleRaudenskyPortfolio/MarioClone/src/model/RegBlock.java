package model;

import controller.Sound;

/**
 * 
 */

/**
 * @author Kyle
 *
 *An Item instance that interacts with mario based on similar rules to question block.
 *The difference is that if mario is big and he collides from below, the block will break,
 *if he is small, nothing happens.
 */
public class RegBlock extends Block {


	/**
	 * @param x
	 * @param y
	 * @param resourceString - image file to load
	 * @param checksForCollision - does the object collide with stuff?
	 */
	public RegBlock(int x, int y) {
		super(x, y, "brick.png", true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	/**
	 * If mario interacts from below, if hes big the block disappears, if small nothing
	 * happens
	 */
	public void interactWithMario(Mario m, Collision c) {
		if (c.getSide() == Collision.TOP) {
			if (m.getPowerUpState() == 1) {
				Sound.BUMP.play();
			}
			else {
				this.deleteOnNextUpdate = true;
				Sound.BREAKBLOCK.play();
			}
		}
		
	}

}
