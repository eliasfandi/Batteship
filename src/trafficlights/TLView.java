package trafficlights;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TLView extends Application implements Observer {

    private final Label redLabel = new Label("Red");
    private final Label amberLabel = new Label("Amber");
    private final Label greenLabel = new Label("Green");
    private final TextField redField = new TextField();
    private final TextField amberField = new TextField();
    private final TextField greenField = new TextField();

    private final Button initialiseButton = new Button("Initialise");
    private final Button changeButton = new Button("Change");
    private final Button runButton = new Button("Run     ");
    private final Button stopButton = new Button("Stop   ");

    private static final int WINDOW_WIDTH = 150;
    private static final int WINDOW_HEIGHT = 120;
    
    private TLModel model;
    private TLController controller;
    
    public GridPane makeLightsPane() {
        GridPane gridPane = new GridPane();
        
        gridPane.add(redLabel, 0, 0);
        gridPane.add(redField, 1, 0);

        gridPane.add(amberLabel, 0, 1);
        gridPane.add(amberField, 1, 1);

        gridPane.add(greenLabel, 0, 2);
        gridPane.add(greenField, 1, 2);
        
        redField.setEditable(false);
        redField.setPrefColumnCount(3);
        amberField.setEditable(false);
        amberField.setPrefColumnCount(3);
        greenField.setEditable(false);
        greenField.setPrefColumnCount(3);

        gridPane.add(initialiseButton, 0, 3);
        gridPane.add(changeButton, 1, 3);
        gridPane.add(runButton, 0, 4);
        gridPane.add(stopButton, 1, 4);
        
        
        

        initialiseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.initialise();
            }
        });

        changeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.change();
            }
        });

        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.run();
            }
        });
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.stop();
            }
        });
        

        return gridPane;
    }

    @Override
    public void start(Stage primaryStage) {

        model = new TLModel();

        controller = new TLController(model);
        controller.setView(this);
        GridPane gridPane = makeLightsPane();

        StackPane root = new StackPane();
        root.getChildren().add(gridPane); 

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle("Traffic Lights");
        primaryStage.setScene(scene);
        primaryStage.show();

        model.addObserver(this);
        update(null, null);

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                Stage stage = new TLGraphicalView (model);
            }
        });
        

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        redField.setText(model.getRed() ? "ON" : "OFF");
        amberField.setText(model.getAmber() ? "ON" : "OFF");
        greenField.setText(model.getGreen() ? "ON" : "OFF");


    }

    public void setEnableRun(boolean enabled) {
        runButton.setDisable(!enabled);
    }
    
    public void setEnableInitialise(boolean enabled) {
        initialiseButton.setDisable(!enabled);
    }

    public void setEnableChange(boolean enabled) {
        changeButton.setDisable(!enabled);
    }

    public void setEnableStop(boolean enabled) {
        stopButton.setDisable(!enabled);
    }
    
}