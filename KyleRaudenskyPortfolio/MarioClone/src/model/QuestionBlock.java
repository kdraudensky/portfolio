package model;

import controller.Sound;
import controller.TimingController;


/**
 * @author Kyle
 * An Item instance that interacts with mario based on simple collision rules. if mario
 * collides from below, spit out its "contents" (either coin or mushroom), otherwise acts
 * like a piece of terrain.
 */
public class QuestionBlock extends Block {
	
	private String contents;
	private boolean isEmpty;
	private Mushroom mush;
	public double flashTimer = 0;

	/**
	 * @param x
	 * @param y
	 * @param resourceString - image to load
	 * @param contents - what is inside it?
	 */
	public QuestionBlock(int x, int y, String contents, Mushroom m) {
		super(x, y, "Items_01.png", true);
		this.contents = contents;
		this.detectsCollision = true;
		this.isEmpty = false;
		this.mush = m;
		stationary = true;
	}
	
	/**
	 * when the game is updated, the question blocks flash based on a timer
	 */
	public void update() {
		
		super.update();
		if (!isEmpty) {
			if (this.flashTimer % 3 < 0.3) {
				this.switchImage("Items_04.png");
			}
			else if (this.flashTimer % 3 < 0.6) {
				this.switchImage("Items_03.png");
			}
			else if (this.flashTimer % 3 < 0.9) {
				this.switchImage("Items_01.png");
			}
			else if (this.flashTimer % 3 < 1.2) {
				flashTimer =0;
			}
			this.flashTimer += TimingController.getTimeSinceLastUpdate();
		}
		
	}
	

	@Override
	/**
	 * if mario interacts with the question block from below, output the contents, otherwise
	 * it treats it as a regular terrain block
	 */
	public void interactWithMario(Mario m, Collision c) {
		
		if (c.getSide() == Collision.TOP) {
			if (this.isEmpty == false) {
				if (this.contents == "coin") {
					this.switchImage("dead-question-block.png");
					Sound.COIN.play();
					m.updateCoinCount();
				}
				if (this.contents == "mushroom") {
					this.switchImage("dead-question-block.png");
					Sound.POWERUPAPPEARS.play();
					mush.setActive(true);
					mush.setVelX(30);
				}
				this.isEmpty = true;
			}
		}
	}
}
