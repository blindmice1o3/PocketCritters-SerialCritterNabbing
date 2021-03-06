package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
import model.entities.critters.stats.StatsModule;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleStateMenu implements IState {

    private Handler handler;
    private Player player;

    private int xIndex, yIndex;
    private String[][] menuMatrix;

    public BattleStateMenu(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        xIndex = 0;
        yIndex = 0;
        menuMatrix = new String[2][2];
        menuMatrix[0][0] = "BattleStateFight";
        menuMatrix[0][1] = "BattleBeltList";
        menuMatrix[1][0] = "BattleStateItemList";
        menuMatrix[1][1] = "BattleStateRun";
    } // **** end BattleStateMenu(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //System.out.println("BattleStateMenu.tick()");

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

                //Object[] args = {critterOfOpponent};
                stateMachine.push(
                        stateMachine.getIState(menuMatrix[yIndex][xIndex]), null
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
        g.drawImage(Assets.backgroundBattleStateMenu,0, 0,
                handler.getGame().getWidth(), handler.getGame().getHeight(), null);

        Critter critterOfOpponent = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();

        //opponent's critter
        int xOffset = 9;
        int yOffset = 5;
        FontGrabber.renderString(g, critterOfOpponent.getNameColloquial(), xOffset, yOffset, 28, 28);
        yOffset += 32;
        xOffset += 80;
        FontGrabber.renderString(g, ":L" + critterOfOpponent.getStatsModule().getLevelCurrent(), xOffset, yOffset, 24, 24);
        //opponent's critter's image
        xOffset = (handler.getGame().getWidth() / 2) + 49;
        yOffset = 5;
        g.drawImage(critterOfOpponent.getSpeciesIcon(), xOffset, yOffset,
                (handler.getGame().getWidth() - xOffset - 5), ((handler.getGame().getHeight() / 3) + 20), null);
        //opponent's hp bar stuff
        int hpEffectiveCurrentOpponent = critterOfOpponent.getHpEffectiveCurrent();
        int hpEffectiveMaxOpponent = critterOfOpponent.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP);
        String hpOpponent = String.format("%3d", hpEffectiveCurrentOpponent) + " of " + String.format("%3d", hpEffectiveMaxOpponent);
        FontGrabber.renderString(g, hpOpponent, 60, 70, 20, 20);
        //hp bar opponent
        int xHpBarBorderOpponent = 60;
        int yHpBarBorderOpponent = 93;
        int widthHpBarBorderOpponent = 200 + 4;
        int heightHpBarBorderOpponent = 8;
        int xHpBarOpponent = xHpBarBorderOpponent + 2;
        int yHpBarOpponent = yHpBarBorderOpponent + 2;
        int widthHpBarOpponent = (int)(((double)critterOfOpponent.getHpEffectiveCurrent() /
                critterOfOpponent.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP)) * 200);
        int heightHpBarOpponent = heightHpBarBorderOpponent - 4;
        g.setColor(Color.GRAY);
        g.fillRect(xHpBarBorderOpponent, yHpBarBorderOpponent, widthHpBarBorderOpponent, heightHpBarBorderOpponent);
        g.setColor(Color.GREEN);
        g.fillRect(xHpBarOpponent, yHpBarOpponent, widthHpBarOpponent, heightHpBarOpponent);



        //player's critter
        xOffset = ((handler.getGame().getWidth() / 2) - 36);
        yOffset = 5 + ((handler.getGame().getHeight() / 3) + 20) + 5;
        FontGrabber.renderString(g, critterOfPlayer.getNameColloquial(), xOffset, yOffset, 28, 28);
        yOffset += 32;
        xOffset += 110;
        FontGrabber.renderString(g, ":L" + critterOfPlayer.getStatsModule().getLevelCurrent(), xOffset, yOffset, 28, 28);
        //player's critter's image
        yOffset = ((handler.getGame().getHeight() / 3) - 30);
        xOffset = 4;
        g.drawImage(critterOfPlayer.getSpeciesIcon(), xOffset, yOffset,
                ((handler.getGame().getWidth() / 2) - 55), ((handler.getGame().getHeight() / 3) + 20), null);
        //player's hp bar stuff
        int hpEffectiveCurrentPlayer = critterOfPlayer.getHpEffectiveCurrent();
        int hpEffectiveMaxPlayer = critterOfPlayer.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP);
        String hpPlayer = String.format("%3d", hpEffectiveCurrentPlayer) + " of " + String.format("%3d", hpEffectiveMaxPlayer);
        FontGrabber.renderString(g, hpPlayer, 340, 300, 20, 20);
        //hp bar player
        int xHpBarBorderPlayer = 340;
        int yHpBarBorderPlayer = 323;
        int widthHpBarBorderPlayer = 200 + 4;
        int heightHpBarBorderPlayer = 8;
        int xHpBarPlayer = xHpBarBorderPlayer + 2;
        int yHpBarPlayer = yHpBarBorderPlayer + 2;
        int widthHpBarPlayer = (int)(((double)critterOfPlayer.getHpEffectiveCurrent() /
                critterOfPlayer.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP)) * 200);
        int heightHpBarPlayer = heightHpBarBorderPlayer - 4;
        g.setColor(Color.GRAY);
        g.fillRect(xHpBarBorderPlayer, yHpBarBorderPlayer, widthHpBarBorderPlayer, heightHpBarBorderPlayer);
        g.setColor(Color.GREEN);
        g.fillRect(xHpBarPlayer, yHpBarPlayer, widthHpBarPlayer, heightHpBarPlayer);



        if (menuMatrix[yIndex][xIndex].equals("BattleStateFight")) {
            g.drawImage(Assets.cursorSprite, 290, 423, 7 * 4, 7 * 4, null);
        } else if (menuMatrix[yIndex][xIndex].equals("BattleBeltList")) {
            g.drawImage(Assets.cursorSprite, 483, 423, 7 * 4, 7 * 4, null);
        } else if (menuMatrix[yIndex][xIndex].equals("BattleStateItemList")) {
            g.drawImage(Assets.cursorSprite, 290, 484, 7 * 4, 7 * 4, null);
        } else if (menuMatrix[yIndex][xIndex].equals("BattleStateRun")) {
            g.drawImage(Assets.cursorSprite, 483, 484, 7 * 4, 7 * 4, null);
        }
    }

    @Override
    public void enter(Object[] args) {
        Critter critterOfOpponent = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();

        System.out.println("critterOfOpponent is in ExpGroup " + critterOfOpponent.getSpecies().getExpGroup().toString() + " with expCurrent: " + critterOfOpponent.getStatsModule().getExpCurrent());
        System.out.println("critterOfPlayer is in ExpGroup " + critterOfPlayer.getSpecies().getExpGroup().toString() + " with expCurrent: " + critterOfPlayer.getStatsModule().getExpCurrent());
    }

    @Override
    public void exit() {

    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

} // **** end BattleStateMenu class ****