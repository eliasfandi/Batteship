package battleship;

import java.util.Map;

public class Controller {
    private final Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

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

    public void updateTries() {
        int tries = model.getTryCount();
        view.setTries(tries + " tries so far.");
    }

    public void notifySunkShip() {
        boolean shipSank = model.sinkCheck();

        if(shipSank) {
            view.setShipNotif("Ship sunk!");
        }
        else {
            view.setShipNotif("No new ship sank");

        }
    }

    public void continueGameCheck() {
        boolean gameEnd = model.healthCheck();
        if(!gameEnd) {
            view.setGameEnd("Game over! You won in " + model.getTryCount() + " tries!");
        }
        else {
            view.setGameEnd("Game in progress");
        }
    }

}
