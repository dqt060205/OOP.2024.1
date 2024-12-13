package com.group19.gameobject;

//package gameobject;

import com.group19.effect.Animation;
import com.group19.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Dinosaur extends GameObject {


    private static final int GRAVITY = 1; // Lực hấp dẫn
    private static final int JUMP_STRENGTH = 15; // Độ cao nhảy
    private int speedY = 0;
    private static final int SCREEN_WIDTH = 1100; // Chiều rộng màn hình
    private static final int SCREEN_HEIGHT = 600; // Chiều cao màn hình
    private long lastUpdateTime = System.nanoTime();
    private static final long DELAY_TIME = 20_000_000;
    
    private boolean isSlowedDown;
    private boolean isx2Score;
    private boolean isShielded;
    private boolean isJumping; // Trạng thái nhảy của Dino
    private boolean isRunning;
    private boolean isTurningLeft; // Kiểm tra hướng của Dino
    public int lives;

    private final BufferedImage idleLeft;
    private final BufferedImage idleRight;
    private final Animation runLeftAnimation;
    private final Animation runRightAnimation;
    private final Animation jumpLeftAnimation;
    private final Animation jumpRightAnimation;
    private final Animation deadLeftAnimation;
    private final Animation deadRightAnimation;

    private BufferedImage currentBuffImage;
    private final Animation timerBarAnimation;
    private long timerBarEndTime = 0;

    // Constructor khởi tạo Dino tại tọa độ (posX, posY)
    public Dinosaur() throws IOException {
        super((SCREEN_WIDTH + 130)/ 2, SCREEN_HEIGHT - 120, 130, 140); // Gọi constructor của GameObject
        isJumping = false;
        speedY = 0;
        isTurningLeft = false;
        lives = 3;
        isRunning = false;
        isShielded = false;
        isSlowedDown = false;
        isx2Score = false;

        CacheDataLoader.getInstance().LoadData();

        idleLeft = ImageIO.read(new File("data/Idle_left_fix.png"));
        idleRight = ImageIO.read(new File("data/Idle_right_fix.png"));

        runRightAnimation = CacheDataLoader.getInstance().getAnimation("run");
        runLeftAnimation = CacheDataLoader.getInstance().getAnimation("run");
        runLeftAnimation.flipAllImage();

        jumpRightAnimation = CacheDataLoader.getInstance().getAnimation("jump");
        jumpLeftAnimation = CacheDataLoader.getInstance().getAnimation("jump");
        jumpLeftAnimation.flipAllImage();

        deadRightAnimation = CacheDataLoader.getInstance().getAnimation("dead");
        deadLeftAnimation = CacheDataLoader.getInstance().getAnimation("dead");
        deadLeftAnimation.flipAllImage();

        timerBarAnimation = CacheDataLoader.getInstance().getAnimation("timerbar");
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public int getLives() {
        return this.lives;
    }
    
    public boolean isShielded() {
        return isShielded;
    }

    
    
    public boolean isSlowedDown() {
        return isSlowedDown;
    }
    public boolean isx2Score() {
        return isx2Score;
    }

    public void activateShielded(BufferedImage buffImage) {
        isShielded = true;
        isx2Score = isSlowedDown = false;
        currentBuffImage = buffImage;
        System.out.println("Shield Activated!");
        resetTimerBar(); // Reset timerbar
        new Thread(() -> {
            try {
                Thread.sleep(10_000); // 10 giây
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            isShielded = false;
            currentBuffImage = null;
            System.out.println("Shield Deactivated!");
        }).start();
    }
    
    public void activateX2Score(BufferedImage buffImage) {
        isx2Score = true;
        isShielded = isSlowedDown = false;
        currentBuffImage = buffImage;
        System.out.println("X2 Score Activated!");
        resetTimerBar(); // Reset timerbar
        new Thread(() -> {
            try {
                Thread.sleep(10_000); // 10 giây
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            isx2Score = false;
            currentBuffImage = null;
            System.out.println("X2 Score Deactivated!");
        }).start();
    }
    
    public void activateSlowedDown(BufferedImage buffImage) {
        isSlowedDown = true;
        isShielded = isx2Score = false;
        currentBuffImage = buffImage;
        System.out.println("Slow Down Activated!");
        resetTimerBar(); // Reset timerbar
        new Thread(() -> {
            try {
                Thread.sleep(10_000); // 10 giây
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            isSlowedDown = false;
            currentBuffImage = null;
            System.out.println("Slow Down Deactivated!");
        }).start();
    }
    
    // Reset thời gian timerbar khi nhặt vật phẩm
    private void resetTimerBar() {
        timerBarAnimation.reset();
        timerBarEndTime = System.currentTimeMillis() + 10_000; // 10 giây
    }
    
    @Override
    public void render(Graphics2D g2) {
        if (lives == 0) {
            if (isTurningLeft) {
                deadLeftAnimation.update(System.nanoTime());
                deadLeftAnimation.draw(posX + 20, posY, g2);
            }
            else {
                deadRightAnimation.update(System.nanoTime());
                deadRightAnimation.draw(posX - 20, posY, g2);
            }
        }
        else if (isJumping) {
            if (isTurningLeft) {
                jumpLeftAnimation.update(System.nanoTime());
                jumpLeftAnimation.draw(posX - 65, posY, g2);
            }
            else {
                jumpRightAnimation.update(System.nanoTime());
                jumpRightAnimation.draw(posX, posY, g2);
            }
        }
        else if (isRunning) {
            if (isTurningLeft) {
                runLeftAnimation.update(System.nanoTime());
                runLeftAnimation.draw(posX - 65, posY, g2);
            }
            else {
                runRightAnimation.update(System.nanoTime());
                runRightAnimation.draw(posX, posY, g2);
            }
        }
        else {
            if (isTurningLeft) {
                g2.drawImage(idleLeft, posX, posY + 10, null);
            } else {
                g2.drawImage(idleRight, posX, posY + 10, null);
            }
        }
        if (System.currentTimeMillis() < timerBarEndTime) {
            timerBarAnimation.update(System.nanoTime());
            timerBarAnimation.draw(47, 140, g2);
        }
        if (currentBuffImage != null) {
            g2.drawImage(currentBuffImage, 35, 570, 70, 70, null); // Vẽ ảnh buff bên cạnh timerbar
        }
        //g2.setColor(Color.RED);  // Màu sắc của hitbox (đỏ)
        //g2.setStroke(new java.awt.BasicStroke(2));  // Độ dày của đường viền
        //g2.drawRect(posX, posY, width, height);  // Vẽ hình chữ nhật bao quanh nhân vật
    }

    public void update() {
        long currentTime = System.nanoTime();
        if (currentTime - lastUpdateTime >= DELAY_TIME) {
            lastUpdateTime = currentTime;
            if(lives == 0) {
            	isRunning = false;
            	isJumping = false;
            	return;
            }
            if(lives > 0){
            if (isJumping) {
                speedY += GRAVITY;
                this.setPosY(this.getPosY() + speedY);
            }

            if (this.getPosY() >= SCREEN_HEIGHT - height) {
                this.setPosY(SCREEN_HEIGHT - height);
                isJumping = false;
                speedY = 0;
                jumpLeftAnimation.reset();
                jumpRightAnimation.reset();
            }
        }
      }
    }

    // Phương thức di chuyển Dino sang phải
    public void run() {
        if (this.isRunning && lives > 0) {
            if (isTurningLeft && posX > this.width + 120) {  // Chạy sang trái
                posX -= 4;
            }
            else if (!isTurningLeft && posX < SCREEN_WIDTH + 50 - this.width) {  // Chạy sang phải
                posX += 4;
            }
            else {
                this.isRunning = false;
            }
        }
    }
    // Phương thức kiểm tra và thực hiện nhảy
    public void jump() {
        if (!isJumping && lives > 0) {
            isJumping = true;
            speedY = - JUMP_STRENGTH;   // Khởi tạo ban đầu khi nhảy

            runLeftAnimation.reset();
            runRightAnimation.reset();
        }
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isIsTurningLeft() {
        return isTurningLeft;
    }

    public void setIsTurningLeft(boolean isTurningLeft) {
        this.isTurningLeft = isTurningLeft;
    }
	public void setIsJumping(boolean b) {
		// TODO Auto-generated method stub
		this.isJumping = b;
	}
	
	public void reset() {
        this.setPosX((SCREEN_WIDTH + 130)/ 2);
        this.setPosY(SCREEN_HEIGHT - 120);
        isJumping = false;
        speedY = 0;
        isTurningLeft = false;
        lives = 3;
        isRunning = false;
        isShielded = isSlowedDown = isx2Score = false;
        timerBarAnimation.reset();
        timerBarEndTime = System.currentTimeMillis();
	}
}