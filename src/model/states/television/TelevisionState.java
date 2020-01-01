package model.states.television;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.states.IState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TelevisionState implements IState {

    private Handler handler;
    private Player player;

    public TelevisionState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end TelevisionState(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("TelevisionState.tick(long) bButton pressed: game.stateManager.pop()");

            ////////////////////////////////
            handler.getStateManager().pop();
            ////////////////////////////////
        }

    }

    @Override
    public void render(Graphics g) {
        int widthTelevision = (handler.getGame().getWidth() / 4);
        int heightTelevision = (handler.getGame().getHeight() / 4);
        int xCenterScreen = (handler.getGame().getWidth() / 2);
        int yCenterScreen = (handler.getGame().getHeight() / 2);

        g.drawImage( Assets.television,
                (xCenterScreen - (widthTelevision / 2)),
                (yCenterScreen - (heightTelevision / 2)),
                widthTelevision,
                heightTelevision, null );
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    // GETTERS AND SETTERS

    public void setPlayer(Player player) { this.player = player; }

} // **** end TelevisionState class ****