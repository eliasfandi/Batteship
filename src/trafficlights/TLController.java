package trafficlights;

import java.util.Timer;
import java.util.TimerTask;

public class TLController {
    
    private TLModel model;
    private TLView view;
    private boolean animating;
    private Timer timer;

    
    public TLController(TLModel model ){
        this.model = model;
        animating = false;
        timer = new Timer();
    }
    
    public void setView(TLView view){
        this.view = view;
        
      
    }
    
    public void initialise(){
        model.initialise();
        configureButtons();
    }
    
    public void change(){
        model.change();
        configureButtons();
    }

    public void run() {
        animating = true;
        changeSoon();
        configureButtons();
    }

    public void stop() {
        animating = false;
        configureButtons();
    }

    private void configureButtons() {
        view.setEnableChange(!animating);
        view.setEnableInitialise(!animating);
        view.setEnableRun(!animating);
        view.setEnableStop(animating);

    }

    private void changeSoon() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (animating) {
                    model.change();
                    changeSoon();
                }
            }
        }, 1000); // delay in miliseconds
    }
    
    
    
}