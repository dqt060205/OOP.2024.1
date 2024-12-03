package com.group19.gameobject;
import com.group19.userinterface.InputManager;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//sao up len roi ma khong thay nhi

public class Level {
    private List<Item> items; // Danh sách các Item trong màn chơi
    private Random random;   // Sinh vị trí ngẫu nhiên cho Item
    private InputManager inputManager;
    public Level(List<Integer> itemTypes) throws IOException {
        items = new ArrayList<>();
        for (int type : itemTypes) {
            items.add(new GameItem(type)); // Khởi tạo Item
        }
    }
    public static List<List<Integer>> loadLevels (String filePath) throws IOException {
        List<List<Integer>> levels = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Tách dòng thành các số nguyên
                String[] tokens = line.split(" ");
                List<Integer> levelItems = new ArrayList<>();
                for (String token : tokens) {
                    levelItems.add(Integer.parseInt(token));
                }
                levels.add(levelItems);
            }
        }
        return levels;
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
