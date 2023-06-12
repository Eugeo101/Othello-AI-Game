package player;

import game.GamePlayer;

import java.awt.*;

public class HumanPlayer extends GamePlayer {
    
    private String serial;

    public HumanPlayer(int mark, String serial) {
        super(mark);
        this.serial = serial;
    }

    @Override
    public boolean isUserPlayer() {
        return true;
    }

    @Override
    public String playerName() {
        return "User_" + serial ;
    }

    @Override
    public Point play(int[][] board) {
        return null;
    }

}
