package com.group19.gameobject;

import com.group19.effect.Animation;
import com.group19.effect.CacheDataLoader;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Dinosaur extends GameObject {

    private ImageIcon dinoImage;

    private static final int GRAVITY = 2; // Lực hấp dẫn
    private static final int JUMP_STRENGTH = 20; // Độ cao nhảy
    private static final int SCREEN_WIDTH = 1400; // Chiều rộng màn hình
    private static final int SCREEN_HEIGHT = 800; // Chiều cao màn hình

    private boolean isJumping; // Trạng thái nhảy của Dino
    private boolean isRunning;
    private boolean isTurningLeft; // Kiểm tra hướng của Dino
    public int lives;

    private Animation runLeftAnimation, runRightAnimation;
    private Animation jumpLeftAnimation, jumpRightAnimation;
    private Animation deadLeftAnimation, deadRightAnimation;

    // Constructor khởi tạo Dino tại tọa độ (posX, posY)
    public Dinosaur(int posX, int posY) {
        super(posX, posY, 130, 115); // Gọi constructor của GameObject
        this.isJumping = false;
        this.isTurningLeft = false;
        this.lives = 3;

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
    public void render(Graphics g) {
        // Vẽ hình ảnh Dino tại vị trí posX, posY với chiều rộng và chiều cao
        g.drawImage(dinoImage.getImage(), posX, posY, width, height, null); 
    }

    @Override
    public void update() {
        run();  // Di chuyển Dino
        jump();  // Kiểm tra trạng thái nhảy và thực hiện nhảy
    }

    // Phương thức di chuyển Dino sang phải
    private void run() {
        if (!this.isRunning) {
            this.isRunning = true;
        }
        if (this.isTurningLeft) {   // Quay sang trái thì di chuyển sang trái theo trục X
            posX -= 3;
        }
        else {
            posX += 3;
        }
    }

    // Phương thức kiểm tra và thực hiện nhảy
    private void jump() {
        if (isJumping) {
            posY -= JUMP_STRENGTH; // Di chuyển lên khi nhảy
            if (posY <= 100) { // Giới hạn độ cao của nhảy
                isJumping = false; // Đổi trạng thái khi lên tới đỉnh
            }
        } else {
            posY += GRAVITY; // Di chuyển xuống khi rơi
            if (posY >= SCREEN_HEIGHT - height) { // Giới hạn khi chạm đất
                posY = SCREEN_HEIGHT - height; // Reset lại vị trí khi chạm đất
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
}
