package model.states.menu;

import main.Handler;
import model.entities.nabbers.Player;
import model.states.IState;

import java.awt.*;

public class MenuStateMenu implements IState {

    private Handler handler;
    private Player player;

    public MenuStateMenu(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuStateMenu(Handler, Player) constructor ****

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

} // **** end MenuStateMenu class ****