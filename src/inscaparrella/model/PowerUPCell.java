package inscaparrella.model;

import inscaparrella.utils.CellType;
import inscaparrella.utils.PowerUp;

public class PowerUPCell extends Cell{

   PowerUp power;

    public PowerUPCell(){
        super();
        this.power = PowerUp.NONE;
    }
    public PowerUPCell(int row, int col){
        this.row = row;
        this.col = col;
        this.cType = CellType.POWERUP;
        this.open = false;
        this.power = PowerUp.values()[(int) Math.random()*3];
    }

    public PowerUPCell(PowerUPCell pwpCell){
        this.row = pwpCell.row;
        this.col = pwpCell.col;
        this.cType = pwpCell.cType;
        this.power = pwpCell.power;
    }

    private void createPowerUPCell(){
        power = PowerUp.values()[(int) Math.random()*3];
    }

    public PowerUp consumePowerUp(){
        PowerUp power1;
        if(isOpen()==true){
            power1 = power;
            power = PowerUp.NONE;
        }else{
            power1 = PowerUp.NONE;
        }
        return power1;
    }


    @Override
    public String emitEcho() {
        String str = "";
        if (power == PowerUp.NONE ){
            str = "";
        }else str= "Clic, clic...";

        return str;
    }

    @Override
    public boolean isDangerus() {
        return false;
    }

    @Override
    public String toString(){
        String str= "";
        if (power == PowerUp.ARROW){
            str= " - Tipus POWERUP (Concedeix el poder de ARROW)";
        }else if (power == PowerUp.JUMPER_BOOTS){
            str = " - Tipus POWERUP (Concedeix el poder JUMPER BOOTS)";
        }
        else str = " - Tipus POWERUP";
        return super.toString() +str;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        else return false;
    }
}
