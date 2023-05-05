package gameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Game;

/**
 * Ghost class
 * 
 * @author Harshal
 *
 */
public class Ghost extends GameObject {

	// Ghost variables
	private Game game;
	private GameObject object;
	private Vector coordinates;
	private Vector velocity = new Vector(0, 0);
	private BufferedImage image;

	// Ghost constructor
	public Ghost(Vector coordinates, int speed, String direction, Game game, GameCharacters character) {
		super(coordinates, speed, direction, character);
		this.coordinates = coordinates;
		this.game = game;
		getImage();
	}

	/**
	 * getImage(): This method gets the image from a specific location.
	 */
	public void getImage() {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(game.ghostPng));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * maintainGhostFleeBoundary(): Sets a smaller boundary for the ghost to avoid
	 * the ghost getting cornered. Increases the chances of the ghost fleeing when
	 * the player approaches the ghost as the player boundary is bigger.
	 */
	private void maintainGhostFleeBoundary() {
		if ((this.coordinates.getY() <= game.scaledTile + (game.scaledTile / 2))) {
			this.coordinates.setY(game.scaledTile + (game.scaledTile / 2) + 5);
		} else if (this.coordinates.getY() >= game.height - (game.scaledTile * 2) - (game.scaledTile / 2)) {
			this.coordinates.setY(game.height - (game.scaledTile * 2) - (game.scaledTile / 2) - 5);
		} else if (this.coordinates.getX() <= game.scaledTile + (game.scaledTile / 2)) {
			this.coordinates.setX(game.scaledTile + (game.scaledTile / 2) + 5);
		} else if (this.coordinates.getX() >= game.width - (game.scaledTile * 2) - (game.scaledTile / 2)) {
			this.coordinates.setX(game.width - (game.scaledTile * 2) - (game.scaledTile / 2) - 5);
		}
	}

	/**
	 * maintainGhostSeekBoundary(): Sets a boundary equal to player for the ghost.
	 */
	private void maintainGhostSeekBoundary() {
		if ((this.coordinates.getY() <= game.scaledTile)) {
			this.coordinates.setY(game.scaledTile + 2);
		} else if (this.coordinates.getY() >= game.height - (game.scaledTile * 2)) {
			this.coordinates.setY(game.height - (game.scaledTile * 2) - 2);
		} else if (this.coordinates.getX() <= game.scaledTile) {
			this.coordinates.setX(game.scaledTile + 2);
		} else if (this.coordinates.getX() >= game.width - (game.scaledTile * 2)) {
			this.coordinates.setX(game.width - (game.scaledTile * 2) - 2);
		}
	}

	/**
	 * update(): Update the ghost behaviour based on the game event.
	 */
	public void update() {
		if (game.flee == false) {
			float action = -1.0f;
			maintainGhostSeekBoundary();
			seekAndFlee(object, action);
		} else {
			float action = 1.0f;
			maintainGhostFleeBoundary();
			seekAndFlee(object, action);
		}
	}

	/**
	 * character(Graphics2D g2d): Render the ghost character
	 */
	public void character(Graphics2D g2d) {
		g2d.drawImage(image, (int) coordinates.getX(), (int) coordinates.getY(), game.scaledTile, game.scaledTile,
				null);
	}

	/**
	 * getBounds(): Return the rectangular bound of the character
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) coordinates.getX(), (int) coordinates.getY(), 38, 38);
	}

	/**
	 * seekAndFlee(GameObject object, float action): Performs seek and flee. Flee is
	 * the inverse of seek
	 * 
	 * @param object
	 * @param action
	 */
	private void seekAndFlee(GameObject object, float action) {
		// Get the player object from GameObject
		for (int i = 0; i < game.object.size(); i++) {
			if (game.object.get(i).getCharacter() == GameCharacters.Player) {
				object = game.object.get(i);
			}
		}

		// Get the direction to the target object
		Vector desired = Vector.sub(this.coordinates, object.coordinates);
		desired.mult(action); // Flee is the inverse of seek and vice-versa.

		// Normalize
		velocity = desired.normalize();

		// The velocity along this direction at full speed
		velocity.mult(this.getSpeed());

		// Increment position of the ghost to seek or flee the player.
		this.coordinates.add(velocity);
	}
}
