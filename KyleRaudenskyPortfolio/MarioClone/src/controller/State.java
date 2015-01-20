//
// State
// Game State Template
// (c) 2013 Chase and the Cat Daddiez
//
// This abstract class lays out all of the properties that a state
// should have and all of the actions that it can perform.
//

package controller;

import controller.Control;

public abstract class State {
	
	protected boolean drawPreviousState = false;
	
	protected int xOffset; // Used for drawing/scrolling purposes.
	protected int yOffset;
	
	protected boolean deleteOnNextUpdate = false;
	protected State newState = null;
	
	public abstract void update();
	
	public abstract void draw();
	
	public abstract void passControls(Control c);
	
	public abstract void loadState();
	
	public abstract void resetState();
	
	public abstract void quitState();
	
	public int getXDrawOffset() {
		return xOffset;
	}
	
	public int getYDrawOffset() {
		return yOffset;
	}
	
	public boolean drawsPreviousState() {
		return drawPreviousState;
	}
	
	public boolean deletesOnNextUpdate() {
		return deleteOnNextUpdate;
	}
	
	public State getNextState() {
		
		State s = newState;
		newState = null;
		
		return s;
		
	}
	
}