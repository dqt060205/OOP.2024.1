package com.group19.gameobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {

    protected int posX, posY; // Vị trí của đối tượng
    protected int width, height; // Kích thước của đối tượng
    public GameObject() {

    }
    // Constructor khởi tạo vị trí và kích thước
    public GameObject(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    // Getter và Setter cho vị trí và kích thước    
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // Phương thức để vẽ đối tượng (Cần được override trong lớp con)
    public abstract void render(Graphics2D g2);

    // Phương thức để cập nhật trạng thái của đối tượng (Cần được override trong lớp con)
    // public abstract void update();

    // Phương thức kiểm tra va chạm (Dùng cho Object Dino và Item)
    public boolean isColliding(GameObject other) {
        Rectangle thisRect = new Rectangle(this.posX, this.posY, this.width, this.height);
        Rectangle otherRect = new Rectangle(other.getPosX(), other.getPosY(), other.getWidth(), other.getHeight());
        return thisRect.intersects(otherRect); // Kiểm tra va chạm giữa hai hình chữ nhật
    }

}