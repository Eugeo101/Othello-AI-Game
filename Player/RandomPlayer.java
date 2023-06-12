package player;

import game.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends GamePlayer {
    
    private String serial;

    Random rnd = new Random();

    public RandomPlayer(int mark, String serial) {
        super(mark);
        this.serial = serial;
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "Random Player_" + serial;
    }

    @Override
    public Point play(int[][] board) {
        ArrayList<Point> myPossibleMoves = BoardHelper.getAllPossibleMoves(board,myMark);

        if(myPossibleMoves.size() > 0){
            return myPossibleMoves.get(rnd.nextInt(myPossibleMoves.size()));
        }else{
            return null;
        }

    }

}
