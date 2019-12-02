package model.states.menu;

import main.Handler;
import model.entities.Player;
import model.states.IState;

import java.awt.*;

public class MenuStateCritterBeltList implements IState {

    private Handler handler;
    private Player player;

    public MenuStateCritterBeltList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStateCritterBeltList(Handler, Player) constructor ****

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

} // **** end MenuStateCritterBeltList class ****