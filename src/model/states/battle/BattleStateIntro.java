package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleStateIntro implements IState {

    private Handler handler;
    private Player player;

    public BattleStateIntro(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end BattleStateIntro(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        System.out.println("BattleStateIntro.tick()");

        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateIntro.tick()... up");
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateIntro.tick()... down");
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("BattleStateIntro.tick()... left");
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("BattleStateIntro.tick()... right");
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateIntro.tick()... aButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine state = battleState.getStateMachine();

                state.push(
                        state.getIState("BattleStateMenu"), null
                );
            }
            ///////////////////////////////
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateIntro.tick()... bButton");
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                handler.getGame().getHeight(), 2, 2, 2+159, 2+145, null);

        FontGrabber.renderString(g, "Cannabis", 190, 411, 40, 40);

        g.drawImage(Assets.fontHashMap.get("6"), 10, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("7"), 50, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("8"), 90, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("M"), 130, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("m"), 170, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("z"), 210, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("C"), 250, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("r"), 290, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("i"), 330, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("t"), 370, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("t"), 410, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("e"), 450, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("r"), 490, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("N"), 530, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("a"), 570, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("b"), 610, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("b"), 650, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("i"), 690, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("i"), 730, 200, 10*4, 10*4, null);
        g.drawImage(Assets.fontHashMap.get("g"), 770, 200, 10*4, 10*4, null);

        //First critterBallSprite (index == 0).
        g.drawImage(Assets.critterBallSprite, 90*4, 76*4, 7*4, 7*4, null);
        //Second critterBallSprite (index == 1).
        g.drawImage(Assets.critterBallSprite, 98*4, 76*4, 7*4, 7*4, null);
        //Third critterBallSprite (index == 2).
        g.drawImage(Assets.critterBallSprite, 106*4, 76*4, 7*4, 7*4, null);
        //Fourth critterBallSprite (index == 3).
        g.drawImage(Assets.critterBallSprite, 114*4, 76*4, 7*4, 7*4, null);
        //Fifth critterBallSprite (index == 4).
        //g.drawImage(Assets.critterBallSprite, 122*4, 76*4, 7*4, 7*4, null);
        //Sixth critterBallSprite (index == 5).
        //g.drawImage(Assets.critterBallSprite, 130*4, 76*4, 7*4, 7*4, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end BattleStateIntro ****