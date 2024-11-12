package com.group19.effect;

import java.util.ArrayList;

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
        
    }

}
