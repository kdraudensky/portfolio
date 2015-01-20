package model;
//
// GameObject
// Base Game Object
// (c) 2013 Chase and the Cat Daddiez
//
// Defines rules for all objects that will appear in the game itself.
//

public abstract class GameObject {
	
	protected int x;
	protected int y;
	
	private int width;
	private int height;
	
	protected boolean deleteOnNextUpdate;
	
	public GameObject(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public abstract void update();
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean getDeleteOnNextUpdate() {
		return deleteOnNextUpdate;
	}
	
}