package inscaparrella.model;

import inscaparrella.utils.*;

import java.util.ArrayList;

public class WumpusLaberynth {

    private ArrayList<ArrayList<Cell>> laberynth;
    private int[] ppos;
    private int[] wumpuspos;
    private int[] batpos;


    public WumpusLaberynth() {
        laberynth = new ArrayList<>();
        ppos = null;
        wumpuspos = null;
        batpos = null;
    }

    public ArrayList<ArrayList<Cell>> getLaberynth() {
        return laberynth;
    }

    public void setLaberynth(ArrayList<ArrayList<Cell>> laberynth) {
        this.laberynth = laberynth;
    }

    public void createNewLaberynth() {

    }

    public int[] getInitialCell(){
        int[] result = null;

        return result;
    }

    public int[] movePlayer(MovementDirection dir) {
        int[] result = null;

        if (laberynth != null && ppos != null) {
            int newRow = ppos[0];
            int newCol = ppos[1];

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
                ppos[0] = newRow;
                ppos[1] = newCol;

                Cell cell = laberynth.get(newRow).get(newCol);
                cell.openCell();

                result = new int[]{newRow, newCol};
            }
        }

        return result;
    }

    public Danger getDanger(){
        return null;
    }

    public PowerUp getPowerUp(){
        return null;
    }
    public int[] batKidnapping(){
            return new int[0];
        }

    public boolean shootArrow(ShootDirection dir) {
        boolean hit = false;

        if (ppos != null) {
            int targetRow = ppos[0];
            int targetCol = ppos[1];

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
                if (wumpuspos[0] == targetRow && wumpuspos[1] == targetCol) {
                    hit = true;
                }
            }
        }

        return hit;
    }

    public boolean startleWumpus(){
        return true;
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
        String echoes = "";

        if (laberynth != null && ppos != null) {
            int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

            for (int i = 0; i < dx.length; i++) {
                int row = ppos[0] + dx[i];
                int col = ppos[1] + dy[i];

                if (checkCorrectCell(row, col)) {
                    echoes += laberynth.get(row).get(col).emitEcho();
                }
            }
        }

        return echoes;
    }

    public String currentCellMessage() {
        String message = "";

        if (laberynth != null && ppos != null) {
            message = laberynth.get(ppos[0]).get(ppos[1]).toString();
        }

        return message;
    }

    public String toString() {
        String result = "";

        for (int i = 0; i < laberynth.size(); i++) {
            for (int j = 0; j < laberynth.get(0).size(); j++) {
                Cell cell = laberynth.get(i).get(j);

                if (ppos != null && ppos[0] == i && ppos[1] == j) {
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