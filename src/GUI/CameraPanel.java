package GUI;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mainPackage.Tracker;

public class CameraPanel extends JPanel{
	Tracker t;
	LeapCameraPanel leap;
	WebcamPanel wp;
	public CameraPanel(Tracker t) {
		this.t = t;
		wp = new WebcamPanel();
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		leap = new LeapCameraPanel(t);
		this.add(leap);
		this.add(wp);
	}
	public static void main(String args[]){
		JFrame jf = new JFrame();
		Tracker t = new Tracker();
		CameraPanel panel = new CameraPanel(t);
		jf.add(panel);
		jf.setSize(100, 100);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
