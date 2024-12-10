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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
    
    private long levelCompletedTime = 0;  // Thời gian hoàn thành level
    private final long levelCompleteDelay = 4000;  // Thời gian chờ trước khi chuyển level (2 giây)
    private boolean levelTransitionInProgress = false;  // Kiểm tra quá trình chuyển màn
 

    public GamePanel() throws IOException {
        dino = new Dinosaur();
        inputManager = new InputManager(dino);
        levelManager = new LevelManager("data/interleaved_itemslist.text", "data/game_saves.text"); // Khởi tạo LevelManager từ file dữ liệu

        // Có thể cần phải đặt level ban đầu, ví dụ: level 1
        levelManager.setCurrentLevel(0); // Giả sử màn chơi đầu tiên là màn 1 (index 0)
        backgroundImage = new javax.swing.ImageIcon("data/BackgroundLevel" + (levelManager.getCurrentLevelIndex() + 1) + ".png" ).getImage();
        
    }
    
    public void showLevelCompletedMessage(Graphics g) {
    // Tải hình ảnh (lưu ý thay đường dẫn và tên tệp hình ảnh phù hợp)
    try {
        Image image = ImageIO.read(new File("data/LevelCompletedBackground.png")); // Đường dẫn tới tệp hình ảnh
        int imageWidth = image.getWidth(null);  // Lấy chiều rộng của hình ảnh
        int imageHeight = image.getHeight(null);  // Lấy chiều cao của hình ảnh
        
        // Vẽ hình ảnh lên màn hình
        int xPosition = (GameFrame.SCREEN_WIDTH - imageWidth) / 2; // Vị trí X để căn giữa
        int yPosition = (GameFrame.SCREEN_HEIGHT - imageHeight) / 2; // Vị trí Y để căn giữa
        g.drawImage(image, xPosition, yPosition, imageWidth, imageHeight, null);
        
    } catch (Exception e) {
        e.printStackTrace(); 
    }
}

    // Phương thức khởi động game
    public void startGame() {
        thread = new Thread(this);
        thread.start();
    }

    // Phương thức bắt đầu game tại một màn chơi cụ thể
    public void startGameAtLevel(int level) {
        levelManager.setCurrentLevel(level - 1); // Cập nhật level hiện tại (0-based index)
        levelManager.getCurrentLevel().resetScore();
        resetForNewLevel();
        startGame(); // Khởi động game với level đã chọn
    }

    private void updateGame() {
    	if(isGameOver) {
    		levelManager.getCurrentLevel().resetScore();
    		return;
    	}
    	
        dino.update();  // Cập nhật Dino
        dino.run();     // Cập nhật hoạt ảnh của Dino
        levelManager.getCurrentLevel().update(dino); // Cập nhật level hiện tại
        
        if(dino.getLives() <= 0) {
        	isGameOver = true;
        	isRunning = false;
        	levelManager.getCurrentLevel().resetScore();
        	return;
        }
        
        if (levelManager.getCurrentLevel().getScore() >= 25 && !levelCompleted && !levelTransitionInProgress) {
            levelCompleted = true;
            levelTransitionInProgress = true;
            //moveToNextLevel();
            levelCompletedTime = System.currentTimeMillis();
            
        }
        if (levelTransitionInProgress && (System.currentTimeMillis() - levelCompletedTime) > levelCompleteDelay) {
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
    	//levelManager.getCurrentLevel().resetScore();
    	levelCompleted = false;
        isGameOver = false;
        dino.reset();
        levelManager.getCurrentLevel().resetScore();
        backgroundImage = new javax.swing.ImageIcon("data/BackgroundLevel" + (levelManager.getCurrentLevelIndex() + 1) + ".png").getImage();
        levelTransitionInProgress = false; 
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

    // private void drawHUD(Graphics2D g2) {
       
    //     g2.setColor(Color.yellow);
    //     g2.setFont(new Font("Arial", Font.BOLD, 20));
    //     g2.drawString("Màn chơi: " + (levelManager.getCurrentLevelIndex() + 1), 20, 70);
    //     g2.drawString("Mạng sống: " + dino.getLives(), 20, 90); // Hiển thị số mạng
        
    //     g2.setColor(Color.yellow);
    //     g2.setFont(new Font("Arial", Font.BOLD, 70));
    //     g2.drawString("  " + levelManager.getCurrentLevel().getScore(), 170, 730);
    // }

    private void drawHUD(Graphics2D g2) {
        // Tải hình ảnh tim từ thư mục data
        Image heartImage = null;
        try {
            heartImage = ImageIO.read(new File("data/heart.png")); // Đường dẫn tới hình ảnh tim
        } catch (Exception e) {
            e.printStackTrace(); // Nếu có lỗi khi tải hình ảnh
        }
        
        // Hiển thị điểm số
        g2.setColor(Color.yellow);
        g2.setFont(new Font(".VnTime", Font.BOLD, 60)); // Sử dụng phông chữ Montserrat


        // g2.setFont(new Font("Arial", Font.BOLD, 70));
        g2.drawString("  " + levelManager.getCurrentLevel().getScore(), 165,625 ); //727
    
        // Vẽ các hình ảnh tim đại diện cho mạng sống
        if (heartImage != null) {
            int heartWidth = heartImage.getWidth(null); 
            int heartHeight = heartImage.getHeight(null);
            int lives = dino.getLives(); // Lấy số mạng sống
            
            for (int i = 0; i < lives; i++) {
                // Tính toán vị trí vẽ các hình ảnh tim
                int x = 5 + i * (heartWidth + 3); // Khoảng cách giữa các hình ảnh tim
                int y = 20; // Vị trí Y cho hình ảnh tim
                g2.drawImage(heartImage, x, y, heartWidth, heartHeight, null);
            }
        }
    }
    
    
    // private void showGameOverMessage(Graphics g) {
    //     g.setColor(Color.RED);
    //     g.setFont(new Font("Arial", Font.BOLD, 50));
    //     g.drawString("GAME OVER", GameFrame.SCREEN_WIDTH / 2 - 150, GameFrame.SCREEN_HEIGHT / 2);
    //     g.setFont(new Font("Arial", Font.BOLD, 20));
    //     g.setColor(Color.WHITE);
    //     g.drawString("Nhấn Enter để chơi lại màn này", GameFrame.SCREEN_WIDTH / 2 - 150, GameFrame.SCREEN_HEIGHT / 2 + 50);
    // }
    private void showGameOverMessage(Graphics g) {
        Image gameOverImage = null;
        try {
            gameOverImage = ImageIO.read(new File("data/GameOver.png")); // Đường dẫn tới hình ảnh game over
        } catch (Exception e) {
            e.printStackTrace(); // Nếu có lỗi khi tải hình ảnh
        }
    
        // Nếu hình ảnh game over đã được tải thành công, vẽ nó lên màn hình
        if (gameOverImage != null) {
            int imageWidth = gameOverImage.getWidth(null); // Lấy chiều rộng của hình ảnh
            int imageHeight = gameOverImage.getHeight(null); // Lấy chiều cao của hình ảnh
    
            // Tính toán vị trí vẽ hình ảnh game over (căn giữa màn hình)
            int xPosition = (GameFrame.SCREEN_WIDTH - imageWidth) / 2;
            int yPosition = (GameFrame.SCREEN_HEIGHT - imageHeight) / 2;
            g.drawImage(gameOverImage, xPosition, yPosition, imageWidth, imageHeight, null);
        }
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
