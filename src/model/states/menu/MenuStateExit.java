package model.states.menu;

import main.Handler;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStateExit implements IState {

    private Handler handler;
    private Player player;

    public MenuStateExit(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStateExit(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //returning to GameState.
        ///////////////////////////////
        if (handler.getStateManager().getCurrentState() instanceof MenuState) {
            MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
            StateMachine stateMachine = menuState.getStateMachine();

            //pop self (MenuStateExit).
            stateMachine.pop();
            //now MenuStateMenu.
            //pop MenuState.
            handler.getStateManager().pop();
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

} // **** end MenuStateExit class ****