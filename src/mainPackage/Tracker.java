package mainPackage;

import com.leapmotion.leap.*;
import dynamicSign.HandGesture;
import dynamicSign.HandGestureSensor;
import imaging.Imager;
import staticSign.HandShape;
import staticSign.HandShapeSensor;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Tracker extends com.leapmotion.leap.Listener implements Observer {
	private static final float THRESH_DYNAMIC = 15;
	private static final float THRESH_STATIC = 5;
	HandShapeSensor hss;
	HandShape hs;
	HandGesture hg;
	HandGesture currentGesture = null;
	HandShape currentShape = null;
	HandGesture lastGesture = null;
	HandShape lastShape = null;
	String sentence = "";
	Differentiator dif = new Differentiator();
	SignChanger signChanger = new SignChanger();
	LinkedList<TrackerListener> listeners = new LinkedList<TrackerListener>();
	
	public Tracker() {
		hss = new HandShapeSensor();
		hg = new HandGesture();
		Imager im = new Imager(new Controller());
		signChanger.addObserver(this);
		this.addListener(signChanger);
		this.addListener(dif);
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
		// Note: not dispatched when running in a debugger.
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
		// uses the hand to create a new handshape
		// System.out.println(hands.length);
		if (hands.length > 0) {
			hs = new HandShape(hands[0]);
			hg.addHand(hs);
			hg.parseToCurves();
			String str = hg.toString();
			HandGestureSensor hgs = new HandGestureSensor();
			// System.out.println("Sign: " + hgs.getHandGesture(hg).name);
			//System.out.println("Hand Shape: " + hss.getHandShape(hs));
			currentGesture = hgs.getHandGesture(hg);
			currentShape = hss.getHandShape(hs);
			updateListeners(hs);
		}
	}

	/**
	 * Gets the hand shape from the last frame
	 * 
	 * @return the hand shape from the last frame
	 */
	public HandShape getHand() {
		return hs;
	}

	/**
	 * Adds listeners to the tracker
	 * 
	 * @param o
	 *            the listener to add
	 */
	public void addListener(TrackerListener o) {
		listeners.add(o);
	}

	/**
	 * Notifies all listeners of a new hand shape (a new frame)
	 * 
	 * @param hs
	 *            the new hand shape
	 */
	public void updateListeners(HandShape hs) {
		for (TrackerListener o : listeners) {
			o.onUpdate(hs);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		//System.out.println("Is it static? it is " + dif.isStatic() + " static.");
		//String printline = dif.isStatic() ? "it is static " : "It is not static"; 
		
		if (!dif.isStatic()) {
			if (lastGesture == null || !currentGesture.name.equals(lastGesture.name)) {
				float distance = hg.distance(currentGesture);
				if(distance < THRESH_DYNAMIC){
					lastGesture = currentGesture;
					sentence += " " + lastGesture.name;
					System.out.println(sentence);
					lastShape = null;
				}
			}
		} else {
			if (lastShape == null || !currentShape.name.equals(lastShape.name)) {
				float distance = hs.distance(currentShape);
				//System.out.println(distance);
				if(distance < THRESH_STATIC){
					lastShape = currentShape;
					sentence += " " + lastShape.name;
					System.out.println(sentence);
					lastGesture = null;
				}
			}
		}
	}

}
