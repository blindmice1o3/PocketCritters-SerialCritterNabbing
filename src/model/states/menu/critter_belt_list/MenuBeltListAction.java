package model.states.menu.critter_belt_list;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.states.IState;
import model.states.menu.MenuState;
import view.Cursor;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuBeltListAction implements IState {

    public static final int WIDTH = 160, HEIGHT = 120;

    private enum Action {
        SUMMARY, SWAP, CANCEL;
    }

    private Handler handler;
    private Player player;
    private int indexCritterBeltList;

    private Action currentAction;

    //initialized when enter(Object]) is called (tracks its background panel position).
    private int xPanel, yPanel;
    //private final int xOffsetCursor, yOffsetCursor;
    //is relative to the current Action (the currentAction).
    private Cursor cursor;
//    private int xCursor, yCursor;

    public MenuBeltListAction(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        //xOffsetCursor = 5;
        //yOffsetCursor = 10;

        currentAction = Action.SUMMARY;

        cursor = new Cursor(0, 0, 40, 20, 20);
    } // **** end MenuBeltListAction(Handler, Player) constructor ****

    /*private void updateCursorPosition() {
        xCursor = xPanel + xOffsetCursor;
        yCursor = yPanel + yOffsetCursor + (currentAction.ordinal() * 40);
    }*/

    @Override
    public void tick(long timeElapsed) {
        //upButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("MenuBeltListAction.tick(long)... upButton");

            if ((currentAction.ordinal() - 1) >= 0) {
                currentAction = Action.values()[(currentAction.ordinal() - 1)];
            } else {
                currentAction = Action.values()[(Action.values().length - 1)];
            }

            cursor.updateCursorPosition(currentAction.ordinal());
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("MenuBeltListAction.tick(long)... downButton");

            if ((currentAction.ordinal() + 1) <= (Action.values().length - 1)) {
                currentAction = Action.values()[(currentAction.ordinal() + 1)];
            } else {
                currentAction = Action.values()[0];
            }

            cursor.updateCursorPosition(currentAction.ordinal());
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuBeltListAction.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState) handler.getStateManager().getCurrentState();
                //pop self (MenuBeltListAction).
                menuState.getStateMachine().pop();
                //now MenuBeltList.
            }
            ///////////////////////////////
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("MenuBeltListAction.tick(long)... aButton");
            System.out.println("currentAction: " + currentAction.toString() + ".");

            MenuState menuState = (MenuState)handler.getStateManager().getIState("MenuState");
            Object[] args = { indexCritterBeltList };
            switch (currentAction) {
                case SUMMARY:
                    MenuBeltListActionSummary menuBeltListActionSummary = (MenuBeltListActionSummary)menuState.getStateMachine().getIState("MenuBeltListActionSummary");
                    ///////////////////////////////
                    menuState.getStateMachine().push(menuBeltListActionSummary, args );
                    ///////////////////////////////
                    break;
                case SWAP:
                    MenuBeltListActionSwap menuBeltListActionSwap = (MenuBeltListActionSwap)menuState.getStateMachine().getIState("MenuBeltListActionSwap");
                    ///////////////////////////////
                    menuState.getStateMachine().push( menuBeltListActionSwap, args );
                    ///////////////////////////////
                    break;
                case CANCEL:
                    ///////////////////////////////
                    //pop self (MenuBeltListAction).
                    menuState.getStateMachine().pop();
                    //now MenuBeltList.
                    ///////////////////////////////
                    break;
                default:
                    System.out.println("MenuBeltListAction.tick(long)... aButton's switch(currentAction) construct's default block.");
                    break;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        MenuState menuState = (MenuState)handler.getStateManager().getIState("MenuState");
        //re-draw the IState below this one as background.
        menuState.getStateMachine().getIState("MenuBeltList").render(g);

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
//        g.drawImage(Assets.critterBallSprite, xCursor, yCursor, 20, 20, null);

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

} // **** end MenuBeltListAction class ****