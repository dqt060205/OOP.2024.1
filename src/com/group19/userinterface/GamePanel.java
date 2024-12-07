package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;
import com.group19.gameobject.LevelManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, Runnable {

    private Thread thread;
    private boolean isRunning = true;
    private InputManager inputManager;
    private Dinosaur dino;
    private LevelManager levelManager;

    public GamePanel() throws IOException {
        dino = new Dinosaur();
        inputManager = new InputManager(dino);
        levelManager = new LevelManager("data/itemslist.text", "data/game_saves.text"); // Khởi tạo LevelManager từ file dữ liệu

        // Có thể cần phải đặt level ban đầu, ví dụ: level 1
        levelManager.setCurrentLevel(0); // Giả sử màn chơi đầu tiên là màn 1 (index 0)
    }

    // Phương thức khởi động game
    public void startGame() {
        thread = new Thread(this);
        thread.start();
    }

    // Phương thức bắt đầu game tại một màn chơi cụ thể
    public void startGameAtLevel(int level) {
        levelManager.setCurrentLevel(level - 1); // Cập nhật level hiện tại (0-based index)
        startGame(); // Khởi động game với level đã chọn
    }

    private void updateGame() {
        dino.update();  // Cập nhật Dino
        dino.run();     // Cập nhật hoạt ảnh của Dino
        levelManager.getCurrentLevel().update(dino); // Cập nhật level hiện tại
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();
        long currentTime;
        long accumulatedTime = 0; // Tích lũy thời gian
        long period = 1000000000 / 80; // 80 FPS (chu kỳ mỗi khung hình)

        while (isRunning) {
            currentTime = System.nanoTime();
            long elapsedTime = currentTime - previousTime;
            previousTime = currentTime;

            accumulatedTime += elapsedTime;

            // Cập nhật game theo từng bước thời gian
            while (accumulatedTime >= period) {
                updateGame();
                accumulatedTime -= period;
            }

            // Vẽ nội dung
            repaint();

            // Ngủ để duy trì FPS
            long sleepTime = period - accumulatedTime;
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 1000000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.processKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.processKeyReleased(e.getKeyCode());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Vẽ nhân vật Dino
        dino.render(g2);

        // Vẽ level hiện tại
        levelManager.getCurrentLevel().render(g2);
    }
}
