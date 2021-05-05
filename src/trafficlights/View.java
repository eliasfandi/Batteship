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
        //Button button = new Button();
        //Label label = new Label("Test");
        Scene scene = new Scene(gridpane, 800, 400);

        for (int i = 0; i < 100; i++) {

            Button tileButton = new Button();
            int y;
            int x;
            if (i > 9) {
                x = Integer.parseInt(Integer.toString(i).substring(1));
                y = Integer.parseInt(Integer.toString(i).substring(0, 1));
            } else {
                x = i;
                y = 0;
            }
            gridpane.add(tileButton, x, y); // column=1 row=0
            gridpane.add(new Label("x"), x, y);
            System.out.println("x: " + x + " y " + y);
            //System.out.println(i);


            tileButton.setOnAction(new EventHandler<ActionEvent>() {
                                       @Override
                                       public void handle(ActionEvent event) {

                                           //add one to x and y
                                           String xIn = Integer.toString(gridpane.getRowIndex(tileButton));
                                           String yIn = Integer.toString(gridpane.getColumnIndex(tileButton));
                                           int concatIndex;


                                               concatIndex = Integer.parseInt(xIn + yIn) +1 ;

                                           System.out.println("Ran Button");
                                           System.out.println("Concat: " + concatIndex);
                                           controller.registerHit(concatIndex);

                                       }
                                   }

            );

        }

        // don't forget to add children to gridpane
        //gridpane.getChildren().addAll(button, label);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
