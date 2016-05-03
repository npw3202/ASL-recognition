package dynamicSign;

import java.io.Serializable;
import java.util.LinkedList;

import staticSign.HandShape;

public class HandGestureData implements Serializable{
	/**
	 * @uml.property  name="handList"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="staticSign.HandShape"
	 */
	public LinkedList<HandShape> handList;

	public HandGestureData(LinkedList<HandShape> handList) {
		this.handList = handList;
	}
}