package ComputerVision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

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
        //Remembering that a Mat is a matric, we get the height and width and turn that into our buffered images
        public void getSpace(Mat mat) {
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

    /**
     * How we capture the video feed through the webcam
     */
    public static class VideoCap {
        static{
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        VideoCapture cap;
        Mat2Image mat2Img = new Mat2Image();

        VideoCap(){
            cap = new VideoCapture();
            cap.open(0);
        }

        BufferedImage getOneFrame() {
            cap.read(mat2Img.mat);
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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyFrame frame = new MyFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}