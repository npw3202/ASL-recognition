package dynamicSign;

public class Point {
	public float x;
	public float y;
	public float z;
	public float t;

	/**
	 * Finds the distance from one point to another
	 * 
	 * @param p2
	 *            the other point
	 * @return the distance between the two points
	 */
	public float distance(Point p2) {
		return (float) Math.sqrt(Math.pow(p2.x - x, 2)
				+ Math.pow(p2.y - y, 2) + Math.pow(p2.z - z, 2));
	}

	/**
	 * Finds the distance squared from one point to another. Much more
	 * efficient than finding the distance
	 * 
	 * @param p2
	 *            the other point
	 * @return the distance squared between the two points
	 */
	public float distanceSquared(Point p2) {
		return (float) (Math.pow(p2.x - x, 2) + Math.pow(p2.y - y, 2) + Math.pow(p2.z - z, 2));
	}

	/**
	 * Finds the dot product of this point and another point
	 * 
	 * @param p2
	 *            the other point
	 * @return the dot product
	 */
	public float dot(Point p2) {
		return (p2.x * x + p2.y * y + p2.z * z);
	}

	public Point(float x, float y, float z, float t) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + x + ", " + y + ", " + z + ", " + t + ")";

	}
}