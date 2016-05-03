package mainPackage;

import com.leapmotion.leap.*;
import dynamicSign.HandGesture;
import dynamicSign.HandGestureSensor;
import imaging.Imager;
import staticSign.HandShape;
import staticSign.HandShapeSensor;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Nicholas
 *
 */
public class Tracker extends com.leapmotion.leap.Listener implements Observer {
	private static final float THRESH_DYNAMIC = 15;
	private static final float THRESH_STATIC = 5;
	/**
	 * @uml.property  name="hss"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	HandShapeSensor hss;
	/**
	 * @uml.property  name="hs"
	 * @uml.associationEnd  
	 */
	HandShape hs;
	/**
	 * @uml.property  name="hg"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	HandGesture hg;
	/**
	 * @uml.property  name="currentGesture"
	 * @uml.associationEnd  
	 */
	HandGesture currentGesture = null;
	/**
	 * @uml.property  name="currentShape"
	 * @uml.associationEnd  
	 */
	HandShape currentShape = null;
	/**
	 * @uml.property  name="lastGesture"
	 * @uml.associationEnd  
	 */
	HandGesture lastGesture = null;
	/**
	 * @uml.property  name="lastShape"
	 * @uml.associationEnd  
	 */
	HandShape lastShape = null;
	/**
	 * @uml.property  name="sentence"
	 */
	String sentence = "";
	/**
	 * @uml.property  name="dif"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Differentiator dif = new Differentiator();
	/**
	 * @uml.property  name="signChanger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	SignChanger signChanger = new SignChanger();
	/**
	 * @uml.property  name="listeners"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="mainPackage.TrackerListener"
	 */
	LinkedList<TrackerListener> listeners = new LinkedList<TrackerListener>();
	/**
	 * @uml.property  name="im"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Imager im;
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Controller c;
	/**
	 * @uml.property  name="confidenceThresh"
	 */
	float confidenceThresh = 0;
	/**
	 * @uml.property  name="confidence"
	 */
	float confidence = 0;
	public Tracker(Controller c) {
		c.setPolicy (Controller.PolicyFlag.POLICY_IMAGES);
		this.c = c;
		im = new Imager(c);
		hss = new HandShapeSensor();
		hg = new HandGesture();
		Imager im = new Imager(new Controller());
		signChanger.addObserver(this);
		this.addListener(signChanger);
		this.addListener(dif);
		c.addListener(this);
	}

	public Tracker() {
		this(new Controller());
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
			confidence = hands[0].confidence();
			hs = new HandShape(hands[0]);
			hg.addHand(hs);
			hg.parseToCurves();
			//String str = hg.toString();
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

	/**
	 * Gets the current text
	 * @return the current text
	 */
	public String getText(){
		return sentence;
	}
	
	/**
	 * Gets the current image from the leap
	 * @return the current image
	 */
	public BufferedImage getImage(){
		return im.getImage();
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		//System.out.println("Is it static? it is " + dif.isStatic() + " static.");
		//String printline = dif.isStatic() ? "it is static " : "It is not static"; 
		System.out.println("Confidence: " +confidence);
		if(confidence > confidenceThresh){
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
}
