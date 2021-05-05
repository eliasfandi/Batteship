package trafficlights;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
        System.out.println("Updated view");

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new Model();
        controller = new Controller(model);
        GridPane gridpane = new GridPane();
        //Button button = new Button();
        //Label label = new Label("Test");
        Scene scene = new Scene(gridpane, 300, 300);
        controller.initGame();
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
                                           int currentX = gridpane.getRowIndex(tileButton);
                                           int currentY = gridpane.getColumnIndex(tileButton);

                                           String xIn = Integer.toString(currentX);
                                           String yIn = Integer.toString(currentY);

                                           int concatIndex = Integer.parseInt(xIn + yIn) + 1;

                                           System.out.println("Ran Button");
                                           System.out.println("Concat: " + concatIndex);

                                           controller.registerHit(concatIndex);
                                           int updatedStatus = controller.getTileStatus(concatIndex);
                                           System.out.println("Status: " + updatedStatus);
                                           //Label test  = (Label) gridpane.getChildren();
                                           for (Node j :  gridpane.getChildren()) {
                                               if (j instanceof Label && gridpane.getRowIndex(j) == currentX &&
                                                       gridpane.getColumnIndex(j) == currentY) {
                                                   ((Label) j).setText(Integer.toString(updatedStatus));
                                               }
                                           }
                                           //test.setText("wow");
                                           //gridpane.add(new Label(Integer.toString(updatedStatus)), currentX, currentY);

                                       }
                                   }

            );

        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
