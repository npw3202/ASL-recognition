package mainPackage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.plaf.basic.BasicSliderUI.TrackListener;

import staticSign.HandShape;
import staticSign.HandShapeData;
import staticSign.HandShapeSensor;

import com.leapmotion.leap.*;

import dynamicSign.HandGesture;
import dynamicSign.HandGestureSensor;

public class Tracker extends Listener {
	HandShapeSensor hss;
	HandShape hs;
	HandGesture hg;
	HandGesture currentGesture = null;
	HandShape currentShape = null;

	LinkedList<TrackerListener> listeners = new LinkedList<TrackerListener>();
	public Tracker(){
		hss = new HandShapeSensor();
		hg = new HandGesture();
	}
	public void onInit(Controller controller) {
        System.out.println("Initialized");
    }
	
    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
	public synchronized void onFrame(Controller controller) {
		Frame frame = controller.frame();
		// the base array is good enough
		Hand[] hands;
		// copy the HandList to hands
		{
			HandList handList = frame.hands();
			hands = new Hand[handList.count()];
			for (int i = 0; i < handList.count(); i++) {
				hands[i] = handList.get(i);
			}
		}
		//uses the hand to create a new handshape
		//System.out.println(hands.length);
		if(hands.length>0){
			//System.out.println("fps: "+frame.currentFramesPerSecond());
			hs = new HandShape(hands[0]);
			hg.addHand(hs);
			hg.parseToCurves();
			String str = hg.toString();
			HandGestureSensor hgs = new HandGestureSensor();
			System.out.println("Sign: " + hgs.getHandGesture(hg).name);
			System.out.println("Hand Shape: " + hss.getHandShape(hs));
			update(hs);
		}
	}
	public HandShape getHand() {
		// TODO Auto-generated method stub
		return hs;
	}
	public void addListener(TrackerListener o){
		listeners.add(o);
	}
	public void update(HandShape hs){
		for(TrackerListener o : listeners){
			o.onUpdate(hs);
		}
	}
	
}
