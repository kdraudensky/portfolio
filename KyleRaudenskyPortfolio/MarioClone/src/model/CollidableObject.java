package model;

import model.PhysicsObject;

//
// CollidableObject
// Collision Detection Routines
// (c) 2013 Chase and the Cat Daddiez
//
// Includes a template for an object with collision and an object
// that represents all information involved in a collision.
//



public class CollidableObject extends PhysicsObject {
	
	protected boolean detectsCollision = false; // Does this particular object check for collision or do other
	                                            // objects check for it? Most won't. Modify in subclasses.
	protected boolean ignore = false;
	
	private double lastX;
	private double lastY;
	
	public CollidableObject(int x, int y, String resourceString) {
		super(x, y, resourceString);
	}
	
	public CollidableObject(int x, int y, String resourceString, boolean checksForCollision) {
		
		super(x, y, resourceString);
		detectsCollision = checksForCollision;
		
	}
	
	public void update() {
		
		lastX = realX;
		lastY = realY;
		
		runPhysics();
		
	}
	
	public Collision detectCollision(CollidableObject other) {
		
		Collision c;
		
		if (active) {
			c = new Collision(this, other, sideOfCollision(other));
		}
		else {
			c = new Collision(this, other, Collision.NONE);
		}
		
		return c;
		
	}
	
	/**
	 * Determines the side of the collision
	 * 
	 * @param other : the object that it is colliding with
	 * @return the side of the collision
	 */
	public int sideOfCollision(CollidableObject other) {
		
		//if (((this.getRealY() + this.getHeight()) > other.getRealY()) && ((this.getRealX() + getWidth() >= other.getRealX()))) {
		//	System.out.println("WIDTH: " + this.getWidth());
		//	return Collision.BOTTOM;
		//}
		
		// Check if the collision is from the top.
		
		//System.out.println("Mario - RealX: " + getRealX() + " Width: " + getWidth() + " Height: " + getHeight());
		//System.out.println("Block: - RealX: " + other.getRealX() + " Width: " + other.getWidth() + " Height: " + other.getHeight());
		
		if ((this.getRealY() <= other.getRealY() + other.getHeight()) && (this.getRealY() > other.getRealY())
				&& (this.getRealX() + this.getWidth() > other.getRealX() && this.getRealX() <= other.getRealX() + other.getWidth())) {
				//(Math.abs(this.getRealX()-other.getRealX()) <= getWidth())) {
			return Collision.TOP;
		}
		
		// Check if the collision is from the left.
		
		if ((this.getRealX() <= other.getRealX() + other.getWidth() && this.getRealX() > other.getRealX() + (other.getWidth() -3) ) && (other.getRealY() + other.getHeight() > this.getRealY() && other.getRealY() + other.getHeight() <= this.getRealY() + this.getHeight())) {
			System.out.println("LEFT");
			return Collision.LEFT;
		}
		
		// Check if the collision is from the right.
		
		if ((this.getRealX() + this.getWidth() >= other.getRealX() && this.getRealX() < other.getRealX() + other.getWidth() - 2) && (other.getRealY() + other.getHeight() > this.getRealY() && other.getRealY() + other.getHeight() <= this.getRealY() + this.getHeight())) {
			return Collision.RIGHT;
		}
		
		
				
		// Check if the collision is from the bottom.
		
		if ((this.getRealY() + this.getHeight() >= other.getRealY()) && (this.getRealY() <= other.getRealY() + other.getHeight())
				&& (this.getRealX() + this.getWidth() > other.getRealX() && this.getRealX() < other.getRealX() + other.getWidth())) {
			grounded = true;
			return Collision.BOTTOM;
		}
		
		// Otherwise, no collision.
		
		return Collision.NONE;
		
	}
	
	/**
	 * check if the object is capable of checking for collisions
	 * @return if the object is capable of checking for 
	 */
	public boolean checksForCollision() {
		return detectsCollision;
	}
	
	public void collidesWith(CollidableObject o, Collision c) {
		pushOut(o, c);
	}
	
	/**
	 * Move the object away from what it is colliding with
	 * 
	 * @param o : the object it is colliding with
	 * @param c : the collision between the two objects
	 */
	public void pushOut(CollidableObject o, Collision c) {
		
		if (o.getIgnoreCollision()) {
			return;
		}
		
		if (c.getSide() == Collision.LEFT || c.getSide() == Collision.RIGHT) {
			
			velX = 0;
			accelX = 0;
			
			if (realX > lastX) {
				System.out.println("REALX: " + realX + " LASTX: " + lastX);
				x = o.getX() - getWidth();
				realX = x;
			}
			else if (realX < lastX) {
				System.out.println("REALX: " + realX + " LASTX: " + lastX);
				x = o.getX() + o.getWidth();
				realX = x;
			}
			
		}
		
		if (c.getSide() == Collision.TOP) {
			
			velY = 0;
			realY = o.getRealY() + o.getHeight();
			y = Math.round((float)realY);
			
		}
		else if (c.getSide() == Collision.BOTTOM) {
			
			if (velY < 0) { // Don't "cling" to a block if jumping.
				grounded = false;
			}
			else if (accelY > 0) {
				
				velY = 0;
				
				y = o.getY() - getHeight();
			
				realY = y;
				
			}
			
		}
		
	}
	
	public boolean getIgnoreCollision() {
		return ignore;
	}
	
}
