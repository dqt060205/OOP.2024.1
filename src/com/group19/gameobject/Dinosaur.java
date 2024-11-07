package com.group19.gameobject;

public class Dinosaur extends GameObject {

    public static final int Dino_Width = 100;
    public static final int Dino_Height = 200;
    public static final int speed = 10;
    private int live;
    private boolean state;

    public Dinosaur(float posX, float posY) {
        super(posX, posY);
        this.live = 3;
        this.state = true;
    }

    public int getLive() {
        return live;
    }                                                                                                                                                                   

    public void setLive(int live) {
        this.live = live;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    

    @Override
    void Draw() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Draw'");
    }

    @Override
    void Update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

}
