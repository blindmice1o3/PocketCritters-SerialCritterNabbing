package model.states.battle;

import main.Handler;
import model.entities.Player;
import model.states.IState;
import model.states.StateManager;

import java.awt.*;

public class BattleState implements IState {

    private Handler handler;
    private Player player;

    public BattleState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        ////////////////////
        initStateManager();
        ////////////////////
    } // **** end BattleState(Handler, Player) constructor ****

    private void initStateManager() {
        StateManager.add("BattleStateIntro", new BattleStateIntro(handler, player));
        StateManager.add("BattleStateMenu", new BattleStateMenu(handler, player));
        StateManager.add("BattleStateFight", new BattleStateFight(handler, player));
        StateManager.add("BattleStateItemList", new BattleStateItemList(handler, player));
        StateManager.add("BattleStateCritterBeltList", new BattleStateCritterBeltList(handler, player));
        StateManager.add("BattleStateRun", new BattleStateRun(handler, player));
        StateManager.add("BattleStateOutro", new BattleStateOutro(handler, player));
    }

    @Override
    public void tick() {
        ///////////////////////////////
        Object[] args = { player };
        StateManager.change("BattleStateIntro", null);
        ///////////////////////////////
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

} // **** end BattleState class ****