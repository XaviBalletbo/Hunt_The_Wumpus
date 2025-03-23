package inscaparrella.model;

import inscaparrella.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WumpusLaberynth {

    private ArrayList<ArrayList<Cell>> laberynth;
    private int[] ppos;                 //player position
    private int[] wumpuspos;            //wumpus position
    private int[] batpos;               //bat position


    public WumpusLaberynth() {
        laberynth = new ArrayList<>();
        ppos = null;
        wumpuspos = null;
        batpos = null;
    }


    //getere amb el deep copy
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
                    newRow.add(new NormallCell(normallCell);
                        if (normallCell.getiType()== InhabitantType.WUMPUS){
                                wumpuspos = new int[]{i,j};
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
                fila.add(new cell.(CellType.NORMAL));

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
            if (cell.getcType() == CellType.NORMAL) {
                cell.setcType();
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
