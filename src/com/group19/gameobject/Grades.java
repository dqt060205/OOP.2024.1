package com.group19.gameobject;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSpeed'");
    }

    

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    
    
}
