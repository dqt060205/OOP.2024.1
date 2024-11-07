package com.group19.effect;

import java.awt.Frame;
import java.util.Hashtable;

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

}
