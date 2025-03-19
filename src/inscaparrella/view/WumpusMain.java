package inscaparrella.view;

import java.util.Scanner;

/*
* Martin Ventaja
* Xavier Balletbó
* Rubén Robles
*/
public class WumpusMain {

    public static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

        //variables
        boolean menu = true;
        int opt;
        String fileName = "";

        do {
            System.out.println("""
                    ~~~ HUNT THE WUMPUS ~~~
                    0. Sortir
                    1. Carregar partida
                    2. Crear nova partida
                    """);
            opt = keyboard.nextInt();

            switch (opt) {
                case 0:
                    menu = false;
                    break;
                case 1:
                    System.out.println("Indica quin fitxer de partida vols carregar (per defecte files/wumpus1.txt)");
                    fileName = keyboard.next();
                    break;
                case 2:
                    System.out.println("Indica un fitxer per guardar la nova partida (per defecte files/wumpus1.txt)");
                    fileName = keyboard.next();
                    break;
            }

        }while (menu);
    }

}
