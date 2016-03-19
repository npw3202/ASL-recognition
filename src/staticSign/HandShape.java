package staticSign;

import mainPackage.Stats;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

/**
 * @author Nicholas
 *
 */
public class HandShape {
	public static final int LEFT = 0;
	public static final int RIGHT = 0;
	private int handSide;
	private Vector fingerPositions[];
	private Vector palmLocation;

	/**
	 * Creates an empty hand shape
	 */
	public HandShape() {

	}

	/**
	 * Gets the handSide
	 * @return the handSide
	 */
	public int getHandSide() {
		return handSide;
	}

	/**
	 * sets the HandSide
	 * @param handSide the handSide to set
	 */
	public void setHandSide(int handSide) {
		this.handSide = handSide;
	}

	/**
	 * gets the fingerPositions
	 * @return the fingerPositions
	 */
	public Vector[] getFingerPosition() {
		return fingerPositions;
	}

	/**
	 * sets the fingerPositions
	 * @param fingerPositions the fingerPositions to set
	 */
	public void setFingerPosition(Vector[] fingerPositions) {
		this.fingerPositions = fingerPositions;
	}

	/**
	 * Gets the palmLocation
	 * @return the palmLocation
	 */
	public Vector getPalmLocation() {
		return palmLocation;
	}

	/**
	 * sets the palmLocation
	 * @param palmLocation the palmLocation to set
	 */
	public void setPalmLocation(Vector palmLocation) {
		this.palmLocation = palmLocation;
	}

	/**
	 * Constructs a hand shape from the Hand object
	 * @param hand the hand to construct the hand shape from
	 */
	public HandShape(Hand hand) {
		if (hand.isLeft()) {
			this.handSide = LEFT;
		} else {
			this.handSide = RIGHT;
		}
		this.fingerPositions = new Vector[hand.fingers().count()];
		for(int i = 0; i < fingerPositions.length; i++){
			fingerPositions[i] = hand.fingers().get(i).tipPosition();
		}
		this.palmLocation = hand.palmPosition();
	}

	/**
	 * Constructs the hand shape from the given parameters
	 * @param handSide which hand (left or right)
	 * @param fingerPosition the position of all the fingers
	 * @param palmLocation the position of the palm
	 */
	public HandShape(int handSide, Vector fingerPosition[], Vector palmLocation) {
		this.handSide = handSide;
		this.fingerPositions = fingerPosition;
		this.palmLocation = palmLocation;
	}

	/**
	 * Standardizes the vectors by using the Z score
	 * @param vects the vectors you want to standardize
	 * @return the standardized vectors
	 */
	private Vector[] standardizeVectors(Vector[] vects) {
		float[] xArr = new float[vects.length];
		float[] yArr = new float[vects.length];
		float[] zArr = new float[vects.length];
		for (int i = 0; i < vects.length; i++) {
			xArr[i] = vects[i].getX();
			yArr[i] = vects[i].getY();
			zArr[i] = vects[i].getZ();
		}
		float xMean = Stats.getMean(xArr);
		float xStdDev = Stats.getStdDev(xArr, xMean);
		float yMean = Stats.getMean(yArr);
		float yStdDev = Stats.getStdDev(yArr, yMean);
		float zMean = Stats.getMean(zArr);
		float zStdDev = Stats.getStdDev(zArr, zMean);
		System.out.println(xMean);
		for (int i = 0; i < xArr.length; i++) {
			xArr[i] = (xArr[i] - xMean) / xStdDev;
		}
		for (int i = 0; i < yArr.length; i++) {
			yArr[i] = (yArr[i] - yMean) / yStdDev;
		}
		for (int i = 0; i < zArr.length; i++) {
			zArr[i] = (zArr[i] - zMean) / zStdDev;
		}
		Vector[] vecs = new Vector[vects.length];
		for (int i = 0; i < vecs.length; i++) {
			vecs[i] = new Vector(xArr[i], yArr[i], zArr[i]);
		}
		return vecs;
	}

	/**
	 * Gets the standardized coordinates of the fingers
	 * @return the standardized coordinates of the fingers
	 */
	public Vector[] getStandardizedPos() {
		return standardizeVectors(fingerPositions);
	}

	/**
	 * Gets the position of the fingers relative to the palm
	 * @return the position of the fingers relative to the palm
	 */
	public Vector[] getRelPos() {
		Vector relPos[] = new Vector[fingerPositions.length];
		for (int i = 0; i < relPos.length; i++) {
			relPos[i] = fingerPositions[i].minus(palmLocation);
		}
		return relPos;
	}
}
