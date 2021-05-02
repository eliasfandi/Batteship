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

        public Model(Ship[] shipArray, Map<Integer, Boolean> map) {
            this.shipArray = shipArray;
            this.shipMap = map;
        }

        public void placeShips() {
            //5, 4, 3, 2, 2
        }

        public void generateShips() {
            int[] shipSizes = {5, 4, 2, 2};

            for (int i = 0; i < 5; i++) {

                Random random = new Random();

                Integer shipBow = random.nextInt(101);

                Integer shipStern;

                int orientation = random.nextInt(2);

                if (orientation == 0) {
                    //hoizontal
                    if (10 - shipBow <= 5) { // spread left
                        shipStern = shipBow - 5;
                    } else { //spread right
                        shipStern = shipBow + 5;
                    }

                } else {
                    //vertical
                    if (((i + 1) * 10 - shipBow) <= 0) { //spread down
                        shipStern = shipBow + ((i + 1) * 10);

                    } else { //spread up
                        shipStern = shipBow - ((i + 1) * 10);
                    }
                }


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