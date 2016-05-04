package GUI;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.javafx.scene.BoundsAccessor;

import javafx.scene.layout.Border;
import mainPackage.Tracker;
import mainPackage.TrackerListener;
import staticSign.HandShape;

public class TextPanel extends JPanel implements TrackerListener{
	Tracker t;
	JLabel text;
	public TextPanel(Tracker t) {
		this.t = t;
		t.addListener(this);
		text = new JLabel();
		text.setFont(new Font("Serif", Font.PLAIN, 50));
		this.add(text);
	}
	@Override
	public void onUpdate(HandShape hs) {
		text.setText(t.getText());
	}
	public static void main(String args[]){
		JFrame jf = new JFrame();
		Tracker t = new Tracker();
		TextPanel panel = new TextPanel(t);
		jf.add(panel);
		jf.setSize(100, 100);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
