package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BattleStateOutro implements IState {

    private Handler handler;
    private Player player;

    public BattleStateOutro(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end BattleStateOutro(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        System.out.println("BattleStateOutro.tick()");

        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateOutro.tick()... up");
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateOutro.tick()... down");
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("BattleStateOutro.tick()... left");
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("BattleStateOutro.tick()... right");
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateOutro.tick()... aButton");

            ((BattleStateMenu)((BattleState)handler.getStateManager().getIState("BattleState")).getStateMachine().getIState("BattleStateMenu")).resetIndexForBattleStateMenu();
            //returning to GameState.
            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                //pop self (BattleStateOutro).
                stateMachine.pop();
                //pop BattleStateRun.
                stateMachine.pop();
                //pop BattleStateMenu.
                stateMachine.pop();
                //now at BattleStateIntro for BattleState.state (a state machine using stack structure).
                //bringing StateManager back to GameState from BattleState.
                if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                    handler.getStateManager().pop();
                }
            }
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateOutro.tick()... bButton");
        }
    }

    int counter = 0;
    int counterTarget = 60;
    int colIndex = 0;
    int rowIndex = 0;
    int xRand = 0;
    int yRand = 0;
    Random rand = new Random();
    @Override
    public void render(Graphics g) {
        counter++;
        if (counter == counterTarget) {
            //g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
            //        handler.getGame().getHeight(), 320, 146, 320 + 159, 146 + 145, null);
            g.clearRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());

            //@@@@@@@@@@@@@@TESTER for Assets.nabbersBufferedImageNestedArray@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            for (int y = 0; y < 6; y++) {
                for (int x = 0; x < 8; x++) {
                    g.drawImage(Assets.nabbersBufferedImageNestedArray[y][x],
                            (x * 68) + (x * 1) + 1, (y * 64) + (y * 2) + 2, null);
                }
            }

            //HORIZONTAL-row moving down-until-resetting
            for (int x = 0; x < 8; x++) {
                g.drawImage(Assets.nabbersBufferedImageNestedArray[yRand][xRand],
                        (x * 68) + (x * 1) + (1), (rowIndex * 64) + (rowIndex * 2) + (2), null);
            }
            rowIndex++;
            if (rowIndex >= 6) {
                rowIndex = 0;
            }
            //VERTICAL-column moving right-until-resetting
            for (int y = 0; y < 6; y++) {
                g.drawImage(Assets.nabbersBufferedImageNestedArray[yRand][xRand],
                        (colIndex * 68) + (colIndex * 1) + (1), (y * 64) + (y * 2) + (2), null);
            }
            colIndex++;
            if (colIndex >= 8) {
                colIndex = 0;
                ////////////////////////////////
                xRand = rand.nextInt(8);
                yRand = rand.nextInt(6);
                ////////////////////////////////
            }

            counter = 0;
        }


        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end BattleStateOutro class ****