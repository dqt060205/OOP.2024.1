import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class SimpleGame extends JPanel implements ActionListener {
    private int headX = 300, headY = 400;  // Position of the dinosaur head
    private int score = 0; // Score
    private int lives = 3; // Lives
    private Timer timer;
    private ArrayList<Rectangle> necks = new ArrayList<>();
    private Random random = new Random();

    public SimpleGame() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(600, 600));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> headX -= 10;
                    case KeyEvent.VK_RIGHT -> headX += 10;
                    case KeyEvent.VK_UP -> headY -= 10;
                    case KeyEvent.VK_DOWN -> headY += 10;
                }
                repaint();
            }
        });

        timer = new Timer(50, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw head
        g.setColor(Color.GREEN);
        g.fillOval(headX - 15, headY - 15, 30, 30);
        
        // Draw score and lives
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 500, 20);
        
        // Draw necks
        g.setColor(Color.GREEN);
        for (Rectangle neck : necks) {
            g.fillRect(neck.x, neck.y, neck.width, neck.height);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (lives <= 0) {
            timer.stop();
            return;
        }
        
        // Add a new neck
        if (random.nextInt(10) < 1) {
            necks.add(new Rectangle(random.nextInt(600), 0, 10, 10));
        }

        // Move necks down
        Iterator<Rectangle> iterator = necks.iterator();
        while (iterator.hasNext()) {
            Rectangle neck = iterator.next();
            neck.y += 5;
            
            // Check if the neck has reached the bottom
            if (neck.y > 600) {
                iterator.remove();
                lives--;
            } else if (neck.intersects(new Rectangle(headX - 15, headY - 15, 30, 30))) {
                // Check collision with head
                score++;
                iterator.remove();
            }
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Game");
        SimpleGame game = new SimpleGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
