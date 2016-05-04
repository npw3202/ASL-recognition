package ComputerVision;

import org.opencv.contrib.FaceRecognizer;

/**
 * Created by qadirhaqq on 5/3/16.
 */
public class LBPHFaceRecognizer extends FaceRecognizer{

    static{ System.loadLibrary("facerec.dll"); }

    private static native long n_createLBPHFaceRecognizer();

    public LBPHFaceRecognizer()
    {
        super(n_createLBPHFaceRecognizer());
    }
}
