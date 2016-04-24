package mainPackage;

import staticSign.HandShape;

public interface TrackerListener {
	/**
	 * Called every time a new hand shape is available
	 * 
	 * @param hs
	 *            the new hand shape
	 */
	public void onUpdate(HandShape hs);
}
