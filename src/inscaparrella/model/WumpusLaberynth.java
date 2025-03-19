package inscaparrella.model;

import inscaparrella.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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

    public void createNewLaberynth(){
        Random rnd = new Random();

        // Laberinto

        int row;
        int col;
        int totalCells;


        row = rnd.nextInt(11) * 5;
        col = rnd.nextInt(11) * 5;

        totalCells = row * col;

        laberynth = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            ArrayList<Cell> fila = new ArrayList<Cell>();
            for (int j = 0; j < col; j++) {
                fila.add(new Cell(CellType.NORMAL));
            }
            laberynth.add(fila);
        }

        // Pozos

        int wellsPlaced = 0;
        int minWells = 2;
        int maxWells = (int) (totalCells * 0.05);
        int wellsToPlace = rnd.nextInt(maxWells - minWells + 1) + minWells;
        int placed = 0;

        while (wellsPlaced < wellsToPlace) {
            int i = rnd.nextInt(row);
            int j = rnd.nextInt(col);

            Cell cell = laberynth.get(i).get(j);
            if (cell.getType() == CellType.NORMAL) {
                cell.setType(CellType.WELL);
                wellsPlaced++;
            }
        }


        // PowerUps

    }

    public int[] getInitialCell(){
        return new int[0];
    }

    public int[] movePlayer(MovementDirection dir){
        return new int[0];
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

    public boolean shootArrow(ShootDirection dir){
        boolean potDisparar = false;

        if (ppos != null){
            potDisparar = true;
        }

        return potDisparar;
    }

    public boolean startleWumpus(){
        return true;
    }

    public void moveBats(){

    }

    public String emitEchoes(){
        return emitEchoes();
    }

    public String currentCellMessage(){
        return currentCellMessage();
    }

    @Override
    public String toString() {
        return "WumpusLaberynth{" +
                "laberynth=" + laberynth +
                ", ppos=" + Arrays.toString(ppos) +
                ", wumpuspos=" + Arrays.toString(wumpuspos) +
                ", batpos=" + Arrays.toString(batpos) +
                '}';
    }

    private boolean checkCorrectCell(int row, int col){
        return true;
    }
}
