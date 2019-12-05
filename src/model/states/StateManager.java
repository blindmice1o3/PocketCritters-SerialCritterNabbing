package model.states;

import main.Handler;
import model.entities.Player;
import model.states.battle.*;
import model.states.computer.ComputerState;
import model.states.game.GameState;
import model.states.menu.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateManager {

    private Handler handler;
    private Map<String, IState> stateHashMap;
    //private IState currentState;
    private List<IState> stateStack;

    public StateManager(Handler handler, Player player) {
        this.handler = handler;
        //currentState =  new EmptyState();
        stateHashMap = new HashMap<String, IState>();

        stateHashMap.put("GameState", new GameState(handler));
        stateHashMap.put("BattleState", new BattleState(handler, player));
        stateHashMap.put("MenuState", new MenuState(handler, player));
        stateHashMap.put("ComputerState", new ComputerState(handler, player));

        stateStack = new ArrayList<IState>();
        stateStack.add(stateHashMap.get("GameState"));
    } // **** end StateManager(Handler, Player) constructor ****

    public IState getIState(String identifier) {
        if (stateHashMap.containsKey(identifier)) {
            return stateHashMap.get(identifier);
        }

        return null;
    }

    public void push(IState newState, Object[] args) {
        newState.enter(args);
        stateStack.add(newState);
    }

    public void pop() {
        stateStack.get( getIndexOfTop() ).exit();
        stateStack.remove( getIndexOfTop() );
    }

    public int getIndexOfTop() {
        return (stateStack.size() - 1);
    }

    //public IState get(String key) { return stateHashMap.get(key); }

    /*
    public void change(String key, Object[] args) {
        //current IState object "closes down shop for the night".
        getCurrentState().exit();

        //get future IState object from stateHashMap.
        IState nextState = stateHashMap.get(key);

        //future IState object "opens up shop for the day".
        nextState.enter(args);
        //add future IState object to top of stateStack.
        stateStack.add(nextState);
    }
    */

    public IState getCurrentState() { return stateStack.get( getIndexOfTop() ); }

} // **** end StateManager class ****