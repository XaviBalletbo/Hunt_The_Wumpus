package inscaparrella.model;

import inscaparrella.utils.CellType;
import inscaparrella.utils.InhabitantType;
import inscaparrella.utils.PowerUp;

public class PowerUPCell extends Cell{

   PowerUp power;

    public PowerUPCell(){
        super();
        this.power = PowerUp.NONE;
    }
    public PowerUPCell(int row, int col){
        super(row, col, CellType.POWERUP, false);
        this.power = PowerUp.values((int) Math.random(*3));
    }

    public PowerUPCell(int row, int col, boolean open, PowerUp power){
        super(row, col,CellType.POWERUP, false);

    }


    @Override
    public String emitEcho() {
        return "";
    }

    @Override
    public boolean isDangerus() {
        return false;
    }
}
