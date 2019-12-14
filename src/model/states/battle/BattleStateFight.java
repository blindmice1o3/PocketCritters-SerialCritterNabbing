package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
import model.entities.critters.moves.MoveModule;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

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
        System.out.println("BattleStateFight.tick()");

        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();
        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateFight.tick()... up");

            index--;

            if (index < 0) {
                index = (critterOfPlayer.getMoveModule().getNumberMovesKnown() - 1);
            }

            updateCursorPosition();
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateFight.tick()... down");

            index++;

            if (index > (critterOfPlayer.getMoveModule().getNumberMovesKnown() - 1)) {
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

            MoveModule moveModule = critterOfPlayer.getMoveModule();
            int idMove = moveModule.getMoves()[index];
            System.out.println("execute move: " + moveModule.lookUpMove(idMove));
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

        Critter critterOfPlayer = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();
        MoveModule moveModule = critterOfPlayer.getMoveModule();

        String nameMove = null;
        //CURRENTLY SELECTED MOVE (and its pp)
        if (moveModule.getMoves()[index] != 0) {
            nameMove = moveModule.lookUpMove(moveModule.getMoves()[index]).getType().toString();
            FontGrabber.renderString(g, nameMove, 60, 240, 30, 30);
            String ppCurrentAndBase = moveModule.getPpMoves()[index] + "/" + moveModule.lookUpMove(moveModule.getMoves()[index]).getPpBase();
            FontGrabber.renderString(g, ppCurrentAndBase, 168, 270, 30, 30);
        }

        //LIST OF MOVES
        int xOffset = xCursor + 35;
        int yOffset = 330;
        for (int i = 0; i < moveModule.getNumberMovesKnown(); i++) {
            nameMove = moveModule.lookUpMove( moveModule.getMoves()[i] ).toString();
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