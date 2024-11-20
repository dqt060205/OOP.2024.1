package com.group19.userinterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;

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




/*package com.group19.userinterface;

import com.group19.gameobject.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.*;

public class GameFrame extends JFrame {
    private Dinosaur dinosaur; // Khai báo đối tượng Dinosaur
    private GamePanel gamePanel;
    private InputManager inputManager; // Khai báo InputManager

    public GameFrame() throws IOException {
        this.dinosaur = new Dinosaur(); // Khởi tạo đối tượng Dinosaur
        this.gamePanel = new GamePanel(dinosaur); // Khởi tạo GamePanel để vẽ Dinosaur
        this.inputManager = new InputManager(dinosaur); // Khởi tạo InputManager

        this.setTitle("Dinosaur Game");
        this.setSize(1300, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình

        // Thêm sự kiện bàn phím
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                inputManager.processKeyPressed(keyCode); // Xử lý khi phím được nhấn
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                inputManager.processKeyReleased(keyCode); // Xử lý khi phím được thả
            }
        });

        this.add(gamePanel);
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new GameFrame(); // Khởi tạo GameFrame
    }
}

class GamePanel extends JPanel {
    private Dinosaur dinosaur;

    public GamePanel(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
        this.setBackground(Color.WHITE); // Đặt màu nền
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Gọi phương thức render của Dinosaur để vẽ lên màn hình
        dinosaur.render(g2d);
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
        dinosaur.update(); // Cập nhật trạng thái của Dinosaur (như di chuyển và nhảy)
        repaint(); // Vẽ lại màn hình sau mỗi lần cập nhật
    }
}*/

