package imaging;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;
import com.leapmotion.leap.Listener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nicholas on 4/17/2016.
 */
public class Imager extends Listener {
    public static final String DEFAULT_PATH = "C:\\Users\\Nicholas\\git\\ASL\\image.jpg";
    Controller c;
    ImageList currImages = null;

    /**
     * Constructor for the Imager class
     *
     * @param c the controller (this is a listener for the controller)
     */
    public Imager(Controller c) {
        this.c = c;
    }

    /**
     * Gets the image from the camera
     *
     * @param imageNum the image to get (0 for left, 1 for right)
     * @return a bufferedImage of the image
     */
    public BufferedImage getImage(int imageNum) {
        ImageList images;
        //get the appropriate image (if an image has been
        //saved, use that, otherwise, grab one)
        if (currImages == null) {
            images = c.images();
        } else {
            images = currImages;
            currImages = null;
        }
        Image image = images.get(imageNum);
        //only return an image if the image is valid
        if (image.isValid()) {
            //the byte array containing the intensity
            byte imageBytes[] = image.data();
            //the buffered image to return
            BufferedImage bi = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_BYTE_GRAY);
            for (int i = 0; i < imageBytes.length; i++) {
                int byteData = imageBytes[i];
                //the red green and blue data are all just the byte data
                int r = (byteData & 0xFF); //convert to unsigned
                int g = (byteData & 0xFF); //convert to unsigned
                int b = (byteData & 0xFF); //convert to unsigned
                //set the appropriate pixel
                bi.setRGB(i % image.width(), i / image.width(), (r << 16) | (g << 8) | b);
            }
            //return the image
            return bi;
        } else {
            //if the image is invalid, return null
            return null;
        }
    }

    /**
     * Gets the image  from the camera
     *
     * @return a bufferedImage of the image
     */
    public BufferedImage getImage() {
        return getImage(0);
    }

    /**
     * Sets the images to use
     *
     * @param images the images to input
     */
    public void setImage(ImageList images) {
        this.currImages = images;
    }

    /**
     * When an image is available save it
     * NOTE: automatically called, do not call manually
     *
     * @param c the controller
     */
    public synchronized void onImages(Controller c) {
        setImage(c.images());
        save();
    }

    /**
     * Save the image to a path (jpg format)
     *
     * @param path the path to save the image to
     */
    private void save(String path) {
        File toOutput = new File(path);
        BufferedImage image = getImage();
        try {
            if (image != null) {
                ImageIO.write(image, "jpg", toOutput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the image to the default location
     */
    private void save() {
        save(DEFAULT_PATH);
    }
}
