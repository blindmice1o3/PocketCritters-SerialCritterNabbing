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
import java.awt.image.BufferedImage;

public class MenuBeltList implements IState {

    public static final int X_OFFSET_CURSOR = 3, Y_OFFSET_CURSOR = 27;

    private Handler handler;
    private Player player;

    private int indexCritterBeltList;

    private int xCursor, yCursor;

    public MenuBeltList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        indexCritterBeltList = 0;
        updateCursorPosition();
    } // **** end MenuBeltList(Handler, Player) constructor ****

    private void updateCursorPosition() {
        xCursor = X_OFFSET_CURSOR;
        yCursor = Y_OFFSET_CURSOR + (indexCritterBeltList * 60);
    }

    @Override
    public void tick(long timeElapsed) {
        //upButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("MenuBeltList.tick(long)... upButton");

            indexCritterBeltList--;
            if (indexCritterBeltList < 0) {
                indexCritterBeltList = (player.getCritterBeltList().size() - 1);
            }

            updateCursorPosition();
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("MenuBeltList.tick(long)... downButton");

            indexCritterBeltList++;
            if (indexCritterBeltList > (player.getCritterBeltList().size() - 1)) {
                indexCritterBeltList = 0;
            }

            updateCursorPosition();
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuBeltList.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuBeltList).
                stateMachine.pop();
                //now MenuStateMenu.
            }
            ///////////////////////////////
        }
        //aButton
        //TODO: pushes MenuBeltListAction onto top of MenuState's stateManager.
        //TODO: !!! pass in xCursor and yCursor (and indexCritterBeltList) as an int[] for Object[] args during enter(Object[]). !!!
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("MenuBeltList.tick(long)... aButton");
            //for developer (to be removed later).
            player.getCritterBeltList().get(indexCritterBeltList).consoleOutIVsAndEVs();

            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();
                int widthSpeciesIcon = player.getCritterBeltList().get(indexCritterBeltList).getSpeciesIcon().getWidth();

                int xNewPanel = (25 + widthSpeciesIcon + X_OFFSET_CURSOR);
                ///////////////////////////////////////////////////////////////////////////////
                //if in top part of the screen, set the new IState's yStart BELOW nameColloquial
                //if in bottom part of screen, set the new IState's yStart ABOVE nameColloquial.
                int yNewPanel = (yCursor < (handler.getGame().getWidth() / 3)) ?
                        (yCursor+5) : (yCursor+5- MenuBeltListAction.HEIGHT-20-3);
                ///////////////////////////////////////////////////////////////////////////////

                int[] cursorPositionAndIndexCritterBeltList = { xNewPanel, yNewPanel, indexCritterBeltList };
                Object[] args = { cursorPositionAndIndexCritterBeltList };

                ///////////////////////////////////////////////////////////////////////////////
                stateMachine.push(
                    stateMachine.getIState("MenuBeltListAction"), args
                );
                ///////////////////////////////////////////////////////////////////////////////
            }
        }
    }

    @Override
    public void render(Graphics g) {
        //BACKGROUND
        g.drawImage(Assets.backgroundCritterBeltList,
                0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                0, 0, Assets.backgroundCritterBeltList.getWidth(), Assets.backgroundCritterBeltList.getHeight(), null);

        //CritterBeltList
        BufferedImage speciesIcon = null;
        int numberOfCritters = player.getCritterBeltList().size();
        int xOffset = 25;
        int yOffset = 5;
        for (int i = 0; i < numberOfCritters; i++) {
            speciesIcon = player.getCritterBeltList().get(i).getSpeciesIcon();
            String name = player.getCritterBeltList().get(i).getNameColloquial();
            String level = String.format("%02d", player.getCritterBeltList().get(i).getLevel());
            String hpCurrent = String.format("%3d", player.getCritterBeltList().get(i).getHpCurrent());
            String hpBase = String.format("%3d", player.getCritterBeltList().get(i).getSpecies().getHpBase());
            ///////////////////////////////
            g.drawImage(speciesIcon, xOffset, yOffset, null);
            FontGrabber.renderString(g, name, (xOffset + speciesIcon.getWidth() + 5), (yOffset + 5), 20, 20);
            FontGrabber.renderString(g, level, (xOffset + 430), (yOffset + 2), 20, 20);
            FontGrabber.renderString(g, hpCurrent, (xOffset + 430), (yOffset + 32), 20, 20);
            FontGrabber.renderString(g, hpBase, (xOffset + 520), (yOffset + 32), 20, 20);
            ///////////////////////////////
            yOffset += 60;
        }

        //CURSOR
        g.drawImage(Assets.critterBallSprite, xCursor, yCursor, 20, 20, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end MenuBeltList class ****