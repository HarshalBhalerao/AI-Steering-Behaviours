package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyInput class
 * 
 * @author Harshal
 *
 */
public class KeyInput implements KeyListener {

	public boolean[] key = new boolean[4];

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * keyPressed(KeyEvent e): Sets a specific boolean variable to true when that
	 * specific key is pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_W) {
			key[0] = true;
		}
		if (keyCode == KeyEvent.VK_S) {
			key[1] = true;
		}
		if (keyCode == KeyEvent.VK_A) {
			key[2] = true;
		}
		if (keyCode == KeyEvent.VK_D) {
			key[3] = true;
		}
	}

	/**
	 * keyReleased(KeyEvent e): Sets a specific boolean variable to false when that
	 * specific key is released.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_W) {
			key[0] = false;
		}
		if (keyCode == KeyEvent.VK_S) {
			key[1] = false;
		}
		if (keyCode == KeyEvent.VK_A) {
			key[2] = false;
		}
		if (keyCode == KeyEvent.VK_D) {
			key[3] = false;
		}

	}

}
