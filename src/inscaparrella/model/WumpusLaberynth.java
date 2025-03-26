package inscaparrella.model;

import inscaparrella.utils.*;
import java.util.ArrayList;
import java.util.List;
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
        for (ArrayList<Cell> row : laberynth) {
            ArrayList<Cell> newRow = new ArrayList<>();
            for (Cell cell : row) {
                if (cell instanceof NormallCell) {
                    newRow.add(new NormallCell((NormallCell) cell));
                } else if (cell instanceof PowerUPCell) {
                    newRow.add(new PowerUPCell((PowerUPCell) cell));
                } else if (cell instanceof WellCell) {
                    newRow.add(new WellCell((WellCell) cell));
                } else {
                    newRow.add(null);
                }
            }
            copy.add(newRow);
        }
        return copy;
    }

    public void setLaberynth(ArrayList<ArrayList<Cell>> newlaberynth) {
        this.laberynth = new ArrayList<>();
        ArrayList<int[]> batposList = new ArrayList<>();

        for (int i = 0; i < newlaberynth.size(); i++) {
            ArrayList<Cell> newRow = new ArrayList<>();
            for (int j = 0; j < newlaberynth.get(i).size(); j++) {
                Cell cell = newlaberynth.get(i).get(j);

                if (cell instanceof NormallCell normallCell) {
                    newRow.add(new NormallCell(normallCell));
                    if (normallCell.getiType() == InhabitantType.WUMPUS) {
                        wumpuspos = new int[][]{{i, j}};
                    }
                    if (normallCell.getiType() == InhabitantType.BAT) {
                        batposList.add(new int[]{i, j});
                    }
                } else if (cell instanceof PowerUPCell) {
                    newRow.add(new PowerUPCell((PowerUPCell) cell));
                } else if (cell instanceof WellCell) {
                    newRow.add(new WellCell((WellCell) cell));
                } else {
                    newRow.add(null);
                }
            }
            this.laberynth.add(newRow);
        }

        if (!batposList.isEmpty()) {
            batspos = new int[batposList.size() * 2];
            for (int i = 0; i < batposList.size(); i++) {
                batspos[i * 2] = batposList.get(i)[0];
                batspos[i * 2 + 1] = batposList.get(i)[1];
            }
        }
    }

    private void placeWells(int quantity) {
        Random rn = new Random();
        int wellsPlaced = 0;
        int tries = 0;
        int maxTries = laberynth.size() * laberynth.get(0).size();

        while (wellsPlaced < quantity && tries < maxTries) {
            int row = rn.nextInt(laberynth.size());
            int col = rn.nextInt(laberynth.get(0).size());
            Cell c = laberynth.get(row).get(col);

            if (c.getcType() == CellType.NORMAL) {
                laberynth.get(row).set(col, new WellCell(row, col));
                wellsPlaced++;
            }
            tries++;
        }
    }

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

        int rows = rn.nextInt(11) + 5; // 5-15 filas
        int cols = rn.nextInt(11) + 5; // 5-15 columnas
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

        // Inicializar laberinto con celdas normales
        laberynth = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(new NormallCell(i, j));
            }
            laberynth.add(row);
        }

        placeWells(wellsToPlace);
        placePowerUps(powerUpToPlace);
        placeBats(batsToPlace);

        placeWumpus();

        ppos = null;
    }

    private void placeWumpus() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < laberynth.size(); row++) {
            for (int col = 0; col < laberynth.get(row).size(); col++) {
                Cell cell = laberynth.get(row).get(col);
                if (cell.getcType() == CellType.NORMAL &&
                        ((NormallCell)cell).getiType() == InhabitantType.NONE) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            int[] pos = emptyCells.get((int)(Math.random() * emptyCells.size()));
            NormallCell wumpusCell = (NormallCell) laberynth.get(pos[0]).get(pos[1]);
            wumpusCell.setiType(InhabitantType.WUMPUS);
            wumpuspos = new int[][]{{pos[0], pos[1]}};
        }
    }

    public int[] getInitialCell() {
        int[] result = null;

        if (laberynth != null && !laberynth.isEmpty()) {
            List<int[]> emptyCells = new ArrayList<>();
            for (int row = 0; row < laberynth.size(); row++) {
                for (int col = 0; col < laberynth.get(row).size(); col++) {
                    Cell cell = laberynth.get(row).get(col);
                    if (cell.getcType() == CellType.NORMAL &&
                            ((NormallCell)cell).getiType() == InhabitantType.NONE) {
                        emptyCells.add(new int[]{row, col});
                    }
                }
            }

            if (!emptyCells.isEmpty()) {
                result = emptyCells.get((int)(Math.random() * emptyCells.size()));
                ppos = new int[][]{{result[0], result[1]}};
                laberynth.get(result[0]).get(result[1]).openCell();
            }
        }

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
                ppos[0][1] = newCol;

                Cell cell = laberynth.get(newRow).get(newCol);
                cell.openCell();

                result = new int[]{newRow, newCol};
            }
        }

        return result;
    }

    public Danger getDanger() {
        Danger danger = Danger.NONE;

        if (laberynth != null && !laberynth.isEmpty() && ppos != null && ppos.length > 0) {
            int row = ppos[0][0];
            int col = ppos[0][1];
            Cell cell = laberynth.get(row).get(col);

            if (cell.getcType() == CellType.WELL) {
                danger = Danger.WELL;
            } else if (cell instanceof NormallCell) {
                InhabitantType inhabitant = ((NormallCell)cell).getiType();
                if (inhabitant == InhabitantType.WUMPUS) {
                    danger = Danger.WUMPUS;
                } else if (inhabitant == InhabitantType.BAT) {
                    danger = Danger.BAT;
                }
            }
        }

        return danger;
    }

    public PowerUp getPowerUp() {
        PowerUp powerUp = PowerUp.NONE;

        if (laberynth != null && !laberynth.isEmpty() && ppos != null && ppos.length > 0) {
            int row = ppos[0][0];
            int col = ppos[0][1];
            Cell cell = laberynth.get(row).get(col);

            if (cell instanceof PowerUPCell) {
                powerUp = ((PowerUPCell)cell).consumePowerUp();
            }
        }

        return powerUp;
    }

    public int[] batKidnapping() {
        int[] result = null;

        if (laberynth != null && !laberynth.isEmpty() && ppos != null && ppos.length > 0) {
            int currentRow = ppos[0][0];
            int currentCol = ppos[0][1];
            Cell currentCell = laberynth.get(currentRow).get(currentCol);

            if (currentCell instanceof NormallCell &&
                    ((NormallCell)currentCell).getiType() == InhabitantType.BAT) {

                List<int[]> validCells = new ArrayList<>();
                for (int row = 0; row < laberynth.size(); row++) {
                    for (int col = 0; col < laberynth.get(row).size(); col++) {
                        Cell cell = laberynth.get(row).get(col);
                        if (cell.getcType() == CellType.NORMAL &&
                                ((NormallCell)cell).getiType() == InhabitantType.NONE &&
                                !(row == currentRow && col == currentCol)) {
                            validCells.add(new int[]{row, col});
                        }
                    }
                }

                if (!validCells.isEmpty()) {
                    result = validCells.get((int)(Math.random() * validCells.size()));
                    ppos[0][0] = result[0];
                    ppos[0][1] = result[1];
                    laberynth.get(result[0]).get(result[1]).openCell();
                }
            }
        }

        return result;
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

        if (laberynth != null && !laberynth.isEmpty() && ppos != null && ppos.length > 0) {
            int randomValue = (int)(Math.random() * 100);

            if (randomValue % 2 == 0) {
                List<int[]> validCells = new ArrayList<>();
                for (int row = 0; row < laberynth.size(); row++) {
                    for (int col = 0; col < laberynth.get(row).size(); col++) {
                        Cell cell = laberynth.get(row).get(col);
                        if (cell.getcType() == CellType.NORMAL &&
                                ((NormallCell)cell).getiType() == InhabitantType.NONE &&
                                !(row == ppos[0][0] && col == ppos[0][1])) {
                            validCells.add(new int[]{row, col});
                        }
                    }
                }

                if (!validCells.isEmpty()) {
                    int[] newPos = validCells.get((int)(Math.random() * validCells.size()));

                    // Limpiar posición actual del Wumpus
                    if (wumpuspos != null && wumpuspos.length > 0) {
                        NormallCell oldCell = (NormallCell) laberynth.get(wumpuspos[0][0]).get(wumpuspos[0][1]);
                        oldCell.setiType(InhabitantType.NONE);
                    }

                    // Mover al Wumpus a nueva posición
                    NormallCell newCell = (NormallCell) laberynth.get(newPos[0]).get(newPos[1]);
                    newCell.setiType(InhabitantType.WUMPUS);
                    wumpuspos = new int[][]{{newPos[0], newPos[1]}};

                    result = true;
                }
            }
        }

        return result;
    }

    public void moveBats() {
        if (laberynth == null || laberynth.isEmpty() || ppos == null || ppos.length == 0) {
            return;
        }

        // Primero recolectamos todas las posiciones actuales de murciélagos
        List<int[]> currentBatPositions = new ArrayList<>();
        for (int row = 0; row < laberynth.size(); row++) {
            for (int col = 0; col < laberynth.get(row).size(); col++) {
                Cell cell = laberynth.get(row).get(col);
                if (cell instanceof NormallCell && ((NormallCell)cell).getiType() == InhabitantType.BAT) {
                    currentBatPositions.add(new int[]{row, col});
                }
            }
        }

        // Para cada murciélago, buscamos una nueva posición válida
        for (int[] batPos : currentBatPositions) {
            List<int[]> validCells = new ArrayList<>();

            for (int row = 0; row < laberynth.size(); row++) {
                for (int col = 0; col < laberynth.get(row).size(); col++) {
                    Cell cell = laberynth.get(row).get(col);
                    if (cell.getcType() == CellType.NORMAL &&
                            ((NormallCell)cell).getiType() == InhabitantType.NONE &&
                            !(row == ppos[0][0] && col == ppos[0][1]) &&
                            !(row == batPos[0] && col == batPos[1])) {
                        validCells.add(new int[]{row, col});
                    }
                }
            }

            if (!validCells.isEmpty()) {
                int[] newPos = validCells.get((int)(Math.random() * validCells.size()));

                // Limpiar posición actual del murciélago
                NormallCell oldCell = (NormallCell) laberynth.get(batPos[0]).get(batPos[1]);
                oldCell.setiType(InhabitantType.NONE);

                // Mover a nueva posición
                NormallCell newCell = (NormallCell) laberynth.get(newPos[0]).get(newPos[1]);
                newCell.setiType(InhabitantType.BAT);

                // Actualizar posición en batspos si existe
                if (batspos != null) {
                    for (int i = 0; i < batspos.length; i += 2) {
                        if (batspos[i] == batPos[0] && batspos[i+1] == batPos[1]) {
                            batspos[i] = newPos[0];
                            batspos[i+1] = newPos[1];
                            break;
                        }
                    }
                }
            }
        }
    }

    public String emitEchoes() {
        StringBuilder echoes = new StringBuilder();

        if (laberynth != null && !laberynth.isEmpty() && ppos != null && ppos.length > 0) {
            int centerRow = ppos[0][0];
            int centerCol = ppos[0][1];

            for (int row = centerRow - 1; row <= centerRow + 1; row++) {
                for (int col = centerCol - 1; col <= centerCol + 1; col++) {
                    if (row == centerRow && col == centerCol) continue;

                    if (checkCorrectCell(row, col)) {
                        String echo = laberynth.get(row).get(col).emitEcho();
                        if (echo != null && !echo.isEmpty()) {
                            if (!echoes.isEmpty()) {
                                echoes.append(" ");
                            }
                            echoes.append(echo);
                        }
                    }
                }
            }
        }

        return echoes.toString();
    }

    public String currentCellMessage() {
        if (laberynth != null && !laberynth.isEmpty() && ppos != null && ppos.length > 0) {
            return laberynth.get(ppos[0][0]).get(ppos[0][1]).toString();
        }
        return "";
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (laberynth != null && !laberynth.isEmpty()) {
            for (int i = 0; i < laberynth.size(); i++) {
                for (int j = 0; j < laberynth.get(i).size(); j++) {
                    Cell cell = laberynth.get(i).get(j);

                    if (ppos != null && ppos.length > 0 && ppos[0][0] == i && ppos[0][1] == j) {
                        sb.append("P");
                    } else if (!cell.isOpen()) {
                        sb.append("#");
                    } else if (cell.getcType() == CellType.WELL) {
                        sb.append("O");
                    } else if (cell instanceof NormallCell && ((NormallCell)cell).getiType() == InhabitantType.WUMPUS) {
                        sb.append("W");
                    } else if (cell instanceof NormallCell && ((NormallCell)cell).getiType() == InhabitantType.BAT) {
                        sb.append("*");
                    } else {
                        sb.append(" ");
                    }
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private boolean checkCorrectCell(int row, int col) {
        return row >= 0 && col >= 0 && row < laberynth.size() && col < laberynth.get(0).size();
    }
}