package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.states.IState;
import model.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleStateCritterBeltList implements IState {

    private Handler handler;
    private Player player;

    private int index;

    public BattleStateCritterBeltList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        index = 0;
    } // **** end BattleStateCritterBeltList(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        System.out.println("BattleStateCritterBeltList.tick()");

        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateCritterBeltList.tick()... up");

            index--;

            if (index < 0) {
                index = (player.getCritterBeltList().length-1);
            }
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateCritterBeltList.tick()... down");

            index++;

            if (index >= player.getCritterBeltList().length) {
                index = 0;
            }
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("BattleStateCritterBeltList.tick()... left");
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("BattleStateCritterBeltList.tick()... right");
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateCritterBeltList.tick()... aButton");

            System.out.println( "Critter selected: " + player.getCritterBeltList()[index] );
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateCritterBeltList.tick()... bButton");

            ///////////////////////////////
            handler.getStateManager().pop();
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                handler.getGame().getHeight(), 320, 2, 320+159, 2+145, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end BattleStateCritterBeltList class ****