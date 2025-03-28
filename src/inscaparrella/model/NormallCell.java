package inscaparrella.model;

import inscaparrella.utils.CellType;
import inscaparrella.utils.InhabitantType;

public class NormallCell extends Cell{


    InhabitantType iType;

    public NormallCell(){
    super();
    this.iType = InhabitantType.NONE;

    }
    public NormallCell(int row, int  col){
        super(row, col);
        this.cType = CellType.NORMAL;
        this.iType = InhabitantType.NONE;
    }

    public NormallCell(NormallCell nCell){
       this.row= nCell.row;
       this.col = nCell.col;
       this.cType = nCell.cType;
       this.open = nCell.open;
       this.iType = nCell.iType;
    }

    public InhabitantType getiType() {
        return iType;
    }

    public void setiType(InhabitantType iType) {
        this.iType = iType;
    }

    @Override
    public String emitEcho() {
        String soroll="";
        if (iType.equals(InhabitantType.WUMPUS)){
            soroll = "gggrrr... gggGGGGGRRRRRrrr...";
        } else if (iType.equals(InhabitantType.BAT)) {
            soroll = "Flap, flap, flap";
        }else soroll = "";
     return soroll;
    }

    @Override
    public boolean isDangerus() {
       boolean habitada = false;
       if (!iType.equals(InhabitantType.NONE)){
           habitada = true;
       }else habitada = false;

       return habitada;
    }

    @Override
    public String toString(){
        String str ="";
        if (iType.equals(InhabitantType.BAT)){
            str = " - NORMAL (Habitada per un ratpenat)";
        } else if (iType.equals(InhabitantType.WUMPUS)) {
            str = " - NORMAL (Habitada pel Wumpus)";
        }else str = " - TIPUS NORMAL;";
        return super.toString() +str;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NormallCell that = (NormallCell) o;
        return getiType() == that.getiType();
    }


}
