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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;



public class GamePanel extends JPanel implements KeyListener, Runnable {
	private boolean levelCompleted = false; //
	private boolean isGameOver = false;

    private Thread thread;
    private boolean isRunning = true;
    private final InputManager inputManager;
    private final Dinosaur dino;
    private final LevelManager levelManager;
    private Image backgroundImage;    
    //private long levelCompletedTime = 0;  // Thời gian hoàn thành level
    //private final long levelCompleteDelay = 4000;  // Thời gian chờ trước khi chuyển level (2 giây)
    private boolean levelTransitionInProgress = false;  // Kiểm tra quá trình chuyển màn
    private Clip levelMusic;
 

    public GamePanel() throws IOException {
        dino = new Dinosaur();
        inputManager = new InputManager(dino);
        levelManager = new LevelManager("data/itemslist.txt", "data/game_saves.txt"); // Khởi tạo LevelManager từ file dữ liệu

        // Có thể cần phải đặt level ban đầu, ví dụ: level 1
        levelManager.setCurrentLevel(0); // Giả sử màn chơi đầu tiên là màn 1 (index 0)
        backgroundImage = new javax.swing.ImageIcon("data/BackgroundLevel" + (levelManager.getCurrentLevelIndex() + 1) + ".png" ).getImage();
        
    }
    
    public void showLevelCompletedMessage(Graphics g) {
        try {
            levelManager.onLevelCompleted();
            // Vẽ hình nền cho màn hoàn thành level
            Image image = ImageIO.read(new File("data/LevelCompletedBackground.png"));
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            
            // Vẽ hình ảnh lên màn hình
            int xPosition = (GameFrame.SCREEN_WIDTH - imageWidth) / 2;
            int yPosition = (GameFrame.SCREEN_HEIGHT - imageHeight) / 2;
            g.drawImage(image, xPosition, yPosition, imageWidth, imageHeight, null);
    
            // Tạo nút "Next"
            setLayout(null);
            JButton nextButton = createButton("data/NextLevelButton.png", 550, 500);
            // Thêm nút vào panel
            this.add(nextButton);
    
            // Cập nhật lại giao diện để nút hiển thị
            this.revalidate();  // Đảm bảo các thành phần giao diện được xác nhận lại
            this.repaint();  // Vẽ lại giao diện để hiển thị nút
    
        } catch (Exception e) {
        }
    }
    

    // Phương thức khởi động game
    /*public void stopGame() {
        isRunning = false; // Đặt cờ dừng vòng lặp
        try {
            if (thread != null) {
                thread.join(); // Chờ luồng cũ kết thúc
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    
    public void startGame() {
        if (thread != null && thread.isAlive()) {
            stopGameLoop();
        }
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    
    // Phương thức bắt đầu game tại một màn chơi cụ thể
    public void startGameAtLevel(int level) {
        if (levelManager.isLevelUnlocked(level - 1)) {
            levelManager.setCurrentLevel(level - 1); // Cập nhật level hiện tại (0-based index)
            levelManager.getCurrentLevel().resetScore();
            resetForNewLevel();
            playMusicAtLevel(level);
            startGame(); // Khởi động game với level đã chọn
            
        }
    }
    public boolean isLevelUnlocked(int level) {
        return levelManager.isLevelUnlocked(level-1);
    }

    private void updateGame() {
    	if(isGameOver) {
    		levelManager.getCurrentLevel().resetScore();
    		
    		return;
    	}
    	
        dino.update();  // Cập nhật Dino
        dino.run();     // Cập nhật hoạt ảnh của Dino
        levelManager.getCurrentLevel().update(dino); // Cập nhật level hiện tại
        
        if(dino.getLives() <= 0 ||(levelManager.getCurrentLevel().isTheEndLevel() && levelManager.getCurrentLevel().getScore() < 250)) {
        	isGameOver = true;
            //isRunning = false;
        	levelManager.getCurrentLevel().resetScore();
        	return;
        }
        //125125
        if (levelManager.getCurrentLevel().getScore() >= 125 && !levelCompleted && !levelTransitionInProgress) {
            levelCompleted = true;
            levelTransitionInProgress = true;
            levelManager.unlockNextLevel();

            //moveToNextLevel();
            //levelCompletedTime = System.currentTimeMillis();
            
        }
        // if (levelTransitionInProgress && (System.currentTimeMillis() - levelCompletedTime) > levelCompleteDelay) {
        //     moveToNextLevel();
        // }
    }

    private void playMusicAtLevel(int level){
        try{
            String musicFilePath = "data/BackgroundMusic" + level + ".wav";
            File levelMusicFile = new File(musicFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(levelMusicFile);
            levelMusic = AudioSystem.getClip();
            levelMusic.open(audioStream);
            levelMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e){

        }
    }

    private void stopLevelMusic(){
        if(levelMusic != null && levelMusic.isRunning()){
            levelMusic.stop();
        }
    }
    public void disableNextButton() {
        for (java.awt.Component component : this.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if ("nextButton".equals(button.getName())) { // So sánh theo thuộc tính tên
                    button.setVisible(false); // Ẩn nút thay vì xóa
                    button.setEnabled(false); // Vô hiệu hóa nút
                }
            }
        }
    }
    private void moveToNextLevel() {
        this.disableNextButton();
        this.revalidate();   // Xác nhận lại giao diện
        this.repaint();      // Vẽ lại giao diện

        //stopGameLoop();
        int nextLevelIndex = levelManager.getCurrentLevelIndex() + 2;
    
        // Nếu còn màn chơi tiếp theo, chuyển đến màn đó
        if (nextLevelIndex <= LevelManager.getTotalLevels()) {
            //resetForNewLevel();  // Reset trạng thái cho màn mới
            startGameAtLevel(nextLevelIndex);
        } else {
            System.out.println("Bạn đã hoàn thành tất cả các màn!");
            isRunning = false; // Kết thúc game nếu không còn màn chơi nào nữa
        }
        // Cập nhật lại giao diện sau khi chuyển level
        this.revalidate();
        this.repaint();
    }
    
    public void stopGameLoop() {
        stopLevelMusic();
        isRunning = false; // Ngừng game loop
        if (thread != null && thread.isAlive()) {
            try {
                thread.join(); // Đợi thread dừng hoàn toàn
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null; // Giải phóng thread
    }
    private void resetForNewLevel() {
        stopGameLoop();
    	//levelManager.getCurrentLevel().resetScore();
    	levelCompleted = false;
        isGameOver = false;
        dino.reset();
        levelManager.getCurrentLevel().resetLevel();
        backgroundImage = new javax.swing.ImageIcon("data/BackgroundLevel" + (levelManager.getCurrentLevelIndex() + 1) + ".png").getImage();
        levelTransitionInProgress = false; 
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();
        long currentTime;
        long accumulatedTime = 0; // Tích lũy thời gian
        final long period = 1000000000 / 80; // 80 FPS (chu kỳ mỗi khung hình)

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
                }else {
                	Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
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
            stopGameLoop();
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
        // Tải hình ảnh tim từ thư mục data
        Image heartImage = null;
        try {
            heartImage = ImageIO.read(new File("data/heart.png")); // Đường dẫn tới hình ảnh tim
        } catch (Exception e) {
            // Nếu có lỗi khi tải hình ảnh
            
        }
        
        // Hiển thị điểm số
        g2.setColor(Color.yellow);
        g2.setFont(new Font(".VnTime", Font.BOLD, 60)); // Sử dụng phông chữ Montserrat


        // g2.setFont(new Font("Arial", Font.BOLD, 70));
        g2.drawString("  " + levelManager.getCurrentLevel().getScore(), 175,727 ); //727
    
        // Vẽ các hình ảnh tim đại diện cho mạng sống
        if (heartImage != null) {
            int heartWidth = heartImage.getWidth(null); 
            int heartHeight = heartImage.getHeight(null);
            int lives = dino.getLives(); // Lấy số mạng sống
            
            for (int i = 0; i < lives; i++) {
                // Tính toán vị trí vẽ các hình ảnh tim
                int x = 1182 + i * (heartWidth + 3); // Khoảng cách giữa các hình ảnh tim
                int y = 35; // Vị trí Y cho hình ảnh tim
                g2.drawImage(heartImage, x, y, heartWidth, heartHeight, null);
            }
        }
    }

    public void resetGame() {
        if (thread != null && thread.isAlive()) {
            stopGameLoop();
        }
        levelManager.setCurrentLevel(0);
        levelManager.getCurrentLevel().resetScore();
        dino.reset();
        levelCompleted = false;
        isGameOver = false;
        levelTransitionInProgress = false;
        backgroundImage = new javax.swing.ImageIcon("data/BackgroundLevel1.png").getImage();
    
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    
        this.revalidate();
        this.repaint();
    }    
    

    private void showGameOverMessage(Graphics g) {
        Image gameOverImage = null;
        try {
            gameOverImage = ImageIO.read(new File("data/GameOver.png")); // Đường dẫn tới hình ảnh game over
        } catch (Exception e) {
            // Nếu có lỗi khi tải hình ảnh
            
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

    private JButton createButton(String iconPath, int x, int y) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton(icon);
        button.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        // Thêm hiệu ứng hover
        /*button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(new ImageIcon(iconPath.replace(".png", "_hover.png"))); // Đổi hình ảnh khi hover
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(new ImageIcon(iconPath)); // Quay lại hình ảnh ban đầu
            }
        });*/
        button.setName("nextButton");
        button.addActionListener(e -> {
            moveToNextLevel();   // Chuyển level
        });
        return button;
    }
}
