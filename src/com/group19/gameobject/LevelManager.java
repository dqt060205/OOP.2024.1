package com.group19.gameobject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private List<Level> levels;       // Danh sách các màn chơi
    private boolean[] unlockedLevels; // Trạng thái mở khóa của từng màn chơi
    private int currentLevelIndex;    // Chỉ số của màn chơi hiện tại

    public LevelManager(String filePath) throws IOException {
        levels = new ArrayList<>();
        loadLevelsFromFile(filePath); // Đọc dữ liệu từ file
        unlockedLevels = new boolean[levels.size()];
        unlockedLevels[0] = true; // Mở khóa màn đầu tiên
        currentLevelIndex = 0;    // Bắt đầu từ màn đầu tiên
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
        return levels.get(currentLevelIndex);
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
    public int getTotalLevels() {
        return levels.size();
    }
}
/* 
    public LevelManager(String filePath) {
        try {
            // Load dữ liệu màn chơi từ file
            this.levels = Level.loadLevels(filePath);
            this.currentLevel = 0; // Bắt đầu từ màn đầu tiên
            this.totalScore = 0;
            loadCurrentLevel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadCurrentLevel() {
        if (currentLevel < levels.size()) {
            // Lấy danh sách các loại Item của màn chơi hiện tại
            List<Integer> itemTypes = levels.get(currentLevel);

            // Tạo màn chơi
            this.level = new Level(itemTypes);
        } else {
            System.out.println("Không còn màn chơi nào!");
        }
    }

    public static void play() {
        while (currentLevel < levels.size()) {
            // Lấy màn chơi
            GameLevel level = levels.get(currentLevel);
            // Kiểm tra xem màn đã mở chưa
            if (!level.isUnlocked()) {
                //code để thông báo màn chơi chưa mở, yêu cầu chọn lại
            }
            while (true) { //Thực hiện cho đến khi break
                if (level.isEnded()) { //kiểm tra xem màn chơi kết thúc chưa
                    if (level.hasWon()) { //nếu qua màn
                        totalScore += level.getScore(); // Cộng điểm của màn vào điểm tổng
                        if (currentLevel < levels.size()-1) { // Mở khóa màn tiếp theo
                            levels.get(currentLevel + 1).unlock();
                            level.isEnded = true;
                        }
                    }
                    else {
                        //Nếu không qua màn
                    }
                    break;
                }
            }
            int checkLevel = currentLevel;
            //code để chọn màn chơi muốn chơi tiếp theo (cập nhật currentLevel)
        }
    }
    Thiết lập lại trò chơi về trạng thái ban đầu
    public void resetGame() {
        currentLevel = 0;
        totalScore = 0;
        for (int i = 0; i < levels.size(); i++) {
            levels.get(i).reset();
            levels.get(i).unlock();  // Mở khóa lại màn 1, các màn khác sẽ khóa lại
            if (i > 0) {
                levels.get(i).unlock(false); // khóa lại các màn từ 2 đến 6
            }
        }
    } 
} 
*/

