package game;

import ai_gui.LUI;
import static game.BoardHelper.getPlayerStoneCount;
import player.*;
import player.ai.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel implements GameEngine {

    //reversi board
    int[][] board;

    //player turn
    //black plays first
    int turn = 1;

    //swing elements
    BoardCell[][] cells;
    JLabel score1;
    JLabel score2;

    int totalscore1 = 0;
    int totalscore2 = 0;

    JLabel tscore1;
    JLabel tscore2;


    GamePlayer player1;
    GamePlayer player2;

    Timer player1HandlerTimer;
    Timer player2HandlerTimer;

    @Override
    public int getBoardValue(int i,int j){
        return board[i][j];
    }

    @Override
    public void setBoardValue(int i,int j,int value){
        board[i][j] = value;
    }
    int flag;
    public GamePanel(int flag){
        this.flag=flag;
        if(flag==0){ // human vs human
            player1=new HumanPlayer(1, "1");
            player2=new HumanPlayer(2, "2");
        } else if (flag==1) { // human vs easy
            player1=new HumanPlayer(1, "1");
            player2=new RandomPlayer(2, "2");
        } else if (flag==2) { // human vs medium
            player1=new HumanPlayer(1, "1");
            player2=new AIPlayerDynamic(2,1, "2");
        } else if (flag==3) { // human vs hard
            player1=new HumanPlayer(1, "1");
            player2=new AIPlayerDynamic(2,6, "2");
        } else if (flag ==4) { // easy vs easy
            player1=new RandomPlayer(1, "1");
            player2=new RandomPlayer(2, "2");
        }else if (flag ==5) { // medium vs easy
            player1=new AIPlayerDynamic(1,1, "1");
            player2=new RandomPlayer(2, "2");
        }else if (flag ==6) { // hard vs easy
            player1=new AIPlayerDynamic(1,6, "1");
            player2=new RandomPlayer(2, "2");
        }else if (flag ==7) { // easy vs medium
            player1=new RandomPlayer(1, "1");
            player2=new AIPlayerDynamic(2,1, "2");
        }else if (flag ==8) { // medium vs medium
            player1=new AIPlayerDynamic(1,1, "1");
            player2=new AIPlayerDynamic(2,1, "2");
        }else if (flag ==9) { // hard vs medium
            player1=new AIPlayerDynamic(1,6, "1");
            player2=new AIPlayerDynamic(2,1, "2");
        }else if (flag ==10) { // easy vs hard
            player1=new RandomPlayer(1, "1");
            player2=new AIPlayerDynamic(2,6, "2");
        }else if (flag ==11) { // medium vs hard
            player1=new AIPlayerDynamic(1,1, "1");
            player2=new AIPlayerDynamic(2,6, "2");
        }else if (flag ==12) { // hard vs hard
            player1=new AIPlayerDynamic(1,6, "1");
            player2=new AIPlayerDynamic(2,6, "2");
        }
        
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JPanel reversiBoard = new JPanel();
        reversiBoard.setLayout(new GridLayout(8,8));
        reversiBoard.setPreferredSize(new Dimension(500,500));
        reversiBoard.setBackground(new Color(41,100, 59));

        //init board
        resetBoard();

        cells = new BoardCell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardCell(this,reversiBoard,i,j);
                reversiBoard.add(cells[i][j]);
            }
        }


        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200,0));

        score1 = new JLabel("Score 1");
        score2 = new JLabel("Score 2");

        tscore1 = new JLabel("Total Score 1");
        tscore2 = new JLabel("Total Score 2");

        sidebar.add(score1);
        sidebar.add(score2);

        sidebar.add(new JLabel("-----------"));

        sidebar.add(tscore1);
        sidebar.add(tscore2);


        this.add(sidebar,BorderLayout.WEST);
        this.add(reversiBoard);

        //
        updateBoardInfo();
        updateTotalScore();

        //AI Handler Timer (to unfreeze gui)
        player1HandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(player1);
            player1HandlerTimer.stop();
            manageTurn();
        });

        player2HandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(player2);
            player2HandlerTimer.stop();
            manageTurn();
        });

        manageTurn();
    }

    private boolean awaitForClick = false;

    public void manageTurn(){
        if(BoardHelper.hasAnyMoves(board,1) || BoardHelper.hasAnyMoves(board,2)) {
            updateBoardInfo();
            if (turn == 1) {
                if(BoardHelper.hasAnyMoves(board,1)) {
                    if (player1.isUserPlayer()) {
                        awaitForClick = true;
                        //after click this function should be call backed
                    } else {
                        player1HandlerTimer.start();
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 1 has no legal moves !");
                    turn = 2;
                    manageTurn();
                }
            } else {
                if(BoardHelper.hasAnyMoves(board,2)) {
                    if (player2.isUserPlayer()) {
                        awaitForClick = true;
                        //after click this function should be call backed
                    } else {
                        player2HandlerTimer.start();
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 2 has no legal moves !");
                    turn = 1;
                    manageTurn();                   
                }
            }
        }else{
            //game finished
            System.out.println("Game Finished !");
            int winner = BoardHelper.getWinner(board);
            if(winner==1) totalscore1++;
            else if(winner==2) totalscore2++;
            updateTotalScore();
            
            String txt = new String(player1.playerName() + " : " + getPlayerStoneCount(board,1) + "\n" + player2.playerName() + " : " + getPlayerStoneCount(board,2));
            LUI neww = new LUI(txt);
    //            this.setVisible(false);
            neww.setVisible(true);
            
//            restart
            resetBoard();
            turn=1;
            manageTurn();
        }
    }

    public void resetBoard(){
        board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j]=0;
            }
        }
        //initial board state
        setBoardValue(3,3,2);
        setBoardValue(3,4,1);
        setBoardValue(4,3,1);
        setBoardValue(4,4,2);
    }

    //update highlights on possible moves and scores
    public void updateBoardInfo(){

        int p1score = 0;
        int p2score = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] == 1) p1score++;
                if(board[i][j] == 2) p2score++;

                if(BoardHelper.canPlay(board,turn,i,j)){
                    cells[i][j].highlight = 1;
                }else{
                    cells[i][j].highlight = 0;
                }
            }
        }

        score1.setText(player1.playerName() + " : " + p1score);
        score2.setText(player2.playerName() + " : " + p2score);
    }

    public void updateTotalScore(){
        tscore1.setText(player1.playerName() + " : " + totalscore1);
        tscore2.setText(player2.playerName() + " : " + totalscore2);
    }

    @Override
    public void handleClick(int i,int j){
        if(awaitForClick && BoardHelper.canPlay(board,turn,i,j)){
            System.out.println("User Played in : "+ i + " , " + j);

            //update board
            board = BoardHelper.getNewBoardAfterMove(board,new Point(i,j),turn);

            //advance turn
            turn = (turn == 1) ? 2 : 1;

            repaint();

            awaitForClick = false;

            //callback
            manageTurn();
        }
    }

    public void handleAI(GamePlayer ai){
        Point aiPlayPoint = ai.play(board);
        int i = aiPlayPoint.x;
        int j = aiPlayPoint.y;
        if(!BoardHelper.canPlay(board,ai.myMark,i,j)) System.err.println("FATAL : AI Invalid Move !");
        System.out.println(ai.playerName() + " Played in : "+ i + " , " + j);

        //update board
        board = BoardHelper.getNewBoardAfterMove(board,aiPlayPoint,turn);

        //advance turn
        turn = (turn == 1) ? 2 : 1;

        repaint();
    }

}
