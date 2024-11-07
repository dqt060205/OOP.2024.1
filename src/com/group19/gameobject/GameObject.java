package com.group19.gameobject;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

    /*private float posX;
    private float posY;
    public GameObject() {
        
    }
    public GameObject(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    abstract void draw();

    abstract void update();
    */
    protected float posX, posY; // Vị trí của đối tượng
    protected float width, height; // Kích thước của đối tượng
    
    // Constructor khởi tạo vị trí và kích thước
    public GameObject(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    // Getter và Setter cho vị trí và kích thước    
    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    // Phương thức để vẽ đối tượng (Cần được override trong lớp con)
    public abstract void render(Graphics g);

    // Phương thức để cập nhật trạng thái của đối tượng (Cần được override trong lớp con)
    public abstract void update();

    // Phương thức kiểm tra va chạm (Dùng cho Object Dino và Item)
    public boolean isColliding(GameObject other) {
        // Khởi tạo đối tượng Rectangle cho cả đối tượng hiện tại và đối tượng khác
        Rectangle thisRect = new Rectangle((int)this.posX, (int)this.posY, (int)this.width, (int)this.height);
        Rectangle otherRect = new Rectangle((int)other.getPosX(), (int)other.getPosY(), (int)other.getWidth(), (int)other.getHeight());
        return thisRect.intersects(otherRect); // Kiểm tra va chạm giữa hai hình chữ nhật
    }

}
