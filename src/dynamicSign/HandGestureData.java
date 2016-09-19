package dynamicSign;

import java.io.Serializable;
import java.util.LinkedList;

import staticSign.HandShape;

public class HandGestureData implements Serializable{

	public LinkedList<HandShape> handList;

	public HandGestureData(LinkedList<HandShape> handList) {
		this.handList = handList;
	}
}