package model;

/**
 * 
 * @author Chase
 *
 *This is an interface implemented by both Enemy and Item, telling each what to do
 *if they interact with mario in any way using a method called InteractsWithMario()
 */
public interface Interactive {
	
	public void interactWithMario(Mario m, Collision c);
	
}