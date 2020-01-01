package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import main.utils.Util;
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
    public static final int X_CURSOR_SPAWN = 161, Y_CURSOR_SPAWN = 395;

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
        Critter critterOfOpponent = ((BattleState) handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
        Critter critterOfPlayer = ((BattleState) handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();

        int numberOfMovesKnown = critterOfOpponent.getMovesModule().getNumberMovesKnown();

        int indexDecision = (int) (Math.random() * numberOfMovesKnown); //random number between [0 to (numberOfMovesKnown-1)], inclusive.
        System.out.println("BattleStateFight.doDecision() [opponent chooses move] indexDecision (should only be [0-1] for now): " + indexDecision);

        if (critterOfOpponent.getMovesModule().getPpMovesCurrent()[indexDecision] > 0) {
            int idMove = critterOfOpponent.getMovesModule().getMovesCurrent()[indexDecision];
            int power = critterOfOpponent.getMovesModule().lookUpMove(idMove).getPower();
            int defenseCritterOfPlayer = critterOfPlayer.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.DEFENSE);

            int damage = critterOfOpponent.calculateDamage(power, defenseCritterOfPlayer);
            ////////////////////////////
            critterOfOpponent.doDamage(critterOfPlayer, damage);
            ////////////////////////////

            ////////////////////////////
            //DECREMENT ppCurrent of the move used.
            critterOfOpponent.getMovesModule().decrementPpMovesCurrent(indexDecision);
            ////////////////////////////

            decisionTicker = 0;
            isFirstTick = true;
            ////////////////////////////
            turnCurrent = Turn.PLAYER;
            ////////////////////////////

            /////////////////////////////////
            checkForFainted(critterOfPlayer);
            /////////////////////////////////
        } else {
            System.out.println("NOT ENOUGH ppCurrent!");
        }
    }

    /**
     * Currently ends the battle, does NOT check the critterBeltList of player or opponent
     * to see if other critters do not have their status set to FAINTED, and pops the
     * BattleState instance off the top of StateManager's stateStack.
     */
    private void checkForFainted(Critter critterDamaged) {
        if (critterDamaged.getStatus() == Critter.StatusConditionNonVolatile.FAINTED) {
            System.out.println("BattleStateFight.checkForFainted(Critter): " +
                    critterDamaged.getNameColloquial() + " had its status set to FAINTED.");

            StateMachine battleStateMachine = ((BattleState)handler.getStateManager().getCurrentState()).getStateMachine();
            //pop self (BattleStateFight).
            battleStateMachine.pop();
            //pop BattleStateMenu.
            battleStateMachine.pop();
            //now at BattleStateIntro (do NOT pop BattleStateIntro... error gets thrown: trying to tick top-of-stack of an empty stack).

            //pop BattleState from StateManager's stateStack.
            handler.getStateManager().pop();
            //now in GameState.
        }
    }

    @Override
    public void tick(long timeElapsed) {
        switch(turnCurrent) {
            case PLAYER:
                getInput(); //turnCurrent is set to Turn.OPPONENT inside the aButton block.
                            //something like checkForWinner() will be called in aButton block.
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
            System.out.println("About to execute move: " + movesModule.lookUpMove(idMove));

            if (movesModule.getPpMovesCurrent()[index] > 0) {
                int power = movesModule.lookUpMove(idMove).getPower();
                int opponentDefenseEffective = critterOfOpponent.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.DEFENSE);
                int damageEffective = critterOfPlayer.calculateDamage(power, opponentDefenseEffective);
                System.out.println(critterOfPlayer.getNameColloquial() + "'s " +
                        movesModule.lookUpMove(idMove).toString() + " will do: " + damageEffective +
                        " damage to " + critterOfOpponent.getNameColloquial() + ".");

                /////////////////////////////////////////////////////////////
                critterOfPlayer.doDamage(critterOfOpponent, damageEffective);
                /////////////////////////////////////////////////////////////
                ////////////////////////////
                //DECREMENT ppCurrent of the move used.
                movesModule.decrementPpMovesCurrent(index);
                ////////////////////////////
                ////////////////////////////
                turnCurrent = Turn.OPPONENT;
                ////////////////////////////

                ///////////////////////////////////
                checkForFainted(critterOfOpponent);
                ///////////////////////////////////
            } else {
                System.out.println("NOT ENOUGH ppCurrent!");
            }
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
                yCursor = 395;
                break;
            case 1:
                yCursor = 425;
                break;
            case 2:
                yCursor = 455;
                break;
            case 3:
                yCursor = 485;
                break;
            default:
                System.out.println("BattleStateFight.updateCursorPosition() switch(index) construct's default block.");
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        ((BattleState)handler.getStateManager().getIState("BattleState")).getStateMachine().getIState("BattleStateMenu").render(g);

        g.drawImage(Assets.backgroundBattleStateFight, 0, 0,
                handler.getGame().getWidth(), handler.getGame().getHeight(), null);

        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();
        MovesModule movesModule = critterOfPlayer.getMovesModule();

        String nameMove = null;
        //CURRENTLY SELECTED MOVE (and its pp)
        if (movesModule.getMovesCurrent()[index] != 0) {
            nameMove = movesModule.lookUpMove(movesModule.getMovesCurrent()[index]).getType().toString();
            FontGrabber.renderString(g, nameMove, 59, 305, 30, 30);
            String ppCurrentAndBase = movesModule.getPpMovesCurrent()[index] + "/" + movesModule.lookUpMove(movesModule.getMovesCurrent()[index]).getPpBase();
            FontGrabber.renderString(g, ppCurrentAndBase, 165, 335, 30, 30);
        }

        //LIST OF MOVES
        int xOffset = xCursor + 35;
        int yOffset = 395;
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