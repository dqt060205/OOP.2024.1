package com.group19.effect;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Frame;
import java.awt.Graphics2D;

public class Animation {

    private String name;
    private ArrayList<FrameImage> frameImages;
    private int currentFrame;
    private ArrayList<Double> delayFrames;
    private long beginTime;

    public Animation(){
        frameImages = new ArrayList<FrameImage>();
        delayFrames = new ArrayList<Double>();
        currentFrame = 0;
        beginTime = 0;
    }

    public Animation(Animation animation){
        frameImages = new ArrayList<FrameImage>();
        for(FrameImage f : animation.frameImages){
            frameImages.add(new FrameImage(f)); //Tạo 1 bản sao của FrameImage f
        }

        delayFrames = new ArrayList<Double>();
        for(Double d : animation.delayFrames){
            delayFrames.add(d);
        }

        currentFrame = animation.currentFrame;
        beginTime = animation.beginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        if(currentFrame >= 0 && currentFrame < frameImages.size()){
            this.currentFrame = currentFrame;
        }
        else {
            this.currentFrame = 0;
        }
    }

    public void reset(){
        currentFrame = 0;
        beginTime = 0;
    }

    public BufferedImage getCurrentImage(){
        return frameImages.get(currentFrame).getImage();
    }

    public void add(FrameImage frameImage, double timeToNextFrame){
        frameImages.add(frameImage);
        delayFrames.add(timeToNextFrame);
    }

    

}
