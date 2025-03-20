package inscaparrella.model;

import inscaparrella.utils.CellType;

public class WellCell extends Cell{

    public WellCell(){
        super();
        this.cType = CellType.WELL;
    }
    public WellCell(int row, int col){
        super(row, col, CellType.WELL, false);
    }



    public WellCell(int row, int col, boolean open){
        super(col, row, CellType.WELL, open);
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
