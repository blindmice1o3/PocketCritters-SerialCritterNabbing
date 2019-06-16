package main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right, aButton, bButton, startButton, selectButton;

    public KeyManager() {
        keys = new boolean[256];
    }

    public void updateInput() {
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];

        aButton = keys[KeyEvent.VK_COMMA];
        bButton = keys[KeyEvent.VK_PERIOD];
        startButton = keys[KeyEvent.VK_ENTER];
        selectButton = keys[KeyEvent.VK_SHIFT];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

} // **** end KeyManager class ****