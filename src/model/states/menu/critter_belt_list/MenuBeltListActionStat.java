package model.states.menu.critter_belt_list;

import main.Handler;
import model.entities.Player;
import model.states.IState;

import java.awt.*;

public class MenuBeltListActionStat implements IState {

    private Handler handler;
    private Player player;
    private int indexCritterSelected;

    public MenuBeltListActionStat(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end MenuBeltListActionStat(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void enter(Object[] args) {
        if (args != null) {
            indexCritterSelected = (int)args[0];
        }
    }

    @Override
    public void exit() {

    }

} // **** end MenuBeltListActionStat class ****