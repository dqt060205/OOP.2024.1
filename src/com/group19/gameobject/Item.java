package com.group19.gameobject;
import java.awt.Image;
import java.util.Random;

public abstract class Item extends GameObject {

    private int value;      // Giá trị của Item
    private int speed;      // Tốc độ rơi: Chưa xử lí
    private Image image;
    private Random random;

    public Item() {
        
    }
    public Item(Image image, int value, int posX, int posY, int width, int height) {
        super(posX, posY, 100, 100); // Chiều dài = chiều rộng = 100 px
        this.value = value;
        this.image = image;
        this.random = new Random();
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void randomPosition() {              // Hàm sinh vị trí ngẫu nhiên trong màn hình theo trục X
        setPosX(random.nextInt(100,1300));
        setPosY(0);
    }
    public abstract void updateSpeed();
    public void falling(int speed) {
        posY += speed; // Vật rơi thẳng đứng, tạm thời để posY tăng dần theo speed
    };

    @Override
    public abstract void update();

    


    
}
