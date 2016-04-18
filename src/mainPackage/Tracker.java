package mainPackage;

import com.leapmotion.leap.*;
import dynamicSign.HandGesture;
import dynamicSign.HandGestureSensor;
import imaging.Imager;
import staticSign.HandShape;
import staticSign.HandShapeSensor;

import java.util.LinkedList;

public class Tracker extends Listener {
	HandShapeSensor hss;
	HandShape hs;
	HandGesture hg;
	LinkedList<TrackerListener> listeners = new LinkedList<TrackerListener>();
	Imager im;

	public Tracker(Controller c){
		hss = new HandShapeSensor();
		hg = new HandGesture();
		im = new Imager(c);
		c.addListener(this);
		c.addListener(im);
		c.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);

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
