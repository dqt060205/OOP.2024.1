package com.group19.gameobject;

import java.awt.Graphics2D;

public class GameItem extends Item {
    public GameItem() {
    }
    
    public GameItem(int value) { // Constructor để gen mặc định toàn bộ thuộc tính (chỉ nhập vào value)
        super.setValue(value);
        super.randomPosition();
        this.setWidth(100);
        this.setHeight(100);
        this.setImage(value);
        setActive(true);
        //this.spawnTime = spawnTime;
    }
    public GameItem(int value,int posX, int posY, int width, int height) {
        super(value, posX, posY, width, height);
        setActive(true);
    }
    /* 
    @Override
    public int getSpawnTime() {
    	return getSpawnTime();
    }
    */
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
            case 5 -> { //Item bao ve
                super.setImage("data/Buff_shield.png");
            }
            case 6 -> { //Item x2 diem
                super.setImage("data/Buff_x2.png");
            }
            case 7 -> { //Item giam toc do roi diem
                super.setImage("data/Buff_slowdown.png"); 
            } 
        }
    }
    public void updateSpeed(Dinosaur dino) {
        double temp;
        if (this.getValue() >= 5 || this.getValue() == 0) {
            temp = 2.2;
        }
        else {
            temp = ((double) this.getValue()) / 2 + 1;
        }
        if (dino.isSlowedDown()) {
            this.setSpeed(temp*2/3);
        }
        else this.setSpeed(temp); // Tốc độ rơi tuỳ thuộc vào giá trị điểm 
    }
    @Override
    public void update (Dinosaur dinosaur) {
        this.updateSpeed(dinosaur);
        long currentTime = System.currentTimeMillis();
        //if(currentTime >= spawnTime) {
        	this.updateSpeed(dinosaur);
        	if(this.posY <= 640 - this.getHeight()) {
        		this.falling(this.getSpeed());
        	}
        	
        	if (this.collidesWith(dinosaur)) {
                System.out.println("Collision!!!");
            switch (this.getValue()) {
                case 0 -> {if (!dinosaur.isShielded()) {
                    dinosaur.setLives(dinosaur.getLives()-1);
                }}
                case 6 -> dinosaur.activateX2Score();
                case 5 -> dinosaur.activateShielded();
                case 7 -> dinosaur.activateSlowedDown();
                default -> {
                }
            }
                System.out.println("Grade collected: " + this.getValue());
                this.setActive(false);  // Vô hiệu hóa GameItem nếu đã ăn
            }
        	
        //}
        
        super.update(dinosaur);
    }
    public int collect() {
        if ((0 <= this.getValue()) && (this.getValue() <= 4)) return this.getValue();
        else return 0;
    }
    public boolean collidesWith(Dinosaur dinosaur) {
        return this.posX < dinosaur.getPosX() + dinosaur.getWidth() &&
               this.posX + this.getWidth() > dinosaur.getPosX() &&
               this.posY < dinosaur.getPosY() + dinosaur.getHeight() &&
               this.posY + this.getHeight() > dinosaur.getPosY();
    }
    
    @Override
    public void render(Graphics2D g2) {        // Đã render (draw) hình ảnh là ảnh của điểm trong folder data
        if (this.isActive())  g2.drawImage(this.getImage(), posX, posY, null);
        //this.setActive(true);
    }
}

