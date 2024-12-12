package com.group19.gameobject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
        private static List<Level> levels;       // Danh sách các màn chơi
        private boolean[] unlockedLevels; // Trạng thái mở khóa của từng màn chơi
        private int currentLevelIndex;    // Chỉ số của màn chơi hiện tại
        private SaveGame saveGame;
    
        public LevelManager(String filePath, String saveFilePath) throws IOException {
            levels = new ArrayList<>();
            loadLevelsFromFile(filePath); // Đọc dữ liệu từ file
            unlockedLevels = new boolean[levels.size()];
            unlockedLevels[0] = true; // Mở khóa màn đầu tiên
            currentLevelIndex = 0;    // Bắt đầu từ màn đầu tiên
            saveGame = new SaveGame(saveFilePath);
        }
    
        // Đọc dữ liệu từ file text
        private void loadLevelsFromFile(String filePath) throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    List<Integer> itemTypes = new ArrayList<>();
                    String[] tokens = line.split(" ");
                    for (String token : tokens) {
                        itemTypes.add(Integer.parseInt(token)); // Chuyển đổi từ chuỗi sang số
                    }
                    levels.add(new Level(itemTypes)); // Tạo màn chơi
                }
            }
        }
    
        // Lấy màn chơi hiện tại
        public Level getCurrentLevel() {
            if (currentLevelIndex >= 0 && currentLevelIndex < levels.size()) {
                return levels.get(currentLevelIndex);
            }
            return null; // Nếu chỉ số màn chơi không hợp lệ
        }
    
        // Kiểm tra màn chơi có được mở khóa không
        public boolean isLevelUnlocked(int index) {
            return index >= 0 && index < unlockedLevels.length && unlockedLevels[index];
        }
    
        // Chuyển đến màn chơi mới
        public boolean switchToLevel(int index) {
            if (isLevelUnlocked(index)) {
                currentLevelIndex = index;
                return true;
            }
            return false;
        }
    
        // Mở khóa màn chơi tiếp theo (nếu có)
        public void unlockNextLevel() {
            if (currentLevelIndex + 1 < levels.size()) {
                unlockedLevels[currentLevelIndex + 1] = true;
            }
        }
    
        // Lấy chỉ số màn chơi hiện tại
        public int getCurrentLevelIndex() {
            return currentLevelIndex;
        }
    
        // Lấy số lượng màn chơi
        public static int getTotalLevels() {
            return levels.size();
    }

    // Thiết lập màn chơi hiện tại
    public void setCurrentLevel(int i) {
        if (i >= 0 && i < levels.size()) {
            currentLevelIndex = i;
        } else {
            throw new IllegalArgumentException("Invalid level index");
        }
    }

    public void saveGame() {
        saveGame.saveCurrentGame(currentLevelIndex, unlockedLevels);
    }

    public void loadGame() {
        saveGame.loadGameState(new int[]{currentLevelIndex}, unlockedLevels);
    }
}
