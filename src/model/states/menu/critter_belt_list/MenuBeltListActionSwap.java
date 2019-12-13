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

    private int indexCritterBeltList;
    private int xCursor, yCursor;

    public MenuBeltListActionSwap(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        indexCritterBeltList = 0;
        updateCursorPosition();
    } // **** end MenuBeltListActionSwap(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //upButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("MenuBeltListActionSwap.tick(long)... upButton");

            indexCritterBeltList--;
            //decrement again if index of next selected Critter is the same as indexFirstCritterSelected.
            if (indexCritterBeltList == indexFirstCritterSelected) {
                indexCritterBeltList--;
            }
            if (indexCritterBeltList < 0) {
                indexCritterBeltList = (player.getCritterBeltList().size() - 1);
            }

            updateCursorPosition();
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("MenuBeltListActionSwap.tick(long)... downButton");

            indexCritterBeltList++;
            //increment again if index of next selected Critter is the same as indexFirstCritterSelected.
            if (indexCritterBeltList == indexFirstCritterSelected) {
                indexCritterBeltList++;
            }
            if (indexCritterBeltList > (player.getCritterBeltList().size() - 1)) {
                indexCritterBeltList = 0;
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
                //pop self (MenuBeltListAction).
                stateMachine.pop();
                //now MenuBeltList.
            }
            ///////////////////////////////
        }
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("MenuBeltListActionSwap.tick(long)... aButton");
            StateMachine stateMachine = ((MenuState)handler.getStateManager().getIState("MenuState")).getStateMachine();
            ArrayList<Critter> critterBeltListCopy = new ArrayList<Critter>(6);
            Critter critter1 = null;
            Critter critter2 = null;
            for (int i = 0; i < player.getCritterBeltList().size(); i++) {
                if (i == indexFirstCritterSelected) {
                    critter1 = player.getCritterBeltList().get(i);
                } else if (i == indexCritterBeltList) {
                    critter2 = player.getCritterBeltList().get(i);
                }
            }
            for (int i = 0; i < player.getCritterBeltList().size(); i++) {
                if (i == indexFirstCritterSelected) {
                    critterBeltListCopy.add(critter2);
                    continue;
                } else if (i == indexCritterBeltList) {
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
        yCursor = MenuBeltList.Y_OFFSET_CURSOR + (indexCritterBeltList * 60);
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
                indexCritterBeltList = 1;
                updateCursorPosition();
            }
        }
    }

    @Override
    public void exit() {

    }

} // **** end MenuBeltListActionSwap class ****