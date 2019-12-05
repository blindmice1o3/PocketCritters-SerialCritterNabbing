package model.states.game_console;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.states.IState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameConsoleState implements IState {

    private Handler handler;
    private Player player;

    public GameConsoleState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end GameConsoleState(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("GameConsoleState.tick(long) bButton pressed: game.stateManager.pop()");

            ////////////////////////////////
            handler.getStateManager().pop();
            ////////////////////////////////
        }

    }

    @Override
    public void render(Graphics g) {
        int widthGameConsole = (handler.getGame().getWidth() / 4);
        int heightGameConsole = (handler.getGame().getHeight() / 4);
        int xCenterScreen = (handler.getGame().getWidth() / 2);
        int yCenterScreen = (handler.getGame().getHeight() / 2);

        g.drawImage( Assets.gameConsole,
                (xCenterScreen - (widthGameConsole / 2)),
                (yCenterScreen - (heightGameConsole / 2)),
                widthGameConsole,
                heightGameConsole, null );
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end GameConsoleState class ****