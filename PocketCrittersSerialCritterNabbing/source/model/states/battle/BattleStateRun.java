package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.entities.critters.Critter;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BattleStateRun implements IState {

    private Handler handler;
    private Player player;

    public BattleStateRun(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end BattleStateRun(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        System.out.println("BattleStateRun.tick()");

        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateRun.tick()... up");
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateRun.tick()... down");
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("BattleStateRun.tick()... left");
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("BattleStateRun.tick()... right");
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateRun.tick()... aButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                //Object[] args = { opponentCritter };
                stateMachine.push(
                        stateMachine.getIState("BattleStateOutro"), null
                );
            }
            ///////////////////////////////
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateRun.tick()... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                stateMachine.pop();
            }
            ///////////////////////////////
        }
    }

    int xCol = 0;
    int yRow = 0;
    Random rand = new Random();
    int counter = 0;
    int counterTarget = 60;
    @Override
    public void render(Graphics g) {
        //@@@@@ImageLoader.cropSpriteFromSpriteSheet(int, int, int, int, int, int, BufferedImage) tester@@@@@
        counter++;

        if (counter == counterTarget) {
            g.clearRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());

            for (int y = 0; y < 13; y++) {
                for (int x = 0; x < 12; x++) {
                    g.drawImage(Assets.crittersBufferedImageNestedArray[y][x],
                            (x * 56) + (x * 1) + 1, (y * 56) + (y * 1) + 1, null);
                }
            }

            xCol++;
            for (int y = 0; y < 13; y++) {
                g.drawImage(Assets.nabbersBufferedImageNestedArray[3][6],
                        (xCol * 56) + (xCol * 1) + 1, (y * 56) + (y * 1) + 1, null);
            }
            if (xCol >= 12) {
                xCol = 0;
            }

            yRow++;
            for (int x = 0; x < 12; x++) {
                g.drawImage(Assets.nabbersBufferedImageNestedArray[3][6],
                        (x * 56) + (x * 1) + 1, (yRow * 56) + (yRow * 1) + 1, null);
            }
            if (yRow >= 13) {
                yRow = 0;
            }

            counter = 0;
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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

} // **** end BattleStateRun class ****