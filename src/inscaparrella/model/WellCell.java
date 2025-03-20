package inscaparrella.model;

import inscaparrella.utils.CellType;

public class WellCell extends Cell{

    public WellCell(){
        super();
        this.cType = CellType.WELL;
    }

    public WellCell(int row, int col){
        this.row = row;
        this.col = col;
        this.cType = CellType.WELL;
        this.open = false;
    }


    public WellCell(WellCell wCell){
        this.row = wCell.row;
        this.col = wCell.col;
        this.cType = wCell.cType;
        this.open = wCell.open;
    }

    @Override
    public String emitEcho() {
        return "FFFFfffff...";
    }

    @Override
    public boolean isDangerus() {
        return true;
    }

    @Override
    public String toString(){
        return super.toString()+ "-Tipus WELL";
    }
}
