package model.states.menu;

import main.Handler;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStatePlayerStats implements IState {

    private Handler handler;
    private Player player;

    public MenuStatePlayerStats(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStatePlayerStats(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuStatePlayerStats.tick()... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuStatePlayerStats).
                stateMachine.pop();
                //now MenuStateMenu.
            }
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end MenuStatePlayerStats class ****