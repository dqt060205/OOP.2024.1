package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JPanel;

public class InputManager {

    private Dinosaur dino;
    private JPanel contentPane;
    private inforPage inforPage;

    public InputManager(Dinosaur dino) throws IOException {
        this.dino = dino;
    }

    public InputManager(inforPage inforPage) throws IOException {
        this.inforPage = inforPage;
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
            case KeyEvent.VK_RIGHT:
                dino.setIsRunning(false);
                break;

            case KeyEvent.VK_UP:
                break;

            case KeyEvent.VK_DOWN:
            
                break;

        }

    }

    /*public void processMouseClicked(MouseEvent e){

        int posXClicked = e.getX();
        int posYClicked = e.getY();

        if((posXClicked >=570 && posXClicked <= 570 + inforPage.instructionIcon.getIconWidth()) && (posYClicked >= 565 && posYClicked <= 565 + inforPage.instructionIcon.getIconHeight())){
            inforPage.setDrawInstructionBackground(true);
            inforPage.setDrawMainBackground(false);
            contentPane.repaint();
        }

    }*/

}
