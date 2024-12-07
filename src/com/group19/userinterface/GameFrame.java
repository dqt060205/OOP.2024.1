package com.group19.userinterface;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class GameFrame extends JFrame {

    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 800;

    private GamePanel gamePanel; // Panel chính của game

    public GameFrame() throws IOException {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2 - 25, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Khởi tạo GamePanel
        gamePanel = new GamePanel(); // GamePanel mặc định sẽ là màn chơi đầu tiên
        add(gamePanel);

        // Đảm bảo GamePanel có thể xử lý các sự kiện bàn phím
        this.addKeyListener(gamePanel);
    }

    // Phương thức khởi động game với level cụ thể
    public void startGameAtLevel(int level) {
        gamePanel.startGameAtLevel(level); // Truyền level cho GamePanel
        this.setVisible(true); // Hiển thị cửa sổ GameFrame
    }

    public void startGame() {
        gamePanel.startGame(); // Gọi logic bắt đầu trò chơi từ GamePanel
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        GameFrame gameFrame = new GameFrame();
        gameFrame.startGame(); // Khởi động game từ level mặc định
    }
}
