package model.states.computer;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.states.IState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ComputerState implements IState {

    private Handler handler;
    private Player player;

    public ComputerState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end ComputerState(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("ComputerState.tick(long) bButton pressed: game.stateManager.pop()");

            ////////////////////////////////
            handler.getStateManager().pop();
            ////////////////////////////////
        }

    }

    @Override
    public void render(Graphics g) {
        int widthComputerKeyboard = (handler.getGame().getWidth() / 4);
        int heightComputerKeyboard = (handler.getGame().getHeight() / 4);
        int xCenterScreen = (handler.getGame().getWidth() / 2);
        int yCenterScreen = (handler.getGame().getHeight() / 2);

        g.drawImage( Assets.computerWithKeyboard,
                (xCenterScreen - (widthComputerKeyboard / 2)),
                (yCenterScreen - (heightComputerKeyboard / 2)),
                widthComputerKeyboard,
                heightComputerKeyboard, null );
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

} // **** end ComputerState class ****