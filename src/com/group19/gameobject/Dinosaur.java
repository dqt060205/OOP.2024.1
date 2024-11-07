package com.group19.gameobject;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Dinosaur extends GameObject {

    private ImageIcon dinoImage;
    public static final int width = 130; // Chiều rộng của Dino
    public static final int height = 115; // Chiều cao của Dino
    private boolean isJumping; // Trạng thái nhảy của Dino
    private static final int GRAVITY = 2; // Lực hấp dẫn
    private static final int JUMP_STRENGTH = 20; // Độ cao nhảy
    private static final int SCREEN_WIDTH = 1400; // Chiều rộng màn hình
    private static final int SCREEN_HEIGHT = 800; // Chiều cao màn hình

    // Constructor khởi tạo Dino tại tọa độ (posX, posY)
    public Dinosaur(float posX, float posY) {
        super(posX, posY, width, height); // Gọi constructor của GameObject
        this.dinoImage = new ImageIcon(getClass().getResource("/images/Idle (10).png"));
        this.isJumping = false;
    }

    @Override
    public void render(Graphics g) {
        // Vẽ hình ảnh Dino tại vị trí posX, posY với chiều rộng và chiều cao
        g.drawImage(dinoImage.getImage(), (int)posX, (int)posY, (int)width, (int)height, null); 
    }

    @Override
    public void update() {
        move();  // Di chuyển Dino
        jump();  // Kiểm tra trạng thái nhảy và thực hiện nhảy
    }

    // Phương thức di chuyển Dino sang phải
    private void move() {
        posX += 10; // Di chuyển Dino sang phải mỗi lần cập nhật
        
        // Kiểm tra nếu Dino đã ra ngoài màn hình, dừng lại ở cạnh phải
        if (posX + width > SCREEN_WIDTH) { // Nếu Dino đi ra ngoài màn hình
            posX = SCREEN_WIDTH - width; // Dừng lại ở cạnh phải
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
