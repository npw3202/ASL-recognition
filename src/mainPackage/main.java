package mainPackage;

import com.leapmotion.leap.Controller;

import java.io.IOException;

class main {
    public static void main(String[] args) {
        // Create a sample listener and controller
        Tracker listener = new Tracker();
        Controller controller = new Controller();
        
        // Have the sample listener receive events from the controller
        controller.addListener(listener);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Remove the sample listener when done
        controller.removeListener(listener);
        
    }
}