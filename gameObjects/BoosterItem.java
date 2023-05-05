package gameObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Game;

/**
 * BoosterItem class
 * 
 * @author Harshal
 *
 */
public class BoosterItem extends GameObject {
	private Game game;
	private Vector coordinates;
	private BufferedImage bufferImage;

	public BoosterItem(Vector coordinates, int speed, String direction, Game game, GameCharacters character) {
		super(coordinates, speed, direction, character);
		this.coordinates = coordinates;
		this.game = game;
		getImage();
	}

	/**
	 * getImage(): Read the image from its location.
	 */
	public void getImage() {
		try {
			bufferImage = ImageIO.read(getClass().getResourceAsStream(game.crossImage));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {

	}

	/**
	 * character(Graphics g2d): Renders the cross
	 */
	@Override
	public void character(Graphics2D g2d) {
		g2d.drawImage(bufferImage, (int) coordinates.getX(), (int) coordinates.getY(), game.scaledTile, game.scaledTile,
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
