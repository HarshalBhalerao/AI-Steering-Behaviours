package background;

import java.awt.Color;
import java.awt.Graphics2D;

import gameObjects.Vector;
import main.Game;

/**
 * Orb class
 * 
 * @author Harshal
 *
 */
public class Orb {

	// Orb variables
	private Vector pos;
	private Vector vel;
	private Game game;

	private float perception = 20;
	private float separation = 10;
	private float maxSpeed = 3;
	private float minSpeed = 2;

	private float cCoef = 0.0005f;
	private float sCoef = 0.05f;
	private float aCoef = 0.05f;

	// Orb Constructor
	public Orb(Vector pos, Vector vel, Game game) {
		this.pos = pos;
		this.vel = vel;
		this.game = game;
	}

	/**
	 * update(): Implement flocking(separation, align and cohesion) complex steering
	 * behavior and make the orbs wrap around the screen for fluid motion
	 */
	public void update() {
		wrap();
		align();
		cohesion();
		separate();
		setSpeed();
	}

	/**
	 * wrap(): Method for fluid motion of orbs
	 */
	public void wrap() {
		if (pos.x < -5) {
			pos.setX(pos.x + game.width);
		}
		if (pos.x > game.width) {
			pos.setX(pos.x - game.width);
		}
		if (pos.y < -5) {
			pos.setY(pos.y + game.height);
		}
		if (pos.y > game.height) {
			pos.setY(pos.y - game.height);
		}
	}

	/**
	 * setSpeed(): The orbs velocity increases so this method is used to regulate
	 * the speed between min and max speed.
	 */
	public void setSpeed() {
		float speed = Vector.getLength(this.vel);
		if (speed < minSpeed) {
			this.vel.div(speed);
			this.vel.mult(minSpeed);
		}
		if (speed > maxSpeed) {
			this.vel.div(speed);
			this.vel.mult(maxSpeed);
		}
		pos.add(vel);
	}

	/**
	 * render(Graphics2D g2d): Renders a single orb
	 * 
	 * @param g2d
	 */
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fillOval((int) pos.x, (int) pos.y, 10, 10);
	}

	/**
	 * cohesion(): Make a boid steer towards local boid's center of mass.
	 */
	public void cohesion() {
		Vector steering = new Vector(); // Steering vector
		float neighbors = 0; // Keep track of neighboring boids

		// We iterate through all the boids
		for (int i = 0; i < game.orbs.length; i++) {
			// Calculate the distance between the current boid and the other boids.
			float distance = Vector.distance(this.pos.x, this.pos.y, game.orbs[i].pos.x, game.orbs[i].pos.y);
			// When any other boid is in the perception radius
			if (distance > 0 && distance < perception) {
				// We add its position into our steering vector
				steering.add(game.orbs[i].pos);
				neighbors++; // Increment the neighbor
			}
		}

		// If there are more than 0 neighbors
		if (neighbors > 0) {
			// Find the average position which will give us the local center of mass of the
			// neighboring boids
			steering.div(neighbors);
			// Find the direction by subtracting with the current boids position and head
			steering.sub(this.pos);
			// Adjust its velocity by multiplying with cohesion coefficient
			steering.mult(cCoef);
			// Update the velocity
			this.vel.add(steering);
		}
	}

	/**
	 * align(): Match the local flockmates velocity
	 */
	public void align() {
		Vector steering = new Vector(); // Steering force vector
		float neighbors = 0; // Keep track of neighboring boids

		// We iterate through all the boids
		for (int i = 0; i < game.orbs.length; i++) {
			// Calculate the distance between the current boid and the other boids.
			float distance = Vector.distance(this.pos.x, this.pos.y, game.orbs[i].pos.x, game.orbs[i].pos.y);
			// When any other boid is in the perception radius
			if (distance > 0 && distance < perception) {
				// We add its velocity into our steering vector
				steering.add(game.orbs[i].vel);
				neighbors++; // Increment the neighbor
			}
		}

		// If there are more than 0 neighbors
		if (neighbors > 0) {
			// Find the average velocity of the local flock mates.
			steering.div(neighbors);
			// Get the direction and head
			steering.sub(this.vel);
			// Adjust its velocity by multiplying with align coefficient
			steering.mult(aCoef);
			// Update the velocity
			this.vel.add(steering);
		}
	}

	/**
	 * seperate(): Avoid running into another boid by applying a opposite force away
	 * from the colliding boids
	 */
	public void separate() {
		Vector steering = new Vector(); // Steering force vector

		// We iterate through all the boids
		for (int i = 0; i < game.orbs.length; i++) {
			// Calculate the distance between the current boid and the other boids.
			float distance = Vector.distance(this.pos.x, this.pos.y, game.orbs[i].pos.x, game.orbs[i].pos.y);
			// When any other boid is in the separation radius
			if (distance > 0 && distance < separation) {
				// Get the direction
				Vector resultDiff = Vector.sub(this.pos, game.orbs[i].pos);
				// Head away
				steering.add(resultDiff);
			}
		}
		// Update the velocity
		steering.mult(sCoef);
		this.vel.add(steering);
	}
}
