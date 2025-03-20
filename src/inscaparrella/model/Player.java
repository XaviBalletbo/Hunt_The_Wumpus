package inscaparrella.model;

import inscaparrella.utils.PowerUp;

import java.util.Arrays;

public class Player {

    int row;
    int col;
    int[] powers = new int[2]; //posi 0 = ARROW, posi 1 = JUMPER_BOOTS

    public Player() {
        this.row = -1;
        this.col = -1;
        this.powers = new int[2];
        this.powers[0] = 2;
        this.powers[1] = 0;
    }

    public Player(int row, int col) {
        this.row = row;
        this.col = col;
        this.powers = new int[2];
        this.powers[0] = 2;
        this.powers[1] = 0;
    }

    public void setStartingCell(int row, int col) {
        if (this.row == -1 && this.col == -1) {
            this.row = row;
            this.col = col;
        }
    }

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getPowerUpQuantity(PowerUp power) {
        int quantity = 0;

        if (power == PowerUp.ARROW)
            quantity = powers[0];
        else if (power == PowerUp.JUMPER_BOOTS)
            quantity = powers[1];

        return quantity;
    }

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

    public void addPower(PowerUp power) {
        if (power == PowerUp.ARROW) {
            powers[0] = powers[0] +1;
        }
        else if (power == PowerUp.JUMPER_BOOTS) {
            powers[1] = powers[1] +1;
        }
    }

    @Override
    public String toString() {
        return "El Player es troba a la posició :" + row + ", " + col + " i te " + powers[0] + " unitats de poder ARROW i " + powers[1] + " unitats de poder JUMPER_BOOTS";
    }
}
