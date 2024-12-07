package com.group19.userinterface;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class GameFrame extends JFrame {

    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 800;

    GamePanel gamePanel;

    public GameFrame() throws IOException {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2 - 25, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        gamePanel = new GamePanel(); // Khởi tạo GamePanel với hình nền
        add(gamePanel);

        this.addKeyListener(gamePanel); // Thêm key listener nếu GamePanel xử lý bàn phím
    }

    public void startGame() {
        gamePanel.startGame(); // Gọi logic bắt đầu trò chơi từ GamePanel
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        GameFrame gameFrame = new GameFrame();
        gameFrame.startGame();
    }
}
