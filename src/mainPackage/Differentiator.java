package mainPackage;

import com.leapmotion.leap.Vector;
import dynamicSign.HandGesture;
import staticSign.HandShape;

/**
 * Created by qadirhaqq on 4/24/16.
 *
 * Used to concatenate Dynamic and Static signs
 */
public class Differentiator implements TrackerListener {
	public static final float DISTANCE_EPSILON = 5f;
	public static final float ORIENTATION_EPSILON = 5f;
	public static final float FINGER_EPSILON = 0.5f;
	public static final int PEEK_BACK_LENGTH = 5;
	HandGesture last = new HandGesture();

	/**
	 * Determines if a hand sign is static or not based on the current palm
	 * orientation and the previous palm orientation
	 * 
	 * @return boolean indicating whether the sign is static or not
	 */
	public boolean isStatic() {
		if (last.data.handList.size() < PEEK_BACK_LENGTH)
			return true;
		HandShape previous = last.data.handList.get(last.data.handList.size() - PEEK_BACK_LENGTH);
		HandShape current = last.data.handList.getLast();
		Vector distance = current.data.palmLocation.minus(previous.data.palmLocation);
		Vector orientation = current.data.palmDirection.minus(previous.data.palmDirection);
		float fingerChange = current.distance(previous);
		if (distance.magnitudeSquared() < DISTANCE_EPSILON 
				&& orientation.magnitudeSquared() < ORIENTATION_EPSILON
				&& fingerChange < FINGER_EPSILON)
			return true;
		return false;
	}

	@Override
	public void onUpdate(HandShape hs) {
		last.addHand(hs);
	}
}
