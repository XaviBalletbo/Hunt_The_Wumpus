package inscaparrella.model;

import inscaparrella.utils.*;

import java.util.ArrayList;
import java.util.Random;

public class WumpusLaberynth {

    private ArrayList<ArrayList<Cell>> laberynth;
    private int[][] ppos;
    private int[][] wumpuspos;
    private int[] batspos;


    public WumpusLaberynth() {
        laberynth = new ArrayList<>();
        ppos = null;
        wumpuspos = null;
        batspos = null;
    }

    public ArrayList<ArrayList<Cell>> getLaberynth() {
        ArrayList<ArrayList<Cell>> copy = new ArrayList<>();
        for (ArrayList<Cell> row : laberynth){
            ArrayList<Cell > newRow = new ArrayList<>();
            for (Cell cell : row){
                if (cell instanceof NormallCell){
                    newRow.add(new NormallCell((NormallCell) cell));
                } else if (cell instanceof PowerUPCell) {
                    newRow.add(new PowerUPCell((PowerUPCell) cell));
                }else if(cell instanceof WellCell){
                    newRow.add(new WellCell ((WellCell) cell));
                }else newRow.add(null);
            }
            copy.add(newRow);
        }
        return copy;
    }

    public void setLaberynth(ArrayList<ArrayList<Cell>> newlaberynth) {
        this.laberynth = new ArrayList<>();
        ArrayList<int[]> batpos = new ArrayList<>();
        for (int i = 0; i < newlaberynth.size(); i++) {
            ArrayList<Cell> newRow = new ArrayList<>();
            for (int j = 0; j <newlaberynth.get(i).size(); j++) {
                Cell cell = newlaberynth.get(i).get(j);

                if (cell instanceof NormallCell normallCell){
                    newRow.add(new NormallCell(normallCell));
                    if (normallCell.getiType()== InhabitantType.WUMPUS){
                        wumpuspos = new int[i][j];
                    }
                    if (normallCell.getiType() == InhabitantType.BAT){
                        batpos.add(new int[]{i,j});
                    }
                }else if(cell instanceof PowerUPCell){
                    newRow.add(new PowerUPCell((PowerUPCell) cell));
                }else if(cell instanceof WellCell){
                    newRow.add(new WellCell((WellCell) cell));
                }else newRow.add(null);
            }
            this.laberynth.add(newRow);
        }
    }

    // Metodes auxiliars de createNewLaberynth

    // Metode per colocar Wells

    private void placeWells(int quanitity){
        Random rn = new Random();
        int wellsPlaced = 0;
        int tries = 0;
        int maxTries = laberynth.size() * laberynth.get(0).size();

        while (wellsPlaced < quanitity && tries < maxTries){
            int row = rn.nextInt(laberynth.size());
            int col = rn.nextInt(laberynth.size());
            Cell c = laberynth.get(row).get(col);

            if (c.getcType() == CellType.NORMAL){
                laberynth.get(row).set(col, new WellCell(row, col));
                wellsPlaced++;
            }

            tries++;
        }
    }

    // Metode per colocar PowerUps

    private void placePowerUps(int quantity) {
        Random rn = new Random();
        int powerUpsPlaced = 0;
        int tries = 0;
        int maxTries = laberynth.size() * laberynth.get(0).size();

        while (powerUpsPlaced < quantity && tries < maxTries) {
            int row = rn.nextInt(laberynth.size());
            int col = rn.nextInt(laberynth.get(0).size());
            Cell c = laberynth.get(row).get(col);

            if (c.getcType() == CellType.NORMAL) {
                laberynth.get(row).set(col, new PowerUPCell(row, col));
                powerUpsPlaced++;
            }
            tries++;
        }
    }

    // Metode per colocar Bats

    private void placeBats(int quantity) {
        Random rn = new Random();
        int batsPlaced = 0;
        int tries = 0;
        int maxTries = laberynth.size() * laberynth.get(0).size();

        batspos = new int[quantity * 2];

        while (batsPlaced < quantity && tries < maxTries) {
            int row = rn.nextInt(laberynth.size());
            int col = rn.nextInt(laberynth.get(0).size());
            Cell c = laberynth.get(row).get(col);

            if (c.getcType() == CellType.NORMAL) {
                NormallCell normallCell = (NormallCell) c;

                if (normallCell.getiType() == InhabitantType.NONE) {
                    normallCell.setiType(InhabitantType.BAT);
                    batspos[batsPlaced * 2] = row;
                    batspos[batsPlaced * 2 + 1] = col;
                    batsPlaced++;
                }
            }
            tries++;
        }
    }

    public void createNewLaberynth() {
        Random rn = new Random();

        int rows = rn.nextInt(11) + 5;
        int cols = rn.nextInt(11) + 5;
        int totalCells = rows * cols;

        int wellsMin = 2;
        int wellsMax = Math.max(wellsMin, (int)(totalCells * 0.05));
        int wellsToPlace = rn.nextInt(wellsMax - wellsMin + 1) + wellsMin;

        int powerUpMin = 2;
        int powerUpMax = Math.max(powerUpMin, (int)(totalCells * 0.10));
        int powerUpToPlace = rn.nextInt(powerUpMax - powerUpMin + 1) + powerUpMin;

        int batsMin = 2;
        int batsMax = Math.max(batsMin, (int)(totalCells * 0.10));
        int batsToPlace = rn.nextInt(batsMax - batsMin + 1) + batsMin;

        laberynth = new ArrayList<>();

        ArrayList<Cell> row = null;
        for (int i = 0; i < rows; i++) {
            row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(new NormallCell(i, j));
            }
        }
        laberynth.add(row);

        placeWells(wellsToPlace);
        placePowerUps(powerUpToPlace);
        placeBats(batsToPlace);

        ppos = null;
    }

    public int[] getInitialCell(){
        int[] result = null;

        return result;
    }

    public int[] movePlayer(MovementDirection dir) {
        int[] result = null;

        if (laberynth != null && ppos != null) {
            int newRow = ppos[0][0];
            int newCol = ppos[0][1];

            if (dir == MovementDirection.UP) {
                newRow--;
            } else if (dir == MovementDirection.DOWN) {
                newRow++;
            } else if (dir == MovementDirection.LEFT) {
                newCol--;
            } else if (dir == MovementDirection.RIGHT) {
                newCol++;
            }

            if (checkCorrectCell(newRow, newCol)) {
                ppos[0][0] = newRow;
                ppos[1][1] = newCol;

                Cell cell = laberynth.get(newRow).get(newCol);
                cell.openCell();

                result = new int[]{newRow, newCol};
            }
        }

        return result;
    }

    public Danger getDanger(){
        Danger danger = Danger.NONE;

        if (laberynth == null || laberynth.isEmpty() || ppos == null || ppos.length == 0) {
            danger = Danger.NONE;
        }

        int row = ppos[0][0];
        int col = ppos[0][1];

        Cell c = laberynth.get(row).get(col);

        if (c.getcType() == CellType.WELL) {
            danger = Danger.WELL;
        } else if (c instanceof NormallCell) {
            NormallCell cell = (NormallCell) c;
            if (cell.getiType() == InhabitantType.WUMPUS) {
                danger = Danger.WUMPUS;
            } else if (cell.getiType() == InhabitantType.BAT) {
                danger = Danger.BAT;
            }
        }

        return danger;
    }

    public PowerUp getPowerUp() {
        PowerUp powerUp = PowerUp.NONE;

        if (laberynth != null && !laberynth.isEmpty() && ppos != null && ppos.length > 0) {
            int row = ppos[0][0];
            int col = ppos[0][1];

            Cell c = laberynth.get(row).get(col);

            if (c instanceof PowerUPCell) {
                powerUp = ((PowerUPCell) c).consumePowerUp();
            }
        }

        return powerUp;
    }

    public int[] batKidnapping(){
        return new int[0];
    }

    public boolean shootArrow(ShootDirection dir) {
        boolean hit = false;

        if (ppos != null) {
            int targetRow = ppos[0][0];
            int targetCol = ppos[0][1];

            if (dir == ShootDirection.UP) {
                targetRow--;
            } else if (dir == ShootDirection.DOWN) {
                targetRow++;
            } else if (dir == ShootDirection.LEFT) {
                targetCol--;
            } else if (dir == ShootDirection.RIGHT) {
                targetCol++;
            }

            if (checkCorrectCell(targetRow, targetCol)) {
                if (wumpuspos[0][0] == targetRow && wumpuspos[0][1] == targetCol) {
                    hit = true;
                }
            }
        }

        return hit;
    }

    public boolean startleWumpus() {
        boolean result = false;

        if (laberynth != null && !laberynth.isEmpty() && ppos != null) {
            int randomValue = (int)(Math.random() * 100);

            if (randomValue % 2 == 0) {
                ArrayList<Object> validCells = new ArrayList<>();

                for (int row = 0; row < laberynth.size(); row++) {
                    for (int col = 0; col < laberynth.get(row).size(); col++) {
                        Cell cell = laberynth.get(row).get(col);

                        boolean isNormal = cell.getcType() == CellType.NORMAL;
                        boolean isEmpty = cell instanceof NormallCell &&
                                ((NormallCell)cell).getiType() == InhabitantType.NONE;
                        boolean notPlayerPos = !(row == ppos[0][0] && col == ppos[0][1]);

                        if (isNormal && isEmpty && notPlayerPos) {
                            validCells.add(new int[]{row, col});
                        }
                    }
                }

                if (!validCells.isEmpty()) {
                    int[] newPos = (int[]) validCells.get((int)(Math.random() * validCells.size()));

                    if (wumpuspos != null && wumpuspos.length > 0) {
                        NormallCell oldCell = (NormallCell)laberynth.get(wumpuspos[0][0]).get(wumpuspos[0][1]);
                        oldCell.setiType(InhabitantType.NONE);
                    }

                    NormallCell newCell = (NormallCell)laberynth.get(newPos[0]).get(newPos[1]);
                    newCell.setiType(InhabitantType.WUMPUS);
                    wumpuspos = new int[][]{newPos};

                    result = true;
                }
            }
        }

        return result;
    }

    public void moveBats(){

    }

    private boolean checkCorrectCell(int row, int col) {
        boolean correct = false;

        if (row >= 0 && col >= 0 &&
                row < laberynth.size() && col < laberynth.get(0).size()) {
            correct = true;
        }

        return correct;
    }

    public String emitEchoes() {
        if (laberynth == null || laberynth.isEmpty() || ppos == null || ppos.length == 0) {
            return "";
        }

        String result = "";
        int centerRow = ppos[0][0];
        int centerCol = ppos[0][1];

        if (!checkCorrectCell(centerRow, centerCol)) {
            return "";
        }

        for (int row = centerRow - 1; row <= centerRow + 1; row++) {
            for (int col = centerCol - 1; col <= centerCol + 1; col++) {
                if (row == centerRow && col == centerCol) {
                    continue;
                }

                if (checkCorrectCell(row, col)) {
                    Cell neighbor = laberynth.get(row).get(col);
                    String echo = neighbor.emitEcho();

                    if (echo != null && !echo.isEmpty()) {
                        if (!result.isEmpty()) {
                            result += " ";
                        }
                        result += echo;
                    }
                }
            }
        }

        return result;
    }
    public String currentCellMessage() {
        String message = "";

        if (laberynth != null && ppos != null) {
            message = laberynth.get(ppos[0][0]).get(ppos[1][1]).toString();
        }

        return message;
    }

    public String toString() {
        String result = "";

        for (int i = 0; i < laberynth.size(); i++) {
            for (int j = 0; j < laberynth.get(0).size(); j++) {
                Cell cell = laberynth.get(i).get(j);

                if (ppos != null && ppos[0][0] == i && ppos[1][1] == j) {
                    result += "P";
                } else if (!cell.isOpen()) {
                    result += "#";
                } else if (cell.getcType() == CellType.WELL) {
                    result += "O";
                } else {
                    result += " ";
                }
            }

            result += "\n";
        }

        return result;
    }
}