package mainPackage;

import staticSign.HandShape;

public interface TrackerListener {
	/**
	 * Called every frame
	 * @param hs the current hand shape
	 */
	public void onUpdate(HandShape hs);
}
