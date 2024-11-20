package com.group19.effect;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;

public class CacheDataLoader {

    private static CacheDataLoader instance = null;

    public static final int X_FRAME = 9;
    public static final int Y_FRAME = 20;
    public static final int WIDTH_FRAME = 130;
    public static final int HEIGHT_FRAME = 115;

    private String framefile = "data/frame.txt";
    private String animationfile = "data/animation.txt";

    private Hashtable<String, FrameImage> frameImages;
    private Hashtable<String, Animation> animations;

    private CacheDataLoader() {}

    public static CacheDataLoader getInstance(){
        if(instance == null){
            instance = new CacheDataLoader();
        }
        return instance;
    }

    public FrameImage getFrameImage(String name){

        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;

    }

    public Animation getAnimation(String name){
        Animation animation = new Animation(instance.animations.get(name));
        return animation;
    }

    public void LoadData() throws IOException{

        LoadFrame();
        LoadAnimation();

    }

    public void LoadFrame() throws IOException{

        frameImages = new Hashtable<String, FrameImage>();

        FileReader fr = new FileReader(framefile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if(br.readLine() == null){
            System.out.println("No data");
            throw new IOException();
        }
        else{

            while((line = br.readLine()).equals(""));

            int n = Integer.parseInt(line);
            String path = null;
            BufferedImage imageData = null;

            for(int i=0; i<n; i++){

                FrameImage frame = new FrameImage();
                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                frame.setName(str[0]);
                path = str[1];

                imageData = ImageIO.read(new File(path));
                if(imageData != null){
                    BufferedImage image = imageData.getSubimage(X_FRAME, Y_FRAME, WIDTH_FRAME, HEIGHT_FRAME);
                    frame.setImage(image);
                }

                instance.frameImages.put(frame.getName(), frame);

            }

        }

        br.close();

    }

    public void LoadAnimation() throws IOException {

        animations = new Hashtable<String, Animation>();

        FileReader fr = new FileReader(animationfile);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        if(line == null){
            System.out.println("No data");
            throw new IOException();
        }
        else {

            while((line = br.readLine()).equals(""));
            int n = Integer.parseInt(line);

            for(int i=0; i<n; i++){

                while((line = br.readLine()).equals(""));
                Animation animation = new Animation();
                animation.setName(line);
                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                for(int j=0; j<str.length; j+=2){
                    animation.add(getFrameImage(str[j]), Double.parseDouble(str[j+1]));
                }
                animations.put(animation.getName(), animation);

            }

        }

        br.close();

    }

}
