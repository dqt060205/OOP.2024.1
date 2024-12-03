package com.group19.gameobject;
import java.util.ArrayList;
import java.util.List;


public class LevelManager {
    private List<LevelState> levels; // Danh sách trạng thái màn chơi
    private int currentLevel;        // Level hiện tại người chơi đang chơi

    public LevelManager(int totalLevels) {
        levels = new ArrayList<>();
        for (int i = 0; i < totalLevels; i++) {
            levels.add(LevelState.LOCKED); // Mặc định tất cả các màn chơi bị khóa
        }
        levels.set(0, LevelState.UNLOCKED); // Mở khóa màn đầu tiên
        currentLevel = 0;
    }

    public boolean isLevelUnlocked(int level) {
        return levels.get(level) != LevelState.LOCKED;
    }

    public boolean isLevelCompleted(int level) {
        return levels.get(level) == LevelState.COMPLETED;
    }

    public void completeCurrentLevel() {
        levels.set(currentLevel, LevelState.COMPLETED); // Đánh dấu hoàn thành
        if (currentLevel + 1 < levels.size()) {
            levels.set(currentLevel + 1, LevelState.UNLOCKED); // Mở khóa màn tiếp theo
        }
    }

    public void retryCurrentLevel() {
        levels.set(currentLevel, LevelState.UNLOCKED); // Cho phép chơi lại màn hiện tại
    }

    public void setCurrentLevel(int level) {
        if (isLevelUnlocked(level)) {
            currentLevel = level;
        } else {
            throw new IllegalArgumentException("Màn chơi này chưa được mở khóa!");
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getTotalLevels() {
        return levels.size();
    }

    public List<LevelState> getLevelStates() {
        return levels;
    }

    // Enum để biểu diễn trạng thái của level
    public enum LevelState {
        LOCKED,
        UNLOCKED,
        COMPLETED
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

