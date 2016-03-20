package staticSign;

import java.io.Serializable;

import mainPackage.Stats;
import staticSign.HandShapeData;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

/**
 * @author Nicholas
 *
 */
public class HandShape implements Serializable{
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public String name = "";
	public HandShapeData data = new HandShapeData();
	/**
	 * Constructs a hand shape from the Hand object
	 * @param hand the hand to construct the hand shape from
	 */
	public HandShape(Hand hand) {
		if (hand.isLeft()) {
			this.data.handSide = LEFT;
		} else {
			this.data.handSide = RIGHT;
		}
		this.data.fingerPositions = new Vector[hand.fingers().count()];
		for(int i = 0; i < data.fingerPositions.length; i++){
			data.fingerPositions[i] = hand.fingers().get(i).tipPosition();
		}
		this.data.palmLocation = hand.palmPosition();
		//this.data.handBasis = hand.basis();
		this.data.palmDirection = hand.palmNormal();
	}

	/**
	 * Constructs the hand shape from the given parameters
	 * @param handSide which hand (left or right)
	 * @param fingerPosition the position of all the fingers
	 * @param palmLocation the position of the palm
	 */
	public HandShape(int handSide, Vector fingerPosition[], Vector palmLocation) {
		this.data.handSide = handSide;
		this.data.fingerPositions = fingerPosition;
		this.data.palmLocation = palmLocation;
	}

	/**
	 * Creates the HandShape based on the data provided
	 * @param readObject the hand shape
	 */
	public HandShape(HandShapeData readObject) {
		this.data = readObject;
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
		return standardizeVectors(data.fingerPositions);
	}

	/**
	 * Gets the position of the fingers relative to the palm
	 * @return the position of the fingers relative to the palm
	 */
	public Vector[] getRelPos() {
		Vector relPos[] = new Vector[data.fingerPositions.length];
		for (int i = 0; i < relPos.length; i++) {
			relPos[i] = data.fingerPositions[i].minus(data.palmLocation);
		}
		return relPos;
	}
	
	/**
	 * Finds the distance between two hand shapes
	 * @param shape the hand shape to compare this hand shape to
	 * @return the distance
	 */
	public float distance(HandShape shape){
		Vector[] stdPos1 = getStandardizedPos();
		Vector[] stdPos2 = shape.getStandardizedPos();
		float distance = 0;
		for(int i = 0; i < stdPos1.length && i < stdPos2.length; i++){
			distance += stdPos1[0].minus(stdPos2[0]).magnitude();
		}
		return distance;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
