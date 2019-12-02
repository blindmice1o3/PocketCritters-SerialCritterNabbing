package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleStateItemList implements IState {

    private Handler handler;
    private Player player;

    private int index = 0;

    public BattleStateItemList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end BattleStateItemList(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        System.out.println("BattleStateItemList.tick()");

        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateItemList.tick()... up");

            index--;

            if (index < 0) {
                index = (player.getInventory().size()-1);
            }
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateItemList.tick()... down");

            index++;

            if (index >= player.getInventory().size()) {
                index = 0;
            }
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("BattleStateItemList.tick()... left");
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("BattleStateItemList.tick()... right");
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateItemList.tick()... aButton");

            //System.out.println( "Item selected for use: " + player.getInventory().get(index) );
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateItemList.tick()... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                stateMachine.pop();
            }
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                handler.getGame().getHeight(), 2, 146, 2+159, 146+145, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end BattleStateItemList class ****