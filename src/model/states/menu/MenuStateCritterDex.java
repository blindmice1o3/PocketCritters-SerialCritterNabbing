package model.states.menu;

import main.Handler;
import model.entities.Player;
import model.states.IState;

import java.awt.*;

public class MenuStateCritterDex implements IState {

    private Handler handler;
    private Player player;

    public MenuStateCritterDex(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStateCritterDex(Handler, Player) constructor ****

    @Override
    public void tick() {

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

} // **** end MenuStateCritterDex class ****