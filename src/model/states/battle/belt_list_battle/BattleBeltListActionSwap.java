package model.states.battle.belt_list_battle;

import main.Handler;
import model.entities.Player;
import model.states.IState;

import java.awt.*;

public class BattleBeltListActionSwap implements IState {

    private Handler handler;
    private Player player;

    public BattleBeltListActionSwap(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end BattleBeltListActionSwap(Handler, Player) constructor ****

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

} // **** end BattleBeltListActionSwap class ****