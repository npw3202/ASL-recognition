package dynamicSign;

import ioSign.ReadHandShape;
import ioSign.ReadSign;
import ioSign.ReaderWriter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import staticSign.HandShape;

public class HandGestureSensor {
	/**
	 * @uml.property  name="handGestures"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="dynamicSign.HandGesture"
	 */
	LinkedList<HandGesture> handGestures = new LinkedList<HandGesture>();

	public HandGestureSensor() {
		File folder = new File("Signs/" + ReaderWriter.PROFILE + "/DynamicSign");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					handGestures.add(ReadSign.readSign(listOfFiles[i].getName()));
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
	}

	/**
	 * Get the hand gesture closest to the provided gesture
	 * 
	 * @param hg
	 *            the gesture to search for
	 * @return the closest hand gestures
	 */
	public HandGesture getHandGesture(HandGesture hg) {
		LinkedList<Float> score = new LinkedList<Float>();
		for (int i = 0; i < handGestures.size(); i++) {
			score.add(hg.distance(handGestures.get(i)));
		}
		int minIndex = 0;
		// use linear search
		for (int i = 1; i < score.size(); i++) {
			if (score.get(i) < score.get(minIndex)) {
				// if the distance is smaller
				minIndex = i;
			}
		}
		// System.out.println(score.get(minIndex));
		if (score.get(minIndex) < 10) {
			hg.clear();
		}
		return handGestures.get(minIndex);
	}
}
