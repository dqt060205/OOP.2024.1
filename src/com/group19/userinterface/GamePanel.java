/*package com.group19.userinterface;

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
}*/

/*
package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;
import com.group19.gameobject.LevelManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, Runnable {
	private boolean levelCompleted = false; //

    private Thread thread;
    private boolean isRunning = true;
    private InputManager inputManager;
    private BufferedImage bufImage;
    private Graphics2D buf2D;
    private Dinosaur dino;
    private LevelManager levelManager;
    private Image backgroundImage;

    public GamePanel() throws IOException {
        dino = new Dinosaur();
        inputManager = new InputManager(dino);
        levelManager = new LevelManager("data/interleaved_itemslist.text", "data/game_saves.text"); // Khởi tạo LevelManager từ file dữ liệu

        // Có thể cần phải đặt level ban đầu, ví dụ: level 1
        levelManager.setCurrentLevel(0); // Giả sử màn chơi đầu tiên là màn 1 (index 0)
        backgroundImage = new javax.swing.ImageIcon("data/BackgroundLevel1.png").getImage();
        
        while (levelManager.getCurrentLevel().isTheEndLevel() && levelManager.getCurrentLevelIndex() <= levelManager.getTotalLevels()) {
            // Nếu level kết thúc, chuyển sang màn tiếp theo
            int currentLevelIndex = levelManager.getCurrentLevelIndex() + 1;
            levelManager.setCurrentLevel(currentLevelIndex);
        }
        
    }
    
    public void showLevelCompletedMessage(Graphics g) { //
        // Thiết lập font và màu sắc cho thông báo
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        
        // Hiển thị thông báo "Bạn đã qua màn"
        g.drawString("Bạn đã qua màn!", GameFrame.SCREEN_WIDTH / 2 - 150, GameFrame.SCREEN_HEIGHT / 2); // Vị trí của thông báo
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
        
        if (levelManager.getCurrentLevel().isTheEndLevel()) { //
            levelCompleted = true; // Đánh dấu level đã hoàn thành
        }
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
        
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Vẽ nhân vật Dino
        dino.render(g2);

        // Vẽ level hiện tại
        levelManager.getCurrentLevel().render(g2);
        if (levelCompleted) { //
            showLevelCompletedMessage(g2);
        }
    }
}
*/

package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;
import com.group19.gameobject.LevelManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;


import javax.swing.JPanel;


public class GamePanel extends JPanel implements KeyListener, Runnable {
	private boolean levelCompleted = false; //
	private boolean isGameOver = false;

    private Thread thread;
    private boolean isRunning = true;
    private InputManager inputManager;
    private Dinosaur dino;
    private LevelManager levelManager;
    private Image backgroundImage;

    public GamePanel() throws IOException {
        dino = new Dinosaur();
        inputManager = new InputManager(dino);
        levelManager = new LevelManager("data/interleaved_itemslist.text", "data/game_saves.text"); // Khởi tạo LevelManager từ file dữ liệu

        // Có thể cần phải đặt level ban đầu, ví dụ: level 1
        levelManager.setCurrentLevel(0); // Giả sử màn chơi đầu tiên là màn 1 (index 0)
        backgroundImage = new javax.swing.ImageIcon("data/BackgroundLevel" + (levelManager.getCurrentLevelIndex() + 1) + ".png" ).getImage();
        
    }
    
    public void showLevelCompletedMessage(Graphics g) { //
        // Thiết lập font và màu sắc cho thông báo
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        
        // Hiển thị thông báo "Bạn đã qua màn"
        g.drawString("Bạn đã qua màn!", GameFrame.SCREEN_WIDTH / 2 - 150, GameFrame.SCREEN_HEIGHT / 2); // Vị trí của thông báo
    }


    // Phương thức khởi động game
    public void startGame() {
        thread = new Thread(this);
        thread.start();
    }

    // Phương thức bắt đầu game tại một màn chơi cụ thể
    public void startGameAtLevel(int level) {
        levelManager.setCurrentLevel(level - 1); // Cập nhật level hiện tại (0-based index)
        resetForNewLevel();
        startGame(); // Khởi động game với level đã chọn
    }

    private void updateGame() {
    	if(isGameOver) {
    		return;
    	}
    	
        dino.update();  // Cập nhật Dino
        dino.run();     // Cập nhật hoạt ảnh của Dino
        levelManager.getCurrentLevel().update(dino); // Cập nhật level hiện tại
        
        if(dino.getLives() <= 0) {
        	isGameOver = true;
        	isRunning = false;
        	return;
        }
        
        if (levelManager.getCurrentLevel().getScore() >= 25 && !levelCompleted) {
            levelCompleted = true;
            moveToNextLevel();
        }
    }
    
    private void moveToNextLevel() {
        int nextLevelIndex = levelManager.getCurrentLevelIndex() + 1;
        
        if (levelManager.getCurrentLevel().getScore() < 25) {
            resetForNewLevel(); // Chơi lại màn hiện tại
            return;
        }
        
        if (nextLevelIndex < LevelManager.getTotalLevels()) {
            levelManager.setCurrentLevel(nextLevelIndex);
            resetForNewLevel(); // Reset trạng thái cho màn mới
        } else {
            System.out.println("Bạn đã hoàn thành tất cả các màn!");
            isRunning = false; // Kết thúc game
        }
    }
    
    private void resetForNewLevel() {
        levelCompleted = false;
        isGameOver = false;
        dino.reset();
        backgroundImage = new javax.swing.ImageIcon("data/BackgroundLevel" + (levelManager.getCurrentLevelIndex() + 1) + ".png").getImage();
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
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER && isGameOver) {
            isGameOver = false;
            isRunning = true;
            dino.reset();  // Reset lại Dino
            levelManager.setCurrentLevel(levelManager.getCurrentLevelIndex()); // Giữ nguyên màn hiện tại
            startGame(); // Bắt đầu lại game từ màn hiện tại
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.processKeyReleased(e.getKeyCode());
    }
    
    private void drawHUD(Graphics2D g2) {
       
        g2.setColor(Color.yellow);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Màn chơi: " + (levelManager.getCurrentLevelIndex() + 1), 20, 70);
        g2.drawString("Mạng sống: " + dino.getLives(), 20, 90); // Hiển thị số mạng
        
        g2.setColor(Color.yellow);
        g2.setFont(new Font("Arial", Font.BOLD, 70));
        g2.drawString("  " + levelManager.getCurrentLevel().getScore(), 170, 730);
    }
    
    private void showGameOverMessage(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", GameFrame.SCREEN_WIDTH / 2 - 150, GameFrame.SCREEN_HEIGHT / 2);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Nhấn Enter để chơi lại màn này", GameFrame.SCREEN_WIDTH / 2 - 150, GameFrame.SCREEN_HEIGHT / 2 + 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Vẽ nhân vật Dino
        dino.render(g2);

        // Vẽ level hiện tại
        levelManager.getCurrentLevel().render(g2);
        if (isGameOver) {
            showGameOverMessage(g2);
        } else {
            drawHUD(g2);
        }
        if (levelCompleted) { //
            showLevelCompletedMessage(g2);
        }
        
    }
}
