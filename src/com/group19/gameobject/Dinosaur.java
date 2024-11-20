package com.group19.gameobject;

import com.group19.effect.Animation;
import com.group19.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Dinosaur extends GameObject {


    private static final int GRAVITY = 10; // Lực hấp dẫn
    private static final int JUMP_STRENGTH = 40; // Độ cao nhảy
    private int speedY = 0;
    private static final int SCREEN_WIDTH = 1300; // Chiều rộng màn hình
    private static final int SCREEN_HEIGHT = 700; // Chiều cao màn hình

    private boolean isJumping; // Trạng thái nhảy của Dino
    private boolean isRunning;
    private boolean isTurningLeft; // Kiểm tra hướng của Dino
    public int lives;

    private BufferedImage idleLeft;
    private BufferedImage idleRight;
    private Animation runLeftAnimation, runRightAnimation;
    private Animation jumpLeftAnimation, jumpRightAnimation;
    private Animation deadLeftAnimation, deadRightAnimation;

    // Constructor khởi tạo Dino tại tọa độ (posX, posY)
    public Dinosaur() throws IOException {
        super(SCREEN_WIDTH / 2, SCREEN_HEIGHT, 130, 115); // Gọi constructor của GameObject
        this.isJumping = false;
        this.speedY = 0;
        this.isTurningLeft = false;
        this.lives = 3;
        this.isRunning = false;

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
    }
    public void setLives(int lives) {
        this.lives  = lives;
    }
    public int getLives() {
        return this.lives;
    }
    
    @Override
    public void render(Graphics2D g2) {
        if (lives == 0) {
            if (isTurningLeft) {
                deadLeftAnimation.update(System.nanoTime());
                deadLeftAnimation.draw(posX, posY, g2);
            }
            else {
                deadRightAnimation.update(System.nanoTime());
                deadRightAnimation.draw(posX, posY, g2);
            }
        }
        else if (isJumping) {
            if (isTurningLeft) {
                jumpLeftAnimation.update(System.nanoTime());
                jumpLeftAnimation.draw(posX, posY, g2);
            }
            else {
                jumpRightAnimation.update(System.nanoTime());
                jumpRightAnimation.draw(posX, posY, g2);
            }
        }
        else if (isRunning) {
            if (isTurningLeft) {
                runLeftAnimation.update(System.nanoTime());
                runLeftAnimation.draw(posX, posY, g2);
            }
            else {
                runRightAnimation.update(System.nanoTime());
                runRightAnimation.draw(posX, posY, g2);
            }
        }
        else {
            if (isTurningLeft) {
                g2.drawImage(idleLeft, posX, posY, width, height, null); // Có thê bị lỗi toạ độ khi vẽ ảnh
            } else {
                g2.drawImage(idleRight, posX, posY, width, height, null); 
            }
        }
        
    }

    @Override
    public void update() {
        if (isJumping) {
            speedY += GRAVITY;
            this.setPosY(this.getPosY() + speedY);
        }

        if (this.getPosY() >= SCREEN_HEIGHT) {
            this.setPosY(SCREEN_HEIGHT);
            isJumping = false;
            speedY = 0;
        }
    }

    // Phương thức di chuyển Dino sang phải
    public void run() {
        if (this.isRunning) {
            if (isTurningLeft && posX > 0) {  // Chạy sang trái
                posX -= 3;
            }
            else if (!isTurningLeft && posX < SCREEN_WIDTH - this.width) {  // Chạy sang phải
                posX += 3;
            }
        }
    }
    // Phương thức kiểm tra và thực hiện nhảy
    public void jump() {
        if (!isJumping) {
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
}
