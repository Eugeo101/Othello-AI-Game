package game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private final int flag;

    public GameWindow(int flag){
        this.flag=flag;
        GamePanel gp = new GamePanel(flag);
        this.add(gp);
//        if(flag==0){
//            GamePanel gp = new GamePanel(flag);
//            this.add(gp);
//        } else if (flag==1) {
//            GamePanel gp = new GamePanel(flag);
//            this.add(gp);
//        }else if(flag==2){
//            GamePanel gp = new GamePanel(flag);
//            this.add(gp);
//        }
        this.setTitle("Othello Game Team 11");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        setLocationRelativeTo(null);
        //this.setSize(500,500);

    }

    //public static void main(String[] args) {
       // new GameWindow();
    //}

}
