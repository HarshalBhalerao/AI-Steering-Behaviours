package gameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * GameObject class
 * 
 * @author Harshal
 *
 */
public abstract class GameObject {
	// GameObject variables
	public Vector coordinates;
	public int speed;
	protected String direction;
	protected GameCharacters character;

	// GameObject constructor
	public GameObject(Vector coordinates, int speed, String direction, GameCharacters character) {
		this.coordinates = coordinates;
		this.speed = speed;
		this.direction = direction;
		this.character = character;
	}

	// abstract methods
	protected abstract void update();

	protected abstract void character(Graphics2D g2d);

	protected abstract Rectangle getBounds();

	/**
	 * getSpeed(): returns speed
	 * 
	 * @return
	 */
	protected int getSpeed() {
		return speed;
	}

	/**
	 * getCharacter(): returns GameCharacters character
	 * 
	 * @return
	 */
	protected GameCharacters getCharacter() {
		return character;
	}
}
