import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class FruitCatcherGame extends JPanel implements ActionListener {
    private Timer timer;
    private Player player;
    private ArrayList<Fruit> fruits;
    private int score;
    private int lives;
    private final int screenWidth = 1024;
    private final int screenHeight = 650;
    private Random random = new Random();

    public FruitCatcherGame() {
        // Thiết lập giao diện
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        
        // Khởi tạo đối tượng và biến
        player = new Player(screenWidth / 2, screenHeight - 50);
        fruits = new ArrayList<>();
        score = 0;
        lives = 3;

        // Thiết lập Timer cho game loop
        timer = new Timer(30, this);
        timer.start();

        // Lắng nghe sự kiện bàn phím
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) player.moveLeft();
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveRight();
                if (e.getKeyCode() == KeyEvent.VK_ENTER && lives <= 0) resetGame();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (lives > 0) {
            spawnFruits();
            updateFruits();
            checkCollisions();
            repaint();
        }
    }

    private void spawnFruits() {
        // Tạo trái cây mới với tỷ lệ 3%
        if (random.nextInt(100) < 3) {  // 3% cơ hội tạo trái cây mỗi lần gọi
            int startX = random.nextInt(screenWidth - 50); // Xác định vị trí ngẫu nhiên cho trái cây
            fruits.add(new Fruit(startX, 0)); // Thêm trái cây mới vào danh sách fruits
        }
    }

    private void updateFruits() {
        // Di chuyển từng trái cây trong danh sách
        Iterator<Fruit> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            Fruit fruit = iterator.next();
            fruit.fall();
            if (fruit.getY() > screenHeight) {
                lives--;
                iterator.remove();
            }
        }
    }

    private void checkCollisions() {
        // Kiểm tra va chạm giữa người chơi và trái cây
        Iterator<Fruit> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            Fruit fruit = iterator.next();
            if (fruit.intersects(player)) {
                score += fruit.getScore();
                iterator.remove();
            }
        }
    }

    private void resetGame() {
        // Đặt lại game khi người chơi nhấn ENTER sau khi thua
        score = 0;
        lives = 3;
        fruits.clear();
        player.setPosition(screenWidth / 2, screenHeight - 50);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Vẽ người chơi
        player.draw(g);
        
        // Vẽ trái cây
        for (Fruit fruit : fruits) {
            fruit.draw(g);
        }
        
        // Vẽ điểm số và số mạng
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);

        // Nếu hết mạng, hiển thị màn hình kết thúc
        if (lives <= 0) {
            g.drawString("Game Over! Press ENTER to restart", screenWidth / 2 - 100, screenHeight / 2);
            timer.stop();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fruit Catcher Game");
        FruitCatcherGame game = new FruitCatcherGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
