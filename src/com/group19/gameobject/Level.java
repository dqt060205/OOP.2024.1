package com.group19.gameobject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.Timer;

public class Level {
    private List<GameItem> loadedItems; // Danh sách các Item trong màn chơi
    private List<GameItem> activeItems; // Danh sách các Item đến lượt xuất hiện
    private final long spawnCycle = 1_000_000_000; //1 giây sẽ sinh 1 item
    private long lastSpawnTime;
    private int totalScore = 0;        
    protected JWindow currentPopup; // Biến lưu popup hiện tại
    private List<Integer> originalItemTypes; 
    public Level(List<Integer> itemTypes) throws IOException {
        originalItemTypes = new ArrayList<>(itemTypes);
        loadedItems = new ArrayList<>();
        activeItems = new ArrayList<>();
        lastSpawnTime = System.nanoTime();

        for (int type : itemTypes) {
            loadedItems.add(new GameItem(type)); // Khởi tạo Item
        }
    }
    public void render(Graphics2D g2) { 
        for (GameItem type : activeItems) {
           type.render(g2);
        }
    }
    private void showPopupEffect(String string) {
        if (currentPopup != null) {
            currentPopup.dispose(); // Đóng popup cũ nếu tồn tại
        }
        ImageIcon image = new ImageIcon(string);
        currentPopup = new JWindow(); // Cửa sổ tạm thời, không có thanh tiêu đề
        currentPopup.setBackground(new Color(0, 0, 0, 0)); // Đặt nền trong suốt
        JLabel label = new JLabel(image); // Sử dụng JLabel để hiển thị ảnh
        currentPopup.getContentPane().add(label);
        
        // Đặt kích thước theo kích thước của ảnh
        int imageWidth = image.getIconWidth();
        int imageHeight = image.getIconHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - imageWidth) / 2;
        int y = (screenHeight - imageHeight) / 2 - 100;
        currentPopup.setBounds(x, y, imageWidth, imageHeight);
        currentPopup.setAlwaysOnTop(true); // Luôn hiển thị trên cùng
        currentPopup.setVisible(true);

        // Tạo Timer để đóng popup sau 1 giây
        Timer fadeOutTimer = new Timer(10, null); // Chạy mỗi 50ms
        final float[] opacity = {1.0f}; // Độ trong suốt ban đầu

        fadeOutTimer.addActionListener(e -> {
            opacity[0] -= 0.2f; // Giảm độ trong suốt
            if (opacity[0] <= 0.0f) {
                currentPopup.dispose(); // Xóa popup khi hoàn toàn mờ
                fadeOutTimer.stop();
                currentPopup = null; // Xóa tham chiếu popup
            } else {
                currentPopup.setOpacity(opacity[0]); // Cập nhật độ trong suốt
            }
        });

        fadeOutTimer.setInitialDelay(200); // Chờ 1 giây trước khi bắt đầu mờ dần
        fadeOutTimer.start();
    }

    public void update(Dinosaur dino) {
        long currentTime = System.nanoTime();

        // Spawn new item if enough time has passed
        if (currentTime - lastSpawnTime >= spawnCycle) {
            if (!loadedItems.isEmpty()) { // Kiểm tra xem danh sách còn item không
                GameItem nextItem = loadedItems.remove(0);
                activeItems.add(nextItem);
                System.out.println("Item spawned: " + nextItem.getValue());
                lastSpawnTime = currentTime;
            }
        }
      int currentLevel = LevelManager.getCurrentLevelIndex() + 1;
        // Update items and remove inactive ones
        Iterator<GameItem> iterator = activeItems.iterator();
        while (iterator.hasNext()) {
            GameItem type = iterator.next();
            type.update(dino, currentLevel);
            
            if (type.collidesWith(dino))  {
                System.out.println("Collision!!!");
                switch (type.getValue()) {
                    case 0 -> {if (!dino.isShielded()) {
                        showPopupEffect("data/Boom.png");
                        dino.setLives(dino.getLives()-1);
                    }}
                    case 1 -> showPopupEffect("data/Bad.png");
                    case 2 -> showPopupEffect("data/GoodJob.png");
                    case 3 -> showPopupEffect("data/Bravo.png");
                    case 4 -> showPopupEffect("data/Excellent.png");
                    case 6 -> dino.activateX2Score(type.getImage());
                    case 5 -> dino.activateShielded(type.getImage());
                    case 7 -> dino.activateSlowedDown(type.getImage());
                    default -> {
                    }
                }
                System.out.println("Grade collected: " + type.getValue());
                type.setActive(false);  // Vô hiệu hóa GameItem nếu đã ăn
                if (dino.isx2Score()) {
                    totalScore = totalScore + type.collect()*2;
                }
                else {
                    totalScore += type.collect();
                } 
                System.out.println("Total Score: " + totalScore);
                iterator.remove();  
            }
            if (type.getPosY() > (640 - type.getHeight())) {
                iterator.remove();  
            }
        }
    }
    public boolean isTheEndLevel() {
        return loadedItems.isEmpty() && activeItems.isEmpty();
    }
    public int getScore() {
        return this.totalScore;
    }
     public void resetScore() {
        this.totalScore = 0;  // Đặt lại điểm số về 0
    }
    public void resetLevel() {
        loadedItems = new ArrayList<>();
        activeItems = new ArrayList<>();
        lastSpawnTime = System.nanoTime();

        for (int type : originalItemTypes) {
            loadedItems.add(new GameItem(type)); // Khởi tạo Item
        }
        resetScore();
    }

}

