package ioSign;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import staticSign.HandShape;
import staticSign.HandShapeData;

/**
 * Loads in a handshape with the specified name
 * @author Nicholas
 *
 */
public class ReadHandShape {
	/**
	 * Loads in the handshape with the specified name
	 * @param fileName the name of the handshape
	 * @return the handshape
	 * @throws IOException if there is no such handshape this is thrown
	 * @throws ClassNotFoundException if the class is out of date, this is thrown
	 */
	public static HandShape readSign(String fileName) throws IOException, ClassNotFoundException{
		ObjectInputStream input;
		FileInputStream fin = new FileInputStream("Signs/HandShape/"+fileName);
		input = new ObjectInputStream(fin);
		HandShape toReturn = new HandShape((HandShapeData) input.readObject());
		toReturn.name = fileName;
		fin.close();
		input.close();
		return toReturn;
	}
}
