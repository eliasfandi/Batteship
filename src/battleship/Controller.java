package battleship;

import java.util.Map;

/**
 * The type Controller.
 */
public class Controller {
    private final Model model;
    private final View view;

    /**
     * Instantiates a new Controller.
     *
     * @param model the model
     * @param view  the view
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

    }

    /**
     * Register hit.
     *
     * @param location the location
     */
    public void registerHit(int location) {
        model.registerHit(location);
    }

    /**
     * Gets tile status.
     *
     * @param location the location
     * @return the tile status
     */
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

    /**
     * Init game.
     */
    public void initGame() {
        model.generateShips();
    }

    /**
     * Update tries.
     */
    public void updateTries() {
        int tries = model.getTryCount();
        view.setTries(tries + " tries so far.");
    }

    /**
     * Notify sunk ship.
     */
    public void notifySunkShip() {
        boolean shipSank = model.sinkCheck();

        if (shipSank) {
            view.setShipNotif("Ship sunk!");
        } else {
            view.setShipNotif("No new ship sank");

        }
    }

    /**
     * Continue game check.
     */
    public void continueGameCheck() {
        boolean gameEnd = model.healthCheck();
        if (!gameEnd) {
            view.setGameEnd("Game over! You won in " + model.getTryCount() + " tries!");
        } else {
            view.setGameEnd("Game in progress");
        }
    }

}
