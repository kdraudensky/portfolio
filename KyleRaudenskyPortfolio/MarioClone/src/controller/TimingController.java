//
// TimingController
// Global Game Timekeeper
// (c) 2013 Chase and the Cat Daddiez
//
// This code keeps track of execution time to keep the game
// running fluidly and at a consistent speed.
//

package controller;

public class TimingController {
	
	private static long timeLastRecorded = 0;
	private static int timeSinceLastUpdate = 0;
	
	public static void init() { timeLastRecorded = System.nanoTime(); }
	
	public static void updateTime() { // Get the time since the last update.
		
		long currentTime = System.nanoTime();
		
		timeSinceLastUpdate = (int)(currentTime - timeLastRecorded);
		timeLastRecorded = currentTime;
		
	}
	
	public static double getTimeSinceLastUpdate() {
		
		if (timeLastRecorded == 0) {
			init();
		}
		
		//return timeSinceLastUpdate * 1.0e-9;
		return 1.0 / 30.0; // Fixed.
		
	}
	
}