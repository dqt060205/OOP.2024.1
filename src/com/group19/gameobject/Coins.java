package com.group19.gameobject;

import java.awt.Graphics;
import java.awt.Image;

public class Coins extends Item {
    public int totalCoins;
    public Coins() {
        super.setValue(1);
    }
    public Coins(Image image, int value, int posX, int posY, int width, int height) {
        super(image, value, posX, posY, width, height);
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
