package gameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Game;
import main.KeyInput;

/**
 * Player class
 * 
 * @author Harshal
 *
 */
public class Player extends GameObject {
	// Player variables
	private Game game;
	private KeyInput input;
	private Vector coordinates;
	private BufferedImage[] image = new BufferedImage[5];
	private int updateNum;
	private int imageNum;

	// Player constructor
	public Player(Vector coordinates, int speed, String direction, Game game, KeyInput input,
			GameCharacters character) {
		super(coordinates, speed, direction, character);
		this.coordinates = coordinates;
		this.game = game;
		this.input = input;
		getImage();
	}

	/**
	 * getImage(): This method gets the images from their specific location.
	 */
	public void getImage() {
		try {
			image[0] = ImageIO.read(getClass().getResourceAsStream(game.pacman1));
			image[1] = ImageIO.read(getClass().getResourceAsStream(game.pacman2));
			image[2] = ImageIO.read(getClass().getResourceAsStream(game.pacman3));
			image[3] = ImageIO.read(getClass().getResourceAsStream(game.pacman4));
			image[4] = ImageIO.read(getClass().getResourceAsStream(game.pacman5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * maintainPlayerBoundary(): Sets the game boundary for player
	 */
	private void maintainPlayerBoundary() {
		if ((this.coordinates.getY() <= game.scaledTile)) {
			this.coordinates.setY(game.scaledTile + 1);
		} else if (this.coordinates.getY() >= game.height - (game.scaledTile * 2)) {
			this.coordinates.setY(game.height - (game.scaledTile * 2) - 1);
		} else if (this.coordinates.getX() <= game.scaledTile) {
			this.coordinates.setX(game.scaledTile + 1);
		} else if (this.coordinates.getX() >= game.width - (game.scaledTile * 2)) {
			this.coordinates.setX(game.width - (game.scaledTile * 2) - 1);
		}
	}

	/**
	 * update(): Update the player position based on keyPressed and maintain player
	 * boundary
	 */
	public void update() {
		maintainPlayerBoundary();
		// W key is pressed
		if (input.key[0] == true) {
			direction = "up";
			float y = (float) coordinates.getY();
			y -= speed;
			coordinates.set(coordinates.getX(), y);
		}
		// S key is pressed
		else if (input.key[1] == true) {
			direction = "down";
			float y = (float) coordinates.getY();
			y += speed;
			coordinates.set(coordinates.getX(), y);
		}
		// A key is pressed
		else if (input.key[2] == true) {
			direction = "left";
			float x = (float) coordinates.getX();
			x -= speed;
			coordinates.set(x, coordinates.getY());
		}
		// D key is pressed
		else if (input.key[3] == true) {
			direction = "right";
			float x = (float) coordinates.getX();
			x += speed;
			coordinates.set(x, coordinates.getY());
		}

		// Update this counter
		updateNum++;
		// When updateNum % 6 is even then imageNum will be 1
		if (updateNum % 6 == 0) {
			imageNum = 1;
		} else {
			// Else imageNum will be 2
			imageNum = 2;
		}
	}

	/**
	 * changeImage(): Change image on different button click. Use the logic in the
	 * update method to change image for pacman.
	 * 
	 * @return
	 */
	private int changeImage() {
		int image = 0;
		switch (direction) {
		case "up":
			if (imageNum == 1) {
				image = 0;
			}
			if (imageNum == 2) {
				image = 4;
			}
			break;
		case "down":
			if (imageNum == 1) {
				image = 1;
			}
			if (imageNum == 2) {
				image = 4;
			}
			break;
		case "left":
			if (imageNum == 1) {
				image = 2;
			}
			if (imageNum == 2) {
				image = 4;
			}
			break;
		case "right":
			if (imageNum == 1) {
				image = 3;
			}
			if (imageNum == 2) {
				image = 4;
			}
		}
		return image;
	}

	/**
	 * character(Graphics g2d): Renders the player character
	 */
	public void character(Graphics2D g2d) {
		int i = changeImage();
		g2d.drawImage(image[i], (int) coordinates.getX(), (int) coordinates.getY(), game.scaledTile, game.scaledTile,
				null);
	}

	/**
	 * getBounds(): Return the rectangular bound of the character
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) coordinates.getX(), (int) coordinates.getY(), 38, 38);
	}
}
