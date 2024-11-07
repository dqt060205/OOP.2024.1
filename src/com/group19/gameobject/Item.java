package com.group19.gameobject;
import java.awt.Image;
import java.util.Random;

public abstract class Item extends GameObject {

    private int value;
    private int speed;
    private Image image;
    private int Item_size = 100;
    private Random random;

    public Item() {

    }
    public Item(Image image, int value, float posX, float posY) {
        super(posX, posY);
        this.value = value;
        this.image = image;
        this.random = new Random();
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public void randomPosition() {
        setPosX(random.nextInt(100,1300));
        setPosY(random.nextInt(100, 700));
    }
    public abstract void updateSpeed();
    @Override
    abstract void draw();

    @Override
    abstract void update();


    
}
