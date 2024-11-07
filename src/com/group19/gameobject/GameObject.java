package com.group19.gameobject;

public abstract class GameObject {

    private float posX;
    private float posY;
    
    public GameObject(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    abstract void Draw();

    abstract void Update();

}
