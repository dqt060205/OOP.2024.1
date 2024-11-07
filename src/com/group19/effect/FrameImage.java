package com.group19.effect;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FrameImage {

    private String name;
    private BufferedImage image;

    public FrameImage(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getWidthImage(){
        return image.getWidth();
    }

    public int getHeightImage(){
        return image.getHeight();
    }

    public void draw(int x, int y, Graphics2D g2){

        g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);

    }

}
