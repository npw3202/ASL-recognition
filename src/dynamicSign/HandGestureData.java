package dynamicSign;

import java.io.Serializable;
import java.util.LinkedList;

import staticSign.HandShape;


/**
 * Stores all of the data necessary for the hand gesture
 * This class is serializable
 * @author Nicholas
 *
 */
public class HandGestureData implements Serializable{
	public LinkedList<HandShape> handList;

	public HandGestureData(LinkedList<HandShape> handList) {
		this.handList = handList;
	}
}