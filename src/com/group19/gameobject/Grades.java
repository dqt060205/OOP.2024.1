package com.group19.gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Grades extends Item {
    private char name;

    public Grades(Image image, int value,int posX, int posY, int width, int height) {
        super(image, value, posX, posY, width, height);
        setName(value);
    }
    public void setName(int value) {
        switch(value){
            case 0 ->  {
                this.name = 'F';
            } 
            case 1 ->  {
                this.name = 'D';
            }
            case 2 -> {
                this.name = 'C';
            }
            case 3 -> {
                this.name = 'B';
            }
            case 4 -> {
                this.name = 'A';
            }
        }
    }
    public char getName() {
        return this.name;
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
