package inscaparrella.view;

import inscaparrella.controller.WumpusController;
import inscaparrella.utils.*;
import inscaparrella.utils.PowerUp;

import java.util.Scanner;

/*
* Martin Ventaja
* Xavier Balletbó
* Rubén Robles
*/
public class WumpusMain {
    private static WumpusController contr  = new WumpusController();
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

        //variables
        boolean menu = true;
        int opt;

        do {
            System.out.println("""
                    ~~~ HUNT THE WUMPUS ~~~
                    0. Sortir
                    1. Carregar partida
                    2. Crear nova partida
                    """);
            opt = keyboard.nextInt();
            keyboard.nextLine();

            switch (opt) {
                case 0:
                    menu = false;
                    break;
                case 1:
                    loadGame();
                    break;
                case 2:
                    createNewGame();
                    break;
                default:
                    System.out.println("Opció no valida: ");
            }

        }while (menu);
    }


    private static void loadGame(){
        System.out.println("Introdueix el nom del fitxer que vols carregar (per defecte files/wumpus1.txt: )");
        String filename = keyboard.nextLine();

        if (contr.loadLabyrinth(filename)) {
            if (contr.startGame()) {
                playGame();
            } else{
            System.out.println("No s'ha pogut iniciar el joc");
            }
        }else {
            System.out.println(contr.getLastTraverseMessage());
            }
    }

    public static void createNewGame(){
        System.out.println("Introdueix el nom del fitxer per guardar la partida (per defecte files/wumpus1.txt): ");
        String filename = keyboard.nextLine();

        contr = new WumpusController();
        contr.loadLabyrinth(filename);

        if (contr.startGame()){
            playGame();
        }else System.out.println("No s'ha pogut iniciar el joc: ");

    }

    private static void playGame() {
        boolean gameActive = true;

        while (gameActive && !contr.isGameEnded()) {
            // Mostrar estado del juego
            System.out.println(contr.toString());

            // Mostrar opciones
            System.out.println("Moviment: w (amunt), s (avall), a (esquerra), d (dreta)");
            System.out.println("Disparar: W (amunt), S (avall), A (esquerra), D (dreta)");
            System.out.print("Selecciona una acció: ");

            String action = keyboard.nextLine().toLowerCase();

            switch (action) {
                case "w":
                    contr.movePlayer(MovementDirection.UP);
                    break;
                case "s":
                    contr.movePlayer(MovementDirection.DOWN);
                    break;
                case "a":
                    contr.movePlayer(MovementDirection.LEFT);
                    break;
                case "d":
                    contr.movePlayer(MovementDirection.RIGHT);
                    break;
                case "W":
                    contr.huntTheWumpus(ShootDirection.UP);
                    break;
                case "S":
                    contr.huntTheWumpus(ShootDirection.DOWN);
                    break;
                case "A":
                    contr.huntTheWumpus(ShootDirection.LEFT);
                    break;
                case "D":
                    contr.huntTheWumpus(ShootDirection.RIGHT);
                    break;
                default:
                    System.out.println("Acció no vàlida");
            }

            // Mostrar mensajes después de cada acción
            System.out.println(contr.getLastTraverseMessage());
            System.out.println("Ecos: " + contr.getLastEchoes());

            // Verificar estado del juego
            if (contr.isGameEnded()) {
                gameActive = false;
                if (contr.isGameWon()) {
                    System.out.println("ENHORABONA! Has guanyat!");
                } else {
                    System.out.println("Game Over! Has perdut...");
                }
            }
        }
    }
}
