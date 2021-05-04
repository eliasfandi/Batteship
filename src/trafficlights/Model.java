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
                if (!remainingSegments) {
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
            shipMap.put(hitLoc, 1);
        }
    }

    public void placeShips() {
        //5, 4, 3, 2, 2
        for (Ship i : shipArray) {

            Integer[] presence = i.shipPresence;

            for (Integer k : i.shipPresence) {
                shipMap.put(k, 3);
            }
        }
    }

    public void generateShips() { //TODO issue with ship placement here
        int[] shipSizes = {5, 4, 3, 2, 2};
        int arrayIndex = 0;
        while (shipArray[4] == null)
        for (int i : shipSizes) {
            /*
            Get random ship starting pos
            select random orientation
            Get stern position
             */
            Random random = new Random();

            Integer shipBow = random.nextInt(101);

            Integer shipStern;

            Integer[] shipPresence = new Integer[i];

            boolean setOrientation;


            boolean orientation = random.nextBoolean();

            //randomly select a direction
            if (orientation == false) { // if orientation horizontal, ch
                int minRound = 10 * (shipBow /10);
                int maxRound = minRound + 10;

                if ((shipBow - minRound) >= i) {

                    for (int k = 0; k < i; k++) {
                        shipPresence[k] = shipBow - k;
                    }
                }

                else {
                    for (int k = 0; k < i; k++) {
                        shipPresence[k] = shipBow + k; //inclusive of shipBow
                    }
                }
            }

            else {
                    int column =  (int) shipBow.toString().charAt(0);

                    if (column + i > 10) {
                        shipPresence[0] = shipBow; // add shipBow itself
                        for (int k = 1; k < i; k++) {
                            shipPresence[k] = shipBow - 10;
                        }
                    }
                    else {
                        shipPresence[0] = shipBow; // add shipBow itself
                        for (int k = 1; k < i; k++) {
                            shipPresence[k] = shipBow + 10;
                        }

                    }
            }
            // Get stern from final generated array
            shipStern = shipPresence[i-1];



            Ship ship = new Ship(shipBow, shipStern, shipPresence, true, orientation);

            shipArray[arrayIndex] = ship;
            arrayIndex++;
        }
    }
    public Map<Integer, Integer> getShipMap() {
        return shipMap;
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
        public Integer[] getShipPresence() {
            return shipPresence;
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


