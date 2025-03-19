package inscaparrella.model;

import inscaparrella.utils.PowerUp;

public class PowerUPCell extends Cell{

   PowerUp power;

    public PowerUPCell(){
        super();
        this.power = PowerUp.NONE;
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
