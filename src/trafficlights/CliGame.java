package trafficlights;

import java.util.Scanner;

public class CliGame {



    public static void main(String[] args) {

        Model model = new Model();

        model.generateShips();
        model.placeShips();
        Scanner input = new Scanner(System.in);

        while (model.healthCheck() == true) {

            System.out.println("Enter where you want to aim: ");
            String position = input.nextLine();

            //input.close();

            //0 ocean 1 miss 2 ship hit 3 ship present
            int y = (int) position.charAt(0);
            int x = Integer.parseInt(position.substring(1));
            y = (y - 64);
            int hitLoc = (y*10) + x -10;

            model.registerHit(hitLoc);

        }
    }
}


