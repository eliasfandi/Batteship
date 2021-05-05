package battleship;

import java.util.Map;

public class Controller {
    private final Model model;
    private View view;

    public Controller(Model model) {
        this.model = model;

    }

    public void registerHit(int location) {
        model.registerHit(location);
    }

    public int getTileStatus(int location) {
        Map<Integer, Integer> map = model.getShipMap();
        int tileStatus;
        if (map.containsKey(location)) {
            tileStatus = map.get(location);

        } else {
            tileStatus = 0;
        }
        return tileStatus;
    }

    public void initGame() {
        model.generateShips();
    }

    public void continueGameCheck() {
        model.healthCheck();
    }

}
