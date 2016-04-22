package staticSign;

import ioSign.ReadHandShape;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Determines what hand shape a user is doing
 * @author Nicholas
 *
 */
public class HandShapeSensor {
	LinkedList<HandShape> handShapes = new LinkedList<HandShape>();
	
	/**
	 * The constructor, automatically searches for all hand shapes
	 */
	public HandShapeSensor() {
		File folder = new File("Signs/HandShape");
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
	 * Gets the hand shape closest to the specified hand shape
	 * @param hs the hand shape to search for
	 * @return the hand shape closest
	 */
	public HandShape getHandShape(HandShape hs) {

		LinkedList<Float> score = new LinkedList<Float>();
		for(int i = 0; i < handShapes.size(); i++){		
			score.add(hs.distance(handShapes.get(i)));
		}

		int minIndex = 0;
		//use linear search
		for(int i = 1; i < score.size(); i++){
			if(score.get(i) < score.get(minIndex)){
				//if the distance is smaller
				minIndex = i;
			}
		}
		return handShapes.get(minIndex);
	}
}
