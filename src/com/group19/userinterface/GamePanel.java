package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;
import com.group19.gameobject.GameItem;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, Runnable {

    private Thread thread;
    private boolean isRunning = true;
    private InputManager inputManager;
    private BufferedImage bufImage;
    private Graphics2D buf2D;
    private Dinosaur dino;
    private List<GameItem> grades;
    
    public GamePanel() throws IOException{
        dino = new Dinosaur();
        //grade = new GameItem(3);
        grades = new ArrayList<>();
        
        long currentTime = System.currentTimeMillis();
        Random random = new Random();
        
        long spawnTime = currentTime;
        for(int i = 0;i < 40; i++) {
        	int value = new Random().nextInt(1,5);
        	long delay = random.nextInt(1000) + 1500;
        	spawnTime += delay;
        	
        	grades.add(new GameItem(value,spawnTime));
        }
        
        inputManager = new InputManager(dino);
    }

    public void startGame(){
        thread = new Thread(this);
        thread.start();
    }
    private void updateGame() {
       try {
    	long currentTime = System.currentTimeMillis();
    	List<GameItem> itemsToRemove = new ArrayList<>();
        for(GameItem grade : grades) {
        	/*if(currentTime >= grade.getSpawnTime()) {
        	  grade.update(dino);
        	  if(grade.getPosY() > 800 || !grade.isActive()) {
        		  itemsToRemove.add(grade);
        	  }
        	}  */
        	if (currentTime >= grade.getSpawnTime()) {
                if (grade.isActive()) {
                    grade.update(dino);  // Cập nhật GameItem

                    // Kiểm tra nếu GameItem đã rơi khỏi màn hình
                    if (grade.getPosY() > 800) {
                        itemsToRemove.add(grade);  // Đánh dấu để xóa
                    }
                } else {
                    itemsToRemove.add(grade);  // Nếu không active thì cũng xóa
                }
            }
        }
        grades.removeAll(itemsToRemove);
        dino.update();  // Cập nhật Dino
        dino.run();  // Cập nhật hoạt ảnh Dino
    	}catch(Exception e) {
        	e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();
        long currentTime;
        long accumulatedTime = 0; // Tích lũy thời gian
        long period = 1000000000 / 80; // 80 FPS (chu kỳ mỗi khung hình)
    
        while (isRunning) {
            // Lấy thời gian hiện tại
        	
            currentTime = System.nanoTime();
            long elapsedTime = currentTime - previousTime;
            previousTime = currentTime;
    
            // Tích lũy thời gian trôi qua
            accumulatedTime += elapsedTime;
    
            // Cập nhật logic theo từng khung
            while (accumulatedTime >= period) {
                updateGame(); // Cập nhật game theo từng bước thời gian
                accumulatedTime -= period; // Giảm thời gian đã sử dụng
            }
    
            // Vẽ nội dung (được điều khiển bởi tốc độ logic)
            repaint();
    
            // Ngủ để duy trì FPS
            long sleepTime = period - accumulatedTime;
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 1000000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.processKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.processKeyReleased(e.getKeyCode());
    }

    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        long currentTime = System.currentTimeMillis();
        grades.sort(Comparator.comparingLong(GameItem :: getSpawnTime));
        for(GameItem grade : grades) {
        	if(currentTime >= grade.getSpawnTime())
        	  grade.render(g2);
        }
        dino.render(g2);
    }
}
