package userInterface;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Image;
import com.leapmotion.leap.ImageList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import processing.core.*;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by qadirhaqq on 4/16/16.
 */
public class Gui extends Application implements Observer {

    private GuiModel gui;
    private Label label = new Label("Select an option");
    private BorderPane main = new BorderPane();
    private FlowPane mainFlow = new FlowPane();
    private GridPane mainGrid = new GridPane();
    private Controller controller = new Controller();



    public void start(Stage primaryStage){
        primaryStage.setTitle("ASL Recognition Application");



        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   public void init(){
       gui = new GuiModel();
       gui.addObserver(this);

   }

    @Override
    public void update(Observable o, Object arg) {

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private BorderPane makeBoardPane(){
        main.setLeft(leftMainView());
        main.setCenter();

        return  main;
    }

    //Belongs are the far left side of the main gridpane
    private GridPane makeFarLeftGrid(){
        GridPane leftGrid = new GridPane();
        Label signs = new Label("Some random text");
        //TODO add signs to model for update
        Button deleteSign = new Button("Delete");
        leftGrid.add(signs, 1, 1);
        leftGrid.add(deleteSign, 1, 2);
        //TODO set constraints
        return leftGrid;
    }

    private VBox leftMainView(){
        Button askNick = new Button("Ask Nick");
        Button record = new Button("Record");
        Button save = new Button("Save");

        VBox vButtons = new VBox();
        vButtons.setSpacing(10);
        vButtons.setPadding(new Insets(20, 0, 20, 20));
        vButtons.getChildren().addAll(askNick, record, save);
        return vButtons;
    }

    private VBox createCenter(){
        if(controller.isConnected()){
            controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
            Frame frame = controller.frame();
            PImage[] cameras = new PImage[2];
            if(frame.isValid()){
                ImageList images = frame.images();
                for( Image image : images ){
                    //Processing PImage class
                    PImage camera = cameras[image.id()];
                    camera = createImage(image.width(), image.height(), PImage.RGB);
                    camera.loadPixels();

                    //Get byte array containing the image data from Image object
                    byte[] imageData = image.data();

                    //Copy image data into display object, in this case PImage defined in Processing
                    for(int i = 0; i < image.width() * image.height(); i++){
                       int r = (imageData[i] & 0xFF) << 16; //convert to unsigned and shift into place
                       int g = (imageData[i] & 0xFF) << 8;
                       int  b = imageData[i] & 0xFF;
                        camera.pixels[i] =  r | g | b;
                    }

                    //Show the image
                    camera.updatePixels();
                    image(camera, 640 * image.id(), 0);
                }
            }

        }

    }

}
