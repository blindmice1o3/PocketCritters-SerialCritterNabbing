package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
import model.entities.critters.moves.MovesModule;
import model.entities.critters.stats.StatsModule;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleStateFight implements IState {

    public enum Turn { PLAYER, OPPONENT; }
    public static final int X_CURSOR_SPAWN = 161, Y_CURSOR_SPAWN = 330;

    private Handler handler;
    private Player player;

    private Turn turnCurrent;
    private int index;
    private int xCursor, yCursor;

    private int decisionTicker;
    private int decisionTickerTarget;
    private boolean isFirstTick;

    public BattleStateFight(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        turnCurrent = Turn.PLAYER;
        index = 0;
        xCursor = X_CURSOR_SPAWN;
        yCursor = Y_CURSOR_SPAWN;

        decisionTicker = 0;
        decisionTickerTarget = 0;
        isFirstTick = true;
    } // **** end BattleStateFight(Handler, Player) constructor ****

    private int simulateTimeDeciding() {
        //random number between [1-10], inclusive.
        return (int)((Math.random() * 10) + 1);
    }
    private void doDecision() {
        Critter critterOfOpponent = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();

        int numberOfMovesKnown = critterOfOpponent.getMovesModule().getNumberMovesKnown();

        int indexDecision = 0;
        indexDecision = (int)(Math.random() * numberOfMovesKnown); //random number between [0 to (numberOfMovesKnown-1)], inclusive.
        System.out.println("BattleStateFight.doDecision() [opponent chooses move] indexDecision (should only be [0-1] for now): " + indexDecision);
        switch (indexDecision) {
            case 0:
            case 1:
                int idMove = critterOfOpponent.getMovesModule().getMovesCurrent()[indexDecision];
                int power = critterOfOpponent.getMovesModule().lookUpMove(idMove).getPower();
                int defenseCritterOfPlayer = critterOfPlayer.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.DEFENSE);

                int damage = critterOfOpponent.calculateDamage(power, defenseCritterOfPlayer);
                critterOfOpponent.doDamage(critterOfPlayer, damage);
                break;
            case 2:
                System.out.println("BattleStateFight.doDecision() switch (indexDecision) construct's case 2.");
                break;
            case 3:
                System.out.println("BattleStateFight.doDecision() switch (indexDecision) construct's case 3.");
                break;
            default:
                System.out.println("BattleStateFight.doDecision() switch (indexDecision) construct's default block.");
                break;
        }
    }

    @Override
    public void tick(long timeElapsed) {
        switch(turnCurrent) {
            case PLAYER:
                getInput(); //turnCurrent is set to Turn.OPPONENT inside the aButton block.

                break;
            case OPPONENT:
                //set the targeted-time
                if (isFirstTick) {
                    System.out.println("BattleStateFight.tick(long) switch (turnCurrent) construct's OPPONENT BLOCK.");
                    decisionTickerTarget = (simulateTimeDeciding() * 60); //[1-10] * 60 will give 1-10 seconds.
                    System.out.println("BattleStateFight.tick(long) decisionTickerTarget set to: " + decisionTickerTarget);
                    isFirstTick = false;
                } else {
                    decisionTicker++;
                    if (decisionTicker % 60 == 0) {
                        System.out.println("OPPONENT decisionTicker: " + decisionTicker);
                    }

                    if (decisionTicker >= decisionTickerTarget) {
                        doDecision();

                        decisionTicker = 0;
                        isFirstTick = true;
                        ////////////////////////////
                        turnCurrent = Turn.PLAYER;
                        ////////////////////////////
                    }
                }

                break;
            default:
                System.out.println("BattleStateFight.tick(long) switch(turnCurrent) construct's default block.");
                break;
        }
    }

    private void getInput() {
        Critter critterOfOpponent = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();
        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateFight.tick()... up");

            index--;

            if (index < 0) {
                index = (critterOfPlayer.getMovesModule().getNumberMovesKnown() - 1);
            }

            updateCursorPosition();
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateFight.tick()... down");

            index++;

            if (index > (critterOfPlayer.getMovesModule().getNumberMovesKnown() - 1)) {
                index = 0;
            }

            updateCursorPosition();
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

            MovesModule movesModule = critterOfPlayer.getMovesModule();
            int idMove = movesModule.getMovesCurrent()[index];
            System.out.println("execute move: " + movesModule.lookUpMove(idMove));

            int power = movesModule.lookUpMove(idMove).getPower();
            int opponentDefenseEffective = critterOfOpponent.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.DEFENSE);
            int damageEffective = critterOfPlayer.calculateDamage(power, opponentDefenseEffective);
            System.out.println(critterOfPlayer.getNameColloquial() + "'s " +
                    movesModule.lookUpMove(idMove).toString() + " will do: " + damageEffective +
                    " damage to " + critterOfOpponent.getNameColloquial() + ".");

            /////////////////////////////////////////////////////////////
            critterOfPlayer.doDamage(critterOfOpponent, damageEffective);
            ////////////////////////////
            turnCurrent = Turn.OPPONENT;
            ////////////////////////////
            /////////////////////////////////////////////////////////////
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateFight.tick()... bButton");


            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                stateMachine.pop();
            }
            ///////////////////////////////
        }
    }

    private void updateCursorPosition() {
        switch(index) {
            case 0:
                yCursor = 330;
                break;
            case 1:
                yCursor = 360;
                break;
            case 2:
                yCursor = 390;
                break;
            case 3:
                yCursor = 420;
                break;
            default:
                System.out.println("BattleStateFight.updateCursorPosition() switch(index) construct's default block.");
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.backgroundBattleStateFight, 0, 0,
                handler.getGame().getWidth(), handler.getGame().getHeight(), null);
//        g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
//                handler.getGame().getHeight(), 161, 146, 161+160, 146+145, null);

        Critter critterOfOpponent = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();
        MovesModule movesModule = critterOfPlayer.getMovesModule();

        String hpOpponent = "hpOpponent: " + critterOfOpponent.getHpEffectiveCurrent() + " of " + critterOfOpponent.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP);
        String hpPlayer = "hpPlayer: " + critterOfPlayer.getHpEffectiveCurrent() + " of " + critterOfPlayer.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP);
        FontGrabber.renderString(g, hpOpponent, 50, 50, 20, 20);
        FontGrabber.renderString(g, hpPlayer, 300, 300, 20, 20);

        String nameMove = null;
        //CURRENTLY SELECTED MOVE (and its pp)
        if (movesModule.getMovesCurrent()[index] != 0) {
            nameMove = movesModule.lookUpMove(movesModule.getMovesCurrent()[index]).getType().toString();
            FontGrabber.renderString(g, nameMove, 59, 240, 30, 30);
            String ppCurrentAndBase = movesModule.getPpMovesCurrent()[index] + "/" + movesModule.lookUpMove(movesModule.getMovesCurrent()[index]).getPpBase();
            FontGrabber.renderString(g, ppCurrentAndBase, 167, 270, 30, 30);
        }

        //LIST OF MOVES
        int xOffset = xCursor + 35;
        int yOffset = 330;
        for (int i = 0; i < movesModule.getNumberMovesKnown(); i++) {
            nameMove = movesModule.lookUpMove( movesModule.getMovesCurrent()[i] ).toString();
            FontGrabber.renderString(g, nameMove, xOffset, yOffset, 30, 30);
            yOffset += 30;
        }

        //CURSOR
        g.drawImage(Assets.cursorSprite, xCursor-1, yCursor+6, 25, 25, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end BattleStateFight class ****