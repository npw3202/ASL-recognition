package dynamicSign;

import java.util.LinkedList;

/**
 * @author Nicholas Wilkins
 *
 */
public class Curve {
	class Point {
		public float x, y, z, t;

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
			return (float) (Math.pow(p2.x - x, 2) + Math.pow(p2.y - y, 2) + Math
					.pow(p2.z - z, 2));
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

	LinkedList<Point> func = new LinkedList<Point>();

	/**
	 * Ads a point to the curve
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
