package model;

import model.Item;

/**
 * @author Kyle
 *An instance of Item, this item moves just like a goomba, but if mario interacts with it
 *it will disappear and mario will power up.
 */
public class Mushroom extends Item {
	
	private final int SPEED = 30;
	private boolean isActive;
	
	/**
	 * @param x
	 * @param y
	 * @param resourceString - image file to load
	 * @param checksForCollision - does the object collide with things
	 */
	public Mushroom(int x, int y) {
		super(x, y, "Items_24.png", false);
		this.velX = 0;
		this.isActive = false;
		stationary = true;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * defines the rules for if the mushroom runs into things. but we never got it 
	 * to move so this is not implemented
	 */
	public void collidesWith(CollidableObject other, Collision c) {

		double oldVel = velX;
		super.collidesWith(other, c);
		
		if (c.getSide() == Collision.LEFT || c.getSide() == Collision.RIGHT) {
			
			velX = -oldVel;
				
		}
			
	}
	
	/**
	 * updates based on whether the mushroom is still inside the ? block or not
	 */
	public void update() {
		super.update();
		if (!isActive) {
			this.active = false;
		}
		else if (isActive) {
			this.active = true;
			this.setVelX(0);
		}
		
	}

	
	public void setVelX(int x) {
		this.velX = x;
	}
	
	public void setActive(boolean b) {
		this.isActive = b;
	}
	


	@Override
	/**
	 * Method that defines what happens when mario collides with the mushroom
	 */
	public void interactWithMario(Mario m, Collision c) {
		//If mario touches the mushroom, make it disappear and power mario up
		deleteOnNextUpdate = true;
		if (m.getPowerUpState() == 1) {
			m.powerUp();
		}
	}

}
