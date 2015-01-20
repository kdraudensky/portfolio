//
// Mario
// Player Object
// (c) 2013 Chase and the Cat Daddiez
//
// Defines all of Mario's behavior, animations, etc.
//

package model;

import controller.Control;
import controller.Sound;
import controller.TimingController;

public class Mario extends CollidableObject {
	
	
	private final int BIG_JUMPING_VEL = -250;
	private final int SMALL_JUMPING_VEL = -170;
	private final int WALKING_SPEED = 64;
	
	//
	// NOTE: In the main game state, Mario will not be placed into any update ArrayList.
	//       In this way, no objects will check if they collide with Mario. Mario will
	//       only check if he was collided with other objects.
	//
	
	private long score;
	
	private boolean inJump = false;
	private boolean jumpd = false;
	
	private int powerUpState;
	private boolean isBig;
	private boolean isFire;
	private boolean isDead = false;
	private boolean deadJump = false;
	private boolean deathDone;
	
	private boolean hasStar;
	private double starTimer; // Potentially, all doubles here could be ints if the game updated at a constant rate...
	
	private boolean graceInvulnerable;
	private double graceTimer;
	
	private int coins;
	private String name;
	
	private int xLock; // Left and right control lock.
	private boolean bDown;
	
	// Animation timers
	
	private double runningTimer = 0;
	private double jumpTimer = 0;
	private double dieTimer = 2;
	
	public Mario(int x, int y, String resourceString) {
		
		super(x, y, resourceString);
		score = 0;
		isBig = false;
		isFire = false;
		powerUpState = 1;
		hasStar = false;
		starTimer = 0;
		graceInvulnerable = false;
		graceTimer = 0;
		detectsCollision = true;
		stationary = false;
		bDown = false;
		
		name = "Mario";
		
		System.out.println("X: " + getX() + " Y: " + getY() + " RealX: " + getRealX() + " RealY: " + getRealY());
		
	}
	
	public void update() {
		
		super.update();
		
		if (this.isDead) {
			this.die();
			return;
		}
		
		if (powerUpState == 0 && !isDead) {
			// This is all we have to do here. The main game state will handle the death.
			Sound.MAINTHEME.stop();
			this.velX = 0;
			this.velY = 0;
			this.accelX = 0;
			this.switchImage("mario-dying.png");
			this.isDead = true;
			return;	
		}
		
		this.determineSprite(); // Change his sprite depending on the action.
		
		if (velX > 0) { // Flip the graphic, if applicable.
			if (velY == 0) {
				flipped = false;
			}
		}
		else if (velX < 0) {
			if (velY ==0) {
				flipped = true;
			}
		}
		
		if (inJump) { // Time based jumping.
			
			jumpd = true;
			
			velY = -150;
			
			jumpTimer += TimingController.getTimeSinceLastUpdate();
			
			if (jumpTimer > .5) {
				
				inJump = false;
				jumpTimer = 0;
				
			}
			
		}
		
		if (graceInvulnerable) {
			
			// Unused
			
			graceTimer -= TimingController.getTimeSinceLastUpdate();
			
			if (graceTimer <= 0) {
				graceInvulnerable = false;
				graceTimer = 0;
			}
			
		}
		
		if (hasStar) {
			
			// Unused
			
			starTimer -= TimingController.getTimeSinceLastUpdate();
			
			if (starTimer <= 0) {
				hasStar = false;
				starTimer = 0;
			}
			
		}
		
	}
	
	public void collidesWith(CollidableObject o, Collision c) {
		
		if (o instanceof Interactive) {
			
			Interactive i = (Interactive) o;
			i.interactWithMario(this, c);
			
		}
		
		pushOut(o, c);
		
		if (c.getSide() == Collision.TOP) {
			inJump = false;
		}
		
		if (c.getSide() == Collision.BOTTOM) {
			jumpd = false;
		}
		
	}
	
	public void takeHit() { // Enemy hits Mario.
		
		if (powerUpState == 1) {
			powerUpState--;
		}
		else {
			powerUpState = 1;
		}
		
		graceInvulnerable = true;
		graceTimer = 5.0;
		
	}
	
	public void determineSprite() {
		if (this.powerUpState == 1) {
			if (jumpd) {
				this.switchImage("mario-normal-jumping.png");
			}
			else {
				if (this.velX == 0) {
					this.switchImage("mario-normal-stationary.png");
				}
				else {
					if (runningTimer < .1) {
						this.switchImage("mario-normal-running1.png");
					}
					else if (runningTimer < .2) {
						this.switchImage("mario-normal-running2.png");
					}
					else if (runningTimer < .3) {
						this.switchImage("mario-normal-running3.png");
					}
					runningTimer = (runningTimer + TimingController.getTimeSinceLastUpdate()) % .3;
					
				}
			}
		}
		else if (this.powerUpState == 2) {
			if (jumpd) {
				this.switchImage("mario-mushroom-jumping.png");
			}
			else if (this.velX ==0) {
				this.switchImage("mario-mushroom-stationary.png");
			}
			else {
				if (runningTimer < .1) {
					this.switchImage("mario-mushroom-running1.png");
				}
				else if (runningTimer < .2) {
					this.switchImage("mario-mushroom-running2.png");
				}
				else if (runningTimer < .3) {
					this.switchImage("mario-mushroom-running3.png");
				}
				runningTimer = (runningTimer + TimingController.getTimeSinceLastUpdate()) % .3;
				
			}
		}
		else if (this.powerUpState == 3) {
			if (jumpd) {
				this.switchImage("mario-fire-jumping.png");
			}
			else if (this.velX == 0) {
				this.switchImage("mario-fire-stationary.png");
			}
			else {
				if (runningTimer < .1) {
					this.switchImage("mario-fire-running1.png");
				}
				else if (runningTimer < .2) {
					this.switchImage("mario-fire-running2.png");
				}
				else if (runningTimer < .3) {
					this.switchImage("mario-fire-running3.png");
				}
				runningTimer = (runningTimer + TimingController.getTimeSinceLastUpdate()) % .3;
				
			}
		}
	}
	

	
	public void recieveControl(Control c) {
		
		if (powerUpState == 0) {
			return;
		}
		
		if (c.keyIsDown()) {
			
			switch (c.getKeyCode()) {
			
				case Control.LEFT_DPAD:
					velX = bDown ? -WALKING_SPEED * 2 : -WALKING_SPEED;
					if ( velY == 0) {
						flipped = true;
					}
					xLock = Control.LEFT_DPAD;
					break;
				case Control.RIGHT_DPAD:
					velX = bDown ? WALKING_SPEED * 2 : WALKING_SPEED;
					if (velY == 0) {
						flipped = false;
					}
					xLock = Control.RIGHT_DPAD;
					break;
				case Control.A_BUTTON:
					if (velY != 0) {
						break;
					}
					else {
						inJump = true;
						jumpd = true;
						if (powerUpState > 1) {
							Sound.JUMPSUPER.play();
						}
						else {
							Sound.JUMPSMALL.play();
						}
					}
					
					break;
				
				case Control.B_BUTTON:
					bDown = true;
					velX *= 2;
					break;
				default:
					break;
			
			}
			
		}
		else {
			
			inJump = false;
			
			switch (c.getKeyCode()) {
			
				case Control.LEFT_DPAD:
					if (xLock == Control.LEFT_DPAD) {
						velX = 0;
					}
					break;
				case Control.RIGHT_DPAD:
					if (xLock == Control.RIGHT_DPAD) {
						velX = 0;
					}
					break;
				case Control.A_BUTTON:
					inJump = false;
					break;
				
				case Control.B_BUTTON:
					bDown = false;
					velX /= 2;
					break;
				default:
					break;
			
			}
			
		}
		
	}
	

	public long getScore() {
		return score;
	}
	
	public void addScore(long scoreToAdd) {
		score += scoreToAdd;
	}
	
	public String getCoins() { // Coin string for the HUD.
		
		if (coins > 9) {
			return Integer.toString(coins);
		}
		else {
			return "0" + Integer.toString(coins);
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getScoreString() { // Score string for the HUD.
		
		String scoreStr = "000000" + score;
		return scoreStr.substring(scoreStr.length() - 7, scoreStr.length() - 1);
		
	}
	
	public void updateCoinCount() {
		this.coins++;
	}
	
	public int getPowerUpState() {
		return this.powerUpState;
	}
	
	public void setPowerUpState(int x) {
		this.powerUpState = x;
	}
	
	public void powerUp() {
		if (this.powerUpState == 3) {
			return;
		}
		else {
			this.powerUpState++;
			this.updatePowerUp();
		}
		Sound.POWERUP.play();
	}
	
	public void updatePowerUp() {
		if (this.powerUpState == 3) {
			this.isBig = true;
			this.isFire = true;
		}
		else if (this.powerUpState == 2) {
			this.isFire = false;
			this.isBig = true;
		}
		else if (this.powerUpState == 1) {
			this.isFire = false;
			this.isBig = false;
		}
		else if (this.powerUpState == 0) {
			this.die();
		}
	}
	
	public void die() {
		
		if (deadJump) {
			this.detectsCollision = false;			
			if (dieTimer <= -28) {
				this.deathDone = true;
			}
			
		}
		else if (dieTimer <= 0) {
			
			grounded = false;
			velY = -200;
			Sound.MARIODIES.play();
			deadJump = true;
			
		}
		
		dieTimer -= 0.2;
		
	}
	
	public void noSteppingBack(int newX) {
		
		realX = newX;
		x = (int) realX;
		
	}
	
	public boolean isDoneDying() {
		if (this.deathDone) {
			return true;
		}
		return false;
	}
	
}