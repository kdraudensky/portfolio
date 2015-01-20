package model;

import controller.TimingController;
import model.Enemy;

public class Goomba extends Enemy {

	private double walkingTimer;
	private boolean leftOut;
	
	public Goomba(int x, int y) {
		super(x, y, "Enemies_01.png", true);
		ignore = true;
	}
	
	public void update() {
		
		super.update();
		
		// animates the goomba and its walking
		
		if (!dead) {
			walkingTimer -= TimingController.getTimeSinceLastUpdate();
			
			if (walkingTimer <= 0) {
				leftOut = !leftOut;
				if (leftOut) {
					switchImage("Enemies_01.png");
				}
				else {
					switchImage("Enemies_02.png");
				}
				
				walkingTimer = 0.5;
			}
		}
	}
	
	public void interactWithMario(Mario m, Collision c) {
		
		// mario walks into a goomba, he takes a hit
		if (c.getSide() != Collision.BOTTOM && !dead) {
			m.takeHit();
		}
		
		//if mario jumps on a goomba, he kills it
		else if (c.getSide() == Collision.BOTTOM && !dead) {
			
			m.velY = -m.velY;
			die();
			grounded = false;
			m.updateCoinCount();
		
		}
		
	}	
	
	public void die() {
		
		// handles death of a goomba
		
		velX = 0;
		realY += 8;
		switchImage("Enemies_03.png");
		super.die();
		
	}

}

