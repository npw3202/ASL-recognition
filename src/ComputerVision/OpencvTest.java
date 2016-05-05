package ComputerVision;

import org.opencv.contrib.FaceRecognizer;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;


public class OpencvTest
{

    /**
     * Conversion of a OpenCV Mat format to Java supported Buffered Image format
     */
    public static class Mat2Image {
        Mat mat = new Mat();
        BufferedImage img;
        byte[] dat;

        public Mat2Image() {
        }

        public Mat2Image(Mat mat) {
            getSpace(mat);
        }
        //Remembering that a Mat is a matrix, we get the height and width and turn that into our buffered images
        public void getSpace(Mat mat) {
            System.out.println("Converting image....");
            this.mat = mat;
            int w = mat.cols(), h = mat.rows();
            if (dat == null || dat.length != w * h * 3)
                dat = new byte[w * h * 3];
            if (img == null || img.getWidth() != w || img.getHeight() != h
                    || img.getType() != BufferedImage.TYPE_3BYTE_BGR)
                img = new BufferedImage(w, h,
                        BufferedImage.TYPE_3BYTE_BGR);
        }

        //Returns a Buffered image for Java
        BufferedImage getImage(Mat mat){
            System.out.println("Converting image....");
            getSpace(mat);
            mat.get(0, 0, dat);
            img.getRaster().setDataElements(0, 0,
                    mat.cols(), mat.rows(), dat);
            return img;
        }
        static{
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
    }

    private static class Locator{

        /**
         * Determines the 2d location of the palm
         * @return a float array containing the 2d location of the palm
         */
        public dynamicSign.Point getPalmLoc(Mat m){

            dynamicSign.Point toReturn = new dynamicSign.Point(0, 0, 0, 0);
            return toReturn;
        }
        /**
         * Determines the 2d location of the head
         * @return a float array containing the 2d location of the head
         */
        public dynamicSign.Point getHeadLoc(Mat m){
            CascadeClassifier faceDetector = new CascadeClassifier(OpencvTest.class.
                    getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
            if(VideoCap.cap.grab()){
                VideoCap.cap.retrieve(m);
                MatOfRect faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(m, faceDetections);
                for(Rect rect: faceDetections.toArray())
                    Core.rectangle(m, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 225, 0));
            }

            FaceRecognizer model = new LBPHFaceRecognizer();
            int[] label = new int[1];
            double[] conf = new double[1];
            model.predict(m, label, conf);
            System.out.println("rec" + label[0] + " " + conf[0]); //rec 3 0.0
            dynamicSign.Point toReturn = new dynamicSign.Point(0, 0, 0, 0);
            return toReturn;
        }

    }
    /**
     * How we capture the video feed through the webcam
     */
    public static class VideoCap {
        static{
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        static VideoCapture cap;
        Mat2Image mat2Img = new Mat2Image();

        VideoCap(){

            cap = new VideoCapture();
            cap.open(0);
        }

        BufferedImage getOneFrame() {
            System.out.println("Convert mat captured image to bufferedimage.");

            cap.read(mat2Img.mat);
            Locator loc = new Locator();

            loc.getPalmLoc(mat2Img.mat);//Get the location of the palm given the image
            loc.getHeadLoc(mat2Img.mat);//Get the location of the head given the image

            return mat2Img.getImage(mat2Img.mat);
        }
    }

    public static class MyFrame extends JFrame {
        private JPanel contentPane;
        /**
         * Create the frame.
         */
        public MyFrame() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(100, 100, 650, 490);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(null);

            new MyThread().start();
        }

        VideoCap videoCap = new VideoCap();

        public void paint(Graphics g){
            System.out.println("Printing Frame");
            g = contentPane.getGraphics();
            g.drawImage(videoCap.getOneFrame(), 0, 0, this);
        }

        //Keep running!
        class MyThread extends Thread{
            @Override
            public void run() {
                for (;;){
                    repaint();
                    try { Thread.sleep(30);
                    } catch (InterruptedException e) {  e.printStackTrace();  }
                }
            }
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        VideoCapture capture = new VideoCapture();
        capture.open(0);
        Mat mat = new Mat();
        MatOfByte mem = new MatOfByte();
        if(capture.grab()){
            capture.read(mat);
            Mat2Image mat2Image = new Mat2Image();

            Highgui.imencode(".bmp", mat, mem);

            //BufferedImage image = mat2Image.getImage(mat);


        }

        /*EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyFrame frame = new MyFrame();
                    System.out.println("Display frame and pray it works.");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
    }
}