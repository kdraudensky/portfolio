//
// Enemy.java
// Enemy Template
// (c) 2013 Chase and the Cat Daddiez
//
// Defines common enemy behavior.
//

package model;

import controller.TimingController;

public abstract class Enemy extends CollidableObject implements Interactive {

	protected final int WALKING_SPEED = 30;
	
	protected double deadTimer; // allows the dead enemy to stay on screen briefly after death
	protected boolean dead;
	
	public Enemy(int x, int y, String resourceString, boolean checksForCollision) {
		
		super(x, y, resourceString, checksForCollision);
		dead = false;
		deadTimer = 0.0;
		stationary = false;
		
	}
	
	public void collidesWith(CollidableObject other, Collision c) {
		
		double oldVel = velX;
		super.collidesWith(other, c);
		
		if (c.getSide() == Collision.LEFT || c.getSide() == Collision.RIGHT) {
			
			flipped = !flipped;
			velX = -oldVel;
				
		}
			
	}
		
		
	public void update() {
		
		super.update();
		
		if (dead) {
			
			deadTimer -= TimingController.getTimeSinceLastUpdate();
			if (deadTimer <= 0) {
				deleteOnNextUpdate = true;
			}
			
		}
		
		if (shown && !dead && !firstShown) {
			velX = -WALKING_SPEED;
			firstShown = true;
		}
		
	}
	
	public void die() {
		
		// handles when an enemy dies
		
		deadTimer = 1;
		dead = true;
	}
	
	 abstract public void interactWithMario(Mario m, Collision c);
	
}
