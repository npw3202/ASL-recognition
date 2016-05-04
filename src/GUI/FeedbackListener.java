package GUI;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainPackage.Tracker;
import mainPackage.TrackerListener;
import staticSign.HandShape;

/**
 * @author Kyle Scagnelli
 *
 */

public class FeedbackListener implements TrackerListener{

	String s = ""; // the sentence to be displayed by the feedback panel of the GUI
	Tracker t;
	JLabel leapPic; // can capture the image in a JLabel and just add the label to the panel
	BufferedImage b = new BufferedImage(0, 0, BufferedImage.TYPE_3BYTE_BGR); // the image to be used; will be captured by the leap camera
	JPanel leap = new JPanel(); // panel used to display the leap POV to users
	
	public FeedbackListener() {
		t = new Tracker();
		t.addListener(this);
	}
	
	public void onUpdate(HandShape hs) {
		// Deals with the Feedback Panel
		if(t.getText().equals(s)) {
			System.out.println(t.getText());
			s = t.getText();
		}
		// Deals with the Leap Panel
		b = t.getImage();
		leapPic = new JLabel(new ImageIcon(b));
		leap.add(leapPic);
	
		/*The image is a swing component. It becomes subject to layout conditions 
		like any other component. */
	}
	
	public static void main(String[] args) {
		FeedbackListener fb = new FeedbackListener();
		// have to do this because it's a multi-threaded program
		// won't run otherwise; main thread would just die.
		while(true){
			
		}
	}

}

