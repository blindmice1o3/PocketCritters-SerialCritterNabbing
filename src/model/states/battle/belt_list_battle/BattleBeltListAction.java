package model.states.battle.belt_list_battle;

import main.Handler;
import main.utils.FontGrabber;
import model.entities.Player;
import model.states.IState;
import model.states.battle.BattleState;
import view.Cursor;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleBeltListAction implements IState {

    public static final int WIDTH = 160, HEIGHT = 120;

    private enum Action {
        SWAP, SUMMARY, CANCEL;
    }

    private Handler handler;
    private Player player;

    private Action currentAction;
    //is relative to the current Action (the currentAction).
    private Cursor cursor;

    //initialized when enter(Object]) is called (tracks its background panel position).
    private int xPanel, yPanel;
    //initialized when enter(Object]) is called, to be passed when pushing the next IState.
    private int indexCritterBeltList;

    public BattleBeltListAction(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        currentAction = Action.SWAP;
        //place-holder for now (when instantiated)... actual xOffset and yOffset values assigned in enter(Object[]).
        cursor = new Cursor(0, 0, 40, 20, 20);
    } // **** end BattleBeltListAction(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //upButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleBeltListAction.tick(long)... upButton");

            if ((currentAction.ordinal() - 1) >= 0) {
                currentAction = Action.values()[(currentAction.ordinal() - 1)];
            } else {
                currentAction = Action.values()[(Action.values().length - 1)];
            }

            cursor.updateCursorPosition(currentAction.ordinal());
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleBeltListAction.tick(long)... downButton");

            if ((currentAction.ordinal() + 1) <= (Action.values().length - 1)) {
                currentAction = Action.values()[(currentAction.ordinal() + 1)];
            } else {
                currentAction = Action.values()[0];
            }

            cursor.updateCursorPosition(currentAction.ordinal());
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleBeltListAction.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState) handler.getStateManager().getCurrentState();
                //pop self (BattleBeltListAction).
                battleState.getStateMachine().pop();
                //now BattleBeltList.
            }
            ///////////////////////////////
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleBeltListAction.tick(long)... aButton");
            System.out.println("currentAction: " + currentAction.toString() + ".");

            BattleState battleState = (BattleState)handler.getStateManager().getIState("BattleState");
            Object[] args = { indexCritterBeltList };
            switch (currentAction) {
                case SWAP:
                    BattleBeltListActionSwap battleBeltListActionSwap = (BattleBeltListActionSwap) battleState.getStateMachine().getIState("BattleBeltListActionSwap");
                    ///////////////////////////////
                    battleState.getStateMachine().push(battleBeltListActionSwap, args);
                    ///////////////////////////////
                    break;
                case SUMMARY:
                    BattleBeltListActionSummary battleBeltListActionSummary = (BattleBeltListActionSummary) battleState.getStateMachine().getIState("BattleBeltListActionSummary");
                    ///////////////////////////////
                    battleState.getStateMachine().push(battleBeltListActionSummary, args);
                    ///////////////////////////////
                    break;
                case CANCEL:
                    ///////////////////////////////
                    //pop self (BattleBeltListAction).
                    battleState.getStateMachine().pop();
                    //now BattleBeltList.
                    ///////////////////////////////
                    break;
                default:
                    System.out.println("BattleBeltListAction.tick(long)... aButton's switch(currentAction) construct's default block.");
                    break;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        BattleState battleState = (BattleState)handler.getStateManager().getIState("BattleState");
        //re-draw the IState below this one as background.
        battleState.getStateMachine().getIState("BattleBeltList").render(g);

        //PANEL
        g.setColor(Color.YELLOW);
        g.fillRect(xPanel, yPanel, WIDTH, HEIGHT);

        //LIST OF ACTIONS
        g.setColor(Color.BLUE);
        int xString = cursor.getxOffset() + 20 + 5;
        for (int i = 0; i < Action.values().length; i++) {
            int yString = cursor.getyOffset() + (i * 40);

            String nameMenuElement = Action.values()[i].toString();
            FontGrabber.renderString(g, nameMenuElement, xString, yString, 20, 20);
        }

        //CURSOR
        cursor.render(g);
    }

    @Override
    public void enter(Object[] args) {
        if (args != null) {
            if (args[0] instanceof int[]) {
                int[] cursorPositionAndIndexCritterBeltList = (int[])args[0];

                xPanel = cursorPositionAndIndexCritterBeltList[0];
                yPanel = cursorPositionAndIndexCritterBeltList[1];
                indexCritterBeltList = cursorPositionAndIndexCritterBeltList[2];

                cursor.setxOffset(xPanel + 5);
                cursor.setyOffset(yPanel + 10);
                cursor.updateCursorPosition(currentAction.ordinal());
            }
        }
    }

    @Override
    public void exit() {

    }

} // **** end BattleBeltListAction class ****