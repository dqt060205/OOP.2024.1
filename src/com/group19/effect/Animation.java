package com.group19.effect;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Graphics2D;

public class Animation {

    private String name;
    private ArrayList<FrameImage> frameImages;
    private int currentFrame;
    private ArrayList<Double> delayFrames;
    private long beginTime;
    private boolean isRepeated;

    public Animation(){
        frameImages = new ArrayList<FrameImage>();
        delayFrames = new ArrayList<Double>();
        currentFrame = 0;
        beginTime = 0;
        isRepeated = true;
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
        isRepeated = animation.isRepeated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsRepeated() {
        return isRepeated;
    }

    public void setIsRepeated(boolean isRepeated) {
        this.isRepeated = isRepeated;
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

    public void nextFrame(){
        if(currentFrame >= frameImages.size()-1){
            if(isRepeated) currentFrame = 0;
        }
        else {
            currentFrame++;
        }
    }

    public void update(long currentTime){
        if(beginTime == 0){
            beginTime = currentTime;
        }
        else{
            if(currentTime - beginTime > delayFrames.get(currentFrame)){
                nextFrame();
                beginTime = currentTime;
            }
        }
    }

    public boolean isLastFrame(){
        if(currentFrame == frameImages.size() - 1){
            return true;
        }
        return false;
    }

    public void flipAllImage(){

        for(int i=0; i<frameImages.size(); i++){

            BufferedImage image = frameImages.get(i).getImage();
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image, null);
            frameImages.get(i).setImage(image);

        }

    }

    public void draw(int x, int y, Graphics2D g2){

        BufferedImage image = getCurrentImage();
        g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
    
    }

}
