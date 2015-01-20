package model;

import controller.Sound;
import controller.TimingController;
import model.Enemy;

public class Koopa extends Enemy {
	
	private final int SHELL_SPEED = 100;
	
	private boolean shell;
	private boolean movingShell;
	private double shellTimer;
	private double flashingTimer;
	private boolean legsOut;
	private double walkingTimer;
	private boolean legsPointedOut;
	
	public Koopa(int x, int y) {
		
		super(x, y, "Enemies_06.png", true);
		shell = false;
		movingShell = false;
		shellTimer = 0;
		ignore = true;
		flashingTimer = 0.5;
		legsOut = true;
		legsPointedOut = false;
		walkingTimer = 0.1;
		
	}
	
	@Override
	public void update() {
		
		super.update();
		if (shell) {
			
			if (movingShell) {
				//velX = SHELL_SPEED;
			}
			
			else {
				
				velX = 0;
				realY += 8;
				shellTimer -= TimingController.getTimeSinceLastUpdate();
				
				
				if (shellTimer <= 5 && shellTimer > 0 ) {
					flashingTimer -= TimingController.getTimeSinceLastUpdate();
					
					// animates the flashing of a shell when it is about to come to life
					if (flashingTimer <= 0) {
						legsOut = !legsOut;
						if (legsOut) {
							switchImage("Enemies_12.png");
						}
						else {
							switchImage("Enemies_13.png");
						}
						flashingTimer = 0.5;
					}
				}
				
				if (shellTimer <= 0) {
					switchImage("Enemies_06.png");
					realY -= 8;
					shell = false;
					shellTimer = 0;
					flashingTimer = 0;
					velX = -WALKING_SPEED;
				}
			}
		}
		
		else {
			
			// animates a walking koopa
			
			walkingTimer -= TimingController.getTimeSinceLastUpdate();
			
			if (walkingTimer <= 0) {
				legsPointedOut = !legsPointedOut;
				if (legsPointedOut) {
					switchImage("Enemies_07.png");
				}
				else {
					switchImage("Enemies_06.png");
				}
				
				walkingTimer = 0.5;
			}
			
			
		}
		
	}
	
	public void interactWithMario(Mario m, Collision c) {
		
		if (c.getSide() != Collision.NONE && c.getSide() != Collision.BOTTOM) {
			
			// if mario runs into a koopa or moving shell, he takes a hit
			if (!shell || (shell && movingShell)) {
				m.takeHit();
				return;
			}
			// if mario walks into a not moving shell, make it move
			else if(shell) {
				Sound.KICK.play();
				switchImage("Enemies_13.png");
				movingShell = true;
				if (m.realX < realX) {
					velX = -SHELL_SPEED;
				}
				else {
					velX = SHELL_SPEED;
				}
				
				return;
			}
			
		}
		else if (c.getSide() == Collision.BOTTOM) {
			
			// if mario jumps on a koopa, turn it into a shell
			if (!shell) {
				m.velY = -m.velY;
				velX = 0;
				switchImage("Enemies_13.png");
				shell = true;
				shellTimer = 12;
				return;
			}
			
			// if mario jumps on a non moving shell make it move
			else if (shell && !movingShell){
				m.velY = -m.velY;
				switchImage("Enemies_12.png");
				movingShell = true;
				if (m.realX < realX) {
					velX = SHELL_SPEED;
				}
				else {
					velX = -SHELL_SPEED;
				}
				return;
			}
		}	
	}
}
