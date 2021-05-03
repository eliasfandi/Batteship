package trafficlights;

import java.util.Map;
import java.util.Observable;
import java.util.HashMap;
import java.util.Random;

public class Model extends Observable {
    private Ship[] shipArray;
    Map<Integer, Integer> shipMap;

    public Model() {
        this.shipArray = new Ship[5];
        this.shipMap = new HashMap<Integer, Integer>(); //0 ocean 1 miss 2 ship hit 3 ship present
    }

    public boolean healthCheck() {
        boolean remainingShips = false;
        for (Ship i : shipArray) {
            if (i.afloat) {
                boolean remainingSegments = false;

                for (Integer k : i.shipPresence) {
                    if (shipMap.get(k) == 3) {
                        remainingSegments = true;
                        remainingShips = true;
                    }
                }
                if (remainingSegments == false) {
                    i.afloat = false;
                }

            }
        }
        return remainingShips;
    }

    public void registerHit(int location) {

        Integer  hitLoc = Integer.valueOf(location);
        //If there is a key, set it to 2 if there is a ship or 1 if it isnt a ship, else create & set empty key to a miss
        if (shipMap.containsKey(location)) {
            if (shipMap.get(hitLoc) == 3 || hitLoc == 2) {
                shipMap.put(hitLoc, 2);
            } else {
                shipMap.put(hitLoc, 1);
            }
        }
        else {
            shipMap.put(location, 1);
        }
    }

    public void placeShips() {
        //5, 4, 3, 2, 2
        for (Ship i : shipArray) {

            Integer[] presence = i.shipPresence;

            for (Integer k : presence) {
                shipMap.put(k, 3);
            }
        }
    }

    public void generateShips() {
        int[] shipSizes = {5, 4, 3, 2, 2};
        int arrayIndex = 0;
        for (int i : shipSizes) {

            Random random = new Random();

            Integer shipBow = random.nextInt(101);

            Integer shipStern;

            Integer[] shipPresence = new Integer[i];


            int orientation = random.nextInt(2);
            boolean setOrientation;

            if (orientation == 0) {
                //hoizontal
                if (10 - shipBow <= i) { // spread left
                    shipStern = shipBow - (i);
                } else { //spread right
                    shipStern = shipBow + (i);
                }

                for (int k = 0; k < i; k++) {
                    shipPresence[k] = Math.min(shipBow, shipStern) + (k + 1);
                }
                setOrientation = true;
            } else {
                //vertical
                if (((i) * 10 - shipBow) <= 0) { //spread down
                    shipStern = shipBow + ((i) * 10);

                } else { //spread up
                    shipStern = shipBow - ((i) * 10);
                }

                for (int k = 0; k < i; k++) {
                    shipPresence[k] = Math.min(shipBow, shipStern) + (10 * (k + 1));
                }

                setOrientation = false;
            }

            Ship ship = new Ship(shipBow, shipStern, shipPresence, true, setOrientation);

            shipArray[arrayIndex] = ship;
            arrayIndex++;
        }
    }


    protected class Ship {
        private Integer shipBow;
        private Integer shipStern;
        private Integer[] shipPresence; // abs(bow-stern) for x and y
        private boolean afloat;
        private boolean orientation; // 0 is horizontal 1 is vertical


        protected Ship(Integer shipBow, Integer shipStern, Integer[] ship, boolean afloat, boolean orientation) {
            this.shipBow = shipBow;
            this.shipStern = shipStern;
            this.shipPresence = ship;
            this.afloat = afloat;
            this.orientation = orientation;
        }

        protected Integer getShipBow() {
            return shipBow;
        }

        protected Integer getShipStern() {
            return shipStern;
        }

        protected Integer[] getHitArray() {
            return shipPresence;
        }


        protected boolean getAfloat() {
            return afloat;
        }

        protected void setAfloat(boolean afloat) {
            this.afloat = afloat;
        }

        protected boolean isOrientation() {
            return orientation;
        }

    }

}


