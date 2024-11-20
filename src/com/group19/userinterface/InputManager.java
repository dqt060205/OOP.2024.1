package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class InputManager {

    private Dinosaur dino;

    public InputManager() throws IOException {
        dino = new Dinosaur(700, 750);
    }

    public void processKeyPressed(int keyCode){

        switch(keyCode){

            case KeyEvent.VK_LEFT:
                dino.setIsTurningLeft(true);
                dino.setIsRunning(true);
                dino.run();
                break;

            case KeyEvent.VK_RIGHT:
                dino.setIsTurningLeft(false);
                dino.setIsRunning(true);
                dino.run();
                break;

            case KeyEvent.VK_UP:
                dino.startJump();
                dino.jump();
                break;

            case KeyEvent.VK_DOWN:
                // Do nothing
                break;

        }

    }

    public void processKeyReleased(int keyCode){

        switch(keyCode){

            case KeyEvent.VK_LEFT:
                dino.setIsRunning(false);
                break;

            case KeyEvent.VK_RIGHT:
                dino.setIsRunning(false);
                break;

            case KeyEvent.VK_UP:

                break;

            case KeyEvent.VK_DOWN:
            
                break;

        }

    }

}
