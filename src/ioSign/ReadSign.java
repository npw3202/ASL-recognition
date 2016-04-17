package ioSign;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import dynamicSign.HandGesture;
import dynamicSign.HandGestureData;
import staticSign.HandShape;
import staticSign.HandShapeData;

public class ReadSign {
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
