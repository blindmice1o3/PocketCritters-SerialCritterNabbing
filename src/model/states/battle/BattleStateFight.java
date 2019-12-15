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
import java.awt.image.BufferedImage;

public class BattleStateFight implements IState {

    private Handler handler;
    private Player player;

    private int index;

    private int xCursor, yCursor;

    public BattleStateFight(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        index = 0;

        xCursor = 162;
        yCursor = 330;
    } // **** end BattleStateFight(Handler, Player) constructor ****

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
    public void tick(long timeElapsed) {
        //System.out.println("BattleStateFight.tick()");

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
            int idMove = movesModule.getMoves()[index];
            System.out.println("execute move: " + movesModule.lookUpMove(idMove));

            int power = movesModule.lookUpMove(idMove).getPower();
            int opponentDefenseEffective = critterOfOpponent.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.DEFENSE);
            int damageEffective = critterOfPlayer.calculateDamage(power, opponentDefenseEffective);
            System.out.println(critterOfPlayer.getNameColloquial() + "'s " +
                    movesModule.lookUpMove(idMove).toString() + " will do: " + damageEffective +
                    " damage to " + critterOfOpponent.getNameColloquial() + ".");

            /////////////////////////////////////////////////////////////
            critterOfPlayer.doDamage(critterOfOpponent, damageEffective);
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

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                handler.getGame().getHeight(), 161, 146, 161+159, 146+145, null);

        Critter critterOfOpponent = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();
        MovesModule movesModule = critterOfPlayer.getMovesModule();

        String hpOpponent = "hpOpponent: " + critterOfOpponent.getHpEffectiveCurrent() + " of " + critterOfOpponent.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP);
        String hpPlayer = "hpPlayer: " + critterOfPlayer.getHpEffectiveCurrent() + " of " + critterOfPlayer.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP);
        FontGrabber.renderString(g, hpOpponent, 50, 50, 20, 20);
        FontGrabber.renderString(g, hpPlayer, 300, 300, 20, 20);

        String nameMove = null;
        //CURRENTLY SELECTED MOVE (and its pp)
        if (movesModule.getMoves()[index] != 0) {
            nameMove = movesModule.lookUpMove(movesModule.getMoves()[index]).getType().toString();
            FontGrabber.renderString(g, nameMove, 60, 240, 30, 30);
            String ppCurrentAndBase = movesModule.getPpMoves()[index] + "/" + movesModule.lookUpMove(movesModule.getMoves()[index]).getPpBase();
            FontGrabber.renderString(g, ppCurrentAndBase, 168, 270, 30, 30);
        }

        //LIST OF MOVES
        int xOffset = xCursor + 35;
        int yOffset = 330;
        for (int i = 0; i < movesModule.getNumberMovesKnown(); i++) {
            nameMove = movesModule.lookUpMove( movesModule.getMoves()[i] ).toString();
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