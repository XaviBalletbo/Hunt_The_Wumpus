package inscaparrella.controller;

import inscaparrella.model.WumpusLaberynth;
import inscaparrella.model.Player;
import inscaparrella.model.*;
import inscaparrella.utils.InhabitantType;


import java.io.*;
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
            this.traverseMessage = "El laberint s'ha carregat correctament";
            this.echoes = "";
            this.gameEnded = false;
            this.won = false;

            return true;

        } catch (IOException | NumberFormatException e) {
            this.traverseMessage = "Error al carregar el laberint: " + e.getMessage();
            return false;
        }
    }


  /*  public boolean saveLaberynth(String filename) {
        // Verificar parámetros de entrada
        if (filename == null || filename.isEmpty() || laberynth == null) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // 1. Guardar dimensiones del laberinto
            int rows = laberynth.getLaberynth().size();
            int cols = rows > 0 ? laberynth.getLaberynth().get(0).size() : 0;
            writer.write(rows + " " + cols + "\n");

            // 2. Guardar cada celda del laberinto
            for (int i = 0; i < rows; i++) {
                String rowData = "";
                for (int j = 0; j < cols; j++) {
                    Cell cell = laberynth.getLaberynth().get(i).get(j);
                    if (cell instanceof WellCell) {
                        rowData += "W ";
                    } else if (cell instanceof PowerUPCell) {
                        rowData += "P ";
                    } else if (cell instanceof NormallCell) {
                        NormallCell normalCell = (NormallCell) cell;
                        if (normalCell.getiType() == InhabitantType.WUMPUS) {
                            rowData += "W ";
                        } else if (normalCell.getiType() == InhabitantType.BAT) {
                            rowData += "B ";
                        } else {
                            rowData += "N ";
                        }
                    }
                }
                writer.write(rowData.trim() + "\n");
            }

            // 3. Guardar posición del jugador
          /*  if (ppos != null && ppos.length > 0 && ppos[0] != null) {
                writer.write(ppos[0][0] + " " + ppos[0][1] + "\n");
            } else {
                writer.write("-1 -1\n");
            }*/
    /*
            // 4. Guardar estado del juego
            writer.write(gameEnded + "\n");
            writer.write(won + "\n");

            // 5. Guardar mensajes y ecos
            writer.write((traverseMessage != null ? traverseMessage : "") + "\n");
            writer.write((echoes != null ? echoes : "") + "\n");

            return true;

        } catch (IOException e) {
            return false;
        }
    }*/

}



