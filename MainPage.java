import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MainPage extends JPanel {

    private int curretnState;
    private String skinName;
    private String musicName;

    // Hình ảnh
    private Image dinoImage;
    private Image infoImage;
    private Image startButtonImage;
    private Image scoreboardImage;
    private Image skinImage;
    private Image musicImage;

    private Font font;
    private String[] scores = new String[5];
        private int currentState;
        public MainPage(int currentState, String skinName, String musicName) {
            this.currentState = currentState;
        this.skinName = skinName;
        this.musicName = musicName;

        // Tải hình ảnh
        loadImages();

        // Tải điểm số từ file
        loadScores();

        // Tạo phông chữ
        font = new Font("Times New Roman", Font.PLAIN, 24);

        // Xử lý sự kiện chuột
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                handleMouseClick(mouseX, mouseY);
            }
        });
    }
    
    private void loadImages() {
        try {
            dinoImage = new ImageIcon("resources/Images/" + skinName + ".png").getImage();
            infoImage = new ImageIcon("resources/Images/Information.png").getImage();
            startButtonImage = new ImageIcon("resources/Images/StartBrown.png").getImage();
            scoreboardImage = new ImageIcon("resources/Images/scoreboard.png").getImage();
            skinImage = new ImageIcon("resources/Images/skin.png").getImage();
            musicImage = new ImageIcon("resources/Images/Music.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/score.txt"))) {
            List<String> scoreList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null && scoreList.size() < 5) {
                scoreList.add(line);
            }
            for (int i = 0; i < 5; i++) {
                if (i < scoreList.size()) {
                    scores[i] = scoreList.get(i);
                } else {
                    scores[i] = "0";  // Default score if no data
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        // Xử lý các nút bấm
        if (isButtonClicked(mouseX, mouseY, 312, 1150, 718, 1340)) {
            currentState = 2;  // Start game
        } else if (isButtonClicked(mouseX, mouseY, 62, 1346, 168, 1448)) {
            currentState = 3;  // Change skin
        } else if (isButtonClicked(mouseX, mouseY, 830, 1350, 932, 1446)) {
            currentState = 4;  // Change music
        } else if (isButtonClicked(mouseX, mouseY, 48, 40, 162, 140)) {
            currentState = 5;  // Show information
        }
    }

    private boolean isButtonClicked(int mouseX, int mouseY, int x1, int y1, int x2, int y2) {
        return mouseX > x1 && mouseX < x2 && mouseY > y1 && mouseY < y2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền
        g.setColor(new Color(255, 204, 153));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Vẽ các hình ảnh
        g.drawImage(dinoImage, 540, 450, null);
        g.drawImage(infoImage, 50, 40, null);
        g.drawImage(scoreboardImage, 60, 400, null);
        g.drawImage(startButtonImage, 300, 1140, null);
        g.drawImage(skinImage, 50, 1340, null);
        g.drawImage(musicImage, 820, 1340, null);

        // Vẽ văn bản
        g.setFont(new Font("Times new roman", Font.PLAIN, 107));
        g.setColor(Color.BLACK);
        g.drawString("Like A Dino!", 200, 200);

        g.setFont(font);
        g.drawString("Music: " + musicName, 60, 570);
        g.drawString("Character: " + skinName, 650, 1040);

        // Vẽ điểm số
        for (int i = 0; i < 5; i++) {
            g.drawString(scores[i], 180, 700 + i * 67);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dino Game");
        MainPage mainPage = new MainPage(1, "defaultSkin", "defaultMusic");

        frame.setSize(1000, 1500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPage);
        frame.setVisible(true);
    }
}
