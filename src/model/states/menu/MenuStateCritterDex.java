package model.states.menu;

import main.Handler;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
import model.states.IState;
import model.states.StateMachine;
import model.states.game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStateCritterDex implements IState {

    private Handler handler;
    private Player player;

    public MenuStateCritterDex(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStateCritterDex(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuStateCritterDex.tick()... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuStateCritterDex).
                stateMachine.pop();
                //now MenuStateMenu.
            }
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        //TODO: currently texting if CritterNet is able to connect with GameState.critterStorageSystem.
        g.setColor(Color.GRAY);
        g.fillRect(25, 25, handler.getGame().getWidth()-50, handler.getGame().getHeight()-50);

        Critter[][] critterStorageSystem = ((GameState)handler.getStateManager().getIState("GameState")).getCritterStorageSystem();
        int indexCurrentBox = ((GameState)handler.getStateManager().getIState("GameState")).getIndexCurrentBox();
        int xOffset = 25 + 10;
        int yOffset = 25 + 10;
        g.setColor(Color.YELLOW);
        for (int i = 0; i < GameState.MAX_NUMBER_OF_CRITTERS_PER_BOX; i++) {
            if (critterStorageSystem[indexCurrentBox][i] != null) {
                String nameCritter = critterStorageSystem[indexCurrentBox][i].getNameColloquial();
                FontGrabber.renderString(g, nameCritter, xOffset, yOffset, 20, 20);
                System.out.println("found critter in: " + indexCurrentBox + ", " + i);
                yOffset += 25;
            }
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end MenuStateCritterDex class ****