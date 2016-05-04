package GUI;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import mainPackage.Tracker;

public class UserInterface extends JFrame{
	Tracker t = new Tracker();
	ContentPanel mainPanel;
	ButtonPanel bp;
	public UserInterface() {
		mainPanel = new ContentPanel(t);
		bp = new ButtonPanel(t);
		this.setLayout(new BorderLayout());
		this.add(mainPanel,BorderLayout.CENTER);
		this.add(bp,BorderLayout.SOUTH);
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			System.out.println("No Nimbus");
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
}
