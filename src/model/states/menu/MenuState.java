package model.states.menu;

import main.Handler;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;
import model.states.StateManager;

import java.awt.*;

public class MenuState implements IState {

    private Handler handler;
    private Player player;

    private StateMachine stateMachine;

    public MenuState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        stateMachine = new StateMachine(handler);
        initStateMachine();
    } // **** end MenuState(Handler, Player) constructor

    private void initStateMachine() {
        stateMachine.addIStateToCollection("MenuStateMenu", new MenuStateMenu(handler, player));
        stateMachine.addIStateToCollection("MenuStateCritterDex", new MenuStateCritterDex(handler, player));
        stateMachine.addIStateToCollection("MenuStateCritterBeltList", new MenuStateCritterBeltList(handler, player));
        stateMachine.addIStateToCollection("MenuStateItemList", new MenuStateItemList(handler, player));
        stateMachine.addIStateToCollection("MenuStatePlayerStats", new MenuStatePlayerStats(handler, player));
        stateMachine.addIStateToCollection("MenuStateSave", new MenuStateSave(handler, player));
        stateMachine.addIStateToCollection( "MenuStateLoad", new MenuStateLoad(handler, player));
        stateMachine.addIStateToCollection("MenuStateExit", new MenuStateExit(handler, player));

        stateMachine.push( stateMachine.getIState("MenuStateMenu"), null );
    }

    @Override
    public void tick(long timeElapsed) {
        stateMachine.getCurrentState().tick(timeElapsed);
    }

    @Override
    public void render(Graphics g) {
        stateMachine.getCurrentState().render(g);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    // GETTERS AND SETTERS

    public StateMachine getStateMachine() { return stateMachine; }

} // **** end MenuState class ****