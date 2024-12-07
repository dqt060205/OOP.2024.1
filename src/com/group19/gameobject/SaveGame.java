package com.group19.gameobject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGame {
    private  String saveFilePath;
    
        public SaveGame(String saveFilePath) {
            this.saveFilePath = saveFilePath;
        }
    
        // Xoá file game đã lưu (phục vụ cho việc nhấn New Game)
        public  void deleteSaveFile() {
                File file = new File(saveFilePath);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Old saved file deleted successfully.");
                }
                else {
                    System.err.println("Failed to delete old saved file");
                }
            }
        }
    
        // Lưu game hiện tại
        public  void saveCurrentGame(int currentLevelIndex, boolean[] unlockedLevels) {
            // Xoá file cũ
            deleteSaveFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath))) {
            // Lưu chỉ số màn chơi hiện tại
            writer.write("CurrentLevel:" + currentLevelIndex);
            writer.newLine();

            // Lưu trạng thái mở khóa các màn
            writer.write("UnlockedLevels:");
            for (boolean unlocked : unlockedLevels) {
                writer.write(unlocked ? "1" : "0");
            }
            writer.newLine();

            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    // Tải game đang chơi dở
    public void loadGameState(int[] currentLevelIndex, boolean[] unlockedLevels) {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFilePath))) {
            // Đọc chỉ số màn chơi hiện tại
            String levelLine = reader.readLine();
            currentLevelIndex[0] = Integer.parseInt(levelLine.split(":")[1]);

            // Đọc trạng thái mở khóa của các màn
            String unlockedLine = reader.readLine();
            String unlockedData = unlockedLine.split(":")[1];
            for (int i = 0; i < unlockedData.length(); i++) {
                unlockedLevels[i] = unlockedData.charAt(i) == '1';
            }

            System.out.println("Game loaded successfully!");
        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
    }
}
