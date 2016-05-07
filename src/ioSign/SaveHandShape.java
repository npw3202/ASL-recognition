package ioSign;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.*;

import staticSign.HandShape;
import staticSign.HandShapeData;
import mainPackage.Tracker;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;

public class SaveHandShape extends JFrame {
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
	 * @uml.property  name="al"
	 */
	ActionListener al = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// open up a new text file
			String name = fileName.getText();
			try {
				HandShape hs = track.getHand();
				if (hs == null) {
					JOptionPane.showMessageDialog(SaveHandShape.this, "Error", "There are no hands showing",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				FileOutputStream fout = new FileOutputStream("Signs/" + ReaderWriter.PROFILE + "/HandShape/" + name);
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				// assuming the hand of interest is the first hand in the array
				oos.writeObject(hs.data);
				oos.close();
				fout.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				e1.printStackTrace();
				System.exit(0);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};

	/**
	 * Creates a new sign saver
	 * 
	 * @param control
	 *            the controller from the leap motion
	 */
	public SaveHandShape(Controller control) {
		track = new Tracker(control);
		control.addListener(track);
		jp = new JPanel(new FlowLayout());
		fileName = new JTextField();
		fileName.setColumns(10);
		jp.add(fileName);
		JButton addSign = new JButton("Add Sign");
		addSign.addActionListener(al);
		jp.add(addSign);
		add(jp);
		this.setSize(100, 100);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Controller controller = new Controller();
		SaveHandShape save = new SaveHandShape(controller);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
