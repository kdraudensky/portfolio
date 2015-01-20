package controller;

// Game
// Main Controller
// (c) 2013 Chase and the Cat Daddiez
//
// This file serves as the main controller for the game. It handles launching
// and running the states as well as drawing the GUI.
//
// TODO: ...
//

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import model.VisibleObject;
import view.GameWindow;
import controller.Control;

public class Game implements KeyListener {

    private static final int FPS = 60;
	private static final long timeUntilNextUpdate = 1000 / FPS;
	private double timeRemaining = timeUntilNextUpdate;

    private ArrayList<State> stateStack; // For all intents and purposes, it's a stack.
	private ArrayDeque<Control> controlQueue;
	private HashSet<Integer> keyRepetition;
    private GameWindow mainFrame;

    public Game() {

		Sound.COIN.play();
	
		stateStack = new ArrayList<State>();
		mainFrame = new GameWindow(this);
		
		VisibleObject.linkWindow(mainFrame);
		
		controlQueue = new ArrayDeque<Control>();
		keyRepetition = new HashSet<Integer>();

		stateStack.add(new MainMenu(mainFrame));

    }

    public void run() {

		while (true) {
			
			if (stateStack.get(stateStack.size() - 1).deletesOnNextUpdate()) {
				stateStack.get(stateStack.size() - 1).quitState();
				stateStack.remove(stateStack.size() - 1);
			}
			
			State s = stateStack.get(stateStack.size() - 1).getNextState();
			
			if (s != null) {
				stateStack.add(s);
			}
			
			// Pass in the current control.
			
			if (controlQueue.size() > 0) {
				stateStack.get(stateStack.size() - 1).passControls(controlQueue.remove());
			}
			
		    // Update all of the objects.

		    stateStack.get(stateStack.size() - 1).update();

		    // Draw them to the window, taking into account the states below.

		    mainFrame.clear();
		    draw(stateStack.size() - 1);
		    mainFrame.swapBuffers();
				
			// The game is limited to 60 frames and updates per second.
			
			try {
				Thread.sleep(timeUntilNextUpdate);
			}
			catch (InterruptedException e) { System.exit(0); }
			
			TimingController.updateTime();
			
		}

    }

    private void draw(int position) {

		if (position == -1) {
		    return;
		}

		State currentState = stateStack.get(position);

		if (currentState.drawsPreviousState()) {
		    draw(position - 1);
		}
		
		currentState.draw();

    }
	
	public void keyPressed(KeyEvent e) {
		
		if (keyRepetition.contains(e.getKeyCode())) {
			return;
		}
		
		controlQueue.add(new Control(e.getKeyCode(), true));
		keyRepetition.add(e.getKeyCode());
		
	}
	
	public void keyReleased(KeyEvent e) {
		
		keyRepetition.remove(e.getKeyCode());
		
		controlQueue.add(new Control(e.getKeyCode(), false));
		
	}
	
	public void keyTyped(KeyEvent e) {
		return;
	}

}