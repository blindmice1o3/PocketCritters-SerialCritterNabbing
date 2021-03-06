package model.states.menu;

import main.Handler;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;
import model.states.menu.belt_list_menu.MenuBeltList;
import model.states.menu.belt_list_menu.MenuBeltListAction;
import model.states.menu.belt_list_menu.MenuBeltListActionSummary;
import model.states.menu.belt_list_menu.MenuBeltListActionSwap;

import java.awt.*;

public class MenuState implements IState {

    private Handler handler;
    private Player player;

    private StateMachine stateMachine;

    public MenuState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        initStateMachine();
    } // **** end MenuState(Handler, Player) constructor

    private void initStateMachine() {
        stateMachine = new StateMachine(handler);

        stateMachine.addIStateToCollection("MenuStateMenu", new MenuStateMenu(handler, player));
        stateMachine.addIStateToCollection("MenuStateCritterDex", new MenuStateCritterDex(handler, player));
        stateMachine.addIStateToCollection("MenuBeltList", new MenuBeltList(handler, player));
        stateMachine.addIStateToCollection("MenuBeltListAction", new MenuBeltListAction(handler, player));
        stateMachine.addIStateToCollection("MenuBeltListActionSummary", new MenuBeltListActionSummary(handler, player));
        stateMachine.addIStateToCollection("MenuBeltListActionSwap", new MenuBeltListActionSwap(handler, player));
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

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    // GETTERS AND SETTERS

    public StateMachine getStateMachine() { return stateMachine; }

} // **** end MenuState class ****