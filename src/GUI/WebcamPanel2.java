package GUI;

import ComputerVision.FaceDetection;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by qadirhaqq on 5/5/16.
 */
public class WebcamPanel2 extends Panel implements ActionListener{
    MatOfRect faceDetections  = new MatOfRect();
    CascadeClassifier faceDetector = new
            CascadeClassifier("/"+ FaceDetection.class.getResource
            ("haarcascade_frontalface_alt.xml").getPath().substring(1));;
    VideoCapture webSource = new VideoCapture();;
    MatOfByte mem = new MatOfByte();;
    Mat frame = new Mat();
    Timer timer=new Timer(Integer.MIN_VALUE, this);
    public void actionPerformed(ActionEvent ev){
        if(ev.getSource()==timer){
            repaint();// this will call at every 1 second
        }
    }

    public WebcamPanel2(){

    }
    @Override
    public void paint(Graphics g) {
        webSource.open(0);
        timer.start();
        webSource.retrieve(frame);
        Mat frame2 = frame.clone();
        Size sz = new Size(200, 200);
        Imgproc.resize(frame2, frame, sz);
        faceDetector.detectMultiScale(frame, faceDetections);
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(frame, new org.opencv.core.Point(rect.x, rect.y),
                    new org.opencv.core.Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        Highgui.imencode(".bmp", frame, mem);
        Image im = null;
        try {
            im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage buff = (BufferedImage) im;
        if(g.drawImage(buff, 0, 0, getWidth(), getHeight()-150 , 0, 0, buff.getWidth(),
                buff.getHeight(), null)){
            System.out.println("Success");
        }else{
            System.out.println("problem");
        }
    }
    public static void main(String args[]){
        JFrame jf = new JFrame();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        WebcamPanel2 panel = new WebcamPanel2();
        jf.add(panel);
        jf.setSize(100, 100);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
