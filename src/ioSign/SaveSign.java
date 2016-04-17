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
import mainPackage.TrackerListener;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;

import dynamicSign.HandGesture;

public class SaveSign extends JFrame implements TrackerListener{
	//the main JPanel
	JPanel jp;
	//the text field containing the file name
	JTextField fileName;
	//the listener for the leap
	Tracker track = new Tracker();
	//the listener for the button
	boolean recording = false;
	JButton addSign;
	HandGesture hg = new HandGesture();
	ActionListener al = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			// open up a new text file
			String name = fileName.getText();
			if(!recording){
				recording = true;
				addSign.setText("Stop Recording");
			}else{
				try {
					recording = false;
					addSign.setText("Start recording");
					if(hg.data.handList.size() == 0){
						JOptionPane.showMessageDialog(SaveSign.this,
							    "Error",
							    "There were no hands showing",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
					FileOutputStream fout = new FileOutputStream("Signs/DynamicSign/" + name);
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					//assuming the hand of interest is the first hand in the array
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

	
	public SaveSign(Controller control) {
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
		if(recording){
			hg.addHand(hs);
			System.out.println(hg.data.handList.size());
		}
	}
}
