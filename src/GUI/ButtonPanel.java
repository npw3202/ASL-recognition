package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ioSign.SaveHandShape;
import ioSign.SaveSign;
import mainPackage.Tracker;

public class ButtonPanel extends JPanel{
	Tracker t;
	JButton addStaticSign = new JButton("Add Static Sign");
	JButton addDynamicSign = new JButton("Add Dynamic Sign");
	JButton clear = new JButton("Clear");
	ActionListener staticWindowOpener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			new SaveHandShape(t.c);
		}
	};
	ActionListener DynamicWindowOpener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			new SaveSign(t.c);
		}
	};
	ActionListener Clearer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			UserInterface.mainPanel.tp.text.setText("");
		}
	};

	public ButtonPanel(Tracker t) {
		
		this.t = t;
		this.setLayout(new FlowLayout());//(this, BoxLayout.X_AXIS));
		this.add(addStaticSign);
		this.add(addDynamicSign);
		this.add(clear);
		addDynamicSign.addActionListener(DynamicWindowOpener);
		addStaticSign.addActionListener(staticWindowOpener);
		clear.addActionListener(Clearer);
		
	}

}
