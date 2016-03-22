package dynamicSign;

import java.util.*;

import com.leapmotion.leap.Vector;

import dynamicSign.Curve.Point;
import staticSign.HandShape;

public class HandGesture {
	public HandGestureData data = new HandGestureData(new LinkedList<HandShape>());
	public String name = "";
	
	public HandGesture(HandGestureData readObject) {
		this.data = readObject;
	}

	public HandGesture() {
		
	}

	public void addHand(HandShape hs) {
		data.handList.add(hs);
	}

	public Curve[] parseToCurves() {
		Curve[] c = new Curve[5];
		for (int i = 0; i < c.length; i++) {
			c[i] = new Curve();
		}
		for (int i = 0; i < data.handList.size(); i++) {
			HandShape hs = data.handList.get(i);
			for (int i2 = 0; i2 < hs.getPos().length; i2++) {
				Vector thisPoint = hs.getPos()[i2];
				c[i2].addPoint(thisPoint.getX(), thisPoint.getY(),
						thisPoint.getZ(), (float) i);
			}
		}
		return c;
	}
	
	public float distance(HandGesture hg2, HandShape start1, HandShape start2) {
		int index1 = -1;
		for (int i = 0; i < data.handList.size(); i++) {
			if (start1 == data.handList.get(0)){
				index1 = i;
			}
		}
		int index2 = -1;
		for (int i = 0; i < data.handList.size(); i++) {
			if (start2 == hg2.data.handList.get(0)){
				index2 = i;
			}
		}
		HandGesture newFirst = new HandGesture();
		
		for(int i = index1; i < data.handList.size(); i++){
			newFirst.addHand(data.handList.get(i));
		}
		HandGesture newSecond = new HandGesture();

		for(int i = index2; i < data.handList.size(); i++){
			newSecond.addHand(data.handList.get(i));
		}
		
		Curve[] c1 = newFirst.parseToCurves();
		Curve[] c2 = newSecond.parseToCurves();
		
		float distance = 0;
		for(int i = 0 ; i < c1.length; i++){
			distance += c1[i].distance(c2[i]);
		}
		
		return distance;
	}
	public float distance(HandGesture hg2) {
		Curve[] c1 = this.parseToCurves();
		Curve[] c2 = hg2.parseToCurves();
		
		float distance = 0;
		for(int i = 0 ; i < c1.length; i++){
			distance += c1[i].distance(c2[i]);
		}
		
		return distance;
	}
	public float distanceHuristic(HandGesture hg2, HandShape start1,
			HandShape start2) {
		HandShape end1 = data.handList.get(data.handList.size() - 1);
		HandShape end2 = hg2.data.handList.get(hg2.data.handList.size() - 1);
		return (end2.distance(end1)+start2.distance(start1));
	}

	@Override
	public String toString() {
		Curve[] c = parseToCurves();
		String s = "";
		for (int i = 0; i < c.length; i++) {
			s += c[i] + "\n";
		}
		return s;
	}
}
