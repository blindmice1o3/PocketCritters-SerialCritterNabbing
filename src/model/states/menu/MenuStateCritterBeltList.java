package model.states.menu;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStateCritterBeltList implements IState {

    private Handler handler;
    private Player player;

    public MenuStateCritterBeltList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStateCritterBeltList(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuStateCritterBeltList.tick()... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuStateCritterBeltList).
                stateMachine.pop();
                //now MenuStateMenu.
            }
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.backgroundCritterBeltList,
                0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                0, 0, Assets.backgroundCritterBeltList.getWidth(), Assets.backgroundCritterBeltList.getHeight(), null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end MenuStateCritterBeltList class ****