package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

import background.Orb;
import gameObjects.BoosterItem;
import gameObjects.GameCharacters;
import gameObjects.GameObject;
import gameObjects.Ghost;
import gameObjects.Player;
import gameObjects.Utilities;
import gameObjects.Vector;

/**
 * Game class
 * 
 * @author Harshal
 */
public class Game extends JPanel implements Runnable {

	private static final long serialVersionUID = -7071532049979466544L;

	// Set the screen
	private final int tile = 16; // Each tile on the screen is going to be 16 pixels
	private final int scale = 3; // Increase the scale of the tile by multiplying it with scale number.

	public final int scaledTile = tile * scale; // The tile will now be 48 pixels of width and height.
	private final int row = 12; // Total number of rows
	private final int col = 16; // Total number of columns.

	// Calculate the width and height of the screen
	public final int width = scaledTile * col;
	public final int height = scaledTile * row;

	// Player Image location
	public String pacman1 = "/images/player/up.png";
	public String pacman2 = "/images/player/down.png";
	public String pacman3 = "/images/player/left.png";
	public String pacman4 = "/images/player/right.png";
	public String pacman5 = "/images/player/close.png";

	// Ghost image location
	public String ghostPng = "/images/ghost/ghost.png";

	// Cross image location
	public String crossImage = "/images/booster/holyCross.png";

	// Thread
	private Thread thread;
	private boolean executing = false;

	// Random
	private Random random;

	// Coordinates
	private Vector playerCoordinates;
	private Vector ghostCoordinates;
	private Vector crossCoordinates;
	private Vector[] orbsCoordinates;
	private Vector[] orbsVelocity;

	// Other classes
	public KeyInput input;
	private Player player;
	private Ghost ghost;
	private Utilities util;
	private BoosterItem cross;
	public Orb[] orbs;

	// The number of background orbs
	private int numberOfOrbs = 100;
	
	// LinkedList of GameObject
	public LinkedList<GameObject> object;

	// Game variables
	public boolean crossVisible;
	public boolean flee;
	public boolean healthEffect;

	// Constructor
	public Game() {
		// Set the size of the screen
		this.setPreferredSize(new Dimension(width, height));

		// Make the background of the screen black
		this.setBackground(Color.black);

		// Uses buffer to paint on the screen
		this.setDoubleBuffered(true);

		// Listen to key inputs from the user.
		input = new KeyInput();
		this.addKeyListener(input);

		random = new Random();

		// Initialize the Vector and object for background orbs
		orbsCoordinates = new Vector[numberOfOrbs];
		orbsVelocity = new Vector[numberOfOrbs];
		orbs = new Orb[numberOfOrbs];
		for (int i = 0; i < numberOfOrbs; i++) {
			orbsCoordinates[i] = new Vector(random.nextFloat(width), random.nextFloat(height));
			orbsVelocity[i] = new Vector(random.nextFloat(1 - (-1)) + (-1), random.nextFloat(1 - (-1)) + (-1));
			orbsVelocity[i].setVectorMagnitude(random.nextFloat(1 - (-1)) + (-1));
			orbs[i] = new Orb(orbsCoordinates[i], orbsVelocity[i], this);
		}
		
		crossVisible = false;
		flee = false;
		healthEffect = false;

		// Initialize all the object vector coordinates
		playerCoordinates = new Vector(100, 300);
		ghostCoordinates = new Vector(600, 300);
		crossCoordinates = new Vector(random.nextFloat(width - (scaledTile * 2) - scaledTile),
				random.nextFloat(height - (scaledTile * 2) - scaledTile));

		// Initialize the game objects
		object = new LinkedList<GameObject>();
		player = new Player(playerCoordinates, 4, "right", this, input, GameCharacters.Player);
		ghost = new Ghost(ghostCoordinates, 3, "down", this, GameCharacters.Ghost);
		cross = new BoosterItem(crossCoordinates, 0, "down", this, GameCharacters.BoosterItem);
		util = new Utilities();

		// Add the main game objects in the GameObject LinkedList
		object.add(player);
		object.add(ghost);
		object.add(cross);
		
		// Take control of the game as soon as screen spawns
		this.setFocusable(true);
	}

	/**
	 * start(): This synchronized method starts a new thread. This game runs on a
	 * single thread. We set our boolean variable executing to true for our game
	 * loop.
	 */
	public synchronized void start() {
		try {
			thread = new Thread(this);
			thread.start();
			executing = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * stop(): This synchronized method stops the executing thread. This method is
	 * called at the end of the game loop to safely stop the execution of the
	 * thread.
	 */
	public synchronized void stop() {
		try {
			thread.join();
			executing = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * processInput(): This method makes the thread sleep to ensure smooth running
	 * of the game. It introduces lag which makes the characters in the game move
	 * frame by frame after the player input.
	 */
	private void processInput() {
		try {
			int lag = 19;
			Thread.sleep(lag);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * run(): A simple Game loop which makes the game playable by controlling the
	 * rate of gameplay. Game loop is used as the game is dependent on an players
	 * input
	 */
	@Override
	public void run() {
		while (executing) {
			processInput();
			update();
			repaint();
		}
		stop();
	}

	/**
	 * update(): This method is called in the game loop and helps keep track of
	 * player or ghost movement and logic.
	 */
	public void update() {
		// Background orbs
		for (int i = 0; i < numberOfOrbs; i++) {
			orbs[i].update();
		}

		// Main game character logic and collision detection
		player.update();
		ghost.update();
		util.update();
		if (crossVisible == true && healthEffect == true) {
			cross.update();
		}
		collision();
		
	}

	/**
	 * paintComponent(Graphics g): This method is called in game loop and helps
	 * render the character graphics.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		// For background orbs
		for (int i = 0; i < numberOfOrbs; i++) {
			orbs[i].render(g2d);
		}

		// Render main game characters
		player.character(g2d);
		ghost.character(g2d);

		// Render the cross based on crossVisibility
		if (crossVisible == true) {
			cross.character(g2d);
		}
		// Render the health bars
		util.render(g2d);
		if(util.playerHealth <= 0) {
			crossVisible = false;
			Font font = new Font("arial", 1, 50);
			g2d.setFont(font);
			g2d.drawString("You Lose!", width/2 - 125, 200);
			g2d.drawString("Your Score: " + util.score, width/2 - 170, 300);
			g2d.drawString("Try Again!", width/2 - 125, 400);
		}
		if(util.enemyHealth <= 0) {
			crossVisible = false;
			Font font = new Font("arial", 1, 50);
			g2d.setFont(font);
			g2d.drawString("You Win!", width/2 - 125, 200);
			g2d.drawString("Your Score: " + util.score, width/2 - 170, 300);
			g2d.drawString("Good Job!", width/2 - 125, 400);
		}
		g2d.dispose();
	}

	/**
	 * collision(): This method keeps track of when the player's bounds intersect
	 * the ghost's or cross' bounds. If the intersection is true then we perform
	 * some specific action
	 */
	private void collision() {
		// If the player intersects the ghost or vice-versa we deduct 2 health points
		// depending on whether the player has obtained the cross(flee for
		// ghost/monster).
		if (player.getBounds().intersects(ghost.getBounds())) {
			if (flee == true) {
				util.enemyHealth -= 2;
			} else {
				util.playerHealth -= 2;
			}
		}

		// According to the game logic,when the pacman intersects the cross object model
		// then the health is replenished and the cross and its effects are set to
		// false. This is when the ghost starts fleeing and the pacman needs to catch up
		// and defeat the ghost/monster.
		if (player.getBounds().intersects(cross.getBounds())) {
			// Set the player health to full
			if (crossVisible == true && healthEffect == true) {
				util.playerHealth = 100;
				flee = true;
			}

			// Make the cross disappear and make the ghost flee
			crossVisible = false;
			healthEffect = false;
		}

		// If the score reaches 1000 we make the cross visible and healthEffects are
		// applicable.
		if (util.score == 1000) {
			crossVisible = true;
			healthEffect = true;
		}
	}
}
