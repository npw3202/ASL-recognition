package staticSign;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.leapmotion.leap.Matrix;
import com.leapmotion.leap.Vector;

public class HandShapeData implements Serializable{
	public int handSide;
	public transient Vector fingerPositions[];	public transient Vector palmLocation;
	public transient Vector palmDirection;
	private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeFloat(palmLocation.getX());
        out.writeFloat(palmLocation.getY());        
        out.writeFloat(palmLocation.getZ());
        
        out.writeFloat(palmDirection.getX());
        out.writeFloat(palmDirection.getY());        
        out.writeFloat(palmDirection.getZ());
        
        out.writeInt(fingerPositions.length);
        
        for(int i = 0; i < fingerPositions.length; i++){
        	out.writeFloat(fingerPositions[i].getX());
            out.writeFloat(fingerPositions[i].getY());        
            out.writeFloat(fingerPositions[i].getZ());	
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        palmLocation = new Vector(in.readFloat(), in.readFloat(), in.readFloat());
        palmDirection = new Vector(in.readFloat(), in.readFloat(), in.readFloat());
        fingerPositions = new Vector[in.readInt()];
        for(int i = 0; i < fingerPositions.length; i++){
            fingerPositions[i] = new Vector(in.readFloat(), in.readFloat(), in.readFloat());
        }
    }
}
