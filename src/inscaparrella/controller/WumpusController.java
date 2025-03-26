package inscaparrella.controller;

import inscaparrella.model.*;
import inscaparrella.utils.InhabitantType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WumpusController {

    WumpusLaberynth laberynth;
    Player player;
    String traverseMessage;
    String echoes;
    boolean gameEnded;
    boolean won;

    public WumpusController() {
        this.laberynth = new WumpusLaberynth();
        this.player = new Player();
        this.traverseMessage = "";
        this.echoes = "";
        this.gameEnded = false;
        this.won = false;
    }

    public boolean loadLabyrinth(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // 1. Leer dimensiones del laberinto
            String line = reader.readLine();
            if (line == null) return false;

            String[] dimensions = line.split(" ");
            if (dimensions.length != 2) return false;

            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            // 2. Crear nueva instancia de WumpusLaberynth
            WumpusLaberynth newLaberynth = new WumpusLaberynth();
            ArrayList<ArrayList<Cell>> labyrinthCells = new ArrayList<>();

            // 3. Leer cada fila del laberinto
            for (int i = 0; i < rows; i++) {
                line = reader.readLine();
                if (line == null) return false;

                ArrayList<Cell> rowCells = new ArrayList<>();
                String[] cellSymbols = line.split(" ");

                for (int j = 0; j < cols; j++) {
                    switch (cellSymbols[j]) {
                        case "N":
                            rowCells.add(new NormallCell(i,j));
                            break;
                        case "P":
                            rowCells.add(new PowerUPCell(i, j));
                            break;
                        case "W":
                            rowCells.add(new WellCell(i, j));
                            break;
                        default:
                            return false;
                    }
                }
                labyrinthCells.add(rowCells);
            }

            // 4. Configurar el laberinto usando el setter
            newLaberynth.setLaberynth(labyrinthCells);

            // 5. Leer posición del Wumpus
            line = reader.readLine();
            if (line == null) return false;
            String[] wumpusPosition = line.split(" ");
            if (wumpusPosition.length != 2) return false;

            int wumpusRow = Integer.parseInt(wumpusPosition[0]);
            int wumpusCol = Integer.parseInt(wumpusPosition[1]);

            // 6. Leer posiciones de los ratpenats
            line = reader.readLine();
            if (line == null) return false;
            String[] batsPositions = line.split(" ");

            // 7. Actualizar el estado del controlador
            this.laberynth = newLaberynth;
            this.player = new Player();
            this.traverseMessage = "Laberinto cargado correctamente";
            this.echoes = "";
            this.gameEnded = false;
            this.won = false;

            return true;

        } catch (IOException | NumberFormatException e) {
            this.traverseMessage = "Error al cargar el laberinto: " + e.getMessage();
            return false;
        }
    }



}
