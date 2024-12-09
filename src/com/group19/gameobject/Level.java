package com.group19.gameobject;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Level {
    private List<GameItem> loadedItems; // Danh sách các Item trong màn chơi
    private List<GameItem> activeItems; // Danh sách các Item đến lượt xuất hiện
    private Random random;   // Sinh vị trí ngẫu nhiên cho Item
    private final long spawnCycle = 1_000_000_000; //1 giây sẽ sinh 1 item
    private long lastSpawnTime;
    private int totalScore = 0;

    public Level(List<Integer> itemTypes) throws IOException {
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
    
        // Update items and remove inactive ones
        Iterator<GameItem> iterator = activeItems.iterator();
        while (iterator.hasNext()) {
            GameItem type = iterator.next();
            type.update(dino);
    
            if (type.collidesWith(dino))  {
                if (dino.isx2Score()) {
                    totalScore = totalScore + type.collect()*2;
                }
                else {
                    totalScore += type.collect();
                } 
                System.out.println("Total Score: " + totalScore);
                iterator.remove();  
            }
        }    
    }
    public boolean isTheEndLevel() {
        return (loadedItems.isEmpty() && activeItems.isEmpty());
    }
    public int getScore() {
        return this.totalScore;
    }
     public void resetScore() {
        this.totalScore = 0;  // Đặt lại điểm số về 0
    }

}

