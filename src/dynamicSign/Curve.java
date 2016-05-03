package dynamicSign;

import java.util.LinkedList;

/**
 * @author Nicholas Wilkins
 *
 */
public class Curve {

	LinkedList<Point> func = new LinkedList<Point>();

	/**
	 * Adds a point to the curve
	 * 
	 * @param x
	 *            the x coordinate of the point to add
	 * @param y
	 *            the y coordinate of the point to add
	 * @param z
	 *            the z coordinate of the point to add
	 * @param t
	 *            the t coordinate of the point to add (time)
	 */
	public void addPoint(float x, float y, float z, float t) {

		Point p = new Point(x, y, z, t);

		func.add(p);
	}

	/**
	 * gets the closest point (spatially) to the given point on the curve
	 * 
	 * @param p2
	 *            the point to find the closest point to
	 * @return the closest point
	 */
	public Point getClosestPoint(Point p2) {
		float closestDistance = Float.MAX_VALUE;
		Point closestPoint = null;
		for (Point p : func) {
			float distSquared = p.distance(p2);
			if (distSquared < closestDistance) {
				closestDistance = distSquared;
				closestPoint = p;
			}
		}
		return closestPoint;
	}

	/**
	 * Gets the distance between two curves. Defined as the squareroot of the
	 * sum of squares of the shortest distance between two curves.
	 * 
	 * @param c2 the curve to find the distance to
	 * @return the distance between the two curves
	 */
	public float distance(Curve c2) {
		float sum = 0;
		for (Point p : func) {
			sum += p.distance(c2.getClosestPoint(p));
		}
		return (float) Math.sqrt(sum);
	}

	@Override
	public String toString() {
		String str = "";
		for (Point p : func) {
			str += p + "\n";
		}
		return str;
	}
}
