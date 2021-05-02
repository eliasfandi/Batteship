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
        Map<Integer, Boolean> shipMap;

        public Model( Map<Integer, Boolean> map) {
            this.shipArray = new Ship[5];
            this.shipMap = map;
        }

        public void placeShips() {
            //5, 4, 3, 2, 2
        }

        public void generateShips() {
            int[] shipSizes = {5, 4, 3, 2, 2};

            for (int i : shipSizes) {

                Random random = new Random();

                Integer shipBow = random.nextInt(101);

                Integer shipStern;

                Integer[] shipPresence = new Integer[i];


                int orientation = random.nextInt(2);

                if (orientation == 0) {
                    //hoizontal
                    if (10 - shipBow <= i) { // spread left
                        shipStern = shipBow - (i);
                    } else { //spread right
                        shipStern = shipBow + (i);
                    }

                    for (int k = 0; k < i; k++) {
                        shipPresence[k] = Math.min(shipBow, shipStern) + (k+1);
                    }

                } else {
                    //vertical
                    if (((i) * 10 - shipBow) <= 0) { //spread down
                        shipStern = shipBow + ((i) * 10);

                    } else { //spread up
                        shipStern = shipBow - ((i) * 10);
                    }

                    for (int k = 0; k < i; k++) {
                        shipPresence[k] = Math.min(shipBow, shipStern) + (10* (k+1));
                    }
                }

                Ship ship = new Ship(shipBow,shipStern, shipPresence,true);

            }
        }


    }


    public class Ship {
        private Integer shipBow;
        private Integer shipStern;
        private Integer[] shipPresence; // abs(bow-stern) for x and y
        private boolean afloat;


        public Ship(Integer shipBow, Integer shipStern, Integer[] ship, boolean afloat) {
            this.shipBow = shipBow;
            this.shipStern = shipStern;
            this.shipPresence = ship;
            this.afloat = afloat;
        }

        public Integer getShipBow() {
            return shipBow;
        }

        public Integer getShipStern() {
            return shipStern;
        }

        public Integer[] getHitArray() {
            return shipPresence;
        }


        public boolean getAfloat() {
            return afloat;
        }

        public void setAfloat(boolean afloat) {
            this.afloat = afloat;
        }

    }
}