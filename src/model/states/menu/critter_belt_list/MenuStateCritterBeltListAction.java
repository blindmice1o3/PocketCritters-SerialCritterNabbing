package model.states.menu.critter_belt_list;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;
import model.states.menu.MenuState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStateCritterBeltListAction implements IState {

    public static final int WIDTH = 160, HEIGHT = 120;

    private enum Action {
        STAT, SWAP, CANCEL;
    }

    private Handler handler;
    private Player player;

    private Action currentAction;

    //initialized when enter(Object]) is called (tracks its background panel position).
    private int xOffsetStart, yOffsetStart;
    private final int xOffsetCursor, yOffsetCursor;
    //is relative to the current Action (the currentAction).
    private int xCursor, yCursor;

    public MenuStateCritterBeltListAction(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        xOffsetCursor = 5;
        yOffsetCursor = 10;

        currentAction = Action.STAT;
    } // **** end MenuStateCritterBeltListAction(Handler, Player) constructor ****

    private void updateCursorPosition() {
        xCursor = xOffsetStart + xOffsetCursor;
        yCursor = yOffsetStart + yOffsetCursor + (currentAction.ordinal() * 40);
    }

    @Override
    public void tick(long timeElapsed) {
        //upButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("MenuStateCritterBeltListAction.tick(long)... upButton");

            if ((currentAction.ordinal() - 1) >= 0) {
                currentAction = Action.values()[(currentAction.ordinal() - 1)];
            } else {
                currentAction = Action.values()[(Action.values().length - 1)];
            }

            updateCursorPosition();
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("MenuStateCritterBeltListAction.tick(long)... downButton");

            if ((currentAction.ordinal() + 1) <= (Action.values().length - 1)) {
                currentAction = Action.values()[(currentAction.ordinal() + 1)];
            } else {
                currentAction = Action.values()[0];
            }

            updateCursorPosition();
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuStateCritterBeltListAction.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState) handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuStateCritterBeltListAction).
                stateMachine.pop();
                //now MenuStateCritterBeltList.
            }
            ///////////////////////////////
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("MenuStateCritterBeltListAction.tick(long)... aButton");

            System.out.println("currentAction: " + currentAction.toString() + ".");
        }
    }

    @Override
    public void render(Graphics g) {
        //BACKGROUND
        g.setColor(Color.YELLOW);
        g.fillRect(xOffsetStart, yOffsetStart, WIDTH, HEIGHT);

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
                int[] offsetPosition = (int[])args[0];

                xOffsetStart = offsetPosition[0];
                yOffsetStart = offsetPosition[1];

                updateCursorPosition();
            }
        }
    }

    @Override
    public void exit() {

    }

} // **** end MenuStateCritterBeltListAction class ****