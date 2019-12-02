package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleStateFight implements IState {

    private Handler handler;
    private Player player;

    public BattleStateFight(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end BattleStateFight(Handler, Player) constructor ****

    //while-loop and track whose turn it is.
    @Override
    public void tick(long timeElapsed) {
        System.out.println("BattleStateFight.tick()");

        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateFight.tick()... up");
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateFight.tick()... down");
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("BattleStateFight.tick()... left");
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("BattleStateFight.tick()... right");
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateFight.tick()... aButton");
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateFight.tick()... bButton");


            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine state = battleState.getStateMachine();

                state.pop();
            }
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                handler.getGame().getHeight(), 161, 146, 161+159, 146+145, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end BattleStateFight class ****