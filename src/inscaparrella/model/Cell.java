package inscaparrella.model;

import inscaparrella.utils.CellType;

import java.util.Objects;

public abstract class Cell {

    int row;
    int col;
    protected CellType cType;
    protected boolean open;


    //Constructor per defecte
    public Cell(){
        this(-1,-1);
        this.cType = CellType.NONE;
        this.open = false;
    }

    //Constructor parametritzat
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cType = CellType.NONE;
        this.open = false;
    }

    //Constructor Coopia
    public Cell(int col, int row, CellType cType, boolean open){
        this.row = row;
        this.col = col;
        this.cType = cType;
        this.open = open;
    }


    //Getters CellType i Open
    public CellType getCellType() {
        return cType;
    }

    public boolean isOpen() {
        return open;
    }

    public void openCell(){
        open = true;
    }

    public abstract String emitEcho(){
        return "";
    }

    public abstract boolean isDangerus(){
        boolean isDangerous = false;
        return isDangerous;
    }


    @Override
    public String toString() {
        return (" Cel·la ["+row+ +col+"]");
    }

    @Override
    public boolean equals(Object o) {
       if(this == o) return true;
       else return false;
    }


}
