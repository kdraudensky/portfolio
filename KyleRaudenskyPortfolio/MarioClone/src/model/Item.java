package model;




/**
 * @author Kyle
 *
 * A CollidableObject that implements the Interactive interface. When an item interacts
 * with mario, it will have certain behavior based on whether it's a certain type of 
 * block, a coin, or a mushroom
 */
public abstract class Item extends CollidableObject implements Interactive {

	/**
	 * @param x
	 * @param y
	 * @param resourceString - image file to load
	 */
	public Item(int x, int y, String resourceString) {
		super(x, y, resourceString);
	}

	/**
	 * @param x
	 * @param y
	 * @param resourceString - image file to load
	 * @param checksForCollision - does this object collide with other objects?
	 */
	public Item(int x, int y, String resourceString, boolean checksForCollision) {
		super(x, y, resourceString, checksForCollision);
	}

	@Override
	abstract public void interactWithMario(Mario m, Collision c);
	
	

}
