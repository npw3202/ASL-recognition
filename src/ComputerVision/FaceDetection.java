package ComputerVision;

/**
 * Created by qadirhaqq on 5/3/16.
 * Credit:Taha Emara, without his example I never would have gotten this far
 */

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class FaceDetection extends javax.swing.JFrame {
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private DaemonThread myThread = null;
    VideoCapture webSource = new VideoCapture();
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    /*
    CascadeClassifier faceDetector = new
            CascadeClassifier(FaceDetection.class.getResource
            ("haarcascade_frontalface_alt.xml").getPath().substring(1));*/
    CascadeClassifier faceDetector = new CascadeClassifier();

    private MatOfRect faceDetections = new MatOfRect();
    class DaemonThread implements Runnable {
        protected volatile boolean runnable = false;
        @Override
        public void run() {
            synchronized (this) {
                webSource.open(0);
                faceDetector.load("/opt/local/share/OpenCV/haarcascades/haarcascade_frontalface_alt.xml");
                //WHY DOES IT NOT WORK FROM SOURCE PATH???
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frame);
                            Graphics g = jPanel1.getGraphics();
                            faceDetector.detectMultiScale(frame, faceDetections);
                            //faceDetector.load("/opt/local/share/OpenCV/lbpcascades/lbpcascade_frontalface.xml");
                            /*faceDetector.load(FaceDetection.class.getResource("lbpcascade_frontalface.xml").getPath()
                                    .substring(1));*/
                            /*Not sure why this only works from this specific directory, notice how it is also in the
                            src path -_- */
                            System.out.println("NUMBER OF FACE DETECTIONS: " + faceDetections.toArray().length);
                            for (Rect rect : faceDetections.toArray()) {
                                Core.rectangle(frame, new Point(rect.x, rect.y),
                                        new Point(rect.x + rect.width, rect.y + rect.height),
                                        new Scalar(0, 255, 0));
                            }
                            Highgui.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            BufferedImage buff = (BufferedImage) im;
                            if (g.drawImage(buff, 0, 0, getWidth(),
                                    getHeight()-150 , 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (!runnable) {
                                    System.out.println("Paused ..... ");
                                    this.wait();
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Error");
                        }
                    }
                }
            }
        }
    }
    /**
     * Creates new form FaceDetection
     */
    public FaceDetection() {
        initComponents();
        System.out.println(FaceDetection.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
    }
    /**
     * GUI Interface for Face Detection
     */
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 376, Short.MAX_VALUE)
        );
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton2.setText("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(255, 255, 255)
                                .addComponent(jButton1)
                                .addGap(86, 86, 86)
                                .addComponent(jButton2)
                                .addContainerGap(258, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        myThread.runnable = false;            // stop thread
        jButton2.setEnabled(false);   // activate start button
        jButton1.setEnabled(true);     // deactivate stop button

        webSource.release();  // stop caturing fron cam


    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        webSource = new VideoCapture(0); // video capture from default cam
        myThread = new DaemonThread(); //create object of thread class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                 //start thread
        jButton1.setEnabled(false);  // deactivate start button
        jButton2.setEnabled(true);  //  activate stop button


    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FaceDetection().setVisible(true);
            }
        });
    }
}