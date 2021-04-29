package trafficlights;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TLGraphicalView extends Stage implements Observer {
    
    private TLModel model;
    private Canvas canvas;
    private static final int WINDOW_WIDTH = 100;
    private static final int WINDOW_HEIGHT = 100;
    
    public TLGraphicalView(TLModel model) {
        this.model=model;
        canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        setScene (new Scene (new Group (canvas)));
        
        show();
        model.addObserver(this);
        update(null, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        int centre = WINDOW_WIDTH / 2;
        int radius = WINDOW_HEIGHT / 4;
        
        context.setFill(model.getRed() ? Color.RED : Color.BLACK);
        context.fillOval(centre, 0, radius, radius);
        context.setFill(model.getAmber() ? Color.ORANGE : Color.BLACK);
        context.fillOval(centre, radius, radius, radius);
        context.setFill(model.getGreen() ? Color.GREEN : Color.BLACK);
        context.fillOval(centre, 2*radius, radius, radius);
        

    }
}
