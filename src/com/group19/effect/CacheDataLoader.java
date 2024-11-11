package com.group19.effect;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.awt.Frame;
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

    private Hashtable<String, FrameImage> frameImages;

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

    public void LoadData() throws IOException{

        LoadFrame();

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
            String[] str = new String[70]; //Dùng để lưu tên và filepath
            int currentStrIndex = 0;

            for(int i=0; i<n; i++){

                FrameImage frame = new FrameImage();
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                frame.setName(str[currentStrIndex]);
                currentStrIndex++;
                path = str[currentStrIndex];
                currentStrIndex++;

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

}
