//
// Control
// Wrapped Control
// (c) 2013 Chase and the Cat Daddiez
//
// Wraps a Java KeyListener event into a convenient package. Also includes
// some utility functions for reassigning the key mappings.
//

package controller;

import java.awt.event.KeyEvent;

public class Control {
	
	private int keyCode;
	private boolean down;
	
	public static final int A_BUTTON = KeyEvent.VK_C;
	public static final int B_BUTTON = KeyEvent.VK_X;
	public static final int START_BUTTON = KeyEvent.VK_ENTER;
	public static final int SELECT_BUTTON = KeyEvent.VK_SHIFT;
	public static final int LEFT_DPAD = KeyEvent.VK_LEFT;
	public static final int RIGHT_DPAD = KeyEvent.VK_RIGHT;
	public static final int UP_DPAD = KeyEvent.VK_UP;
	public static final int DOWN_DPAD = KeyEvent.VK_DOWN;
	
	public Control(int code, boolean state) {
		
		keyCode = code; // Which key is it?
		down = state;   // Is the key up or down?
		
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public boolean isA() {
		return keyCode == A_BUTTON;
	}
	
	public boolean isB() {
		return keyCode == B_BUTTON;
	}
	
	public boolean isLeft() {
		return keyCode == LEFT_DPAD;
	}
	
	public boolean isRight() {
		return keyCode == RIGHT_DPAD;
	}
	
	public boolean isUp() {
		return keyCode == UP_DPAD;
	}
	
	public boolean isDown() {
		return keyCode == DOWN_DPAD;
	}
	
	public boolean isStart() {
		return keyCode == START_BUTTON;
	}
	
	public boolean isSelect() {
		return keyCode == SELECT_BUTTON;
	}
	
	public boolean keyIsDown() {
		return down;
	}
	
}