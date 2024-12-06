package com.group19.gameobject;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//sao up len roi ma khong thay nhi

public class Level {
    private List<Item> loadedItems; // Danh sách các Item trong màn chơi
    private List<Item> activeItems; // Danh sách các Item đến lượt xuất hiện
    private Random random;   // Sinh vị trí ngẫu nhiên cho Item
    private final long spawnCycle = 1_000_000_000; //1 giây sẽ sinh 1 item
    private long lastSpawnTime;

    public Level(List<Integer> itemTypes) throws IOException {
        loadedItems = new ArrayList<>();
        activeItems = new ArrayList<>();
        lastSpawnTime = System.nanoTime();

        for (int type : itemTypes) {
            loadedItems.add(new GameItem(type)); // Khởi tạo Item
        }
    }
    public void render(Graphics2D g2) { 
        for (Item type : activeItems) {
           type.render(g2);
        }
    }
    public void update(Dinosaur dino) {
        long currentTime = System.nanoTime();

        if (currentTime - lastSpawnTime >= spawnCycle) {
            Item nextItem = loadedItems.remove(0);
            activeItems.add(nextItem);
            System.out.println("Item spawned: " + nextItem.getValue());
            lastSpawnTime = currentTime;
        }
        for (Item type : activeItems) {
            type.update(dino);
        }
    }
}
