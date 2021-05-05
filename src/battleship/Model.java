package battleship;

import java.util.*;

/**
 * The type Model.
 */
public class Model extends Observable {

    /**
     * The Ship map.
     */
    Map<Integer, Integer> shipMap;
    /**
     * The Try count.
     */
    int tryCount;
    private final Ship[] shipArray;
    private final boolean[] pastStatus;


    /**
     * Instantiates a new Model.
     */
    public Model() {
        this.shipArray = new Ship[5];
        this.shipMap = new HashMap<Integer, Integer>(); //0 ocean 1 miss 2 ship hit 3 ship present
        this.tryCount = 0;
        this.pastStatus = new boolean[5];

    }

    /**
     * Sink check boolean.
     *
     * @return boolean
     */
    public boolean sinkCheck() {
        boolean shipSank = false;
        for (int i = 0; i < 5; i++) {
            if (pastStatus[i] && !shipArray[i].getAfloat()) {
                shipSank = true;
            }
        }

        return shipSank;
    }

    /**
     * Health check boolean.
     *
     * @return boolean
     */
    public boolean healthCheck() {
        boolean remainingShips = false;


        for (Ship i : shipArray) {
            if (i.getAfloat()) {
                boolean remainingSegments = false;

                for (Integer k : i.shipPresence) {
                    if (shipMap.get(k) == 3) {
                        remainingSegments = true;
                        remainingShips = true;
                    }
                }
                if (!remainingSegments) {

                    i.setAfloat(false);
                }

            }
        }
        return remainingShips;
    }

    /**
     * Register hit.
     *
     * @param location the location
     */
    public void registerHit(int location) {

        //Store status of ships before hit for sink detection
        for (int i = 0; i < 5; i++) {
            pastStatus[i] = shipArray[i].getAfloat();

        }
        Integer hitLoc = Integer.valueOf(location);
        //If there is a key, set it to 2 if there is a ship or 1 if it isnt a ship, else create & set empty key to a miss
        if (shipMap.containsKey(location)) {
            if (shipMap.get(hitLoc) == 3 || shipMap.get(hitLoc) == 2) {
                shipMap.put(hitLoc, 2);
            } else {
                shipMap.put(hitLoc, 1);
            }
        } else {
            shipMap.put(hitLoc, 1);
        }
        tryCount++;
    }


    /**
     * Generates ships and places on game board
     */
    public void generateShips() { //TODO VERIFY SHIP PLACEMENT
        int[] shipSizes = {5, 4, 3, 2, 2};
        int arrayIndex = 0;
        Random random = new Random();


        while (shipArray[4] == null) {
            shipMap = new HashMap<Integer, Integer>();
            System.out.println("Restarted");
            loopCheck:
            for (int i : shipSizes) {
            /*
            Get random ship starting pos
            select random orientation
            Get stern position
             */

                Integer shipBow = random.nextInt(100);

                Integer shipStern;

                Integer[] shipPresence = new Integer[i];

                boolean setOrientation;


                boolean orientation = random.nextBoolean();


                //randomly select a direction
                if (orientation == false) { // if orientation horizontal, ch
                    int minRound = 10 * (shipBow / 10);
                    int maxRound = minRound + 10;

                    if ((shipBow - minRound) > i) {

                        for (int k = 0; k < i; k++) {
                            shipPresence[k] = shipBow - (k + 1);
                        }
                    } else {
                        for (int k = 0; k < i; k++) {
                            shipPresence[k] = shipBow + (k + 1); //inclusive of shipBow
                        }
                    }
                } else {
                    int column;
                    if (shipBow >= 10) {
                        column = Integer.parseInt(String.valueOf(shipBow.toString().charAt(0)));
                    } else {
                        column = 1;
                    }
                    // System.out.println("Column: " + column);
                    if (column + i <= 10) {
                        // 2 + 6 = = 8
                        shipPresence[0] = shipBow; // add shipBow itself
                        for (int k = 1; k < i; k++) {
                            if (k == 1) {
                                shipPresence[k] = shipBow + 10;
                            } else {
                                shipPresence[k] = shipPresence[k - 1] + 10;
                            }
                        }
                    } else {
                        shipPresence[0] = shipBow; // add shipBow itself
                        for (int k = 1; k < i; k++) {
                            if (k == 1) {
                                shipPresence[k] = shipBow - 10;
                            } else {
                                shipPresence[k] = shipPresence[k - 1] - 10;
                            }
                        }

                    }
                }
                // Get stern from final generated array
                shipStern = shipPresence[i - 1];


                Ship ship = new Ship(shipBow, shipStern, shipPresence, true, orientation);


                shipArray[arrayIndex] = ship;
                for (Integer k : shipPresence) {

                    //Restart generation if there is an intersect
                    if (shipMap.containsKey(k)) {
                        //restart process
                        //break startloop;
                        //break;
                        System.out.println("Interference");
                        shipMap = new HashMap<Integer, Integer>();
                        arrayIndex = 0;

                        break loopCheck;
                        //continue;

                    } else {
                        shipMap.put(k, 3);
                    }
                }
                System.out.println(i);
                System.out.println(Arrays.toString(shipPresence));

                arrayIndex++;

            }


        }
    }

    /**
     * Gets ship map.
     *
     * @return ship map
     */
    public Map<Integer, Integer> getShipMap() {
        return shipMap;
    }

    /**
     * Gets try count.
     *
     * @return try count
     */
    public int getTryCount() {
        return tryCount;
    }


    /**
     * The type Ship.
     */
    protected class Ship {
        private final Integer shipBow;
        private final Integer shipStern;


        private final Integer[] shipPresence; // abs(bow-stern) for x and y
        private boolean afloat;
        private final boolean orientation; // 0 is horizontal 1 is vertical


        /**
         * Instantiates a new Ship.
         *
         * @param shipBow     the ship bow
         * @param shipStern   the ship stern
         * @param ship        the ship
         * @param afloat      the afloat
         * @param orientation the orientation
         */
        protected Ship(Integer shipBow, Integer shipStern, Integer[] ship, boolean afloat, boolean orientation) {
            this.shipBow = shipBow;
            this.shipStern = shipStern;
            this.shipPresence = ship;
            this.afloat = afloat;
            this.orientation = orientation;
        }

        /**
         * Get ship presence integer [ ].
         *
         * @return the integer [ ]
         */
        public Integer[] getShipPresence() {
            return shipPresence;
        }

        /**
         * Gets ship bow.
         *
         * @return the ship bow
         */
        protected Integer getShipBow() {
            return shipBow;
        }

        /**
         * Gets ship stern.
         *
         * @return the ship stern
         */
        protected Integer getShipStern() {
            return shipStern;
        }

        /**
         * Get hit array integer [ ].
         *
         * @return the integer [ ]
         */
        protected Integer[] getHitArray() {
            return shipPresence;
        }


        /**
         * Gets afloat.
         *
         * @return the afloat
         */
        protected boolean getAfloat() {
            return afloat;
        }

        /**
         * Sets afloat.
         *
         * @param afloat the afloat
         */
        protected void setAfloat(boolean afloat) {
            this.afloat = afloat;
        }

        /**
         * Is orientation boolean.
         *
         * @return the boolean
         */
        protected boolean isOrientation() {
            return orientation;
        }


    }

}


