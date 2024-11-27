package com.group19.gameobject;
import java.util.ArrayList;
import java.util.List;


public class LevelManager {
    final private List<GameLevel> levels;
    private int currentLevel;
    private int totalScore;

    public LevelManager() {
        levels = new ArrayList<>();
        levels.add(new GameLevel(/*các tham số truyền vào */)); //thêm từng màn vào ArrayList
        levels.add(new GameLevel());
        levels.add(new GameLevel());
        levels.add(new GameLevel());
        levels.add(new GameLevel());
        currentLevel = 0;
        totalScore = 0;
    }
    public void play() {
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
    /*Thiết lập lại trò chơi về trạng thái ban đầu
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
    } */
}

