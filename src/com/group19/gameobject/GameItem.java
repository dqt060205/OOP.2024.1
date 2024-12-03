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
            //Item bao ve
            case 5 -> {
                super.setImage("data/Buff_shield.png");
            }
            //Item x2 diem
            case 6 -> {
                super.setImage("data/Buff_x2.png");
            }
            //Item giam toc do roi diem
            case 7 -> {
                super.setImage("data/Buff_slowdown.png");
            }
        }
    }
    @Override
    public void updateSpeed() {
        int baseSpeed = 2;
        int temp = this.getValue();
        int speed = baseSpeed + temp;
        this.setSpeed(speed); // Tốc độ rơi tuỳ thuộc vào giá trị điểm 
    }
    @Override
    public void update (Dinosaur dinosaur) {
        //this.updateSpeed();
        long currentTime = System.currentTimeMillis();
        //if(currentTime >= spawnTime) {
        	this.updateSpeed();
        	if(this.posY <= 800) {
        		this.falling(this.getSpeed());
        	}
        	
        	if (this.collidesWith(dinosaur) && this.isActive()) {
                System.out.println("Grade collected: " + this.getValue());
                this.setActive(false);  // Vô hiệu hóa GameItem nếu đã ăn
            }
        	
        //}
        
        super.update(dinosaur);
    }
    
    public boolean collidesWith(Dinosaur dinosaur) {
        return this.posX < dinosaur.getPosX() + dinosaur.getWidth() &&
               this.posX + this.getWidth() > dinosaur.getPosX() &&
               this.posY < dinosaur.getPosY() + dinosaur.getHeight() &&
               this.posY + this.getHeight() > dinosaur.getPosY();
    }
    
    @Override
    public void render(Graphics2D g2) {        // Đã render (draw) hình ảnh là ảnh của điểm trong folder data
        if (this.isActive() == true)  g2.drawImage(this.getImage(), posX, posY, null);
        //this.setActive(true);
    }
}

/*package com.group19.gameobject;

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
    }
    public GameItem(int value,int posX, int posY, int width, int height) {
        super(value, posX, posY, width, height);
        setActive(true);
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
            case 5 -> { //Item bao ve
                super.setImage(imagePath:"");
            }
            case 6 -> { //Item x2 diem
                super.setImage(imagePath:"");
            }
            case 7 -> { //Item giam toc do roi diem
                super.setImage(imagePath:""); 
            } 
        }
    }
    @Override
    public void updateSpeed() {
        int baseSpeed = 2;
        int temp = this.getValue();
        int speed = baseSpeed + temp;
        this.setSpeed(speed); // Tốc độ rơi tuỳ thuộc vào giá trị điểm 
    }
    @Override
    public void update (Dinosaur dinosaur) {
        this.updateSpeed();
        if (this.posY <= 800) {       
            // Chưa có map nên là để tạm theo SCREEN_HEIGHT
            this.falling(this.getSpeed()); // Khi chưa chạm biên thì rơi theo tốc độ  
        }
        super.update(dinosaur);    
    }
    @Override
    public void render(Graphics2D g2) {        // Đã render (draw) hình ảnh là ảnh của điểm trong folder data
        if (this.isActive() == true)  g2.drawImage(this.getImage(), posX, posY, null);
    }
    public void remove() {
        this.isActive = false;
    }
    public boolean isOutOfScreen() {
        return this.height > 800; // Biến mất nếu rơi ra khỏi màn hình
    }

}
}*/
