package model;

public class Collision {

	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int NONE = -1;

	private int side;
	private CollidableObject collider;
	private CollidableObject collidee;
	
	public Collision(CollidableObject c1, CollidableObject c2, int s) {
		
		collider = c1;
		collidee = c2;
		side = s;
		
	}
	
	public int getSide() {
		return side;
	}
	
	public CollidableObject getCollider() {
		return collider;
	}
	
	public CollidableObject getCollidee() {
		return collidee;
	}
	
}
