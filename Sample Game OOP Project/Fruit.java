import java.awt.*;
import java.util.Random;

public class Fruit {
    private int x, y;
    private int speed;
    private final int width = 50;
    private final int height = 50;
    private final int score;

    public Fruit(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.speed = new Random().nextInt(3) + 1;
        this.score = (3 - speed + 1) * 10; // Điểm số tùy thuộc vào tốc độ rơi
    }

    public void fall() {
        y += speed;
    }

    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public boolean intersects(Player player) {
        return new Rectangle(x, y, width, height).intersects(player.getBounds());
    }
}
