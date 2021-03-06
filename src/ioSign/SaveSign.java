package ioSign;

import com.leapmotion.leap.Controller;
import dynamicSign.HandGesture;
import mainPackage.Tracker;
import mainPackage.TrackerListener;
import staticSign.HandShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SaveSign extends JFrame implements TrackerListener {
	// the main JPanel
	/**
	 * @uml.property  name="jp"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JPanel jp;
	// the text field containing the file name
	/**
	 * @uml.property  name="fileName"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JTextField fileName;
	// the listener for the leap
	/**
	 * @uml.property  name="track"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Tracker track;
	// the listener for the button
	/**
	 * @uml.property  name="recording"
	 */
	boolean recording = false;
	/**
	 * @uml.property  name="addSign"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JButton addSign;
	/**
	 * @uml.property  name="hg"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	HandGesture hg = new HandGesture();
	/**
	 * @uml.property  name="al"
	 */
	ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// open up a new text file
			String name = fileName.getText();
			if (!recording) {
				recording = true;
				addSign.setText("Stop Recording");
			} else {
				try {
					recording = false;
					addSign.setText("Start recording");
					if (hg.data.handList.size() == 0) {
						JOptionPane.showMessageDialog(SaveSign.this, "Error", "There were no hands showing",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					FileOutputStream fout = new FileOutputStream("Signs/DynamicSign/" + name);
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					// assuming the hand of interest is the first hand in the
					// array
					oos.writeObject(hg.data);
					oos.close();
					fout.close();
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	};

	/**
	 * Creates a new Sign Saver
	 * 
	 * @param control
	 *            the controller from the leap
	 */
	public SaveSign(Controller control) {
		track = new Tracker(control);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		control.addListener(track);
		track.addListener(this);
		jp = new JPanel(new FlowLayout());
		fileName = new JTextField();
		fileName.setColumns(10);
		jp.add(fileName);
		addSign = new JButton("Start recording");
		addSign.addActionListener(al);
		jp.add(addSign);
		add(jp);
		this.setSize(100, 100);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Controller controller = new Controller();
		SaveSign save = new SaveSign(controller);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpdate(HandShape hs) {
		if (recording) {
			hg.addHand(hs);
			System.out.println(hg.data.handList.size());
		}
	}
}
