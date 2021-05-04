package trafficlights;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class View extends Application implements Observer {

    private Model model;
    private Controller controller;



    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new Model();
        controller = new Controller(model);
        GridPane gridpane = new GridPane();
        Button button = new Button();
        Label label = new Label("Test");
        Scene scene = new Scene(gridpane,800,400);

        gridpane.add(new Button(), 1, 0); // column=1 row=0
        gridpane.add(new Label(), 2, 0);  // column=2 row=0
        // or convenience methods set more than one constraint at once...
        //GridPane.setConstraints(label, 2, 0); // column=2 row=0

        // don't forget to add children to gridpane
        gridpane.getChildren().addAll(button, label);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
