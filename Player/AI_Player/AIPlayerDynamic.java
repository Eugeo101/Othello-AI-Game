package player.ai;

import game.GamePlayer;

import java.awt.*;

public class AIPlayerDynamic extends GamePlayer {

    private int searchDepth;
    private Evaluator evaluator;
    private String serial;

    public AIPlayerDynamic(int mark, int depth, String serial) {
        super(mark);
        searchDepth = depth;
        evaluator = new DynamicEvaluator();
        this.serial = serial;
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "Dynamic AI_" + serial + " (Depth " + searchDepth + ")";
    }

    @Override
    public Point play(int[][] board) {
        return Minimax.solve(board,myMark,searchDepth,evaluator);
    }
}
