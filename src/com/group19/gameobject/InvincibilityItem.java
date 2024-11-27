package com.group19.gameobject;

import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;


public class InvincibilityItem extends Item {
    public InvincibilityItem(Image image) { 
        super.randomPosition();
        this.setWidth(100);
        this.setHeight(100);
        this.setImage(.....);
        setActive(true);
    }
    public InvincibilityItem(Image image, int value, int posX, int posY, int width, int height) {
        super(image, posX, posY, width, height);
        setActive(true);
    }

    @Override
    public void update() {
        
        
    }

    @Override
    public void updateSpeed() {
       
        
    }

    @Override
    public void render(Graphics2D g2) {
        // TODO Auto-generated method stub
        
    }
    

}
