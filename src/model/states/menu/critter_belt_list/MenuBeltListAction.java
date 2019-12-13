package model.states.menu.critter_belt_list;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.states.IState;
import model.states.menu.MenuState;

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
    private int xOffsetStart, yOffsetStart;
    private final int xOffsetCursor, yOffsetCursor;
    //is relative to the current Action (the currentAction).
    private int xCursor, yCursor;

    public MenuBeltListAction(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        xOffsetCursor = 5;
        yOffsetCursor = 10;

        currentAction = Action.SUMMARY;
    } // **** end MenuBeltListAction(Handler, Player) constructor ****

    private void updateCursorPosition() {
        xCursor = xOffsetStart + xOffsetCursor;
        yCursor = yOffsetStart + yOffsetCursor + (currentAction.ordinal() * 40);
    }

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

            updateCursorPosition();
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("MenuBeltListAction.tick(long)... downButton");

            if ((currentAction.ordinal() + 1) <= (Action.values().length - 1)) {
                currentAction = Action.values()[(currentAction.ordinal() + 1)];
            } else {
                currentAction = Action.values()[0];
            }

            updateCursorPosition();
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
        g.fillRect(xOffsetStart, yOffsetStart, WIDTH, HEIGHT);

        //LIST OF ACTIONS
        g.setColor(Color.BLUE);
        int xString = xOffsetStart + xOffsetCursor + 20 + xOffsetCursor;
        for (int i = 0; i < Action.values().length; i++) {
            int yString = yOffsetStart + yOffsetCursor + (i * 40);

            String nameMenuElement = Action.values()[i].toString();
            FontGrabber.renderString(g, nameMenuElement, xString, yString, 20, 20);
        }

        //CURSOR
        g.drawImage(Assets.critterBallSprite, xCursor, yCursor, 20, 20, null);

    }

    @Override
    public void enter(Object[] args) {
        if (args != null) {
            if (args[0] instanceof int[]) {
                int[] cursorPositionAndIndexCritterBeltList = (int[])args[0];

                xOffsetStart = cursorPositionAndIndexCritterBeltList[0];
                yOffsetStart = cursorPositionAndIndexCritterBeltList[1];
                indexCritterBeltList = cursorPositionAndIndexCritterBeltList[2];

                updateCursorPosition();
            }
        }
    }

    @Override
    public void exit() {

    }

} // **** end MenuBeltListAction class ****