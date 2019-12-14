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

public class BattleStateMenu implements IState {

    private Handler handler;
    private Player player;

    private Critter opponentCritter;
    private Critter playerCritterFirst;

    private int xIndex, yIndex;
    private String[][] menuMatrix;

    public BattleStateMenu(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        xIndex = 0;
        yIndex = 0;
        menuMatrix = new String[2][2];
        menuMatrix[0][0] = "BattleStateFight";
        menuMatrix[0][1] = "BattleStateCritterBeltList";
        menuMatrix[1][0] = "BattleStateItemList";
        menuMatrix[1][1] = "BattleStateRun";
    } // **** end BattleStateMenu(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        System.out.println("BattleStateMenu.tick()");

        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateMenu.tick()... up");
            if (yIndex == 1) {
                yIndex--;
            }
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateMenu.tick()... down");
            if (yIndex == 0) {
                yIndex++;
            }
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("BattleStateMenu.tick()... left");
            if (xIndex == 1) {
                xIndex--;
            }
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("BattleStateMenu.tick()... right");
            if (xIndex == 0) {
                xIndex++;
            }
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateMenu.tick()... aButton");

            ////////////////////////////////////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                Object[] args = { opponentCritter };
                stateMachine.push(
                        stateMachine.getIState(menuMatrix[yIndex][xIndex]), args
                );
            }
            ////////////////////////////////////////////////////////////
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateMenu.tick()... bButton");
        }
    }

    public void resetIndexForBattleStateMenu() {
        xIndex = 0;
        yIndex = 0;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.battleStateSpriteSheet,
                0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                161, 2, 161+159, 2+145, null);

        //opponent's critter
        int xOffset = 10;
        int yOffset = 5;
        FontGrabber.renderString(g, opponentCritter.getNameColloquial(), xOffset, yOffset, 28, 28);
        yOffset += 32;
        xOffset += 80;
        FontGrabber.renderString(g, ":L" + opponentCritter.getLevel(), xOffset, yOffset, 24, 24);
        xOffset = (handler.getGame().getWidth() / 2) + 50;
        yOffset = 5;
        g.drawImage(opponentCritter.getSpeciesIcon(), xOffset, yOffset,
                (handler.getGame().getWidth() - xOffset - 5), ((handler.getGame().getHeight() / 3) + 20), null);

        //player's critter
        xOffset = ((handler.getGame().getWidth() / 2) - 35);
        yOffset = 5 + ((handler.getGame().getHeight() / 3) + 20) + 5;
        FontGrabber.renderString(g, playerCritterFirst.getNameColloquial(), xOffset, yOffset, 28, 28);
        yOffset += 32;
        xOffset += 110;
        FontGrabber.renderString(g, ":L" + playerCritterFirst.getLevel(), xOffset, yOffset, 28, 28);
        //player's critter's image
        yOffset = ((handler.getGame().getHeight() / 3) - 30);
        xOffset = 5;
        g.drawImage(playerCritterFirst.getSpeciesIcon(), xOffset, yOffset,
                ((handler.getGame().getWidth() / 2) - 55), ((handler.getGame().getHeight() / 3) + 20), null);

        if (menuMatrix[yIndex][xIndex].equals("BattleStateFight")) {
            g.drawImage(Assets.cursorSprite, 291, 423, 7 * 4, 7 * 4, null);
        } else if (menuMatrix[yIndex][xIndex].equals("BattleStateCritterBeltList")) {
            g.drawImage(Assets.cursorSprite, 484, 423, 7 * 4, 7 * 4, null);
        } else if (menuMatrix[yIndex][xIndex].equals("BattleStateItemList")) {
            g.drawImage(Assets.cursorSprite, 291, 484, 7 * 4, 7 * 4, null);
        } else if (menuMatrix[yIndex][xIndex].equals("BattleStateRun")) {
            g.drawImage(Assets.cursorSprite, 484, 484, 7 * 4, 7 * 4, null);
        }
    }

    @Override
    public void enter(Object[] args) {
        if (args != null) {
            if (args[0] instanceof Critter) {
                opponentCritter = (Critter)args[0];
            }
        }

        for (int i = 0; i < player.getCritterBeltList().size(); i++) {
            if (player.getCritterBeltList().get(i).getStatus() != Critter.StatusConditionNonVolatile.FAINTED) {
                playerCritterFirst = player.getCritterBeltList().get(i);
                break;
            }
        }
    }

    @Override
    public void exit() {

    }

} // **** end BattleStateMenu class ****