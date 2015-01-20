package model;


/**
 * @author Kyle
 *A CollidableObject that has very basic collision rules in the game. Moving things can run
 *into these objects but they will not interact, the moving objects will just simply not be
 *able to go through them.
 *
 */
public class Terrain extends CollidableObject {

	/**
	 * @param x
	 * @param y
	 * @param resourceString - file to load
	 */
	public Terrain(int x, int y, String resourceString) {
		super(x, y, resourceString);
	}

	/**
	 * @param x
	 * @param y
	 * @param resourceString - file to load
	 * @param checksForCollision - does this object collide with stuff?
	 */
	public Terrain(int x, int y, String resourceString,
			boolean checksForCollision) {
		super(x, y, resourceString, checksForCollision);
	}

}
