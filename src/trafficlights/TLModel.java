package trafficlights;

import java.util.Observable;

public class TLModel extends Observable {
    
    private boolean red;
    private boolean amber;
    private boolean green;
    
    public TLModel() {
        initialise();
    }
    
    public boolean getRed(){
        return red;
    }
    
    public boolean getAmber(){
        return amber;
    }

    public boolean getGreen(){
        return green;
    }

    public void change() {
        if (red && !amber && !green) {
            amber = true;
        } else if (red && amber && !green) {
            red = false; amber = false; green = true;
        } else if (!red && !amber && green) {
            green = false; amber = true;
        } else { // just assume amber (is this wise?)
            red = true; amber = false; green = false;
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
    class Point {

        private int x;
        private int y;

        public Point(int _x, int _y) {
            x = _x;
            y = _y;
        }
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }
     class Ship extends Observable {
        private Point shipBow;
        private Point shipStern;
         private int[][] hitArray;

        Ship(Point shipBow, Point shipStern, int[][] hitArray) {
            this.shipBow = shipBow;
            this.shipStern = shipStern;
            this.hitArray = hitArray;
        }

        public Point getShipBow() {
            return shipBow;
        }
        public Point getShipStern() {
            return shipStern;
        }

         public int[][] getHitArray() {
             return hitArray;
         }






    }
}