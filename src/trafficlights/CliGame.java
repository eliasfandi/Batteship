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

            input.close();

        }
    }
}


