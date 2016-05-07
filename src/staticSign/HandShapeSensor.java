package staticSign;

import ioSign.ReadHandShape;
import ioSign.ReaderWriter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class HandShapeSensor {
	/**
	 * @uml.property  name="handShapes"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="staticSign.HandShape"
	 */
	LinkedList<HandShape> handShapes = new LinkedList<HandShape>();

	public HandShapeSensor() {
		File folder = new File("Signs/" + ReaderWriter.PROFILE + "/HandShape");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					handShapes.add(ReadHandShape.readSign(listOfFiles[i].getName()));
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
	}

	/**
	 * Determines the handshape that best matches the provided handshape
	 * 
	 * @param hs
	 *            the handshape to compare
	 * @return the closest known handshape
	 */
	public HandShape getHandShape(HandShape hs) {

		LinkedList<Float> score = new LinkedList<Float>();
		for (int i = 0; i < handShapes.size(); i++) {
			score.add(hs.distance(handShapes.get(i)));
		}

		int minIndex = 0;
		// use linear search
		for (int i = 1; i < score.size(); i++) {
			if (score.get(i) < score.get(minIndex)) {
				// if the distance is smaller
				minIndex = i;
			}
		}
		return handShapes.get(minIndex);
	}
}
