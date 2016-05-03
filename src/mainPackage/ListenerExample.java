package mainPackage;

import java.awt.image.BufferedImage;

import staticSign.HandShape;

public class ListenerExample implements TrackerListener {
	/**
	 * @uml.property  name="t"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Tracker t;
	/**
	 * @uml.property  name="sentence"
	 */
	String sentence = "";
	/**
	 * @uml.property  name="bi"
	 */
	BufferedImage bi = new BufferedImage(0, 0, BufferedImage.TYPE_3BYTE_BGR);
	public ListenerExample() {
		t = new Tracker();
		t.addListener(this);
	}

	@Override
	public void onUpdate(HandShape hs) {
		if(!t.getText().equals(sentence)){
			System.out.println(t.getText());
			sentence = t.getText();
		}
		bi = t.getImage();
	}
	
	public static void main(String args[]){
		ListenerExample l = new ListenerExample();
		while(true){
			
		}
	}
}
