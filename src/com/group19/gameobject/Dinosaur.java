package com.group19.gameobject;

import com.group19.effect.Animation;
import com.group19.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Dinosaur extends GameObject {


    private static final int GRAVITY = 2; // Lực hấp dẫn
    private static final int JUMP_STRENGTH = 20; // Độ cao nhảy
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
    public Dinosaur(int posX, int posY) throws IOException {
        super(posX, posY, 130, 115); // Gọi constructor của GameObject
        this.isJumping = false;
        this.isTurningLeft = false;
        this.lives = 3;
        this.isRunning = false;

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
                g2.drawImage(idleLeft, posX, posY, width, height, null); //Có thê bị lỗi toạ độ khi vẽ ảnh
            } else {
                g2.drawImage(idleRight, posX, posY, width, height, null); 
            }
        }
        
    }

    @Override
    public void update() {
        run();  // Di chuyển Dino
        jump();  // Kiểm tra trạng thái nhảy và thực hiện nhảy
    }

    // Phương thức di chuyển Dino sang phải
    public void run() {
        if (this.isRunning && posX > 0) {   // Quay sang trái thì di chuyển sang trái theo trục X
            this.isTurningLeft = true;
            posX -= 3;
        }
        else if(this.isRunning && posX < SCREEN_WIDTH - this.width) {
            this.isTurningLeft = false;
            posX += 3;
        }
    }
    // Phương thức kiểm tra và thực hiện nhảy
    public void jump() {
        if (isJumping) {
            posY -= JUMP_STRENGTH; // Di chuyển lên khi nhảy
            if (posY <= 80) { // Giới hạn độ cao của nhảy
                this.stopJump(); // Đổi trạng thái khi lên tới đỉnh
            }
        } else {
            posY += GRAVITY; // Di chuyển xuống khi rơi
            if (posY >= SCREEN_HEIGHT - this.height) { // Giới hạn khi chạm đất
                posY = SCREEN_HEIGHT - this.height; // Reset lại vị trí khi chạm đất
            }
        }
    }

    // Phương thức bắt đầu nhảy
    public void startJump() {
        if (posY == SCREEN_HEIGHT - height) { // Đảm bảo Dino chỉ nhảy khi đang ở mặt đất
            isJumping = true;
        }
    }

    // Phương thức dừng nhảy
    public void stopJump() {
        isJumping = false;
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
