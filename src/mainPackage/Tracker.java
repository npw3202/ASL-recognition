package mainPackage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import staticSign.HandShape;
import staticSign.HandShapeData;

import com.leapmotion.leap.*;

public class Tracker extends Listener {
	HandShape hs;
	HandShape a;
	HandShape b;
	HandShape c;
	HandShape father;
	public Tracker(){
		try {
			ObjectInputStream input;
			FileInputStream fin = new FileInputStream("Signs/a");
			input = new ObjectInputStream(fin);
			a = new HandShape((HandShapeData) input.readObject());
			fin.close();
			input.close();
			fin = new FileInputStream("Signs/b");
			input = new ObjectInputStream(fin);
			b = new HandShape((HandShapeData) input.readObject());
			fin.close();
			input.close();
			fin = new FileInputStream("Signs/c");
			input = new ObjectInputStream(fin);
			c = new HandShape((HandShapeData) input.readObject());
			fin.close();
			input.close();

			//fin = new FileInputStream("Signs/FATHER");
			//input = new ObjectInputStream(fin);
			//father = new HandShape((HandShapeData) input.readObject());
			//fin.close();
			//input.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		for (int i = 0; i < hands.length; i++) {
			HandShape hs = new HandShape(hands[i]);
		}
		hs = new HandShape(hands[0]);
		if(hs.distance(a) > hs.distance(b) && hs.distance(father) > hs.distance(b)){
			System.out.println("B");
		}else if(hs.distance(a) > hs.distance(c)){
			System.out.println("C");
		}else{
			System.out.println("A");
		}
	}
	public HandShape getHand(){
		return hs;
	}
	
}
