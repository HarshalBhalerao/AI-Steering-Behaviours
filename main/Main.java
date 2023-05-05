package main;

import javax.swing.JFrame;

/**
 * Main Class
 * 
 * @author Harshal
 *
 */
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		// Set the title of the game
		frame.setTitle("AI Steering Behaviour Game -> PacMan fights back!");

		// Make the game to not resize
		frame.setResizable(false);

		// Add the game to the game frame and ensure that the window is sized properly
		// to fit the preferred size and its subcomponents.
		Game game = new Game();
		frame.add(game);
		frame.pack();

		// Exit from the game by clicking the close button on top right.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Spawn the game frame in the middle
		frame.setLocationRelativeTo(null);

		// Make the game frame visible.
		frame.setVisible(true);

		// Start the thread
		game.start();
	}
}
