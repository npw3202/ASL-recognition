package mainPackage;

import dynamicSign.HandGesture;
import staticSign.HandShape;

import java.util.Observable;

public class SignChanger extends Observable implements TrackerListener {
	public static final float EPSILON = 0.5f;
	public static final int PEEK_BACK_LENGTH = 5;
	public static final int NUM_FRAME_BETWEEN = 100;
	/**
	 * @uml.property  name="last"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	HandGesture last = new HandGesture();
	/**
	 * @uml.property  name="frameNum"
	 */
	int frameNum;
	@Override
	public void onUpdate(HandShape hs) {
		// we must have at least PEEK_BACK_LENGTH frames so we can peek back
		// that many frames
		if (last.data.handList.size() < PEEK_BACK_LENGTH) {
			if (last.data.handList.size() == 0) {
				notifyObservers();
			}
			last.addHand(hs);
			return;
		} else {
			last.addHand(hs);
			// if a pause is noted and there is enough time between, there is a
			// new sign
			if (frameNum > NUM_FRAME_BETWEEN
					&& hs.distance(last.data.handList.get(last.data.handList.size() - PEEK_BACK_LENGTH)) <= EPSILON) {
				setChanged();
				notifyObservers();
				frameNum = 0;
				return;
			}
		}
		frameNum++;
	}
}
