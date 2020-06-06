package main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right, aButton, bButton, startButton, selectButton;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void tick() {
        //update for justPressed boolean[]. Used for one key press per press/release instead of multiple calls if held down over multiple tick().
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if(!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }

        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];

        aButton = keys[KeyEvent.VK_COMMA];
        bButton = keys[KeyEvent.VK_PERIOD];
        startButton = keys[KeyEvent.VK_ENTER];
        selectButton = keys[KeyEvent.VK_SHIFT];
    }

    public boolean keyJustPressed(int keyCode) {
        if ( (keyCode < 0) || (keyCode >= keys.length) ) {
            return false;
        }

        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ( (e.getKeyCode() < 0) || (e.getKeyCode() >= keys.length)) {
            return;
        }

        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if ( (e.getKeyCode() < 0) || (e.getKeyCode() >= keys.length)) {
            return;
        }

        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

} // **** end KeyManager class ****