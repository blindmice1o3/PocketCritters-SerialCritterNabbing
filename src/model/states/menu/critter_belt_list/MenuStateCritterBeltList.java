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

public class MenuStateCritterBeltList implements IState {

    private Handler handler;
    private Player player;

    private StateMachine stateMachine;
    private int index;
    private int xCursor, yCursor;

    public MenuStateCritterBeltList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        initStateMachine();
        index = 0;
    } // **** end MenuStateCritterBeltList(Handler, Player) constructor ****

    private void initStateMachine() {
        stateMachine = new StateMachine(handler);

        //TODO: implement IState subclasses of MenuStateCritterBeltList (e.g. BeltListCritterStats, BeltListCritterSwap, BeltListCritterCancel).
        stateMachine.addIStateToCollection("CritterBeltListEntryMenuOption", new CritterBeltListEntryMenuOption(handler));

    }

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuStateCritterBeltList.tick()... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuStateCritterBeltList).
                stateMachine.pop();
                //now MenuStateMenu.
            }
            ///////////////////////////////
        }
        //upButton
        //TODO: decrement the index, check if less than 0 (if so, set to player.critterBeltList.size()-1 and update cursor's position).
        //downButton
        //TODO: increment the index, check if greater than player.critterBeltList.size()-1 (if so, set to 0 and update cursor's position).
        //aButton
        //TODO: pushes CritterBeltListEntryMenuOption onto top of stack. Should pass in the selected Critter instance or the index of critterBeltList.
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
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end MenuStateCritterBeltList class ****