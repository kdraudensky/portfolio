package model;
import controller.TimingController;

//
// PhysicsObject
// Physics Based Object
// (c) 2013 Chase and the Cat Daddiez
//
// This class defines all kinematic physics behavior.
//

public abstract class PhysicsObject extends VisibleObject {
	
	// The actual position of the object
	protected double realX; // Interpolate to X and Y
	protected double realY;
	
	// Theoretical position
	protected double velX;
	protected double velY;
	
	// acceleration
	protected double accelX;
	protected double accelY;
	
	protected boolean stationary = true; //stationary objects are no subject to gravity
	protected boolean grounded = false;
	
	// gravitational constant, universally implemented
	public static final int GRAVITY = 460; // Units: pixels/s^2
	
	private int frameCount;
	
	public PhysicsObject(int x, int y, String resourceString) {
		
		super(x, y, resourceString);
		
		realX = x;
		realY = y;
		
		accelY = GRAVITY;
		
	}
	
	public void update() {
		runPhysics();
	}
	
	public void runPhysics() {
		
		// position and velocity are updated using Euler's method
		// acceleration is changed based on collisions and whether or not the object is in free fall
		
		if (!active) {
			return;
		}
		
		// the time step
		double dt = TimingController.getTimeSinceLastUpdate();
		
		velX += accelX * dt;
		
		if (!stationary && !grounded) {
			velY += accelY * dt;
		}
		
		realX += velX * dt;
		realY += velY * dt;
		
		x = Math.round((float)realX);
		y = Math.round((float)realY);
		
		frameCount++;	
		
	}

	public double getRealX() {
		return realX;
	}

	public double getRealY() {
		return realY;
	}
	
	public void setRealX(double x) {
		realX = x;
	}

	public void setRealY(double y) {
		realY = y;
	}
	
	public double getVelX() {
		return velX;
	}
	
	public double getVelY() {
		return velY;
	}
	
	public void setVelX(double x) {
		velX = x;
	}
	
	public void setVelY(double y) {
		velY = y;
	}
	
	public void setGrounded(boolean g) {
		grounded = g;
	}
	
}