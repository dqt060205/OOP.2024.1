package com.group19.userinterface;

import com.group19.gameobject.Dinosaur;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, Runnable {

    private Thread thread;
    private boolean isRunning = true;
    private InputManager inputManager;
    private BufferedImage bufImage;
    private Graphics2D buf2D;

    public GamePanel() throws IOException{
        inputManager = new InputManager(new Dinosaur());
    }

    public void startGame(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;
        long period = 1000000000/80;

        while(isRunning){
            //Update, render and repaint

            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);
            try{
                if(sleepTime > 0){
                    Thread.sleep(sleepTime/1000000);
                }
                else{
                    Thread.sleep(sleepTime/2000000);
                }
            }
            catch(Exception e){

            }

            previousTime = System.nanoTime();

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.processKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.processKeyReleased(e.getKeyCode());
    }

}
