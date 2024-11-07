package com.group19.gameobject;

import java.awt.Image;

public class Coins extends Item {
    public int totalCoins;
    public Coins() {
        super.setValue(1);
    }
    public Coins(Image image, int value, float posX, float posY) {
        super(image, value, posX, posY);
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
    void draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
