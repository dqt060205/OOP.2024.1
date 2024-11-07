package com.group19.gameobject;

import java.awt.Image;

public class Grades extends Item {
    private char name;

    public Grades(Image image, int value,float posX, float posY) {
        super(image, value, posX, posY);
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
    void draw() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    
    
}
