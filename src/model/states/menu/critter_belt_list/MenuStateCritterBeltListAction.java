package model.states.menu.critter_belt_list;

import main.Handler;
import model.entities.Player;
import model.states.IState;

import java.awt.*;

public class MenuStateCritterBeltListAction implements IState {

    private enum Action {
        STAT, SWAP, CANCEL;
    }

    private Handler handler;
    private Player player;

    private Action indexAction;

    public MenuStateCritterBeltListAction(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        indexAction = Action.STAT;
    } // **** end MenuStateCritterBeltListAction(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end CritterbeltListEntryMenuOption class ****