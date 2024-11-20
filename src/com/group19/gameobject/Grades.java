package com.group19.gameobject;

import java.awt.Graphics2D;

public class Grades extends Item {

    public Grades(int value) {      // Constructor để gen mặc định toàn bộ thuộc tính (chỉ nhập vào value)
        super.setValue(value);
        super.randomPosition();
        this.setWidth(100);
        this.setHeight(100);
        this.setImage(value);
    }
    public Grades(int value,int posX, int posY, int width, int height) {
        super(value, posX, posY, width, height);
    }
    public void setImage(int value) {   // Set ảnh cho các grades theo value
        switch (value) {
            case 0 ->  {
                super.setImage("data/GradeF.png");
            }
            case 1 -> {
                super.setImage("data/GradeD.png");
            }
            case 2 -> {
                super.setImage("data/GradeC.png");
            }
            case 3 -> {
                super.setImage("data/GradeB.png");
            }
            case 4 -> {
                super.setImage("data/GradeA.png");
            }
        }
    }
    @Override
    public void updateSpeed() {
        int baseSpeed = 2;
        this.setSpeed(this.getValue() + baseSpeed); // Tốc độ rơi tuỳ thuộc vào giá trị điểm
    }
    
    
    @Override
    public void update() {
        while (this.posY <= 800) {                          // Chưa có map nên là để tạm theo SCREEN_HEIGHT
            this.falling(this.getSpeed()); // Khi chưa chạm biên thì rơi theo tốc độ
        }
    }
    @Override
    public void render(Graphics2D g2) {        // Đã render (draw) hình ảnh là ảnh của điểm trong folder data
        g2.drawImage(this.getImage(), posX, posY, null);
    }

}
