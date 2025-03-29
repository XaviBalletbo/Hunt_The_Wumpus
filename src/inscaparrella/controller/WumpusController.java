package inscaparrella.controller;

import inscaparrella.model.WumpusLaberynth;
import inscaparrella.model.Player;
import inscaparrella.model.*;
import inscaparrella.utils.*;


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

        boolean file = false;

        if (filename == null || filename.isEmpty()) {
            file = false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // 1. Leer dimensiones del laberinto
            String line = reader.readLine();
            if (line == null) file = false;

            String[] dimensions = line.split(" ");
            if (dimensions.length != 2) file = false;

            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            // 2. Crear nueva instancia de WumpusLaberynth
            WumpusLaberynth newLaberynth = new WumpusLaberynth();
            ArrayList<ArrayList<Cell>> labyrinthCells = new ArrayList<>();

            // 3. Leer cada fila del laberinto
            for (int i = 0; i < rows; i++) {
                line = reader.readLine();
                if (line == null) file = false;

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
                            file = false;
                    }
                }
                labyrinthCells.add(rowCells);
            }

            // 4. Configurar el laberinto usando el setter
            newLaberynth.setLaberynth(labyrinthCells);

            // 5. Leer posición del Wumpus
            line = reader.readLine();
            if (line == null) file = false;
            String[] wumpusPosition = line.split(" ");
            if (wumpusPosition.length != 2) file = false;

            int wumpusRow = Integer.parseInt(wumpusPosition[0]);
            int wumpusCol = Integer.parseInt(wumpusPosition[1]);

            // 6. Leer posiciones de los ratpenats
            line = reader.readLine();
            if (line == null) file = false;
            String[] batsPositions = line.split(" ");

            // 7. Actualizar el estado del controlador
            this.laberynth = newLaberynth;
            this.player = new Player();
            this.traverseMessage = "El laberint s'ha carregat correctament";
            this.echoes = "";
            this.gameEnded = false;
            this.won = false;

            file = true;

        } catch (IOException | NumberFormatException e) {
            this.traverseMessage = "Error al carregar el laberint: " + e.getMessage();
            file = false;
        }

        return file;
    }


    public boolean saveLaberynth(String filename) {
        boolean file =false;
        if (filename == null || filename.isEmpty() || laberynth == null) {
            file = false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // 1. Escribir dimensiones del laberinto
            ArrayList<ArrayList<Cell>> cells = laberynth.getLaberynth();
            int rows = cells.size();
            int cols = rows > 0 ? cells.get(0).size() : 0;
            writer.write(rows + " " + cols);
            writer.newLine();

            // 2. Escribir cada celda del laberinto
            int wumpusRow = -1, wumpusCol = -1;
            String batsLine = "";

            for (int i = 0; i < rows; i++) {
                String line = "";
                for (int j = 0; j < cols; j++) {
                    Cell cell = cells.get(i).get(j);
                    if (cell instanceof NormallCell) {
                        NormallCell normalCell = (NormallCell) cell;
                        if (normalCell.getiType() == InhabitantType.WUMPUS) {
                            line += "W ";
                            wumpusRow = i;
                            wumpusCol = j;
                        } else if (normalCell.getiType() == InhabitantType.BAT) {
                            line += "N "; // Los murciélagos no cambian el tipo de celda
                            batsLine += (i * cols + j) + " ";
                        } else {
                            line += "N ";
                        }
                    } else if (cell instanceof PowerUPCell) {
                        line += "P ";
                    } else if (cell instanceof WellCell) {
                        line += "W ";
                    }
                }
                writer.write(line.trim());
                writer.newLine();
            }

            // 3. Escribir posición del Wumpus (detectada durante el escaneo)
            writer.write(wumpusRow + " " + wumpusCol);
            writer.newLine();

            // 4. Escribir posiciones de los murciélagos (en formato lineal)
            writer.write(batsLine.trim());
            writer.newLine();

            file = true;

        } catch (IOException e) {
            file = false;
        }
        return file;
    }

    public boolean startGame() {
        boolean rtrn;
        int[] startCell = laberynth.getInitialCell();

        if (startCell == null)
            rtrn = false;

        gameEnded = false;
        won = false;

        player.setStartingCell(startCell[0], startCell[1]);

        rtrn = true;

        return rtrn;
    }

    public void movePlayer(MovementDirection movementDirection) {
        if (gameEnded != true){
            laberynth.movePlayer(movementDirection);
        }
    }

    public void huntTheWumpus(ShootDirection shootDirection) {
        if (gameEnded != true){
            laberynth.shootArrow(shootDirection);
        }
    }

    public String getLastTraverseMessage(){
        return traverseMessage;
    }

    public String getLastEchoes(){
        return echoes;
    }

    public boolean isGameEnded(){
        return gameEnded;
    }

    public boolean isGameWon(){
        return won;
    }

    public String toString(){
        String resultat = "";
        if (laberynth != null){
            resultat += laberynth.toString();
        }else resultat += "Laberint no carregat \n";

        if (player != null){
            resultat += "\n" +player.toString();
        }else resultat += "\n Jugado no inicialitzat";

        resultat += "\n\n Estat del joc: ";

        if (gameEnded){
            if (won == true){
                resultat += "GUANYAT";
            }else resultat += "PERDUT";
        }else resultat += "EN CURS";

        if (traverseMessage != null && !traverseMessage.isEmpty()){
            resultat += "\n\n Últim missatge: " +getLastTraverseMessage();
        }
        if (echoes != null && !echoes.isEmpty()){
            resultat += "\n Ecos: " +echoes;
        }
        return resultat;
    }

    private void traverseCell(){

        String msg = "";

        if (laberynth!= null && player != null && !gameEnded){
            String playerPosStr = player.toString();
            String  [] parts = playerPosStr.split("posició: " )[1].split(" ");
            int row = Integer.parseInt(parts[0].trim());
            int col = Integer.parseInt(parts[1].trim());

            //obtenir cela actual

            Cell currentCell = laberynth.getLaberynth().get(row).get(col);
            currentCell.openCell();

            //comprobar perills

            if (currentCell.isDangerus()){
                if (currentCell.getcType()== CellType.NORMAL){
                    NormallCell normallCell = (NormallCell) currentCell;
                        if (normallCell.getiType()== InhabitantType.WUMPUS){
                            gameEnded = true;
                            msg = currentCell.toString() + "El wumpus ha atacat i malferit al jugador!";
                        }
                 //celes de tipus Well
                else if (currentCell.getcType() == CellType.WELL) {
                    if (player.getPowerUpQuantity(PowerUp.JUMPER_BOOTS)> 0 && player.usePower(PowerUp.JUMPER_BOOTS)){
                        msg = currentCell.toString() + "El jugador ha estat a punt de caure en un pou, pero, per sort, portaba les Jumpper.BOOTS";
                        gameEnded = false;
                    }else {
                    gameEnded = true;
                    msg = currentCell.toString() + "El jugador ha caigut en un pou i ha quedat malferit!";
                    }
                }
                //celes normals amb algun event
                } else if (currentCell.getcType()== CellType.NORMAL) {
                    NormallCell normallCell = (NormallCell) currentCell;
                        if (normallCell.getiType() == InhabitantType.BAT){
                            msg = currentCell.toString() + "Un ratpenat s'enduu el jugador!";
                            laberynth.batKidnapping();

                            traverseCell();
                        }
                   //celes de poder
                } else if (currentCell.getcType() == CellType.POWERUP) {
                    PowerUPCell powerUPCell = (PowerUPCell) currentCell;
                    PowerUp power = powerUPCell.consumePowerUp();
                    if (power != PowerUp.NONE){
                        player.addPower(power);
                        msg = currentCell.toString() + "El jugador ha trobat una unitat del poder " +power.name();
                    }
                }

                //celes normals sense cap tipus d'events
                else {
                    msg = currentCell.toString();
                }
            }
        }
        traverseMessage = msg;
    }



}
