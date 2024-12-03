package com.group19.gameobject;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//sao up len roi ma khong thay nhi

public class Level {
    private List<Item> items; // Danh sách các Item trong màn chơi
    private Random random;   // Sinh vị trí ngẫu nhiên cho Item

    public Level(List<Integer> itemTypes) throws IOException {
        items = new ArrayList<>();
        for (int type : itemTypes) {
            items.add(new GameItem(type)); // Khởi tạo Item
        }
    }
    public void render(Graphics2D g2) { 
        for (Item type : items) {
           type.render(g2);
        }
    }
    public void update(Dinosaur dino) {
        for (Item type : items) {
            type.update(dino);
        }
    }
}
