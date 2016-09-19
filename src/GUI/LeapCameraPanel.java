package GUI;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mainPackage.Tracker;
import mainPackage.TrackerListener;
import staticSign.HandShape;

/**
 * Displays the leap camera
 * @author Nicholas
 *
 */
public class LeapCameraPanel extends JPanel implements TrackerListener{
	Tracker t;
	public LeapCameraPanel(Tracker t) {
		this.t = t;
		t.addListener(this);
	}
	
	@Override
	public void onUpdate(HandShape hs) {
		repaint();
		System.out.println("updated");
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		BufferedImage biLoc = t.getImage();
		g.drawImage(biLoc, 0, 0,this.getWidth(),this.getHeight(), null);
	}
	
	public static void main(String args[]){
		JFrame jf = new JFrame();
		Tracker t = new Tracker();
		LeapCameraPanel panel = new LeapCameraPanel(t);
		jf.add(panel);
		jf.setSize(100, 100);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
