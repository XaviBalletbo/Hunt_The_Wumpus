package inscaparrella.model;

import inscaparrella.utils.CellType;

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
    public Cell(Cell cell){
        this.row = cell.row;
        this.col = cell.col;
        this.cType = cell.cType;
        this.open = cell.open;
    }


    //Getters CellType i Open


    public CellType getcType() {
        return cType;
    }

    public void setcType() {
        this.cType = cType;
    }

    public boolean isOpen() {
        return open;
    }

    public void openCell(){
        open = true;
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

    public abstract String emitEcho();

    public abstract boolean isDangerus();
}
