package GUI;

import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;


import mainPackage.Tracker;

public class WebcamPanel extends Panel implements ActionListener{

	 Timer timer=new Timer(100, this);
	 public void actionPerformed(ActionEvent ev){
		    if(ev.getSource()==timer){
		      repaint();// this will call at every 1 second
		    }
	 }
	 class Mat2Image {
		    Mat mat = new Mat();
		    BufferedImage img;
		    byte[] dat;
		    public Mat2Image() {
		    }
		    public Mat2Image(Mat mat) {
		        getSpace(mat);
		    }
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
		        BufferedImage getImage(Mat mat){
		            getSpace(mat);
		            mat.get(0, 0, dat);
		            img.getRaster().setDataElements(0, 0, 
		                               mat.cols(), mat.rows(), dat);
		        return img;
		    }
		}
	static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();

    public WebcamPanel(){
        cap = new VideoCapture();
        cap.open(0);
        timer.start();
    } 
 
    BufferedImage getOneFrame() {
        cap.read(mat2Img.mat);
        return mat2Img.getImage(mat2Img.mat);
    }
    @Override
    public void paint(Graphics g) {
		super.paint(g);
		BufferedImage bi = getOneFrame();
		g.drawImage(bi, 0, 0,this.getWidth(),this.getHeight(), null);
	}
    public static void main(String args[]){
		JFrame jf = new JFrame();
		Tracker t = new Tracker();
		WebcamPanel panel = new WebcamPanel();
		jf.add(panel);
		jf.setSize(100, 100);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
