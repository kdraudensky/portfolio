package model;

/**
 * 
 */

/**
 * @author Kyle
 *
 */
public class Pipe extends Terrain {
	

	/**
	 * @param x
	 * @param y
	 * @param resourceString
	 * 
	 * 
	 * an instance of Terrain that shows up as a pipe image. Its height depends on the
	 * number following the P in the level text file
	 * 
	 */
	public Pipe(int x, int y, int height) {
		super(x, y-(16*height), height == 1 ? "PipeSmall.png" : "PipeMedium.png");
		
	}

}
