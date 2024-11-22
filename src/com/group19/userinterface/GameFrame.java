package com.group19.userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
//jujgyfytviguf
public class GameFrame extends JFrame {

    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 800;

    GamePanel gamePanel;

    public GameFrame() throws IOException{

        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH)/2, (dimension.height - SCREEN_HEIGHT)/2 - 25, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        add(gamePanel);

        this.addKeyListener(gamePanel);

    }

    public void startGame(){
        gamePanel.startGame();
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException{
        GameFrame gameFrame = new GameFrame();
        gameFrame.startGame();
    }

}
