package GUI;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mainPackage.Tracker;

public class ContentPanel extends JPanel{
	Tracker t;
	CameraPanel cam;
	public TextPanel tp;
	ProfileSelector ps;
	public ContentPanel(Tracker t) {
		this.t = t;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		cam = new CameraPanel(t);
		tp = new TextPanel(t);
		ps = new ProfileSelector();
		this.add(cam);
		this.add(tp);
		this.add(ps);
	}
	public static void main(String args[]){
		JFrame jf = new JFrame();
		Tracker t = new Tracker();
		ContentPanel panel = new ContentPanel(t);
		jf.add(panel);
		jf.setSize(100, 100);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
