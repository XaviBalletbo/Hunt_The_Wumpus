package inscaparrella.view;

import inscaparrella.model.Player;
import inscaparrella.model.WumpusLaberynth;
import inscaparrella.utils.PowerUp;

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
        int row = 0;
        int col = 0;
        String fileName = "";
        WumpusLaberynth laberynth;
        Player player = new Player();
        boolean won = true;
        String opcio = "";

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
                    System.out.println("CEL·LA ACTUAL:");
                    System.out.println("Cel·la [" + row + ", " + col + "] -Tipus "); // tipus NORMAL...
                    System.out.println("ECOS:");
                    //ecos

                    do {
                        System.out.println("Jugador a la posició (" + row + ", " + col + ")");
                        System.out.println("ARROW: " + player.getPowerUpQuantity(PowerUp.ARROW));
                        System.out.println("JUMER_BOOTS: " + player.getPowerUpQuantity(PowerUp.JUMPER_BOOTS));

                        //print tablero

                        System.out.println("w -> moure amunt; s -> moure avall; a -> moure esquerra; d -> moure dreta");
                        System.out.println("W -> disparar amunt; S -> dispara avall; A -> dispara esquerra; A -> dispara dreta");

                        System.out.println("Opció:");
                        opcio = keyboard.next();
                        if (opcio.length() != 1)
                            System.out.println("Introdueix un valor valid.");
                    }while (won);
                    break;
                case 2:
                    System.out.println("Indica un fitxer per guardar la nova partida (per defecte files/wumpus1.txt)");
                    fileName = keyboard.next();
                    break;
            }

        }while (menu);
    }

}
