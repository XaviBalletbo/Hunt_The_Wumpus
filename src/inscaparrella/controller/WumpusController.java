package inscaparrella.controller;

import inscaparrella.model.Player;
import inscaparrella.model.WumpusLaberynth;

public class WumpusController {

    WumpusLaberynth laberynth;
    Player player;
    String traverseMessage;
    String echoes;
    boolean gameEnded;
    boolean won;

    public WumpusController() {
        this.laberynth = new WumpusLaberynth();
        this.player = new Player();
        this.traverseMessage = "";
        this.echoes = "";
        this.gameEnded = false;
        this.won = false;
    }

    public boolean loadLabyrinth() {
        return false;
    }



}
