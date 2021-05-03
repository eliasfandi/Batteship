package trafficlights;

import java.util.Map;
import java.util.Observable;
import java.util.HashMap;
import java.util.Random;

public class TLModel extends Observable {

    private boolean red;
    private boolean amber;
    private boolean green;

    public TLModel() {
        initialise();
    }

    public boolean getRed() {
        return red;
    }

    public boolean getAmber() {
        return amber;
    }

    public boolean getGreen() {
        return green;
    }

    public void change() {
        if (red && !amber && !green) {
            amber = true;
        } else if (red && amber && !green) {
            red = false;
            amber = false;
            green = true;
        } else if (!red && !amber && green) {
            green = false;
            amber = true;
        } else { // just assume amber (is this wise?)
            red = true;
            amber = false;
            green = false;
        }
        setChanged();
        notifyObservers();

    }

    public void initialise() {

        red = true;
        amber = false;
        green = false;
        setChanged();
        notifyObservers();
    }


    //////////////////////////////////////////////////


    public class Model extends Observable {
        private Ship[] shipArray;
        Map<Integer, Integer> shipMap;

        public Model(Map<Integer, Integer> map) {
            this.shipArray = new Ship[5];
            this.shipMap = map;
        }

        public int healthCheck() {
            boolean remainingShips = false;
            for (Ship i : shipArray) {
                if (i.afloat) {
                    boolean remainingSegments = false;

                    for (Integer k : i.shipPresence) {
                        if (shipMap.get(k) != 2) {
                            remainingSegments = true;
                            remainingShips = true;
                        }
                    }
                    if (remainingSegments == false) {
                        i.afloat = false;
                    }

                }
            }
            return 0;
        }

        public void registerHit(int location) {
            shipMap.put(Integer.valueOf(location), 2);
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

                shipArray[i] = ship;
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


}