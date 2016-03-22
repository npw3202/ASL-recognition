package ioSign;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import staticSign.HandShape;
import staticSign.HandShapeData;

public class ReadHandShape {
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
