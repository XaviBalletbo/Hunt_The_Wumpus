package inscaparrella.model;

import inscaparrella.utils.PowerUp;

import java.util.Arrays;

public class Player {

    int row;
    int col;
    int[] powers = new int[2]; //posi 0 = ARROW, posi 1 = JUMPER_BOOTS

    //constructor per defecte
    public Player() {
        this.row = -1;
        this.col = -1;
        this.powers = new int[2];
        this.powers[0] = 2;
        this.powers[1] = 0;
    }

    //constructor parametritzat
    public Player(int row, int col) {
        this.row = row;
        this.col = col;
        this.powers = new int[2];
        this.powers[0] = 2;
        this.powers[1] = 0;
    }

    //funcio per posar el jugador a la starting cell
    public void setStartingCell(int row, int col) {
        if (this.row == -1 && this.col == -1) {
            this.row = row;
            this.col = col;
        }
    }

    //funció per moure el jugador pasant fila y columna
    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //getter de la quantitat del power indicat(ARROW o JUMPER_BOOTS).
    public int getPowerUpQuantity(PowerUp power) {
        int quantity = 0;

        if (power == PowerUp.ARROW)
            quantity = powers[0];
        else if (power == PowerUp.JUMPER_BOOTS)
            quantity = powers[1];

        return quantity;
    }

    //funcio per utilitzar un Power retorna un boolea i resta un quantity al power
    public boolean usePower(PowerUp power) {
        boolean used = false;
        if (power == PowerUp.ARROW && powers[0] > 0) {
            powers[0] = powers[0] -1;
            used = true;
        }
        else if (power == PowerUp.JUMPER_BOOTS && powers[1] > 0) {
            powers[1] = powers[1] -1;
            used = true;
        }
        return used;
    }

    //funcio per añadir un power up comproba quin dels dos powerUos es i incrementa en 1 la seva quantity
    public void addPower(PowerUp power) {
        if (power == PowerUp.ARROW) {
            powers[0] = powers[0] +1;
        }
        else if (power == PowerUp.JUMPER_BOOTS) {
            powers[1] = powers[1] +1;
        }
    }

    //funió ToString
    @Override
    public String toString() {
        return "El Player es troba a la posició :" + row + ", " + col + " i te " + powers[0] + " unitats de poder ARROW i " + powers[1] + " unitats de poder JUMPER_BOOTS";
    }
}
