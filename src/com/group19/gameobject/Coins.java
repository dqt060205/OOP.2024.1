package com.group19.gameobject;

import java.awt.Graphics2D;

public class Coins extends Item {
    public int totalCoins;
    public Coins() {
        super.setValue(1);
    }
    public Coins(int value, int posX, int posY, int width, int height) {
        super(value, posX, posY, width, height);
        super.setValue(1);
    }
    public void increaseCoins() {
        totalCoins++;
    }
    public void useCoins() {
        totalCoins--;
    }
    @Override
    public void updateSpeed() {
        this.setSpeed(20); //Coins có tốc độ rơi cố định = 20 (Tạm thời)
    }

    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Graphics2D g2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
