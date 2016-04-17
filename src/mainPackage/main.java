package mainPackage;

import com.leapmotion.leap.Controller;

import java.io.IOException;

class main {
    public static void main(String[] args) {
        // Create a sample listener and controller
        Controller controller = new Controller();
        Tracker t = new Tracker(controller);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Remove the sample listener when done
        controller.removeListener(t);

    }
}