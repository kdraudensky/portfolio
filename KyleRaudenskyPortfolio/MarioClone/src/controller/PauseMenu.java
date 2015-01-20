//
// PauseMenu
// Super Mario Bros. Main State
// (c) 2013 Chase and the Cat Daddiez
//
// Self-explanitory.
//

package controller;

import view.GameWindow;
import model.Level;
import model.Mario;
import model.StaticImage;
import controller.Control;

public class PauseMenu extends State {
	
	public PauseMenu() {
		
		drawPreviousState = true;
		loadState();
		
	}
	
	public void update() { }
	
	public void draw() { }
	
	public void passControls(Control c) {
		
		if (c.getKeyCode() == Control.START_BUTTON && c.keyIsDown()) {
			deleteOnNextUpdate = true;
		}
		
	}
	
	public void incrementLevel() { }
	
	public void loadState() {
		
		Sound.PAUSE.play();
		Sound.MAINTHEME.pause();
		
	}
	
	public void resetState() { }
	
	public void quitState() {
		
		Sound.PAUSE.play();
		Sound.MAINTHEME.continuePlay();
		
	}
	
}