package gameObjects;

import main.Game;

/**
 * Vector class: Handles vector calculations and operations
 * 
 * @author Harshal
 */
public class Vector {
	public float x;
	public float y;
	Game game;

	/**
	 * Vector(float x, float y)
	 * 
	 * @param x
	 * @param y
	 */
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Vector(): This constructor does not require parameters and sets the x and y
	 * to 0.
	 */
	public Vector() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Vector(Vector vector): This constructor helps create a vector clone
	 * 
	 * @param vector
	 */
	public Vector(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	/**
	 * getX(): Returns x
	 * 
	 * @return x
	 */
	public float getX() {
		return x;
	}

	/**
	 * getY(): Returns y
	 * 
	 * @return y
	 */
	public float getY() {
		return y;
	}

	/**
	 * setX(float x): sets x
	 * 
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * setY(float y): sets y
	 * 
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * set(float x, float y)
	 * 
	 * @param x
	 * @param y Sets both x and y
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * set(Vector vector)
	 * 
	 * @param vector Sets x and y from a vector
	 */
	public void set(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	/**
	 * direction(Vector v1, Vector v2): Calculates the direction between Vector v1
	 * and Vector v2
	 * 
	 * @param v1
	 * @param v2
	 * @return result
	 */
	public Vector direction(Vector v1, Vector v2) {
		float vectorX = v1.x - v2.x;
		float vectorY = v1.y - v2.y;
		Vector result = new Vector(vectorX, vectorY);
		return result;
	}

	/**
	 * getLength(): Returns the length or magnitude of the vector
	 * 
	 * @return
	 */
	public float getLength() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	/**
	 * getLength(Vector vector): Takes a vector parameter and returns the
	 * length/magnitude of the vector.
	 * 
	 * @param vector
	 * @return
	 */
	public static float getLength(Vector vector) {
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y);
	}

	/**
	 * normalize(): Normalizes a vector (Makes it a unit vector by making it of
	 * length 1)
	 * 
	 * @return
	 */
	public Vector normalize() {
		float magnitude = getLength();
		if (magnitude != 0) {
			this.x /= magnitude;
			this.y /= magnitude;
		}
		Vector result = new Vector(this.x, this.y);
		return result;
	}

	/**
	 * add(Vector vector): Adds two vectors
	 * 
	 * @param vector
	 */
	public void add(Vector vector) {
		this.x += vector.x;
		this.y += vector.y;
	}

	/**
	 * add(float n): Adds a number to both the coordinates of a vector
	 * 
	 * @param n
	 */
	public void add(float n) {
		this.x += n;
		this.y += n;
	}

	/**
	 * sub(float n): Subtracts a number from both the coordinates of a vector.
	 * 
	 * @param n
	 */
	public void sub(float n) {
		this.x -= n;
		this.y -= n;
	}

	/**
	 * sub(Vector vector): Subtracts a vector from a vector.
	 * 
	 * @param vector
	 */
	public void sub(Vector vector) {
		this.x -= vector.x;
		this.y -= vector.y;
	}

	/**
	 * sub(Vector v1, Vector v2): This static method subtracts one vector from
	 * another.
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector sub(Vector v1, Vector v2) {
		float subVx = v1.x - v2.x;
		float subVy = v1.y - v2.y;
		Vector result = new Vector(subVx, subVy);
		return result;
	}

	/**
	 * div(float size): Divides a vector by a number
	 * 
	 * @param size
	 */
	public void div(float size) {
		this.x /= size;
		this.y /= size;
	}

	/**
	 * distance(Vector vector): Finds the distance between two vectors.
	 * 
	 * @param vector
	 * @return
	 */
	public float distance(Vector vector) {
		float vectorX = vector.x - this.x;
		float vectorY = vector.y - this.y;
		return (float) Math.sqrt(vectorX * vectorX + vectorY * vectorY);
	}

	/**
	 * distance(float x1, float y1, float x2, float y2): Finds the distance between
	 * two vectors.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static float distance(float x1, float y1, float x2, float y2) {
		float vectorX = x1 - x2;
		float vectorY = y1 - y2;
		return (float) Math.sqrt(vectorX * vectorX + vectorY * vectorY);
	}

	/**
	 * mult(float number): Multiplies a number to a vector.
	 * 
	 * @param number
	 */
	public void mult(float number) {
		this.x *= number;
		this.y *= number;
	}

	/**
	 * mult(int number): Multiplies a number to a vector and returns a new vector
	 * 
	 * @param number
	 * @return
	 */
	public Vector mult(int number) {
		this.x *= number;
		this.y *= number;
		Vector result = new Vector(this.x, this.y);
		return result;
	}

	/**
	 * mult(Vector vector): Multiplies a vector to a vector and returns a new
	 * vector.
	 * 
	 * @param vector
	 * @return
	 */
	public Vector mult(Vector vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		Vector result = new Vector(this.x, this.y);
		return result;
	}

	/**
	 * setLength(int length): Sets the magnitude of the vector by making it a unit
	 * vector and multiplying it by the new magnitude.
	 * 
	 * @param length
	 * @return
	 */
	public Vector setLength(int length) {
		this.normalize();
		return this.mult(length);
	}

	/**
	 * setVectorMagnitude(float length): Sets the magnitude of the vector by making
	 * it a unit vector and multiplying it by the new magnitude.
	 * 
	 * @param length
	 * @return
	 */
	public void setVectorMagnitude(float length) {
		this.normalize().mult(length);
	}

}
