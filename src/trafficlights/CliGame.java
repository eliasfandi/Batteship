package trafficlights;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CliGame {



    public static void main(String[] args) {

        Model model = new Model();

        model.generateShips();
        model.placeShips();
        Scanner input = new Scanner(System.in);

        int[] gameArray  = new int[100];

        while (model.healthCheck()) {

            System.out.println("Enter where you want to aim: ");
            String position = input.nextLine();

            //input.close();

            //0 ocean 1 miss 2 ship hit 3 ship present
            int y = (int) position.charAt(0);
            int x = Integer.parseInt(position.substring(1));
            y = (y - 64);
            int hitLoc = (y*10) + x -10;

            
            model.registerHit(hitLoc);
            Map<Integer, Integer> tileMap = model.getShipMap();
            Set<Map.Entry<Integer, Integer>> activeTiles = tileMap.entrySet();
            //Integer[] activeTiles2 = tileMap.keySet().toArray(new Integer[activeTiles.size()]);


            for(Map.Entry<Integer, Integer> tile : activeTiles) {

                gameArray[tile.getKey()-1] = tile.getValue();
            }

            for (int i = 0; i<= 90; i= i+10) {

                int[] subset  = Arrays.copyOfRange(gameArray, i, i + 10);
                System.out.println(Arrays.toString(subset));
            }


            model.healthCheck();

        }
    }
}


