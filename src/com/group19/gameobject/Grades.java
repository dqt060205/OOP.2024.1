package com.group19.gameobject;

import java.awt.Color;
import java.awt.Graphics;

public class Grades extends Item {

    public Grades() {

    }
    public Grades(int value,int posX, int posY, int width, int height) {
        super(value, posX, posY, width, height);
    }
    public void setImage(int value) {
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
        this.setSpeed(this.getValue() * baseSpeed); // Tốc độ rơi tuỳ thuộc vào giá trị điểm
    }

    

    @Override
    public void update() {
        while (this.posY <= 700) {                          // Chưa có map nên là để tạm theo SCREEN_HEIGHT
            this.setPosY(this.getPosY() + this.getSpeed()); // Khi chưa chạm biên thì cập nhật toạ độ theo tốc độ
        }
    }
    @Override
    public void render(Graphics g) {        // Tạm thời render hình ảnh điểm là màu đỏ (vì chưa có hình)
        g.setColor(Color.RED);
        g.fillRect(posX, posY, width, height);
    }

    
    
}
