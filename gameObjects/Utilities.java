package gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Utilities class
 * 
 * @author bhale
 *
 */
public class Utilities {
	// Health of each characters
	public int playerHealth = 100;
	public int enemyHealth = 200;
	
	// Score
	public int score = 0; 

	/**
	 * update(): Makes sure the game hud behaves normally.
	 */
	public void update() {
		if(playerHealth > 0 && playerHealth <= 100 && enemyHealth > 0 && enemyHealth <= 200) {
			score++;
		}
		if (playerHealth <= 0) {
			playerHealth = 0;
		} else if (playerHealth >= 100) {
			playerHealth = 100;
		}

		if (enemyHealth <= 0) {
			enemyHealth = 0;
		} else if (enemyHealth >= 200) {
			enemyHealth = 200;
		}
	}

	/**
	 * render(Graphics2D g2d): Renders the game hud
	 * @param g2d
	 */
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.red);
		g2d.fillRect(15, 520, 200, 32);

		g2d.setColor(new Color(75, 255, 0));
		g2d.fillRect(15, 520, (int) (playerHealth * 2), 32);

		g2d.setColor(Color.white);
		g2d.drawRect(15, 520, 200, 32);

		g2d.drawString("Player", 15, 570);
		
		g2d.drawString("Score: " + score, 170, 570);

		g2d.setColor(Color.red);
		g2d.fillRect(550, 520, 200, 32);

		g2d.setColor(new Color(75, 255, 0));
		g2d.fillRect(550, 520, (int) (enemyHealth), 32);

		g2d.setColor(Color.white);
		g2d.drawRect(550, 520, 200, 32);

		g2d.drawString("Ghost", 550, 570);
	}
}
