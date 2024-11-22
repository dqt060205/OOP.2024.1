package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InputManager {

    private Dinosaur dino;
    private JPanel contentPane;
    private InforPage inforPage;

    public InputManager(Dinosaur dino) throws IOException {
        this.dino = dino;
    }

    public InputManager(InforPage inforPage) throws IOException {
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

    public void addMouseListeners(JButton musicButton, JButton continueButton, JButton newGameButton, JButton instructionButton, JButton aboutUsButton, JButton returnButton){

        newGameButton.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent e){
                
            }

        });

    }

}
