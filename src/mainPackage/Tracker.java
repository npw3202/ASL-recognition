package mainPackage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import staticSign.HandShape;
import staticSign.HandShapeData;
import staticSign.HandShapeSensor;

import com.leapmotion.leap.*;

public class Tracker extends Listener {
	HandShapeSensor hss;
	HandShape hs;
	public Tracker(){
		hss = new HandShapeSensor();
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
		hs = new HandShape(hands[0]);
		System.out.println(hss.getHandShape(hs));

	}
	public HandShape getHand() {
		// TODO Auto-generated method stub
		return hs;
	}
	
}
