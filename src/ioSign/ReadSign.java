package ioSign;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import dynamicSign.HandGesture;
import dynamicSign.HandGestureData;
import staticSign.HandShape;
import staticSign.HandShapeData;

public class ReadSign {
	/**
	 * Reads a sign from the file
	 * 
	 * @param fileName
	 *            the name of the sign/file
	 * @return the hand gesture representing the sign
	 * @throws IOException
	 *             if a sign of the provided name is not found
	 * @throws ClassNotFoundException
	 *             if the sign is out of date
	 */
	public static HandGesture readSign(String fileName) throws IOException, ClassNotFoundException {
		ObjectInputStream input;
		FileInputStream fin = new FileInputStream("Signs/DynamicSign/" + fileName);
		input = new ObjectInputStream(fin);
		HandGestureData hgd = (HandGestureData) input.readObject();
		HandGesture toReturn = new HandGesture(hgd);
		toReturn.name = fileName;
		fin.close();
		input.close();
		return toReturn;
	}
}
