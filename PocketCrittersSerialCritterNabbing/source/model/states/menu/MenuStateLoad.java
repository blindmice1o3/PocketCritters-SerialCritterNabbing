package model.states.menu;

import main.Handler;
import main.utils.SerializationDoer;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;
import model.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStateLoad implements IState {

    private Handler handler;
    private Player player;
    private boolean pressed = false;

    public MenuStateLoad(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStateLoad(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            ///////////////
            pressed = true;
            ///////////////


            SerializationDoer loader = new SerializationDoer(handler);
            loader.loadReadFromFile();

            //returning to GameState.
            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuStateLoad).
                stateMachine.pop();
                //now MenuStateMenu.
                //pop MenuState.
                handler.getStateManager().pop();
            }
            ///////////////////////////////
        } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuStateLoad).
                stateMachine.pop();
                //now MenuStateMenu.
            }
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(100, 100, 200, 100);
        g.setColor(Color.BLACK);
        if (pressed) {
            g.drawString("Loading...", 140, 140);
        } else {
            g.drawString("Press aButton to load game.", 120, 120);
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

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

} // **** end MenuStateLoad class ****