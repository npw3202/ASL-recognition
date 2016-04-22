package ioSign;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import dynamicSign.HandGesture;
import dynamicSign.HandGestureData;
import staticSign.HandShape;
import staticSign.HandShapeData;

/**
 * Loads in a gesture with the specified name
 * @author Nicholas
 *
 */
public class ReadSign {
	/**
	 * Loads in the sign (gesture) with the specified name
	 * @param fileName the name of the sign
	 * @return the sign
	 * @throws IOException if there is no such sign this is thrown
	 * @throws ClassNotFoundException if the class is out of date, this is thrown
	 */
	public static HandGesture readSign(String fileName) throws IOException, ClassNotFoundException{
		ObjectInputStream input;
		FileInputStream fin = new FileInputStream("Signs/DynamicSign/"+fileName);
		input = new ObjectInputStream(fin);
		HandGestureData hgd = (HandGestureData) input.readObject();
		HandGesture toReturn = new HandGesture(hgd);
		toReturn.name = fileName;
		fin.close();
		input.close();
		return toReturn;
	}
}
