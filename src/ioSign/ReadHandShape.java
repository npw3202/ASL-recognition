package ioSign;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import staticSign.HandShape;
import staticSign.HandShapeData;

public class ReadHandShape {
	/**
	 * Reads a hand shape from the file
	 * 
	 * @param fileName
	 *            the name of the hand shape/file
	 * @return the hand shape representing the sign
	 * @throws IOException
	 *             if a hand shape of the provided name is not found
	 * @throws ClassNotFoundException
	 *             if the hand shape is out of date
	 */
	public static HandShape readSign(String fileName) throws IOException, ClassNotFoundException {
		ObjectInputStream input;
		System.out.println("Signs/nicholas/HandShape/" + fileName);
		FileInputStream fin = new FileInputStream("Signs/"+ReaderWriter.PROFILE+"/HandShape/" + fileName);
		input = new ObjectInputStream(fin);
		HandShape toReturn = new HandShape((HandShapeData) input.readObject());
		toReturn.name = fileName;
		fin.close();
		input.close();
		return toReturn;
	}
}
