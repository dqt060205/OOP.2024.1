package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;

public class InputManager {

    private Dinosaur dino;

    public InputManager(){
        dino = new Dinosaur(700, 750);
    }

    public void processKeyPressed(int keyCode){

        switch(keyCode){

            case KeyEvent.VK_LEFT:
                dino.setIsRunningLeft(true);
                break;

            case KeyEvent.VK_RIGHT:
                dino.setIsRunningRight(true);
                break;

            case KeyEvent.VK_UP:

                break;

            case KeyEvent.VK_DOWN:
            
                break;

        }

    }

    public void processKeyReleased(int keyCode){

        switch(keyCode){

            case KeyEvent.VK_LEFT:
                dino.setIsRunningLeft(false);
                break;

            case KeyEvent.VK_RIGHT:
                dino.setIsRunningRight(false);
                break;

            case KeyEvent.VK_UP:

                break;

            case KeyEvent.VK_DOWN:
            
                break;

        }

    }

}
