package model.states.menu;

import main.Handler;
import main.utils.SerializationDoer;
import model.entities.Player;
import model.states.IState;
import model.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStateSave implements IState {

    private Handler handler;
    private Player player;
    private boolean pressed = false;

    public MenuStateSave(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStateSave(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            ///////////////
            pressed = true;
            ///////////////


            SerializationDoer writer = new SerializationDoer(handler);
            writer.saveWriteToFile();


            ///////////////////////////////
            Object[] args = { player };
            handler.getStateManager().change("GameState", args);
            ///////////////////////////////
        } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            ///////////////////////////////
            Object[] args = { player };
            handler.getStateManager().change("MenuStateMenu", args);
            ///////////////////////////////
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(100, 100, 200, 100);
        g.setColor(Color.BLACK);
        if (pressed) {
            g.drawString("Saving...", 140, 140);
        } else {
            g.drawString("Press aButton to save game.", 120, 120);
            g.drawString("Press bButton to return to menu.", 120, 140);
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {
        pressed = false;
    }

} // **** end MenuStateSave class ****