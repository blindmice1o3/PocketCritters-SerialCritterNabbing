package model.states.menu.critter_belt_list;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;
import model.entities.critters.Critter;
import model.states.IState;
import model.states.StateMachine;
import model.states.menu.MenuState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuBeltListActionSwap implements IState {

    private Handler handler;
    private Player player;
    private int indexFirstCritterSelected;

    private int indexCurrentCritterSelected;
    private int xCursor, yCursor;

    public MenuBeltListActionSwap(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        indexCurrentCritterSelected = 0;
        updateCursorPosition();
    } // **** end MenuBeltListActionSwap(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //upButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("MenuBeltListActionSwap.tick(long)... upButton");

            indexCurrentCritterSelected--;
            //decrement again if index of next selected Critter is the same as indexFirstCritterSelected.
            if (indexCurrentCritterSelected == indexFirstCritterSelected) {
                indexCurrentCritterSelected--;
            }
            if (indexCurrentCritterSelected < 0) {
                indexCurrentCritterSelected = (player.getCritterBeltList().size() - 1);
            }

            updateCursorPosition();
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("MenuBeltListActionSwap.tick(long)... downButton");

            indexCurrentCritterSelected++;
            //increment again if index of next selected Critter is the same as indexFirstCritterSelected.
            if (indexCurrentCritterSelected == indexFirstCritterSelected) {
                indexCurrentCritterSelected++;
            }
            if (indexCurrentCritterSelected > (player.getCritterBeltList().size() - 1)) {
                indexCurrentCritterSelected = 0;
            }

            updateCursorPosition();
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuBeltListActionSwap.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuBeltListActionSwap).
                stateMachine.pop();
                //pop (MenuBeltListAction).
                stateMachine.pop();
                //now MenuBeltList.
            }
            ///////////////////////////////
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("MenuBeltListActionSwap.tick(long)... aButton");
            StateMachine stateMachine = ((MenuState)handler.getStateManager().getIState("MenuState")).getStateMachine();

            Critter critter1 = player.getCritterBeltList().get(indexFirstCritterSelected);
            Critter critter2 = player.getCritterBeltList().get(indexCurrentCritterSelected);

            ArrayList<Critter> critterBeltListCopy = new ArrayList<Critter>(6);
            for (int i = 0; i < player.getCritterBeltList().size(); i++) {
                //swapping critter1's and critter2's position in the belt.
                if (i == indexFirstCritterSelected) {
                    critterBeltListCopy.add(critter2);
                    continue;
                } else if (i == indexCurrentCritterSelected) {
                    critterBeltListCopy.add(critter1);
                    continue;
                }
                critterBeltListCopy.add(player.getCritterBeltList().get(i));
            }

            ///////////////////////////////////////////////
            player.setCritterBeltList(critterBeltListCopy);
            //pop self (MenuBeltListActionSwap).
            stateMachine.pop();
            //pop self (MenuBeltListAction).
            stateMachine.pop();
            //now MenuBeltList.
            ///////////////////////////////////////////////
        }
    }

    private void updateCursorPosition() {
        xCursor = MenuBeltList.X_OFFSET_CURSOR;
        yCursor = MenuBeltList.Y_OFFSET_CURSOR + (indexCurrentCritterSelected * 60);
    }

    @Override
    public void render(Graphics g) {
        MenuState menuState = (MenuState)handler.getStateManager().getIState("MenuState");
        //re-draw the IState below this one as background.
        menuState.getStateMachine().getIState("MenuBeltList").render(g);

        //CURSOR
        g.drawImage(Assets.critterBallSprite, xCursor, yCursor, 20, 20, null);
    }

    @Override
    public void enter(Object[] args) {
        if (args != null) {
            indexFirstCritterSelected = (int)args[0];

            if (indexFirstCritterSelected == 0) {
                indexCurrentCritterSelected = 1;
                updateCursorPosition();
            }
        }
    }

    @Override
    public void exit() {

    }

} // **** end MenuBeltListActionSwap class ****