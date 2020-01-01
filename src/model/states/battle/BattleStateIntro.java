package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
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
        //System.out.println("BattleStateIntro.tick()");

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
                StateMachine stateMachine = battleState.getStateMachine();

                //Object[] args = {critterOfOpponent};
                stateMachine.push(
                        stateMachine.getIState("BattleStateMenu"), null
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
        g.drawImage(Assets.backgroundBattleStateIntro, 0, 0,
                handler.getGame().getWidth(), handler.getGame().getHeight(), null);

        Critter critterOfOpponent = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
        int xOffset = 9;
        int yOffset = 5;
        FontGrabber.renderString(g, critterOfOpponent.getNameColloquial(), xOffset, yOffset, 28, 28);
        yOffset += 32;
        xOffset += 110;
        FontGrabber.renderString(g, ":L" + critterOfOpponent.getLevel(), xOffset, yOffset, 28, 28);
        xOffset = (handler.getGame().getWidth() / 2) + 49;
        yOffset = 5;
        g.drawImage(critterOfOpponent.getSpeciesIcon(), xOffset, yOffset,
                (handler.getGame().getWidth() - xOffset - 5), ((handler.getGame().getHeight() / 3) + 20), null);

        FontGrabber.renderString(g, critterOfOpponent.getNameColloquial(), 174, 418, 37, 37);

        int xFirstCritterBall = 359;
        int yFirstCritterBall = 304;
        for (int i = 0; i < player.getCritterBeltList().size(); i++) {
            if (player.getCritterBeltList().get(i).getStatus() == Critter.StatusConditionNonVolatile.OK) {
                g.drawImage(Assets.critterBallSprite, xFirstCritterBall, yFirstCritterBall, 28, 28, null);
                xFirstCritterBall += 32;
            } else {
                //TODO: get different critter-ball sprite for non-OK Critter instances.
                g.drawImage(Assets.cursorSprite, xFirstCritterBall, yFirstCritterBall, 28, 28, null);
                xFirstCritterBall += 32;
            }
        }

        /*
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
        */
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

} // **** end BattleStateIntro ****